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

import br.com.fiap.autottu.model.Manutencao;
import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.repository.MotoRepository;
import br.com.fiap.autottu.service.ManutencaoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manutencoes")
public class ManutencaoController {

	@Autowired
	private ManutencaoService manutencaoService;

	@Autowired
	private MotoRepository motoRepository;

	// LISTA: GET /manutencoes -> "manutencao/list"
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("manutencao/list");
		mv.addObject("pageTitle", "Manutenções");
		mv.addObject("manutencoes", manutencaoService.listarTodos());
		return mv;
	}

	// NOVO: GET /manutencoes/novo -> "manutencao/form"
	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("manutencao/form");
		mv.addObject("pageTitle", "Agendar Manutenção");
		mv.addObject("manutencao", new Manutencao());
		mv.addObject("motos", motoRepository.findAll());
		return mv;
	}

	// CRIAR: POST /manutencoes -> redireciona para /manutencoes
    @PostMapping
    public ModelAndView criar(@Valid @ModelAttribute("manutencao") Manutencao manutencao,
                     BindingResult binding,
                     @RequestParam(value = "motoId", required = false) Integer motoId,
                     RedirectAttributes ra) {

        Moto motoSelecionada = buscarMoto(motoId);
        if (motoSelecionada == null) {
            binding.rejectValue("moto", "manutencao.moto.obrigatoria", "Selecione uma moto");
        } else {
            manutencao.setMoto(motoSelecionada);
        }

        if(binding.hasErrors()) {
            return retornarFormulario(manutencao);
        }

        try {
            manutencaoService.agendar(manutencao);
            ra.addFlashAttribute("msgSucesso", "Manutenção agendada!");
        } catch (IllegalArgumentException ex) {
            ModelAndView mv = retornarFormulario(manutencao);
            mv.addObject("msgErro", ex.getMessage());
            return mv;
        }
        return new ModelAndView("redirect:/manutencoes");
    }

	// EXCLUIR: POST /manutencoes/{id}/delete -> redireciona para /manutencoes
    @PostMapping("/{id}/delete")
    public ModelAndView excluir(@PathVariable Long id, RedirectAttributes ra) {
        manutencaoService.excluir(id);
        ra.addFlashAttribute("msgSucesso", "Manutenção removida!");
        return new ModelAndView("redirect:/manutencoes");
    }

    private ModelAndView retornarFormulario(Manutencao manutencao) {
        ModelAndView mv = new ModelAndView("manutencao/form");
        mv.addObject("pageTitle", "Agendar Manutenção");
        mv.addObject("manutencao", manutencao);
        mv.addObject("motos", motoRepository.findAll());
        return mv;
    }

    private Moto buscarMoto(Integer motoId) {
        if (motoId == null) {
            return null;
        }
        return motoRepository.findById(motoId).orElse(null);
    }
}
