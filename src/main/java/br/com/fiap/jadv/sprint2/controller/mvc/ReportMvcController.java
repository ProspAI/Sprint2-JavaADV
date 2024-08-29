package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.jadv.sprint2.dto.request.ReportRequestDTO;
import br.com.fiap.jadv.sprint2.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportMvcController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String listarReports(Model model) {
        model.addAttribute("reports", reportService.obterTodosReports());
        return "reports/reports"; // Retorna a página de listagem de reports na pasta correta
    }

    @GetMapping("/{id}")
    public String visualizarReport(@PathVariable Long id, Model model) {
        return reportService.obterReportPorId(id)
                .map(report -> {
                    model.addAttribute("report", report);
                    return "reports/report-view"; // Retorna a página de visualização de um report específico na pasta correta
                })
                .orElse("redirect:/reports"); // Redireciona para a listagem se o report não for encontrado
    }

    @GetMapping("/novo")
    public String novoReportForm(Model model) {
        model.addAttribute("report", new ReportRequestDTO());
        return "reports/report-form"; // Retorna a página de criação de novo report na pasta correta
    }

    @PostMapping("/salvar")
    public String salvarReport(@ModelAttribute ReportRequestDTO reportRequestDTO) {
        reportService.criarReport(reportRequestDTO);
        return "redirect:/reports"; // Redireciona para a listagem após salvar o novo report
    }

    @GetMapping("/editar/{id}")
    public String editarReportForm(@PathVariable Long id, Model model) {
        return reportService.obterReportPorId(id)
                .map(report -> {
                    model.addAttribute("report", report);
                    return "reports/report-form"; // Retorna a página de edição de report na pasta correta
                })
                .orElse("redirect:/reports"); // Redireciona para a listagem se o report não for encontrado
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarReport(@PathVariable Long id, @ModelAttribute ReportRequestDTO reportRequestDTO) {
        reportService.atualizarReport(id, reportRequestDTO);
        return "redirect:/reports"; // Redireciona para a listagem após atualizar o report
    }

    @GetMapping("/deletar/{id}")
    public String deletarReport(@PathVariable Long id) {
        reportService.deletarReport(id);
        return "redirect:/reports"; // Redireciona para a listagem após deletar o report
    }
}
