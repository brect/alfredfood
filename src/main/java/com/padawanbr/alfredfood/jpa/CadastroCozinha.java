package com.padawanbr.alfredfood.jpa;

import com.padawanbr.alfredfood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listarCozinhas(){
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    public Cozinha adicionarCozinha(Cozinha cozinha){
        return entityManager.merge(cozinha);
    }

    public Cozinha buscarCozinhaPorId(UUID id){
        return entityManager.find(Cozinha.class, id);
    }

    @Transactional
    public void removerCozinha(Cozinha cozinha){
        entityManager.remove(cozinha);
    }


}
