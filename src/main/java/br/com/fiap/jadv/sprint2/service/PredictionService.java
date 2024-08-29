package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.api.PredictionController;
import br.com.fiap.jadv.sprint2.dto.request.PredictionRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.PredictionResponseDTO;
import br.com.fiap.jadv.sprint2.entity.Prediction;
import br.com.fiap.jadv.sprint2.repository.PredictionRepository;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public List<PredictionResponseDTO> obterTodasPredicoes() {
        return predictionRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<PredictionResponseDTO> obterPredicaoPorId(Long id) {
        return predictionRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public PredictionResponseDTO criarPredicao(PredictionRequestDTO predictionRequestDTO) {
        Prediction prediction = new Prediction();
        prediction.setTitulo(predictionRequestDTO.getTitulo());
        prediction.setDescricao(predictionRequestDTO.getDescricao());
        return converterParaResponseDTO(predictionRepository.save(prediction));
    }

    public Optional<PredictionResponseDTO> atualizarPredicao(Long id, PredictionRequestDTO predictionRequestDTO) {
        return predictionRepository.findById(id).map(prediction -> {
            prediction.setTitulo(predictionRequestDTO.getTitulo());
            prediction.setDescricao(predictionRequestDTO.getDescricao());
            return converterParaResponseDTO(predictionRepository.save(prediction));
        });
    }

    public void deletarPredicao(Long id) {
        predictionRepository.deleteById(id);
    }

    private PredictionResponseDTO converterParaResponseDTO(Prediction prediction) {
        PredictionResponseDTO predictionResponseDTO = new PredictionResponseDTO();
        predictionResponseDTO.setId(prediction.getId());
        predictionResponseDTO.setTitulo(prediction.getTitulo());
        predictionResponseDTO.setDescricao(prediction.getDescricao());

        // Adicionando links HATEOAS
        predictionResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterPredicaoPorId(prediction.getId())).withSelfRel());
        predictionResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PredictionController.class).obterTodasPredicoes()).withRel("todas-predicoes"));

        return predictionResponseDTO;
    }
}
