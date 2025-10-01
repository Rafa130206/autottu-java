package br.com.fiap.autottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.autottu.api.request.CheckinRequest;
import br.com.fiap.autottu.model.Checkin;
import br.com.fiap.autottu.repository.CheckinRepository;
import br.com.fiap.autottu.repository.MotoRepository;
import br.com.fiap.autottu.repository.SlotRepository;
import br.com.fiap.autottu.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/checkins")
public class CheckinController {

	@Autowired
	private CheckinRepository checkinRepository;

	@Autowired
	private MotoRepository motoRepository;

	@Autowired
	private SlotRepository slotRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("pageTitle", "Check-ins");
		model.addAttribute("checkins", checkinRepository.findAll());
		return "checkin/list";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("pageTitle", "Novo Check-in");
		model.addAttribute("checkin", new CheckinRequest(null, null, null, false, null, null));
		carregarListas(model);
		return "checkin/form";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		Optional<Checkin> op = checkinRepository.findById(id);
		if(op.isPresent()) {
			Checkin checkin = op.get();
			CheckinRequest req = new CheckinRequest(
				checkin.getMoto() != null ? checkin.getMoto().getId() : null,
				checkin.getSlot() != null ? checkin.getSlot().getId() : null,
				checkin.getUsuario() != null ? checkin.getUsuario().getId() : null,
				checkin.getViolada(),
				checkin.getObservacao(),
				checkin.getImagens()
			);
			model.addAttribute("pageTitle", "Editar Check-in");
			model.addAttribute("checkinId", checkin.getId());
			model.addAttribute("checkin", req);
			carregarListas(model);
			return "checkin/form";
		}
		ra.addFlashAttribute("msgErro", "Check-in não encontrado");
		return "redirect:/checkins";
	}

	@PostMapping
	public String criar(@Valid @ModelAttribute("checkin") CheckinRequest req,
						BindingResult binding,
						RedirectAttributes ra,
						Model model) {
		if (binding.hasErrors()) {
			model.addAttribute("pageTitle", "Novo Check-in");
			carregarListas(model);
			return "checkin/form";
		}
		Checkin checkin = new Checkin();
		preencherDados(checkin, req);
		checkin.setTimestamp(new java.util.Date());
		checkinRepository.save(checkin);
		ra.addFlashAttribute("msgSucesso", "Check-in realizado! ID: " + checkin.getId());
		return "redirect:/checkins";
	}

	@PostMapping("/{id}")
	public String atualizarOuExcluir(@PathVariable Integer id,
									 @RequestParam(value = "_method", required = false) String method,
									 @Valid @ModelAttribute("checkin") CheckinRequest req,
									 BindingResult binding,
									 RedirectAttributes ra,
									 Model model) {
		if ("put".equalsIgnoreCase(method)) {
			if (binding.hasErrors()) {
				model.addAttribute("pageTitle", "Editar Check-in");
				model.addAttribute("checkinId", id);
				carregarListas(model);
				return "checkin/form";
			}
			Optional<Checkin> op = checkinRepository.findById(id);
			if(op.isPresent()) {
				Checkin checkin = op.get();
				preencherDados(checkin, req);
				checkinRepository.save(checkin);
			}
			ra.addFlashAttribute("msgSucesso", "Check-in atualizado!");
		} else if ("delete".equalsIgnoreCase(method)) {
			checkinRepository.deleteById(id);
			ra.addFlashAttribute("msgSucesso", "Check-in excluído!");
		}
		return "redirect:/checkins";
	}

	private void carregarListas(Model model) {
		model.addAttribute("slotsLivres", slotRepository.findByOcupadoFalse());
		model.addAttribute("motosElegiveis", motoRepository.findAll());
		model.addAttribute("usuarios", usuarioRepository.findAll());
	}

	private void preencherDados(Checkin checkin, CheckinRequest req) {
		if(req.motoId() != null) {
			motoRepository.findById(req.motoId()).ifPresent(checkin::setMoto);
		}
		if(req.slotId() != null) {
			slotRepository.findById(req.slotId()).ifPresent(checkin::setSlot);
		}
		if(req.usuarioId() != null) {
			usuarioRepository.findById(req.usuarioId()).ifPresent(checkin::setUsuario);
		}
		checkin.setViolada(req.violada());
		checkin.setObservacao(req.observacao());
		checkin.setImagens(req.imagens());
	}
}
