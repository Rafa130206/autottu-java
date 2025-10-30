package br.com.fiap.autottu.messaging.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.autottu.messaging.config.RabbitMQConfig;
import br.com.fiap.autottu.messaging.dto.TestRideNotificacaoDTO;

@Service
@Profile("!mock")  // Não carregar este produtor quando profile for "mock"
public class RabbitMQProdutor implements INotificacaoProdutor {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final ObjectMapper objectMapper;

	public RabbitMQProdutor() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public void enviarNotificacaoTestRideAprovado(TestRideNotificacaoDTO notificacao) {
		try {
			String mensagemJson = objectMapper.writeValueAsString(notificacao);
			rabbitTemplate.convertAndSend(
				RabbitMQConfig.ROTEADOR_TESTRIDE,
				RabbitMQConfig.CHAVE_ROTA_TESTRIDE,
				mensagemJson
			);
			System.out.println("✅ Notificação enviada via RabbitMQ: " + mensagemJson);
		} catch (JsonProcessingException e) {
			System.err.println("❌ Erro ao serializar notificação: " + e.getMessage());
		}
	}
}

