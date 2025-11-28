package br.com.yuriabe.Bestiario.service;

import br.com.yuriabe.Bestiario.dto.JogoDTO;
import br.com.yuriabe.Bestiario.exceptions.ResourceNotFoundException;
import br.com.yuriabe.Bestiario.model.JogoModel;
import br.com.yuriabe.Bestiario.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;

    // ✔️ Converter Model → DTO
    private JogoDTO toDTO(JogoModel model) {
        JogoDTO dto = new JogoDTO();
        dto.setId(model.getId());
        dto.setTitulo(model.getTitulo());
        dto.setGenero(model.getGenero());
        dto.setEstudio(model.getEstudio());
        return dto;
    }

    // ✔️ Converter DTO → Model
    private JogoModel toModel(JogoDTO dto) {
        JogoModel model = new JogoModel();
        model.setId(dto.getId());
        model.setTitulo(dto.getTitulo());
        model.setGenero(dto.getGenero());
        model.setEstudio(dto.getEstudio());
        return model;
    }

    // ✔️ LISTAR TODOS
    public List<JogoDTO> findAll() {
        return jogoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ✔️ BUSCAR POR ID
    public JogoDTO findById(Long id) {
        JogoModel model = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado!"));
        return toDTO(model);
    }

    // ✔️ CREATE (SALVAR)
    public JogoDTO save(JogoDTO dto) {
        JogoModel model = toModel(dto);
        JogoModel saved = jogoRepository.save(model);
        return toDTO(saved);
    }

    // ✔️ UPDATE
    public JogoDTO update(Long id, JogoDTO dto) {
        JogoModel existing = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado!"));

        existing.setTitulo(dto.getTitulo());
        existing.setGenero(dto.getGenero());
        existing.setEstudio(dto.getEstudio());

        JogoModel updated = jogoRepository.save(existing);
        return toDTO(updated);
    }

    // ✔️ DELETE
    public void delete(Long id) {
        JogoModel model = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado!"));

        jogoRepository.delete(model);
    }

}
