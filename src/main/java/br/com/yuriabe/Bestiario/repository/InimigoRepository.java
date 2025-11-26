package br.com.yuriabe.Bestiario.repository;

import br.com.yuriabe.Bestiario.model.InimigoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InimigoRepository extends JpaRepository<InimigoModel, Long> {
    
}