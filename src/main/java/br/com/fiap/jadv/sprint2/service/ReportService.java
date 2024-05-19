package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.ReportController;
import br.com.fiap.jadv.sprint2.dto.request.ReportRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.ReportResponseDTO;
import br.com.fiap.jadv.sprint2.entity.Report;
import br.com.fiap.jadv.sprint2.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportResponseDTO> obterTodosReports() {
        return reportRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<ReportResponseDTO> obterReportPorId(Long id) {
        return reportRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public ReportResponseDTO criarReport(ReportRequestDTO reportRequestDTO) {
        Report report = new Report();
        report.setTitulo(reportRequestDTO.getTitulo());
        report.setDescricao(reportRequestDTO.getDescricao());
        return converterParaResponseDTO(reportRepository.save(report));
    }

    public Optional<ReportResponseDTO> atualizarReport(Long id, ReportRequestDTO reportRequestDTO) {
        return reportRepository.findById(id).map(report -> {
            report.setTitulo(reportRequestDTO.getTitulo());
            report.setDescricao(reportRequestDTO.getDescricao());
            return converterParaResponseDTO(reportRepository.save(report));
        });
    }

    public void deletarReport(Long id) {
        reportRepository.deleteById(id);
    }

    private ReportResponseDTO converterParaResponseDTO(Report report) {
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        reportResponseDTO.setId(report.getId());
        reportResponseDTO.setTitulo(report.getTitulo());
        reportResponseDTO.setDescricao(report.getDescricao());

        // Adicionando links HATEOAS
        reportResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterReportPorId(report.getId())).withSelfRel());
        reportResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withRel("todos-reports"));

        return reportResponseDTO;
    }
}
