package br.com.fiap.autottu.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "AUT_T_MANUTENCAO")
public class Manutencao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_manutencao")
	private Long id;

    @ManyToOne
    @JoinColumn(name = "AUT_T_MOTO_id_moto")
    private Moto moto;

	@NotNull(message = "Informe a quilometragem atual")
	@Column(name = "km_atual")
	private Integer quilometragemAtual;

	@NotNull(message = "Informe a data desejada")
	@FutureOrPresent(message = "A data deve ser hoje ou futura")
	@Column(name = "data_agendada")
	private LocalDate dataAgendada;

	@Column(name = "proxima_revisao_km")
	private Integer proximaRevisaoKm;

	@Column(name = "proxima_revisao_data")
	private LocalDate proximaRevisaoData;

	@Column(name = "observacoes", length = 500)
	private String observacoes;

	private String status;

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

	public Integer getQuilometragemAtual() {
		return quilometragemAtual;
	}

	public void setQuilometragemAtual(Integer quilometragemAtual) {
		this.quilometragemAtual = quilometragemAtual;
	}

	public LocalDate getDataAgendada() {
		return dataAgendada;
	}

	public void setDataAgendada(LocalDate dataAgendada) {
		this.dataAgendada = dataAgendada;
	}

	public Integer getProximaRevisaoKm() {
		return proximaRevisaoKm;
	}

	public void setProximaRevisaoKm(Integer proximaRevisaoKm) {
		this.proximaRevisaoKm = proximaRevisaoKm;
	}

	public LocalDate getProximaRevisaoData() {
		return proximaRevisaoData;
	}

	public void setProximaRevisaoData(LocalDate proximaRevisaoData) {
		this.proximaRevisaoData = proximaRevisaoData;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
