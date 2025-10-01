package br.com.fiap.autottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.model.Slot;
import br.com.fiap.autottu.repository.MotoRepository;
import br.com.fiap.autottu.repository.SlotRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("slot/list");
        mv.addObject("pageTitle", "Slots");
        mv.addObject("slots", slotRepository.findAll());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("slot/form");
        mv.addObject("pageTitle", "Novo Slot");
        mv.addObject("slot", new Slot());
        mv.addObject("motos", buscarMotosOrdenadas());
        return mv;
    }

    @PostMapping
    @Transactional
    public ModelAndView criar(@Valid @ModelAttribute("slot") Slot slot,
            BindingResult binding, @RequestParam(value = "motoId", required = false) Integer motoId) {

        Moto motoSelecionada = buscarMotoPorId(motoId);
        if (!validarRegras(slot, motoSelecionada, binding)) {
            return retornarFormulario(slot, "Novo Slot");
        }

        if (!resolverConflitoMoto(slot, motoSelecionada, binding)) {
            return retornarFormulario(slot, "Novo Slot");
        }

        slot.setMoto(motoSelecionada);
        slot.setOcupado(Boolean.TRUE.equals(slot.getOcupado()) || motoSelecionada != null);

        slotRepository.save(slot);
        return new ModelAndView("redirect:/slots");
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Integer id) {
        Optional<Slot> slotOpt = slotRepository.findById(id);
        if (slotOpt.isEmpty()) {
            return new ModelAndView("redirect:/slots");
        }

        ModelAndView mv = new ModelAndView("slot/form");
        mv.addObject("pageTitle", "Editar Slot");
        mv.addObject("slot", slotOpt.get());
        mv.addObject("motos", buscarMotosOrdenadas());
        return mv;
    }

    @PostMapping("/{id}")
    @Transactional
    public ModelAndView atualizar(@PathVariable Integer id,
            @Valid @ModelAttribute("slot") Slot slot,
            BindingResult binding,
            @RequestParam(value = "motoId", required = false) Integer motoId) {

        Optional<Slot> slotOpt = slotRepository.findById(id);
        if (slotOpt.isPresent()) {
            Slot slotPersistido = slotOpt.get();

            Moto motoSelecionada = buscarMotoPorId(motoId);
            if (!validarRegras(slot, motoSelecionada, binding)) {
                return retornarFormulario(slot, "Editar Slot");
            }

            if (!resolverConflitoMoto(slotPersistido, motoSelecionada, binding)) {
                return retornarFormulario(slot, "Editar Slot");
            }

            slotPersistido.setOcupado(Boolean.TRUE.equals(slot.getOcupado()) || motoSelecionada != null);
            slotPersistido.setMoto(motoSelecionada);
            slotRepository.save(slotPersistido);
        }

        return new ModelAndView("redirect:/slots");
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deletar(@PathVariable Integer id) {
        slotRepository.findById(id).ifPresent(slot -> {
            Moto moto = slot.getMoto();
            if (moto != null) {
                moto.setSlot(null);
                slot.setMoto(null);
                slot.setOcupado(Boolean.FALSE);
                motoRepository.save(moto);
            }
            slotRepository.delete(slot);
        });
        return new ModelAndView("redirect:/slots");
    }

    private List<Moto> buscarMotosOrdenadas() {
        return motoRepository.findAll()
                .stream()
                .sorted((a, b) -> a.getModelo().compareToIgnoreCase(b.getModelo()))
                .toList();
    }

    private Moto buscarMotoPorId(Integer motoId) {
        if (motoId == null) {
            return null;
        }
        return motoRepository.findById(motoId).orElse(null);
    }

    private boolean resolverConflitoMoto(Slot slotDestino, Moto novaMoto, BindingResult binding) {
        if (novaMoto == null) {
            return true;
        }

        Optional<Slot> slotExistente = slotRepository.findByMoto_Id(novaMoto.getId());
        if (slotExistente.isPresent() && !slotExistente.get().getId().equals(slotDestino.getId())) {
            binding.rejectValue("moto", "slot.moto.ocupada",
                    String.format("A moto %s já está vinculada ao slot %d.",
                            novaMoto.getModelo(), slotExistente.get().getId()));
            return false;
        }

        return true;
    }

    private boolean validarRegras(Slot slot, Moto motoSelecionada, BindingResult binding) {
        boolean ocupacaoMarcada = Boolean.TRUE.equals(slot.getOcupado());

        if (!ocupacaoMarcada && motoSelecionada != null) {
            binding.rejectValue("ocupado", "slot.ocupado.incoerente", "Marque o slot como ocupado ao vincular uma moto.");
        }

        if (ocupacaoMarcada && motoSelecionada == null) {
            binding.rejectValue("moto", "slot.moto.obrigatoria", "Selecione uma moto ou desmarque o slot como ocupado.");
        }

        return !binding.hasErrors();
    }

    private ModelAndView retornarFormulario(Slot slot, String titulo) {
        ModelAndView mv = new ModelAndView("slot/form");
        mv.addObject("pageTitle", titulo);
        mv.addObject("slot", slot);
        mv.addObject("motos", buscarMotosOrdenadas());
        return mv;
    }
}

