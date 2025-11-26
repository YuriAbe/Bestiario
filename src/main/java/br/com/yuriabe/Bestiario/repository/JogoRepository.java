package br.com.yuriabe.Bestiario.repository;

import br.com.yuriabe.Bestiario.model.JogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<JogoModel, Long> 
{
    
}


