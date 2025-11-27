package br.com.yuriabe.Bestiario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "inimigos")

// encapsulating methods
@Getter
@Setter
// constructor methods
@AllArgsConstructor
@NoArgsConstructor
// another way to define ecapsulating methods

public class InimigoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "especie", length = 100)
    private String especie;

    @Column(name = "dificuldade", length = 50)
    private String dificuldade;

    @Column(name = "ataque_especial", length = 150)
    private String ataque_especial;

    // relacionamento - Muitos para Um | 1:N - Muitos Inimigos são associados com Um
    // Jogo
    // @ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    // Join Column, or foreign key (chave estrangeira)
    @JoinColumn(name = "jogo_id")
    private JogoModel jogo;
}
