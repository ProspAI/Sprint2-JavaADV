package br.com.fiap.jadv.sprint2.controller.api;

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

import br.com.fiap.jadv.sprint2.dto.request.FeedbackRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.FeedbackResponseDTO;
import br.com.fiap.jadv.sprint2.service.FeedbackService;

@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<FeedbackResponseDTO>>> obterTodosFeedbacks() {
        List<EntityModel<FeedbackResponseDTO>> feedbacks = feedbackService.obterTodosFeedbacks().stream()
                .map(feedback -> EntityModel.of(feedback,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterFeedbackPorId(feedback.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withRel("todos-feedbacks")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(feedbacks,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FeedbackResponseDTO>> obterFeedbackPorId(@PathVariable Long id) {
        Optional<FeedbackResponseDTO> feedback = feedbackService.obterFeedbackPorId(id);
        return feedback.map(f -> ResponseEntity.ok(EntityModel.of(f,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterFeedbackPorId(f.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withRel("todos-feedbacks"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<FeedbackResponseDTO>> criarFeedback(@Validated @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        FeedbackResponseDTO feedbackCriado = feedbackService.criarFeedback(feedbackRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(feedbackCriado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterFeedbackPorId(feedbackCriado.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withRel("todos-feedbacks")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FeedbackResponseDTO>> atualizarFeedback(@PathVariable Long id, @Validated @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        Optional<FeedbackResponseDTO> feedbackAtualizado = feedbackService.atualizarFeedback(id, feedbackRequestDTO);
        return feedbackAtualizado.map(f -> ResponseEntity.ok(EntityModel.of(f,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterFeedbackPorId(f.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).obterTodosFeedbacks()).withRel("todos-feedbacks"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFeedback(@PathVariable Long id) {
        feedbackService.deletarFeedback(id);
        return ResponseEntity.noContent().build();
    }
}