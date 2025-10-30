package br.com.fiap.autottu.messaging.dto;

import java.time.LocalDate;

public class TestRideNotificacaoDTO {

	private Long testRideId;
	private String nomeUsuario;
	private String emailUsuario;
	private String modeloMoto;
	private LocalDate dataDesejada;
	private String status;
	private String proposito;

	public TestRideNotificacaoDTO() {
	}

	public TestRideNotificacaoDTO(Long testRideId, String nomeUsuario, String emailUsuario, 
	                               String modeloMoto, LocalDate dataDesejada, String status, String proposito) {
		this.testRideId = testRideId;
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
		this.modeloMoto = modeloMoto;
		this.dataDesejada = dataDesejada;
		this.status = status;
		this.proposito = proposito;
	}

	public Long getTestRideId() {
		return testRideId;
	}

	public void setTestRideId(Long testRideId) {
		this.testRideId = testRideId;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getModeloMoto() {
		return modeloMoto;
	}

	public void setModeloMoto(String modeloMoto) {
		this.modeloMoto = modeloMoto;
	}

	public LocalDate getDataDesejada() {
		return dataDesejada;
	}

	public void setDataDesejada(LocalDate dataDesejada) {
		this.dataDesejada = dataDesejada;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	@Override
	public String toString() {
		return "TestRideNotificacaoDTO{" +
				"testRideId=" + testRideId +
				", nomeUsuario='" + nomeUsuario + '\'' +
				", emailUsuario='" + emailUsuario + '\'' +
				", modeloMoto='" + modeloMoto + '\'' +
				", dataDesejada=" + dataDesejada +
				", status='" + status + '\'' +
				", proposito='" + proposito + '\'' +
				'}';
	}
}

