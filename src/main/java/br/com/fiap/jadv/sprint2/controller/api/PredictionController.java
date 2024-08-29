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

import br.com.fiap.jadv.sprint2.dto.request.PredictionRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.PredictionResponseDTO;
import br.com.fiap.jadv.sprint2.service.PredictionService;

@RestController
@RequestMapping("api/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PredictionResponseDTO>>> obterTodasPredicoes() {
        List<EntityModel<PredictionResponseDTO>> predictions = predictionService.obterTodasPredicoes().stream()
                .map(prediction -> EntityModel.of(prediction,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterPredicaoPorId(prediction.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withRel("todas-predicoes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(predictions,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PredictionResponseDTO>> obterPredicaoPorId(@PathVariable Long id) {
        Optional<PredictionResponseDTO> prediction = predictionService.obterPredicaoPorId(id);
        return prediction.map(p -> ResponseEntity.ok(EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterPredicaoPorId(p.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withRel("todas-predicoes"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<PredictionResponseDTO>> criarPredicao(@Validated @RequestBody PredictionRequestDTO predictionRequestDTO) {
        PredictionResponseDTO predictionCriada = predictionService.criarPredicao(predictionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(predictionCriada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterPredicaoPorId(predictionCriada.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withRel("todas-predicoes")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PredictionResponseDTO>> atualizarPredicao(@PathVariable Long id, @Validated @RequestBody PredictionRequestDTO predictionRequestDTO) {
        Optional<PredictionResponseDTO> predictionAtualizada = predictionService.atualizarPredicao(id, predictionRequestDTO);
        return predictionAtualizada.map(p -> ResponseEntity.ok(EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterPredicaoPorId(p.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withRel("todas-predicoes"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPredicao(@PathVariable Long id) {
        predictionService.deletarPredicao(id);
        return ResponseEntity.noContent().build();
    }
}
