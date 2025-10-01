package br.com.fiap.autottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.autottu.model.Checkin;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

}
