package br.com.fiap.jadv.sprint2.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesStrategyResponseDTO extends RepresentationModel<SalesStrategyResponseDTO> {

    private Long id;
    private String titulo;
    private String descricao;
}
