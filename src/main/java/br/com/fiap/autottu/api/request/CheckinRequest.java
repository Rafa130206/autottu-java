package br.com.fiap.autottu.api.request;

public record CheckinRequest(
		Integer motoId,
		Integer slotId,
		Integer usuarioId,
		Boolean violada,
		String observacao,
		String imagens
) {
	
	// Construtor padrão para formulário vazio
	public static CheckinRequest vazio() {
		return new CheckinRequest(null, null, null, false, null, null);
	}
}
