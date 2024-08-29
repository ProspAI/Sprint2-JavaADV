package br.com.fiap.jadv.sprint2.dto.response;

import lombok.Data;

@Data
public class FeedbackResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer nota;
}

