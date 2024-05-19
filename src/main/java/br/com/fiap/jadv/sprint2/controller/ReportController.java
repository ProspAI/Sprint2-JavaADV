package br.com.fiap.jadv.sprint2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.jadv.sprint2.dto.request.ReportRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.ReportResponseDTO;
import br.com.fiap.jadv.sprint2.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReportResponseDTO>>> obterTodosReports() {
        List<EntityModel<ReportResponseDTO>> reports = reportService.obterTodosReports().stream()
                .map(report -> EntityModel.of(report,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterReportPorId(report.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withRel("todos-reports")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(reports,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReportResponseDTO>> obterReportPorId(@PathVariable Long id) {
        Optional<ReportResponseDTO> report = reportService.obterReportPorId(id);
        return report.map(r -> ResponseEntity.ok(EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterReportPorId(r.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withRel("todos-reports"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ReportResponseDTO>> criarReport(@Validated @RequestBody ReportRequestDTO reportRequestDTO) {
        ReportResponseDTO reportCriado = reportService.criarReport(reportRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(reportCriado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterReportPorId(reportCriado.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withRel("todos-reports")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReportResponseDTO>> atualizarReport(@PathVariable Long id, @Validated @RequestBody ReportRequestDTO reportRequestDTO) {
        Optional<ReportResponseDTO> reportAtualizado = reportService.atualizarReport(id, reportRequestDTO);
        return reportAtualizado.map(r -> ResponseEntity.ok(EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterReportPorId(r.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportController.class).obterTodosReports()).withRel("todos-reports"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReport(@PathVariable Long id) {
        reportService.deletarReport(id);
        return ResponseEntity.noContent().build();
    }
}
