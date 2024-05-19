package br.com.fiap.jadv.sprint2.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class PredictionResponseDTO extends RepresentationModel<PredictionResponseDTO> {

    private Long id;
    private String titulo;
    private String descricao;
}
