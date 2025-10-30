package br.com.fiap.autottu.messaging.consumer;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.autottu.messaging.config.KafkaConfig;
import br.com.fiap.autottu.messaging.dto.CheckinEventoDTO;
import br.com.fiap.autottu.messaging.dto.ManutencaoEventoDTO;

@Component
@Profile("!mock")  // Não carregar quando profile for "mock"
public class KafkaConsumidor {

	private final ObjectMapper objectMapper;

	public KafkaConsumidor() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	/**
	 * Consome eventos de Check-in do Kafka
	 */
	@KafkaListener(topics = KafkaConfig.TOPICO_CHECKIN, groupId = "autottu-grupo")
	public void consumirEventoCheckin(String mensagemJson) {
		try {
			CheckinEventoDTO evento = objectMapper.readValue(mensagemJson, CheckinEventoDTO.class);
			
			System.out.println("🎯 ======= EVENTO KAFKA: CHECK-IN =======");
			System.out.println("📡 Tipo: " + evento.getTipoEvento());
			System.out.println("🆔 Check-in ID: " + evento.getCheckinId());
			System.out.println("🏍️ Moto: " + evento.getModeloMoto() + " (ID: " + evento.getMotoId() + ")");
			System.out.println("👤 Usuário: " + evento.getNomeUsuario() + " (ID: " + evento.getUsuarioId() + ")");
			System.out.println("📍 Localização: " + evento.getLocalizacao());
			System.out.println("⏰ Timestamp: " + evento.getTimestamp());
			if (evento.getObservacoes() != null && !evento.getObservacoes().isEmpty()) {
				System.out.println("📝 Observações: " + evento.getObservacoes());
			}
			System.out.println("=========================================");
			
			// Aqui você pode adicionar lógicas adicionais:
			// - Atualizar dashboard em tempo real
			// - Enviar alertas se houver problemas
			// - Registrar analytics
			// - Atualizar cache
			
		} catch (Exception e) {
			System.err.println("❌ [KAFKA] Erro ao processar evento de Check-in: " + e.getMessage());
		}
	}

	/**
	 * Consome eventos de Manutenção do Kafka
	 */
	@KafkaListener(topics = KafkaConfig.TOPICO_MANUTENCAO, groupId = "autottu-grupo")
	public void consumirEventoManutencao(String mensagemJson) {
		try {
			ManutencaoEventoDTO evento = objectMapper.readValue(mensagemJson, ManutencaoEventoDTO.class);
			
			System.out.println("🎯 ====== EVENTO KAFKA: MANUTENÇÃO ======");
			System.out.println("📡 Tipo: " + evento.getTipoEvento());
			System.out.println("🆔 Manutenção ID: " + evento.getManutencaoId());
			System.out.println("🏍️ Moto: " + evento.getModeloMoto() + " (ID: " + evento.getMotoId() + ")");
			System.out.println("🔧 Tipo de Manutenção: " + evento.getTipo());
			System.out.println("📅 Data Agendada: " + evento.getDataAgendada());
			System.out.println("📝 Descrição: " + evento.getDescricao());
			System.out.println("=========================================");
			
			// Aqui você pode adicionar lógicas adicionais:
			// - Notificar equipe de manutenção
			// - Bloquear moto no sistema
			// - Atualizar calendário de manutenções
			
		} catch (Exception e) {
			System.err.println("❌ [KAFKA] Erro ao processar evento de Manutenção: " + e.getMessage());
		}
	}

	/**
	 * Consome eventos de Auditoria do Kafka
	 */
	@KafkaListener(topics = KafkaConfig.TOPICO_AUDITORIA, groupId = "autottu-grupo")
	public void consumirEventoAuditoria(String mensagemJson) {
		System.out.println("🎯 ====== EVENTO KAFKA: AUDITORIA =======");
		System.out.println("📋 " + mensagemJson);
		System.out.println("=========================================");
		
		// Aqui você pode:
		// - Salvar em banco de auditoria
		// - Enviar para sistema de logs centralizado
		// - Análise de segurança
	}
}

