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
        dto.setAtaqueEspecial(model.getAtaque_especial());

        // Mapeia apenas o ID do Jogo para o DTO
        if (model.getJogo() != null) {
            dto.setJogoId(model.getJogo().getId());
            dto.setJogoTitulo(model.getJogo().getTitulo());
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
        model.setAtaque_especial(dto.getAtaqueEspecial());

        // Busca o JogoModel pelo ID
        if (dto.getJogoId() != null) {
            JogoModel jogo = jogoRepository.findById(dto.getJogoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com ID: " + dto.getJogoId()));
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

    public InimigoDTO update(long id, InimigoDTO inimigoDTO) {
        // 1. Verifica se o inimigo existe
        inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));

        // 2. Garante que o ID está no DTO e converte para Model
        inimigoDTO.setId(id);
        InimigoModel inimigoModel = toModel(inimigoDTO);

        // 3. Salva (atualiza)
        InimigoModel updatedModel = inimigoRepository.save(inimigoModel);
        return toDTO(updatedModel);
    }

    public void delete(long id) {
        InimigoModel inimigo = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));
        inimigoRepository.delete(inimigo);
    }
}
