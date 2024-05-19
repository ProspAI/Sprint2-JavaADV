package br.com.fiap.jadv.sprint2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "PREDICTIONS")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prediction_seq")
    @SequenceGenerator(name = "prediction_seq", sequenceName = "PREDICTIONS_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @NotBlank
    @Size(max = 255)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;
}
