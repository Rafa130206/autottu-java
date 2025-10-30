package br.com.fiap.autottu.messaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.autottu.messaging.config.RabbitMQConfig;
import br.com.fiap.autottu.messaging.dto.TestRideNotificacaoDTO;

@Service
@Profile("!mock")  // Não carregar este consumidor quando profile for "mock"
public class RabbitMQConsumidor {

	private final ObjectMapper objectMapper;

	public RabbitMQConsumidor() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@RabbitListener(queues = RabbitMQConfig.FILA_TESTRIDE)
	public void processarNotificacaoTestRide(String mensagemJson) {
		try {
			TestRideNotificacaoDTO notificacao = objectMapper.readValue(mensagemJson, TestRideNotificacaoDTO.class);
			
			System.out.println("📩 ===== NOTIFICAÇÃO DE TEST RIDE APROVADO =====");
			System.out.println("🆔 Test Ride ID: " + notificacao.getTestRideId());
			System.out.println("👤 Usuário: " + notificacao.getNomeUsuario());
			System.out.println("📧 Email: " + notificacao.getEmailUsuario());
			System.out.println("🏍️ Moto: " + notificacao.getModeloMoto());
			System.out.println("📅 Data Desejada: " + notificacao.getDataDesejada());
			System.out.println("✅ Status: " + notificacao.getStatus());
			System.out.println("📝 Propósito: " + notificacao.getProposito());
			System.out.println("================================================");
			
			// Aqui você pode adicionar lógicas adicionais como:
			// - Enviar email para o usuário
			// - Enviar SMS
			// - Notificar em um dashboard em tempo real
			// - Registrar em sistema de auditoria
			
		} catch (Exception e) {
			System.err.println("❌ Erro ao processar notificação: " + e.getMessage());
		}
	}
}

