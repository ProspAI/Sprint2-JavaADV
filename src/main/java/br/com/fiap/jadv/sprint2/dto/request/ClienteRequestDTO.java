package br.com.fiap.jadv.sprint2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 15)
    private String telefone;
}
