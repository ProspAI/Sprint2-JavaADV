package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.jadv.sprint2.dto.request.SalesStrategyRequestDTO;
import br.com.fiap.jadv.sprint2.service.SalesStrategyService;

@Controller
@RequestMapping("/sales-strategies")
public class SalesStrategyMvcController {

    @Autowired
    private SalesStrategyService salesStrategyService;

    @GetMapping
    public String listarEstrategias(Model model) {
        model.addAttribute("salesStrategies", salesStrategyService.obterTodasEstrategias());
        return "sales-strategies";
    }

    @GetMapping("/{id}")
    public String visualizarEstrategia(@PathVariable Long id, Model model) {
        salesStrategyService.obterEstrategiaPorId(id).ifPresent(salesStrategy -> model.addAttribute("salesStrategy", salesStrategy));
        return "sales-strategy-view";
    }

    @GetMapping("/novo")
    public String novaEstrategiaForm(Model model) {
        model.addAttribute("salesStrategy", new SalesStrategyRequestDTO());
        return "sales-strategy-form";
    }

    @PostMapping("/salvar")
    public String salvarEstrategia(@ModelAttribute SalesStrategyRequestDTO salesStrategyRequestDTO) {
        salesStrategyService.criarEstrategia(salesStrategyRequestDTO);
        return "redirect:/sales-strategies";
    }

    @GetMapping("/editar/{id}")
    public String editarEstrategiaForm(@PathVariable Long id, Model model) {
        salesStrategyService.obterEstrategiaPorId(id).ifPresent(salesStrategy -> model.addAttribute("salesStrategy", salesStrategy));
        return "sales-strategy-form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarEstrategia(@PathVariable Long id, @ModelAttribute SalesStrategyRequestDTO salesStrategyRequestDTO) {
        salesStrategyService.atualizarEstrategia(id, salesStrategyRequestDTO);
        return "redirect:/sales-strategies";
    }

    @GetMapping("/deletar/{id}")
    public String deletarEstrategia(@PathVariable Long id) {
        salesStrategyService.deletarEstrategia(id);
        return "redirect:/sales-strategies";
    }
}
