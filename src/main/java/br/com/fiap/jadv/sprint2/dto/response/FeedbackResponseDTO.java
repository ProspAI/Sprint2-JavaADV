package br.com.fiap.jadv.sprint2.dto.response;

import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackResponseDTO extends RepresentationModel<FeedbackResponseDTO> {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer nota;
}
