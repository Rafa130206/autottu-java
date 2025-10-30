package br.com.fiap.autottu.model;

public enum EnumStatusMoto {

	OK("ok"), VIOLADA("violada");

	private final String descricao;

	private EnumStatusMoto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
