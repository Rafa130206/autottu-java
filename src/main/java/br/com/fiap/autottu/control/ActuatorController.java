package br.com.fiap.autottu.control;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ActuatorController {

	private final RestTemplate template = new RestTemplate();

	@GetMapping("/telemetria")
	public ModelAndView retornarTelemetria(HttpServletRequest req) {
		// Coleta métricas do Spring Boot Actuator:
		// - health: status da aplicação
		// - system.cpu.usage: uso da CPU (%)
		// - jvm.memory.used: memória utilizada pela JVM (MB)
		// - jvm.memory.max: memória máxima alocada para JVM (GB)
		
		ModelAndView mv = new ModelAndView("telemetria");

		try {
			// Status de saúde da aplicação
			Map health = template.getForObject("http://localhost:8080/actuator/health", Map.class);
			mv.addObject("health", health);
			
			// Uso da CPU
			Map cpu_usage = template.getForObject("http://localhost:8080/actuator/metrics/system.cpu.usage", Map.class);
			
			List<Map<String,Object>> list_cpu_usage = ((List<Map<String,Object>>) cpu_usage.get("measurements"));
			Double double_cpu_usage = ((Number) list_cpu_usage.get(0).get("value")).doubleValue();		
			
			mv.addObject("cpu_usage", new DecimalFormat("#.##").format(double_cpu_usage * 100));
			
			// Memória JVM utilizada
			Map jvm_used = template.getForObject("http://localhost:8080/actuator/metrics/jvm.memory.used", Map.class);
			
			List<Map<String,Object>> list_jvm_used = ((List<Map<String,Object>>) jvm_used.get("measurements"));
			Double double_jvm_used = ((Number) list_jvm_used.get(0).get("value")).doubleValue();		
			
			// Memória JVM máxima
			Map jvm_max = template.getForObject("http://localhost:8080/actuator/metrics/jvm.memory.max", Map.class);
			
			List<Map<String,Object>> list_jvm_max = ((List<Map<String,Object>>) jvm_max.get("measurements"));
			Double double_jvm_max = ((Number) list_jvm_max.get(0).get("value")).doubleValue();		
			
			// Converte bytes para MB e GB
			mv.addObject("jvm_used", new DecimalFormat("#.##").format(double_jvm_used / (1024 * 1024)));
			mv.addObject("jvm_max", new DecimalFormat("#.##").format(double_jvm_max / (1024 * 1024 * 1024)));
			
		} catch (RestClientException e) {
			// Em caso de erro ao acessar o Actuator
			System.err.println("❌ Erro ao acessar endpoints do Actuator: " + e.getMessage());
			mv.addObject("error", "Erro ao coletar métricas do Actuator. Verifique se os endpoints estão acessíveis.");
			
			// Define valores padrão para evitar erros na view
			mv.addObject("health", Map.of("status", "UNKNOWN"));
			mv.addObject("cpu_usage", "N/A");
			mv.addObject("jvm_used", "N/A");
			mv.addObject("jvm_max", "N/A");
		}
		
		mv.addObject("uri", req.getRequestURI());

		return mv;
	}
}

