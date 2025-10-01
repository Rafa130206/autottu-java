package br.com.fiap.autottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer>{

}
