package br.com.fiap.jadv.sprint2.dto.response;

import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {
    private Long id;
    private String nome;
    private String email;
}
