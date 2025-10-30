package br.com.fiap.autottu.messaging.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.autottu.messaging.config.KafkaConfig;
import br.com.fiap.autottu.messaging.dto.CheckinEventoDTO;
import br.com.fiap.autottu.messaging.dto.ManutencaoEventoDTO;

@Service
@Profile("!mock")  // Não carregar quando profile for "mock"
public class KafkaProdutor {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private final ObjectMapper objectMapper;

	public KafkaProdutor() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	/**
	 * Envia evento de Check-in para o Kafka
	 */
	public void enviarEventoCheckin(CheckinEventoDTO evento) {
		try {
			String mensagemJson = objectMapper.writeValueAsString(evento);
			kafkaTemplate.send(KafkaConfig.TOPICO_CHECKIN, mensagemJson);
			System.out.println("📡 [KAFKA] Evento Check-in enviado: " + evento.getTipoEvento());
		} catch (JsonProcessingException e) {
			System.err.println("❌ [KAFKA] Erro ao serializar evento de Check-in: " + e.getMessage());
		}
	}

	/**
	 * Envia evento de Manutenção para o Kafka
	 */
	public void enviarEventoManutencao(ManutencaoEventoDTO evento) {
		try {
			String mensagemJson = objectMapper.writeValueAsString(evento);
			kafkaTemplate.send(KafkaConfig.TOPICO_MANUTENCAO, mensagemJson);
			System.out.println("📡 [KAFKA] Evento Manutenção enviado: " + evento.getTipoEvento());
		} catch (JsonProcessingException e) {
			System.err.println("❌ [KAFKA] Erro ao serializar evento de Manutenção: " + e.getMessage());
		}
	}

	/**
	 * Envia evento genérico de auditoria
	 */
	public void enviarEventoAuditoria(String acao, String entidade, String usuario, String detalhes) {
		String mensagem = String.format(
			"{\"acao\":\"%s\",\"entidade\":\"%s\",\"usuario\":\"%s\",\"detalhes\":\"%s\",\"timestamp\":\"%s\"}",
			acao, entidade, usuario, detalhes, java.time.LocalDateTime.now()
		);
		kafkaTemplate.send(KafkaConfig.TOPICO_AUDITORIA, mensagem);
		System.out.println("📡 [KAFKA] Evento Auditoria enviado: " + acao + " - " + entidade);
	}
}

