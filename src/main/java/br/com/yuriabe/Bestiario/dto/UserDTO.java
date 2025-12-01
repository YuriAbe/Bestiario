package br.com.yuriabe.Bestiario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "O usuário é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @NotBlank(message = "A confirmação da senha é obrigatória")
    private String confirmPassword;

    // Não necessidade de criar o Enabled e Roles aqui, pois não são valores que o usuário deve preencher,
    // dessa forma, como são valores not nulls gere um erro antes de executar o método register na service.
}