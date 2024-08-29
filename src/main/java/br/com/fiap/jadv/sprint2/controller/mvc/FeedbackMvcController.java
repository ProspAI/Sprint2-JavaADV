package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.jadv.sprint2.dto.request.FeedbackRequestDTO;
import br.com.fiap.jadv.sprint2.service.FeedbackService;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackMvcController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String listarFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.obterTodosFeedbacks());
        return "feedbacks/feedbacks"; // Retorna a página de listagem de feedbacks na pasta correta
    }

    @GetMapping("/{id}")
    public String visualizarFeedback(@PathVariable Long id, Model model) {
        return feedbackService.obterFeedbackPorId(id)
                .map(feedback -> {
                    model.addAttribute("feedback", feedback);
                    return "feedbacks/feedback-view"; // Retorna a página de visualização de um feedback específico na pasta correta
                })
                .orElse("redirect:/feedbacks"); // Redireciona para a listagem se o feedback não for encontrado
    }

    @GetMapping("/novo")
    public String novoFeedbackForm(Model model) {
        model.addAttribute("feedback", new FeedbackRequestDTO());
        return "feedbacks/feedback-form"; // Retorna a página de criação de novo feedback na pasta correta
    }

    @PostMapping("/salvar")
    public String salvarFeedback(@ModelAttribute FeedbackRequestDTO feedbackRequestDTO) {
        feedbackService.criarFeedback(feedbackRequestDTO);
        return "redirect:/feedbacks"; // Redireciona para a listagem após salvar o novo feedback
    }

    @GetMapping("/editar/{id}")
    public String editarFeedbackForm(@PathVariable Long id, Model model) {
        return feedbackService.obterFeedbackPorId(id)
                .map(feedback -> {
                    model.addAttribute("feedback", feedback);
                    return "feedbacks/feedback-form"; // Retorna a página de edição de feedback na pasta correta
                })
                .orElse("redirect:/feedbacks"); // Redireciona para a listagem se o feedback não for encontrado
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarFeedback(@PathVariable Long id, @ModelAttribute FeedbackRequestDTO feedbackRequestDTO) {
        feedbackService.atualizarFeedback(id, feedbackRequestDTO);
        return "redirect:/feedbacks"; // Redireciona para a listagem após atualizar o feedback
    }

    @GetMapping("/deletar/{id}")
    public String deletarFeedback(@PathVariable Long id) {
        feedbackService.deletarFeedback(id);
        return "redirect:/feedbacks"; // Redireciona para a listagem após deletar o feedback
    }
}
