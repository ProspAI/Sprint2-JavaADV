package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.jadv.sprint2.dto.request.PredictionRequestDTO;
import br.com.fiap.jadv.sprint2.service.PredictionService;

@Controller
@RequestMapping("/predictions")
public class PredictionMvcController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping
    public String listarPredicoes(Model model) {
        model.addAttribute("predictions", predictionService.obterTodasPredicoes());
        return "predictions/predictions";  // Ajustado para corresponder à estrutura de pastas
    }

    @GetMapping("/{id}")
    public String visualizarPredicao(@PathVariable Long id, Model model) {
        return predictionService.obterPredicaoPorId(id)
                .map(prediction -> {
                    model.addAttribute("prediction", prediction);
                    return "predictions/prediction-view";  // Ajustado para corresponder à estrutura de pastas
                })
                .orElse("redirect:/predictions");
    }

    @GetMapping("/novo")
    public String novaPredicaoForm(Model model) {
        model.addAttribute("prediction", new PredictionRequestDTO());
        return "predictions/prediction-form";  // Ajustado para corresponder à estrutura de pastas
    }

    @PostMapping("/salvar")
    public String salvarPredicao(@ModelAttribute PredictionRequestDTO predictionRequestDTO) {
        predictionService.criarPredicao(predictionRequestDTO);
        return "redirect:/predictions";
    }

    @GetMapping("/editar/{id}")
    public String editarPredicaoForm(@PathVariable Long id, Model model) {
        return predictionService.obterPredicaoPorId(id)
                .map(prediction -> {
                    model.addAttribute("prediction", prediction);
                    return "predictions/prediction-form";  // Ajustado para corresponder à estrutura de pastas
                })
                .orElse("redirect:/predictions");
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarPredicao(@PathVariable Long id, @ModelAttribute PredictionRequestDTO predictionRequestDTO) {
        predictionService.atualizarPredicao(id, predictionRequestDTO);
        return "redirect:/predictions";
    }

    @GetMapping("/deletar/{id}")
    public String deletarPredicao(@PathVariable Long id) {
        predictionService.deletarPredicao(id);
        return "redirect:/predictions";
    }
}
