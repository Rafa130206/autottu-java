package br.com.fiap.autottu.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.autottu.service.SpringAIService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ia")
public class SpringAIController {

	@Autowired
	private SpringAIService springAIService;

	// ========== PÁGINA PRINCIPAL DO ASSISTENTE IA ==========
	
	@GetMapping
	public ModelAndView index(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("ia/index");
		mv.addObject("pageTitle", "Assistente IA - AutoTTU");
		mv.addObject("uri", req.getRequestURI());
		return mv;
	}

	@PostMapping
	public ModelAndView perguntarIA(
			HttpServletRequest req,
			@RequestParam(name = "pergunta") String pergunta) {
		
		ModelAndView mv = new ModelAndView("ia/index");
		mv.addObject("pageTitle", "Assistente IA - AutoTTU");
		mv.addObject("uri", req.getRequestURI());
		mv.addObject("pergunta", pergunta);
		
		String resposta = springAIService.perguntarIA(pergunta);
		mv.addObject("resposta", resposta);
		
		return mv;
	}
}

