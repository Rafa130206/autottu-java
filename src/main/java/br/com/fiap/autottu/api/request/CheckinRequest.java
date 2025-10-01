package br.com.fiap.autottu.api.request;

import jakarta.validation.constraints.NotNull;

public record CheckinRequest(

		@NotNull(message = "O id da moto não pode ser nulo")
		Integer motoId,

		@NotNull(message = "O id do slot não pode ser nulo")
		Integer slotId,

		@NotNull(message = "O id do usuário não pode ser nulo")
		Integer usuarioId,

		Boolean violada,
		String observacao,
		String imagens

) {
}
