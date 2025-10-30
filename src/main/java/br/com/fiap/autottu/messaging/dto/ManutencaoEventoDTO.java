package br.com.fiap.autottu.messaging.dto;

import java.time.LocalDate;

/**
 * DTO para eventos de Manutenção transmitidos via Kafka
 */
public class ManutencaoEventoDTO {

	private Long manutencaoId;
	private String tipoEvento; // "MANUTENCAO_AGENDADA", "MANUTENCAO_CONCLUIDA", "MANUTENCAO_CANCELADA"
	private Long motoId;
	private String modeloMoto;
	private String tipo;
	private LocalDate dataAgendada;
	private String descricao;

	public ManutencaoEventoDTO() {
	}

	public ManutencaoEventoDTO(Long manutencaoId, String tipoEvento, Long motoId, 
	                           String modeloMoto, String tipo, LocalDate dataAgendada, 
	                           String descricao) {
		this.manutencaoId = manutencaoId;
		this.tipoEvento = tipoEvento;
		this.motoId = motoId;
		this.modeloMoto = modeloMoto;
		this.tipo = tipo;
		this.dataAgendada = dataAgendada;
		this.descricao = descricao;
	}

	public Long getManutencaoId() {
		return manutencaoId;
	}

	public void setManutencaoId(Long manutencaoId) {
		this.manutencaoId = manutencaoId;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Long getMotoId() {
		return motoId;
	}

	public void setMotoId(Long motoId) {
		this.motoId = motoId;
	}

	public String getModeloMoto() {
		return modeloMoto;
	}

	public void setModeloMoto(String modeloMoto) {
		this.modeloMoto = modeloMoto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataAgendada() {
		return dataAgendada;
	}

	public void setDataAgendada(LocalDate dataAgendada) {
		this.dataAgendada = dataAgendada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "ManutencaoEventoDTO{" +
				"manutencaoId=" + manutencaoId +
				", tipoEvento='" + tipoEvento + '\'' +
				", motoId=" + motoId +
				", modeloMoto='" + modeloMoto + '\'' +
				", tipo='" + tipo + '\'' +
				", dataAgendada=" + dataAgendada +
				", descricao='" + descricao + '\'' +
				'}';
	}
}

