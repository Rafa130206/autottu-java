package br.com.fiap.autottu.messaging.producer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.messaging.dto.TestRideNotificacaoDTO;

/**
 * Versão MOCK do RabbitMQProdutor para testar sem RabbitMQ rodando.
 * Para usar, adicione no application.properties: spring.profiles.active=mock
 */
@Service
@Profile("mock")
public class RabbitMQProdutorMock implements INotificacaoProdutor {

	@Override
	public void enviarNotificacaoTestRideAprovado(TestRideNotificacaoDTO notificacao) {
		System.out.println("==============================================");
		System.out.println("🧪 MODO MOCK - Simulando envio para RabbitMQ");
		System.out.println("==============================================");
		System.out.println("📩 Notificação que SERIA enviada:");
		System.out.println("🆔 Test Ride ID: " + notificacao.getTestRideId());
		System.out.println("👤 Usuário: " + notificacao.getNomeUsuario());
		System.out.println("📧 Email: " + notificacao.getEmailUsuario());
		System.out.println("🏍️ Moto: " + notificacao.getModeloMoto());
		System.out.println("📅 Data Desejada: " + notificacao.getDataDesejada());
		System.out.println("✅ Status: " + notificacao.getStatus());
		System.out.println("📝 Propósito: " + notificacao.getProposito());
		System.out.println("==============================================");
		System.out.println("✅ Mensagem 'enviada' com sucesso (modo simulação)");
		System.out.println("==============================================");
	}
}

