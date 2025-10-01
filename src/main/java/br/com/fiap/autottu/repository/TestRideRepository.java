package br.com.fiap.autottu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.TestRide;

@Repository
public interface TestRideRepository extends JpaRepository<TestRide, Long> {

	List<TestRide> findByMotoIdAndDataDesejada(Integer motoId, LocalDate dataDesejada);
}
