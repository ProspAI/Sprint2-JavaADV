package br.com.fiap.jadv.sprint2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.jadv.sprint2.entity.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
