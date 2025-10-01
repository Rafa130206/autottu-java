package br.com.fiap.autottu.model;

public enum EnumStatusMoto {

	DISPONIVEL("Disponível"), OCUPADA("Ocupada"), 
	MANUTENCAO("Manutenção"), INDISPONIVEL("Indisponível");

	private final String descricao;

	private EnumStatusMoto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
