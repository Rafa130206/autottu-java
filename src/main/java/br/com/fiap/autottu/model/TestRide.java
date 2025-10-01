package br.com.fiap.autottu.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "AUT_T_TEST_RIDE")
public class TestRide {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_test_ride")
	private Long id;

    @ManyToOne
    @JoinColumn(name = "AUT_T_MOTO_id_moto")
    private Moto moto;

	@ManyToOne
	@JoinColumn(name = "AUT_T_USUARIO_id_usuario")
	private Usuario usuario;

	@NotBlank(message = "Informe o propósito do teste")
	private String proposito;

	@FutureOrPresent(message = "A data deve ser hoje ou futura")
	private LocalDate dataDesejada;

	private LocalDate dataLimite;

	@Enumerated(EnumType.STRING)
	private TestRideStatus status = TestRideStatus.PENDENTE;

	private String observacoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Moto getMoto() {
		return moto;
	}

	public void setMoto(Moto moto) {
		this.moto = moto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public LocalDate getDataDesejada() {
		return dataDesejada;
	}

	public void setDataDesejada(LocalDate dataDesejada) {
		this.dataDesejada = dataDesejada;
	}

	public LocalDate getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}

	public TestRideStatus getStatus() {
		return status;
	}

	public void setStatus(TestRideStatus status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}
