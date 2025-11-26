package br.com.yuriabe.Bestiario.dto;

import lombok.Data;

@Data
public class JogoDTO {
    private Long id;
    private String titulo;
    private String genero;
    private String estudio;
    // Não incluímos a lista de Inimigos para quebrar o ciclo de serialização
}
