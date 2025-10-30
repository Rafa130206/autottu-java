package br.com.fiap.autottu.messaging.dto;

import java.util.Date;

/**
 * DTO para eventos de Check-in transmitidos via Kafka
 */
public class CheckinEventoDTO {

	private Long checkinId;
	private String tipoEvento; // "CHECKIN_CRIADO", "CHECKIN_ATUALIZADO", "CHECKIN_REMOVIDO"
	private Long motoId;
	private String modeloMoto;
	private Long usuarioId;
	private String nomeUsuario;
	private String localizacao;
	private Date timestamp;
	private String observacoes;

	public CheckinEventoDTO() {
	}

	public CheckinEventoDTO(Long checkinId, String tipoEvento, Long motoId, String modeloMoto, 
	                         Long usuarioId, String nomeUsuario, String localizacao, 
	                         Date timestamp, String observacoes) {
		this.checkinId = checkinId;
		this.tipoEvento = tipoEvento;
		this.motoId = motoId;
		this.modeloMoto = modeloMoto;
		this.usuarioId = usuarioId;
		this.nomeUsuario = nomeUsuario;
		this.localizacao = localizacao;
		this.timestamp = timestamp;
		this.observacoes = observacoes;
	}

	public Long getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Long checkinId) {
		this.checkinId = checkinId;
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		return "CheckinEventoDTO{" +
				"checkinId=" + checkinId +
				", tipoEvento='" + tipoEvento + '\'' +
				", motoId=" + motoId +
				", modeloMoto='" + modeloMoto + '\'' +
				", usuarioId=" + usuarioId +
				", nomeUsuario='" + nomeUsuario + '\'' +
				", localizacao='" + localizacao + '\'' +
				", timestamp=" + timestamp +
				", observacoes='" + observacoes + '\'' +
				'}';
	}
}

