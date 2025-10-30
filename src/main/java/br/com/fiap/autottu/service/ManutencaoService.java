package br.com.fiap.autottu.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.messaging.dto.ManutencaoEventoDTO;
import br.com.fiap.autottu.model.Manutencao;
import br.com.fiap.autottu.repository.ManutencaoRepository;

@Service
public class ManutencaoService {

	@Autowired
	private ManutencaoRepository manutencaoRepository;

	@Autowired(required = false)
	private br.com.fiap.autottu.messaging.producer.KafkaProdutor kafkaProdutor;

	@Autowired(required = false)
	private br.com.fiap.autottu.messaging.producer.KafkaProdutorMock kafkaProdutorMock;

	public Manutencao agendar(Manutencao manutencao) {
		calcularProximaRevisao(manutencao);
		validarConflitos(manutencao);
		manutencao.setStatus(definirStatus(manutencao.getDataAgendada()));
		Manutencao salva = manutencaoRepository.save(manutencao);
		
		// Enviar evento para Kafka
		enviarEventoKafka(salva, "MANUTENCAO_AGENDADA");
		
		return salva;
	}

	public List<Manutencao> listarTodos() {
		return manutencaoRepository.findAll();
	}

	public void excluir(Long id) {
		manutencaoRepository.findById(id).ifPresent(manutencao -> {
			// Enviar evento antes de excluir
			enviarEventoKafka(manutencao, "MANUTENCAO_CANCELADA");
		});
		manutencaoRepository.deleteById(id);
	}

	private void calcularProximaRevisao(Manutencao manutencao) {
		int kmAtual = manutencao.getQuilometragemAtual();
		manutencao.setProximaRevisaoKm(kmAtual + 5000);
		manutencao.setProximaRevisaoData(manutencao.getDataAgendada().plusMonths(6));
	}

	private void validarConflitos(Manutencao manutencao) {
		List<Manutencao> existentes = manutencaoRepository
			.findByMotoIdAndDataAgendada(manutencao.getMoto().getId(), manutencao.getDataAgendada());
		if (!existentes.isEmpty()) {
			throw new IllegalArgumentException("Já existe manutenção agendada para esta moto na data selecionada.");
		}
	}

	private String definirStatus(LocalDate dataAgendada) {
		if (dataAgendada.isBefore(LocalDate.now())) {
			return "ATRASADA";
		} else if (dataAgendada.equals(LocalDate.now())) {
			return "HOJE";
		}
		return "AGENDADA";
	}

	// Envia evento de Manutenção para Kafka
	private void enviarEventoKafka(Manutencao manutencao, String tipoEvento) {
		ManutencaoEventoDTO evento = new ManutencaoEventoDTO(
			manutencao.getId(),
			tipoEvento,
			manutencao.getMoto() != null ? manutencao.getMoto().getId().longValue() : null,
			manutencao.getMoto() != null ? manutencao.getMoto().getModelo() : "N/A",
			manutencao.getStatus() != null ? manutencao.getStatus() : "N/A",
			manutencao.getDataAgendada(),
			manutencao.getObservacoes() != null ? manutencao.getObservacoes() : "Sem descrição"
		);

		// Enviar para o produtor correto (real ou mock)
		if (kafkaProdutor != null) {
			kafkaProdutor.enviarEventoManutencao(evento);
		} else if (kafkaProdutorMock != null) {
			kafkaProdutorMock.enviarEventoManutencao(evento);
		}
	}
}
