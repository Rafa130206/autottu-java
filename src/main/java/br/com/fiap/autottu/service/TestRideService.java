package br.com.fiap.autottu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.autottu.messaging.dto.TestRideNotificacaoDTO;
import br.com.fiap.autottu.messaging.producer.INotificacaoProdutor;
import br.com.fiap.autottu.model.TestRide;
import br.com.fiap.autottu.model.TestRideStatus;
import br.com.fiap.autottu.repository.CheckinRepository;
import br.com.fiap.autottu.repository.ManutencaoRepository;
import br.com.fiap.autottu.repository.TestRideRepository;

@Service
public class TestRideService {

	@Autowired
	private TestRideRepository testRideRepository;

	@Autowired
	private ManutencaoRepository manutencaoRepository;

	@Autowired
	private CheckinRepository checkinRepository;

	@Autowired
	private INotificacaoProdutor notificacaoProdutor;

	public List<TestRide> listar() {
		List<TestRide> rides = testRideRepository.findAll();
		rides.forEach(this::atualizarStatusSeExpirado);
		return rides;
	}

	@Transactional
	public TestRide solicitar(TestRide testRide) {
		validarDisponibilidade(testRide);
		testRide.setStatus(TestRideStatus.PENDENTE);
		testRide.setDataLimite(testRide.getDataDesejada().plusDays(7));
		return testRideRepository.save(testRide);
	}

	@Transactional
	public void aprovar(Long id) {
		Optional<TestRide> opt = testRideRepository.findById(id);
		opt.ifPresent(ride -> {
			ride.setStatus(TestRideStatus.APROVADO);
			testRideRepository.save(ride);
			
			// Enviar notificação via RabbitMQ
			TestRideNotificacaoDTO notificacao = new TestRideNotificacaoDTO(
				ride.getId(),
				ride.getUsuario().getNome(),
				ride.getUsuario().getEmail(),
				ride.getMoto().getModelo(),
				ride.getDataDesejada(),
				TestRideStatus.APROVADO.toString(),
				ride.getProposito()
			);
			
			notificacaoProdutor.enviarNotificacaoTestRideAprovado(notificacao);
		});
	}

	@Transactional
	public void rejeitar(Long id) {
		alterarStatus(id, TestRideStatus.REJEITADO);
	}

	@Transactional
	public void concluir(Long id) {
		alterarStatus(id, TestRideStatus.CONCLUIDO);
	}

	@Transactional
	public void excluir(Long id) {
		testRideRepository.deleteById(id);
	}

	private void alterarStatus(Long id, TestRideStatus status) {
		Optional<TestRide> opt = testRideRepository.findById(id);
		opt.ifPresent(ride -> {
			ride.setStatus(status);
			testRideRepository.save(ride);
		});
	}

	private void validarDisponibilidade(TestRide testRide) {
		LocalDate data = testRide.getDataDesejada();

		boolean manutencao = !manutencaoRepository
			.findByMotoIdAndDataAgendada(testRide.getMoto().getId(), data)
			.isEmpty();

		boolean checkin = checkinRepository.findAll().stream()
			.anyMatch(check -> check.getMoto().getId().equals(testRide.getMoto().getId())
				&& check.getTimestamp() != null
				&& check.getTimestamp().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(data));

		boolean ride = testRideRepository
			.findByMotoIdAndDataDesejada(testRide.getMoto().getId(), data)
			.stream()
			.anyMatch(existing -> !existing.getStatus().equals(TestRideStatus.REJEITADO));

		if (manutencao || checkin || ride) {
			throw new IllegalArgumentException("Moto indisponível para teste na data selecionada.");
		}
	}

	private void atualizarStatusSeExpirado(TestRide ride) {
		if (ride.getStatus() == TestRideStatus.PENDENTE && ride.getDataLimite() != null
			&& ride.getDataLimite().isBefore(LocalDate.now())) {
			ride.setStatus(TestRideStatus.EXPIRADO);
			testRideRepository.save(ride);
		}
	}
}
