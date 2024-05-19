package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.FeedbackController;
import br.com.fiap.jadv.sprint2.dto.request.FeedbackRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.FeedbackResponseDTO;
import br.com.fiap.jadv.sprint2.entity.Feedback;
import br.com.fiap.jadv.sprint2.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<FeedbackResponseDTO> obterTodosFeedbacks() {
        return feedbackRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<FeedbackResponseDTO> obterFeedbackPorId(Long id) {
        return feedbackRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public FeedbackResponseDTO criarFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        Feedback feedback = new Feedback();
        feedback.setTitulo(feedbackRequestDTO.getTitulo());
        feedback.setDescricao(feedbackRequestDTO.getDescricao());
        feedback.setNota(feedbackRequestDTO.getNota());
        return converterParaResponseDTO(feedbackRepository.save(feedback));
    }

    public Optional<FeedbackResponseDTO> atualizarFeedback(Long id, FeedbackRequestDTO feedbackRequestDTO) {
        return feedbackRepository.findById(id).map(feedback -> {
            feedback.setTitulo(feedbackRequestDTO.getTitulo());
            feedback.setDescricao(feedbackRequestDTO.getDescricao());
            feedback.setNota(feedbackRequestDTO.getNota());
            return converterParaResponseDTO(feedbackRepository.save(feedback));
        });
    }

    public void deletarFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    private FeedbackResponseDTO converterParaResponseDTO(Feedback feedback) {
        FeedbackResponseDTO feedbackResponseDTO = new FeedbackResponseDTO();
        feedbackResponseDTO.setId(feedback.getId());
        feedbackResponseDTO.setTitulo(feedback.getTitulo());
        feedbackResponseDTO.setDescricao(feedback.getDescricao());
        feedbackResponseDTO.setNota(feedback.getNota());

        // Adicionando links HATEOAS
        feedbackResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterFeedbackPorId(feedback.getId())).withSelfRel());
        feedbackResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withRel("todos-feedbacks"));

        return feedbackResponseDTO;
    }
}
