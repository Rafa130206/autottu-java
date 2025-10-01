package br.com.fiap.autottu.model;

import java.util.Date;

import br.com.fiap.autottu.shared.converter.YesNoBooleanConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "AUT_T_CHECKIN")
public class Checkin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_checkin")
	private Integer id;

	@Convert(converter = YesNoBooleanConverter.class)
	private Boolean violada;

	private String observacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String imagens;

	@ManyToOne
	@JoinColumn(name = "AUT_T_MOTO_id_moto")
	private Moto moto;

	@ManyToOne
	@JoinColumn(name = "AUT_T_USUARIO_id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "AUT_T_SLOT_id_slot")
	private Slot slot;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getViolada() {
		return violada;
	}

	public void setViolada(Boolean violada) {
		this.violada = violada;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getImagens() {
		return imagens;
	}

	public void setImagens(String imagens) {
		this.imagens = imagens;
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

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}
}
