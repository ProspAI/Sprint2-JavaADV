package br.com.fiap.jadv.sprint2.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "CLIENTES_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Email
    @NotBlank
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 15)
    @Column(name = "TELEFONE", nullable = false)
    private String telefone;
}
