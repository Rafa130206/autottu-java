package br.com.fiap.autottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.autottu.model.EnumPerfil;
import br.com.fiap.autottu.model.Usuario;
import br.com.fiap.autottu.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// HOME: GET / -> home(index)
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("pageTitle", "AutoTTU");
		return mv;
	}

	// LOGIN: GET /login -> "login" para login de usuário
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("pageTitle", "Entrar");
		return mv;
	}

	// REGISTRAR: GET /registrar -> "usuario/registrar" para cadastro de usuário
	@GetMapping("/registrar")
	public ModelAndView registrar() {
		return retornarFormulario(new Usuario(), "Registrar Usuário");
	}

	// CRIAR: POST /registrar -> redireciona para /login quando finalizar o cadastro
	@PostMapping("/registrar")
	public ModelAndView criar(@Valid @ModelAttribute("usuario") Usuario usuario,
				 BindingResult binding,
				 RedirectAttributes ra) {
		if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
				binding.rejectValue("username", "username.duplicado", "Já existe um usuário com esse Username.");
				return retornarFormulario(usuario, "Registrar Usuário");
			}

		if(binding.hasErrors()) {
			return retornarFormulario(usuario, "Registrar Usuário");
		}

		if(usuario.getPerfil() == null) {
			usuario.setPerfil(EnumPerfil.USUARIO);
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		ra.addFlashAttribute("msgSucesso", "Conta criada com sucesso!");
		return new ModelAndView("redirect:/login");
	}

	// PERFIL: GET /perfil -> "usuario/perfil"
    @GetMapping("/perfil")
    public ModelAndView visualizarPerfil(RedirectAttributes ra) {
        Usuario logado = obterUsuarioLogado();
        if (logado == null) {
            ra.addFlashAttribute("msgErro", "Sessão expirada. Faça login novamente.");
            return new ModelAndView("redirect:/login");
        }
        return retornarFormularioPerfil(logado, "Atualizar Perfil");
    }

	// ATUALIZAR PERFIL: POST /perfil -> redireciona para /perfil
    @PostMapping("/perfil")
    public ModelAndView atualizarPerfil(@Valid @ModelAttribute("usuario") Usuario usuario,
                        BindingResult binding,
                        RedirectAttributes ra) {

        Usuario logado = obterUsuarioLogado();
        if (logado == null) {
            ra.addFlashAttribute("msgErro", "Sessão expirada. Faça login novamente.");
            return new ModelAndView("redirect:/login");
        }

        usuarioRepository.findByUsername(usuario.getUsername()).ifPresent(existing -> {
            if (!existing.getId().equals(logado.getId())) {
                binding.rejectValue("username", "username.duplicado", "Já existe um usuário com esse Username.");
            }
        });

        if(binding.hasErrors()) {
            return retornarFormularioPerfil(usuario, "Atualizar Perfil");
        }

        logado.setNome(usuario.getNome());
        logado.setEmail(usuario.getEmail());
        logado.setTelefone(usuario.getTelefone());
        logado.setUsername(usuario.getUsername());

        if(usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            logado.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuarioRepository.save(logado);
        ra.addFlashAttribute("msgSucesso", "Perfil atualizado!");
        return new ModelAndView("redirect:/perfil");
    }

	// MENU: GET /menu -> "menu"
	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("menu");
		mv.addObject("pageTitle", "Menu");
		return mv;
	}

	private ModelAndView retornarFormulario(Usuario usuario, String titulo) {
		ModelAndView mv = new ModelAndView("usuario/registrar");
		mv.addObject("pageTitle", titulo);
		mv.addObject("usuario", usuario);
		return mv;
	}

    private ModelAndView retornarFormularioPerfil(Usuario usuario, String titulo) {
        ModelAndView mv = new ModelAndView("usuario/perfil");
        mv.addObject("pageTitle", titulo);
        mv.addObject("usuario", usuario);
        return mv;
    }
//Faz a validação do usuário logado
    private Usuario obterUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String username = auth.getName();
        if (username == null || "anonymousUser".equalsIgnoreCase(username)) {
            return null;
        }
        return usuarioRepository.findByUsername(username).orElse(null);
    }
}