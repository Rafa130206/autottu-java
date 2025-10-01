package br.com.fiap.autottu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

	List<Slot> findByOcupadoFalse();

    Optional<Slot> findByMoto_Id(Integer idMoto);
}
