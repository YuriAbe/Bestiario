package br.com.yuriabe.Bestiario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JogoDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String genero;

    private String estudio;
    
    // Não incluímos a lista de Inimigos para quebrar o ciclo de serialização
}
