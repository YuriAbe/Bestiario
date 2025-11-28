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

    // ✔️ Converter Model → DTO
    public InimigoDTO toDTO(InimigoModel model) {
        InimigoDTO dto = new InimigoDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEspecie(model.getEspecie());
        dto.setDificuldade(model.getDificuldade());
        dto.setAtaque_especial(model.getAtaque_especial());

        if (model.getJogo() != null) {
            dto.setJogo_id(model.getJogo().getId());
            dto.setJogo_titulo(model.getJogo().getTitulo());
        }

        return dto;
    }

    // ✔️ Converter DTO → Model 
public InimigoModel toModel(InimigoDTO dto) {

    InimigoModel model = new InimigoModel();

    // ❌ Nunca setar ID no create, ✔️ Só seta ID se não for null (ou seja, no UPDATE)
    if (dto.getId() != null) {
        model.setId(dto.getId());
    }

    model.setNome(dto.getNome());
    model.setEspecie(dto.getEspecie());
    model.setDificuldade(dto.getDificuldade());
    model.setAtaque_especial(dto.getAtaque_especial());

    // ✔️ Jogo obrigatório
    if (dto.getJogo_id() == null) {
        throw new RuntimeException("O jogo é obrigatório");
    }

    // ✔️ Buscar e associar jogo
    JogoModel jogo = jogoRepository.findById(dto.getJogo_id())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Jogo não encontrado com ID: " + dto.getJogo_id()));

    model.setJogo(jogo);

    return model;
}


    // ✔️ LISTAR TODOS
    public List<InimigoDTO> findAll() {
        return inimigoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✔️ BUSCAR POR ID
    public InimigoDTO findById(long id) {
        InimigoModel inimigo = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));

        return toDTO(inimigo);
    }

    // ✔️ CREATE (SALVAR)
    public InimigoDTO save(InimigoDTO inimigoDTO) {
        InimigoModel model = toModel(inimigoDTO);

        InimigoModel savedModel = inimigoRepository.save(model);

        return toDTO(savedModel);
    }

    // ✔️ UPDATE
    public InimigoDTO update(long id, InimigoDTO inimigoDTO) {

        InimigoModel existingModel = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));

        existingModel.setNome(inimigoDTO.getNome());
        existingModel.setEspecie(inimigoDTO.getEspecie());
        existingModel.setDificuldade(inimigoDTO.getDificuldade());
        existingModel.setAtaque_especial(inimigoDTO.getAtaque_especial());

        if (inimigoDTO.getJogo_id() != null) {
            JogoModel jogo = jogoRepository.findById(inimigoDTO.getJogo_id())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Jogo não encontrado com ID: " + inimigoDTO.getJogo_id()));
            existingModel.setJogo(jogo);
        } else {
            existingModel.setJogo(null);
        }

        InimigoModel updatedModel = inimigoRepository.save(existingModel);

        return toDTO(updatedModel);
    }

    // ✔️ DELETE
    public void delete(long id) {
        InimigoModel inimigo = inimigoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inimigo não encontrado com ID: " + id));

        inimigoRepository.delete(inimigo);
    }
}
