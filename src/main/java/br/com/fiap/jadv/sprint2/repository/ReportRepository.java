package br.com.fiap.jadv.sprint2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.jadv.sprint2.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
