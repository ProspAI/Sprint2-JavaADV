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

import br.com.fiap.jadv.sprint2.dto.request.SalesStrategyRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.SalesStrategyResponseDTO;
import br.com.fiap.jadv.sprint2.service.SalesStrategyService;

@RestController
@RequestMapping("/sales-strategies")
public class SalesStrategyController {

    @Autowired
    private SalesStrategyService salesStrategyService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SalesStrategyResponseDTO>>> obterTodasEstrategias() {
        List<EntityModel<SalesStrategyResponseDTO>> salesStrategies = salesStrategyService.obterTodasEstrategias().stream()
                .map(salesStrategy -> EntityModel.of(salesStrategy,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterEstrategiaPorId(salesStrategy.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withRel("todas-estrategias")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(salesStrategies,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SalesStrategyResponseDTO>> obterEstrategiaPorId(@PathVariable Long id) {
        Optional<SalesStrategyResponseDTO> salesStrategy = salesStrategyService.obterEstrategiaPorId(id);
        return salesStrategy.map(s -> ResponseEntity.ok(EntityModel.of(s,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterEstrategiaPorId(s.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withRel("todas-estrategias"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<SalesStrategyResponseDTO>> criarEstrategia(@Validated @RequestBody SalesStrategyRequestDTO salesStrategyRequestDTO) {
        SalesStrategyResponseDTO salesStrategyCriada = salesStrategyService.criarEstrategia(salesStrategyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(salesStrategyCriada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterEstrategiaPorId(salesStrategyCriada.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withRel("todas-estrategias")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SalesStrategyResponseDTO>> atualizarEstrategia(@PathVariable Long id, @Validated @RequestBody SalesStrategyRequestDTO salesStrategyRequestDTO) {
        Optional<SalesStrategyResponseDTO> salesStrategyAtualizada = salesStrategyService.atualizarEstrategia(id, salesStrategyRequestDTO);
        return salesStrategyAtualizada.map(s -> ResponseEntity.ok(EntityModel.of(s,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterEstrategiaPorId(s.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withRel("todas-estrategias"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstrategia(@PathVariable Long id) {
        salesStrategyService.deletarEstrategia(id);
        return ResponseEntity.noContent().build();
    }
}
