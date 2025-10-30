package br.com.fiap.autottu.messaging.producer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.messaging.dto.CheckinEventoDTO;
import br.com.fiap.autottu.messaging.dto.ManutencaoEventoDTO;

/**
 * Versão MOCK do KafkaProdutor para testar sem Kafka rodando
 */
@Service
@Profile("mock")
public class KafkaProdutorMock {

	public void enviarEventoCheckin(CheckinEventoDTO evento) {
		System.out.println("==============================================");
		System.out.println("🧪 MODO MOCK - Simulando envio Kafka Check-in");
		System.out.println("==============================================");
		System.out.println("📡 Evento: " + evento.getTipoEvento());
		System.out.println("🆔 Check-in ID: " + evento.getCheckinId());
		System.out.println("🏍️ Moto: " + evento.getModeloMoto());
		System.out.println("👤 Usuário: " + evento.getNomeUsuario());
		System.out.println("📍 Localização: " + evento.getLocalizacao());
		System.out.println("⏰ Timestamp: " + evento.getTimestamp());
		System.out.println("==============================================");
	}

	public void enviarEventoManutencao(ManutencaoEventoDTO evento) {
		System.out.println("==============================================");
		System.out.println("🧪 MODO MOCK - Simulando envio Kafka Manutenção");
		System.out.println("==============================================");
		System.out.println("📡 Evento: " + evento.getTipoEvento());
		System.out.println("🆔 Manutenção ID: " + evento.getManutencaoId());
		System.out.println("🏍️ Moto: " + evento.getModeloMoto());
		System.out.println("🔧 Tipo: " + evento.getTipo());
		System.out.println("📅 Data: " + evento.getDataAgendada());
		System.out.println("==============================================");
	}

	public void enviarEventoAuditoria(String acao, String entidade, String usuario, String detalhes) {
		System.out.println("==============================================");
		System.out.println("🧪 MODO MOCK - Simulando envio Kafka Auditoria");
		System.out.println("==============================================");
		System.out.println("🔍 Ação: " + acao);
		System.out.println("📦 Entidade: " + entidade);
		System.out.println("👤 Usuário: " + usuario);
		System.out.println("📝 Detalhes: " + detalhes);
		System.out.println("==============================================");
	}
}

