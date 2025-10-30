package br.com.fiap.autottu.service;

import java.util.List;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.model.Manutencao;
import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.model.Usuario;

@Service
public class SpringAIService {

	@Autowired
	private OpenAiChatClient chatClient;

	/**
	 * Faz uma pergunta genérica para a IA
	 */
	public String perguntarIA(String pergunta) {
		try {
			return chatClient.call(pergunta);
		} catch (Exception e) {
			return "Erro ao comunicar com a IA: " + e.getMessage() + 
				   "\n\nVerifique se a chave API está configurada corretamente em application.properties";
		}
	}

	/**
	 * Recomenda uma moto baseada no perfil do usuário
	 */
	public String recomendarMoto(String perfil, String experiencia, String uso) {
		String prompt = String.format(
			"Com base no seguinte perfil, recomende uma moto ideal:\n" +
			"- Perfil: %s\n" +
			"- Experiência: %s\n" +
			"- Uso pretendido: %s\n\n" +
			"Forneça uma recomendação detalhada incluindo:\n" +
			"1. Tipo de moto recomendado (street, esportiva, custom, touring, etc.)\n" +
			"2. Cilindrada ideal\n" +
			"3. 2-3 modelos específicos sugeridos\n" +
			"4. Pontos importantes a considerar\n" +
			"5. Dicas para iniciantes (se aplicável)",
			perfil, experiencia, uso
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao gerar recomendação: " + e.getMessage();
		}
	}

	/**
	 * Analisa quando uma moto precisará de manutenção
	 */
	public String analisarManutencao(String modeloMoto, Integer quilometragem, 
									 String ultimaManutencao, String historicoProblemas) {
		String prompt = String.format(
			"Analise a necessidade de manutenção da seguinte moto:\n" +
			"- Modelo: %s\n" +
			"- Quilometragem atual: %d km\n" +
			"- Última manutenção: %s\n" +
			"- Histórico de problemas: %s\n\n" +
			"Forneça:\n" +
			"1. Itens que provavelmente precisam de manutenção em breve\n" +
			"2. Urgência (baixa/média/alta)\n" +
			"3. Estimativa de quilometragem para próxima revisão\n" +
			"4. Custos aproximados\n" +
			"5. Recomendações preventivas",
			modeloMoto, quilometragem, ultimaManutencao, historicoProblemas
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao analisar manutenção: " + e.getMessage();
		}
	}

	/**
	 * Auxilia na preparação de um test ride
	 */
	public String assistenteTestRide(String modeloMoto, String experienciaUsuario) {
		String prompt = String.format(
			"Um usuário (%s) vai fazer um test ride de uma %s.\n\n" +
			"Forneça:\n" +
			"1. O que observar durante o test ride\n" +
			"2. Aspectos técnicos importantes a testar\n" +
			"3. Perguntas a fazer ao vendedor\n" +
			"4. Sinais de alerta (red flags)\n" +
			"5. Checklist de segurança antes do test ride\n" +
			"6. Dicas específicas para o nível de experiência do piloto",
			experienciaUsuario, modeloMoto
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao gerar assistência para test ride: " + e.getMessage();
		}
	}

	/**
	 * Responde dúvidas sobre modelos de motos
	 */
	public String responderDuvidaMoto(String duvida) {
		String prompt = String.format(
			"Responda a seguinte dúvida sobre motos de forma clara e objetiva:\n\n%s\n\n" +
			"Forneça uma resposta técnica mas acessível, com exemplos práticos quando relevante.",
			duvida
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao responder dúvida: " + e.getMessage();
		}
	}

	/**
	 * Gera um resumo inteligente de feedbacks de test rides
	 */
	public String resumirFeedbacks(List<String> feedbacks) {
		if (feedbacks == null || feedbacks.isEmpty()) {
			return "Nenhum feedback disponível para resumir.";
		}
		
		StringBuilder feedbacksTexto = new StringBuilder();
		for (int i = 0; i < feedbacks.size(); i++) {
			feedbacksTexto.append(String.format("Feedback %d: %s\n", i + 1, feedbacks.get(i)));
		}
		
		String prompt = String.format(
			"Analise e resuma os seguintes feedbacks de test rides:\n\n%s\n\n" +
			"Forneça:\n" +
			"1. Pontos positivos mais mencionados\n" +
			"2. Pontos negativos recorrentes\n" +
			"3. Impressão geral\n" +
			"4. Recomendação final (sim/não/condicional)\n" +
			"5. Perfil de usuário ideal para esta moto",
			feedbacksTexto.toString()
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao resumir feedbacks: " + e.getMessage();
		}
	}

	/**
	 * Sugere horários menos concorridos para test rides
	 */
	public String sugerirHorarioTestRide(String diaSemana, String periodo) {
		String prompt = String.format(
			"Com base em padrões típicos de concessionárias de motos,\n" +
			"sugira os melhores horários para agendar um test ride em:\n" +
			"- Dia: %s\n" +
			"- Período: %s\n\n" +
			"Considere:\n" +
			"1. Horários com menos movimento\n" +
			"2. Disponibilidade de consultores\n" +
			"3. Condições de trânsito\n" +
			"4. Iluminação natural (se aplicável)\n" +
			"5. Dicas para melhor aproveitamento do test ride",
			diaSemana, periodo
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao sugerir horário: " + e.getMessage();
		}
	}

	/**
	 * Gera descrição automática de uma moto
	 */
	public String gerarDescricaoMoto(String modelo, String marca, String status) {
		String prompt = String.format(
			"Gere uma descrição comercial atrativa e informativa para:\n" +
			"- Marca: %s\n" +
			"- Modelo: %s\n" +
			"- Status: %s\n\n" +
			"A descrição deve conter:\n" +
			"1. Características principais\n" +
			"2. Público-alvo\n" +
			"3. Diferenciais\n" +
			"4. Aplicações ideais\n" +
			"Mantenha um tom profissional mas acessível, com no máximo 150 palavras.",
			marca, modelo, status
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao gerar descrição: " + e.getMessage();
		}
	}

	/**
	 * Compara duas ou mais motos
	 */
	public String compararMotos(List<String> modelos) {
		if (modelos == null || modelos.size() < 2) {
			return "É necessário fornecer pelo menos 2 motos para comparação.";
		}
		
		String modelosTexto = String.join(", ", modelos);
		String prompt = String.format(
			"Compare as seguintes motos: %s\n\n" +
			"Forneça uma análise comparativa incluindo:\n" +
			"1. Ficha técnica resumida de cada uma\n" +
			"2. Prós e contras de cada modelo\n" +
			"3. Comparação de desempenho\n" +
			"4. Comparação de custo-benefício\n" +
			"5. Recomendação por perfil de usuário\n" +
			"6. Veredicto final",
			modelosTexto
		);
		
		try {
			return chatClient.call(prompt);
		} catch (Exception e) {
			return "Erro ao comparar motos: " + e.getMessage();
		}
	}
}

