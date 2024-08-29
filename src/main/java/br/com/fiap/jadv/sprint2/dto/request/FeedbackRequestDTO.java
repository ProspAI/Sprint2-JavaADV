package br.com.fiap.jadv.sprint2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackRequestDTO {

    private Long id;  // Campo adicionado para identificar a edição

    @NotBlank(message = "O título não deve estar em branco")
    private String titulo;

    @NotBlank(message = "A descrição não deve estar em branco")
    private String descricao;

    @NotNull(message = "A nota não deve estar em branco")
    private Integer nota;

}