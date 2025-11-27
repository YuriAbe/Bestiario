package br.com.yuriabe.Bestiario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InimigoDTO {

    private Long id;

    @NotBlank(message = "O nome do inimigo é obrigatório")
    private String nome;

    private String especie;

    private String dificuldade;

    private String ataque_especial;

    @NotNull(message = "O Jogo associado é obrigatório")
    private Long jogo_id;

    private String jogo_titulo; // Para exibição na listagem/detalhe
}
