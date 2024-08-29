package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.api.SalesStrategyController;
import br.com.fiap.jadv.sprint2.dto.request.SalesStrategyRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.SalesStrategyResponseDTO;
import br.com.fiap.jadv.sprint2.entity.SalesStrategy;
import br.com.fiap.jadv.sprint2.repository.SalesStrategyRepository;

@Service
public class SalesStrategyService {

    @Autowired
    private SalesStrategyRepository salesStrategyRepository;

    public List<SalesStrategyResponseDTO> obterTodasEstrategias() {
        return salesStrategyRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<SalesStrategyResponseDTO> obterEstrategiaPorId(Long id) {
        return salesStrategyRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public SalesStrategyResponseDTO criarEstrategia(SalesStrategyRequestDTO salesStrategyRequestDTO) {
        SalesStrategy salesStrategy = new SalesStrategy();
        salesStrategy.setTitulo(salesStrategyRequestDTO.getTitulo());
        salesStrategy.setDescricao(salesStrategyRequestDTO.getDescricao());
        return converterParaResponseDTO(salesStrategyRepository.save(salesStrategy));
    }

    public Optional<SalesStrategyResponseDTO> atualizarEstrategia(Long id, SalesStrategyRequestDTO salesStrategyRequestDTO) {
        return salesStrategyRepository.findById(id).map(salesStrategy -> {
            salesStrategy.setTitulo(salesStrategyRequestDTO.getTitulo());
            salesStrategy.setDescricao(salesStrategyRequestDTO.getDescricao());
            return converterParaResponseDTO(salesStrategyRepository.save(salesStrategy));
        });
    }

    public void deletarEstrategia(Long id) {
        salesStrategyRepository.deleteById(id);
    }

    private SalesStrategyResponseDTO converterParaResponseDTO(SalesStrategy salesStrategy) {
        SalesStrategyResponseDTO salesStrategyResponseDTO = new SalesStrategyResponseDTO();
        salesStrategyResponseDTO.setId(salesStrategy.getId());
        salesStrategyResponseDTO.setTitulo(salesStrategy.getTitulo());
        salesStrategyResponseDTO.setDescricao(salesStrategy.getDescricao());

        // Adicionando links HATEOAS
        salesStrategyResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterEstrategiaPorId(salesStrategy.getId())).withSelfRel());
        salesStrategyResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SalesStrategyController.class).obterTodasEstrategias()).withRel("todas-estrategias"));

        return salesStrategyResponseDTO;
    }
}
