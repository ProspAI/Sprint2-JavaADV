package br.com.fiap.jadv.sprint2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "FEEDBACKS")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
    @SequenceGenerator(name = "feedback_seq", sequenceName = "FEEDBACKS_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "O título não deve estar em branco")
    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @NotBlank(message = "A descrição não deve estar em branco")
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @NotNull(message = "A nota não deve estar em branco")
    @Column(name = "NOTA", nullable = false)
    private Integer nota;
}
