package br.com.fiap.autottu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.Manutencao;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

	List<Manutencao> findByMotoIdAndDataAgendada(Integer motoId, LocalDate dataAgendada);
}
