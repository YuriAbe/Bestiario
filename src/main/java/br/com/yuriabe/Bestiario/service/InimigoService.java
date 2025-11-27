package br.com.yuriabe.Bestiario.service;

import br.com.yuriabe.Bestiario.dto.InimigoDTO;
import br.com.yuriabe.Bestiario.exceptions.ResourceNotFoundException;
import br.com.yuriabe.Bestiario.model.InimigoModel;
import br.com.yuriabe.Bestiario.model.JogoModel;
import br.com.yuriabe.Bestiario.repository.InimigoRepository;
import br.com.yuriabe.Bestiario.repository.JogoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InimigoService {

    @Autowired
    private InimigoRepository inimigoRepository;

    @Autowired
    private JogoRepository jogoRepository;

    // Método para converter Model para DTO
    public InimigoDTO toDTO(InimigoModel model) {
        InimigoDTO dto = new InimigoDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEspecie(model.getEspecie());
        dto.setDificuldade(model.getDificuldade());
        dto.setAtaque_especial(model.getAtaque_especial());

        // Mapeia apenas o ID do Jogo para o DTO
        if (model.getJogo() != null) {
            dto.setJogo_id(model.getJogo().getId());
            dto.setJogo_titulo(model.getJogo().getTitulo());
        }
        return dto;
    }

    // Método para converter DTO para Model
    public InimigoModel toModel(InimigoDTO dto) {
        InimigoModel model = new InimigoModel();
        model.setId(dto.getId());
        model.setNome(dto.getNome());
        model.setEspecie(dto.getEspecie());
        model.setDificuldade(dto.getDificuldade());
        model.setAtaque_especial(dto.getAtaque_especial());

        // Busca o JogoModel pelo ID
        if (dto.getJogo_id() != null) {
            JogoModel jogo = jogoRepository.findById(dto.getJogo_id())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Jogo não encontrado com ID: " + dto.getJogo_id()));
            model.setJogo(jogo);
        }
        return model;
    }

    public List<InimigoDTO> findAll() {
        return inimigoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InimigoDTO findById(long id) {
        InimigoModel inimigo = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));
        return toDTO(inimigo);
    }

    public InimigoDTO save(InimigoDTO inimigoDTO) {
        InimigoModel inimigoModel = toModel(inimigoDTO);
        InimigoModel savedModel = inimigoRepository.save(inimigoModel);
        return toDTO(savedModel);
    }

    // InimigoService.java - Método update CORRIGIDO
    public InimigoDTO update(long id, InimigoDTO inimigoDTO) {
        // 1. Busca o InimigoModel existente (ou lança exceção se não existir)
        InimigoModel existingModel = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));

        // 2. Aplica as mudanças do DTO no Model existente
        existingModel.setNome(inimigoDTO.getNome());
        existingModel.setEspecie(inimigoDTO.getEspecie());
        existingModel.setDificuldade(inimigoDTO.getDificuldade());
        existingModel.setAtaque_especial(inimigoDTO.getAtaque_especial());

        // 3. Atualiza o relacionamento Jogo
        if (inimigoDTO.getJogo_id() != null) {
            JogoModel jogo = jogoRepository.findById(inimigoDTO.getJogo_id())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Jogo não encontrado com ID: " + inimigoDTO.getJogo_id()));
            existingModel.setJogo(jogo);
        } else {
            existingModel.setJogo(null); // Ou trate como preferir se o jogo for opcional
        }

        // 4. Salva (o Hibernate fará o UPDATE porque o objeto tem ID)
        InimigoModel updatedModel = inimigoRepository.save(existingModel);
        return toDTO(updatedModel);
    }

    public void delete(long id) {
        InimigoModel inimigo = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));
        inimigoRepository.delete(inimigo);
    }
}
