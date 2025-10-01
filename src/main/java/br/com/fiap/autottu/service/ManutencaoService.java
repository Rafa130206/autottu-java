package br.com.fiap.autottu.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.model.Manutencao;
import br.com.fiap.autottu.repository.ManutencaoRepository;

@Service
public class ManutencaoService {

	@Autowired
	private ManutencaoRepository manutencaoRepository;

	public Manutencao agendar(Manutencao manutencao) {
		calcularProximaRevisao(manutencao);
		validarConflitos(manutencao);
		manutencao.setStatus(definirStatus(manutencao.getDataAgendada()));
		return manutencaoRepository.save(manutencao);
	}

	public List<Manutencao> listarTodos() {
		return manutencaoRepository.findAll();
	}

	public void excluir(Long id) {
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
}
