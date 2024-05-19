package br.com.fiap.jadv.sprint2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "SALES_STRATEGIES")
public class SalesStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_strategy_seq")
    @SequenceGenerator(name = "sales_strategy_seq", sequenceName = "SALES_STRATEGIES_SEQ", allocationSize = 1)
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
