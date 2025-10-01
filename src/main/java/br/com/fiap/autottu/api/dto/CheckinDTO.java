package br.com.fiap.autottu.api.dto;

import java.util.Date;

import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.model.Slot;
import br.com.fiap.autottu.model.Usuario;

public record CheckinDTO(
		Integer id,
		Moto moto,
		Slot slot,
		Usuario usuario,
		Date timestamp,
		Boolean violada,
		String observacao,
		String imagens
) {
}
