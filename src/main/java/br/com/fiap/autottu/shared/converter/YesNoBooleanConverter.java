package br.com.fiap.autottu.shared.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class YesNoBooleanConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if (value == null) {
			return null;
		}
		return value ? "S" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		if (value == null) {
			return null;
		}
		return "S".equalsIgnoreCase(value);
	}
}
