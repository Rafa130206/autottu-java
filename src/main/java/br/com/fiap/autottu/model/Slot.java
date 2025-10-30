package br.com.fiap.autottu.model;

import br.com.fiap.autottu.shared.converter.YesNoBooleanConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "AUT_T_SLOT")
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slot_seq")
	@SequenceGenerator(name = "slot_seq", sequenceName = "SEQ_AUT_T_SLOT", allocationSize = 1)
	@Column(name = "id_slot")
	private Integer id;

    @Convert(converter = YesNoBooleanConverter.class)
    private Boolean ocupado;

    @OneToOne
    @JoinColumn(name = "AUT_T_MOTO_id_moto", unique = true)
	private Moto moto;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getOcupado() {
		return ocupado;
	}

	public void setOcupado(Boolean ocupado) {
		this.ocupado = ocupado;
	}

	public Moto getMoto() {
		return moto;
	}

	public void setMoto(Moto moto) {
		this.moto = moto;
	}
}
