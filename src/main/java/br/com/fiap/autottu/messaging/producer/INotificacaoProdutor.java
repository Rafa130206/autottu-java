package br.com.fiap.autottu.messaging.producer;

import br.com.fiap.autottu.messaging.dto.TestRideNotificacaoDTO;

/**
 * Interface para produtores de notificação.
 * Permite ter múltiplas implementações (real e mock).
 */
public interface INotificacaoProdutor {
	
	void enviarNotificacaoTestRideAprovado(TestRideNotificacaoDTO notificacao);
	
}

