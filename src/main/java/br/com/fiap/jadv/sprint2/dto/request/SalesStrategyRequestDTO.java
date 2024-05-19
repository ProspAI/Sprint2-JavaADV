package br.com.fiap.jadv.sprint2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalesStrategyRequestDTO {

    @NotBlank(message = "O título não deve estar em branco")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição não deve estar em branco")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;
}
