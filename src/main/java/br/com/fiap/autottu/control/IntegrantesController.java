package br.com.fiap.autottu.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IntegrantesController {

	// PÁGINA: GET /integrantes -> "integrantes"
	@GetMapping("/integrantes")
	public ModelAndView integrantes() {
		ModelAndView mv = new ModelAndView("integrantes");
		mv.addObject("pageTitle", "Integrantes");
		return mv;
	}
}
