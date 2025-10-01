package br.com.fiap.autottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.autottu.model.EnumStatusMoto;
import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.repository.MotoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/motos")
public class MotoController {

	@Autowired
	private MotoRepository repM;
	
	// LISTA: GET /motos -> "moto/list"
	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("moto/list");
		mv.addObject("pageTitle", "Motos");
		mv.addObject("motos", repM.findAll());
		return mv;
	}

	// NOVO: GET /motos/novo -> "moto/form"
	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("moto/form");
		mv.addObject("pageTitle", "Nova Moto");
		mv.addObject("moto", new Moto());
		mv.addObject("lista_status", EnumStatusMoto.values());
		return mv;
	}

	// EDITAR: GET /motos/{id}/editar -> "moto/form"
	@GetMapping("/{id}/editar")
	public ModelAndView editar(@PathVariable Integer id) {
		Optional<Moto> op = repM.findById(id);
		
		if(op.isPresent()) {
			ModelAndView mv = new ModelAndView("moto/form");
			mv.addObject("pageTitle", "Editar Moto");
			mv.addObject("moto", op.get());
			mv.addObject("lista_status", EnumStatusMoto.values());
			
			return mv;
		} else {
			return new ModelAndView("redirect:/motos");
		}
	}

	// CRIAR: POST /motos (form)
	@PostMapping
	public ModelAndView criar(@Valid @ModelAttribute("moto") Moto moto,
						BindingResult binding) {
		if (binding.hasErrors()) {
			ModelAndView mv = new ModelAndView("moto/form");
			mv.addObject("pageTitle", "Nova Moto");
			mv.addObject("moto", moto);
			mv.addObject("lista_status", EnumStatusMoto.values());
			return mv;
		}
		repM.save(moto);
		return new ModelAndView("redirect:/motos");
	}

	// ATUALIZAR (via _method=put): POST /motos/{id}
	@PostMapping("/{id}")
	public ModelAndView atualizar(@PathVariable Integer id,
									 @Valid @ModelAttribute("moto") Moto moto,
									 BindingResult binding) {
		if (binding.hasErrors()) {
			ModelAndView mv = new ModelAndView("moto/form");
			mv.addObject("pageTitle", "Editar Moto");
			mv.addObject("moto", moto);
			mv.addObject("lista_status", EnumStatusMoto.values());
			return mv;
		}
		Optional<Moto> op = repM.findById(id);
		if(op.isPresent()) {
			Moto motoAtualizada = op.get();
			motoAtualizada.transferirMoto(moto);
			repM.save(motoAtualizada);
		}
		return new ModelAndView("redirect:/motos");
	}

	// EXCLUIR: POST /motos/{id}/delete
	@PostMapping("/{id}/delete")
	public ModelAndView excluir(@PathVariable Integer id) {
		repM.deleteById(id);
		return new ModelAndView("redirect:/motos");
	}

	// DETALHES: GET /motos/{id} -> "moto/detalhes"
	@GetMapping("/{id}")
	public ModelAndView exibirDetalhes(@PathVariable Integer id) {
		Optional<Moto> op = repM.findById(id);
		
		if(op.isPresent()) {
			ModelAndView mv = new ModelAndView("moto/detalhes");
			mv.addObject("moto", op.get());
			
			return mv;
		} else {
			return new ModelAndView("redirect:/motos");
		}
	}
}