package br.com.fiap.jadv.sprint2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "REPORTS")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "REPORTS_SEQ", allocationSize = 1)
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
