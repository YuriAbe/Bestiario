package br.com.yuriabe.Bestiario.service;

import br.com.yuriabe.Bestiario.dto.UserDTO;
import br.com.yuriabe.Bestiario.model.UserModel;
import br.com.yuriabe.Bestiario.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✔️️ Converter Model → DTO
    private UserDTO toDTO(UserModel model) {
        UserDTO dto = new UserDTO();
        dto.setId(model.getId());
        dto.setUsername(model.getUsername());
        dto.setPassword("");

        return dto;
    }

    // ✔️️ Converter DTO → Model
    private UserModel toModel(UserDTO dto) {
        UserModel model = new UserModel();

        // ❌ Nunca setar ID no create, ✔️️ Só seta ID se não for null (ou seja, no
        // UPDATE)
        if (dto.getId() != null) {
            model.setId(dto.getId());
        }

        model.setUsername(dto.getUsername());
        model.setPassword(passwordEncoder.encode(dto.getPassword())); // senha criptografada
        model.setEnabled(true);
        model.setRoles("USER");
        
        return model;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Método de registro de usuário
    public UserDTO register(UserDTO dto) {
        
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Usuário já existe!");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        
        UserModel saved = userRepository.save(toModel(dto));
        return toDTO(saved);
    }

    // Método Esqueceu sua Senha
    public void updatePassword(String username, String newPassword) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
