package br.com.fiap.autottu.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!mock")  // Não carregar esta configuração quando profile for "mock"
public class RabbitMQConfig {

	public static final String FILA_TESTRIDE = "autottu-testride-fila";
	public static final String ROTEADOR_TESTRIDE = "autottu-testride-roteador";
	public static final String CHAVE_ROTA_TESTRIDE = "autottu-testride-aprovado";

	// Criar a fila
	@Bean
	public Queue queueTestRide() {
		return new Queue(FILA_TESTRIDE, true); // true = fila durável
	}

	// Criar o roteador (exchange)
	@Bean
	public DirectExchange directExchangeTestRide() {
		return new DirectExchange(ROTEADOR_TESTRIDE);
	}

	// Associar a fila ao roteador via chave de rota
	@Bean
	public Binding bindingTestRide(Queue queueTestRide, DirectExchange directExchangeTestRide) {
		return BindingBuilder.bind(queueTestRide).to(directExchangeTestRide).with(CHAVE_ROTA_TESTRIDE);
	}
}

