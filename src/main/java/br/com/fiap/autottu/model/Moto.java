package br.com.fiap.autottu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "AUT_T_MOTO")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private Integer id;

	@NotBlank(message = "A placa é obrigatória")
	@Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres")
	private String placa;

	@NotBlank(message = "O modelo é obrigatório")
	private String modelo;

	@NotBlank(message = "A marca é obrigatória")
	private String marca;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private EnumStatusMoto status;

	@Column(name = "url_foto")
	private String urlFoto;

	/* Relacionamento 1:1 com Slot (opcional) */
	@OneToOne(mappedBy = "moto")
	private Slot slot;

	public Moto() {

	}

	public Moto(Integer id, String placa, String modelo, String marca, EnumStatusMoto status, String urlFoto) {
		super();
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.status = status;
		this.urlFoto = urlFoto;
	}

	public void transferirMoto(Moto moto) {
		setPlaca(moto.getPlaca());
		setModelo(moto.getModelo());
		setMarca(moto.getMarca());
		setStatus(moto.getStatus());
		setUrlFoto(moto.getUrlFoto());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public EnumStatusMoto getStatus() {
		return status;
	}

	public void setStatus(EnumStatusMoto status) {
		this.status = status;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}
}
