package br.com.fiap.autottu.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.autottu.model.Usuario;
import br.com.fiap.autottu.repository.UsuarioRepository;

@ControllerAdvice
public class UsuarioLogadoAdvice {

	private static final Logger log = LoggerFactory.getLogger(UsuarioLogadoAdvice.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@ModelAttribute("usuarioLogado")
	public Usuario adicionarUsuarioLogadoAoModelo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			log.debug("Contexto de segurança sem autenticação ativa.");
			return null;
		}

		String username = authentication.getName();
		if (username == null || "anonymousUser".equalsIgnoreCase(username)) {
			log.debug("Usuário anônimo ou não autenticado identificado: {}", username);
			return null;
		}

		Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
		if (usuarioOpt.isPresent()) {
			log.debug("Usuário autenticado encontrado: {}", username);
			return usuarioOpt.get();
		} else {
			log.warn("Usuário autenticado {} não encontrado no repositório.", username);
			return null;
		}
	}
}

