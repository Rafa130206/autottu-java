package br.com.fiap.autottu.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.model.Usuario;
import br.com.fiap.autottu.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
		Usuario usuario = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name());
		return new User(usuario.getUsername(), usuario.getSenha(), Collections.singleton(authority));
	}
}