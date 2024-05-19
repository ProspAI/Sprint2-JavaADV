package br.com.fiap.jadv.sprint2.dto.response;

import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClienteResponseDTO extends RepresentationModel<ClienteResponseDTO> {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
}
