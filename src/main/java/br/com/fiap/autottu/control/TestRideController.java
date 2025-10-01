package br.com.fiap.autottu.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.model.TestRide;
import br.com.fiap.autottu.model.Usuario;
import br.com.fiap.autottu.repository.MotoRepository;
import br.com.fiap.autottu.repository.UsuarioRepository;
import br.com.fiap.autottu.service.TestRideService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/testrides")
public class TestRideController {

	@Autowired
	private TestRideService testRideService;

	@Autowired
	private MotoRepository motoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("testride/list");
		mv.addObject("pageTitle", "Solicitações de Test Ride");
		mv.addObject("testrides", testRideService.listar());
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novo() {
		return retornarFormulario(new TestRide());
	}

	@PostMapping
	public ModelAndView criar(@ModelAttribute("testride") TestRide testride,
				 BindingResult binding,
				 @RequestParam(value = "motoId", required = false) String motoId,
				 @RequestParam(value = "usuarioId", required = false) String usuarioId,
				 RedirectAttributes ra) {

		Moto moto = buscarMoto(motoId);
		if (moto == null) {
			binding.reject("moto", "Selecione uma moto válida.");
		} else {
			testride.setMoto(moto);
		}

		Usuario usuario = buscarUsuario(usuarioId);
		if (usuario == null) {
			binding.reject("usuario", "Informe o solicitante.");
		} else {
			testride.setUsuario(usuario);
		}

		if(binding.hasErrors()) {
			return retornarFormulario(testride);
		}

        try {
            testRideService.solicitar(testride);
            ra.addFlashAttribute("msgSucesso", "Solicitação registrada!");
        } catch (IllegalArgumentException ex) {
            ModelAndView mv = retornarFormulario(testride);
            mv.addObject("msgErro", ex.getMessage());
            return mv;
        }
        return new ModelAndView("redirect:/testrides");
    }

	@PostMapping("/{id}/aprovar")
	public ModelAndView aprovar(@PathVariable Long id, RedirectAttributes ra) {
		testRideService.aprovar(id);
		ra.addFlashAttribute("msgSucesso", "Solicitação aprovada!");
		return new ModelAndView("redirect:/testrides");
	}

	@PostMapping("/{id}/rejeitar")
	public ModelAndView rejeitar(@PathVariable Long id, RedirectAttributes ra) {
		testRideService.rejeitar(id);
		ra.addFlashAttribute("msgSucesso", "Solicitação rejeitada!");
		return new ModelAndView("redirect:/testrides");
	}

	@PostMapping("/{id}/concluir")
	public ModelAndView concluir(@PathVariable Long id, RedirectAttributes ra) {
		testRideService.concluir(id);
		ra.addFlashAttribute("msgSucesso", "Test ride concluído!");
		return new ModelAndView("redirect:/testrides");
	}

	@PostMapping("/{id}/delete")
	public ModelAndView excluir(@PathVariable Long id, RedirectAttributes ra) {
		testRideService.excluir(id);
		ra.addFlashAttribute("msgSucesso", "Solicitação removida!");
		return new ModelAndView("redirect:/testrides");
	}

	private ModelAndView retornarFormulario(TestRide testride) {
		ModelAndView mv = new ModelAndView("testride/form");
		mv.addObject("pageTitle", "Solicitar Test Ride");
		mv.addObject("testride", testride);
		mv.addObject("motos", motoRepository.findAll());
		mv.addObject("usuarios", usuarioRepository.findAll());
		return mv;
	}

	private Moto buscarMoto(String motoId) {
        if (motoId == null || motoId.isBlank()) {
			return null;
		}
        try {
            return motoRepository.findById(Integer.valueOf(motoId)).orElse(null);
        } catch (NumberFormatException ex) {
            return null;
        }
	}

	private Usuario buscarUsuario(String usuarioId) {
        if (usuarioId == null || usuarioId.isBlank()) {
			return null;
		}
        try {
            return usuarioRepository.findById(Integer.valueOf(usuarioId)).orElse(null);
        } catch (NumberFormatException ex) {
            return null;
        }
	}
}
