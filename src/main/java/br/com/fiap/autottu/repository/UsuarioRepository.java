package br.com.fiap.autottu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByUsername(String username);


	Optional<Usuario> findByEmail(String email);

	
	Optional<Usuario> findById(Integer id);

	void deleteById(Integer id);
}
