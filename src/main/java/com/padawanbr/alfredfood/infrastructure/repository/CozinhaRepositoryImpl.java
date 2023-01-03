package com.padawanbr.alfredfood.infrastructure.repository;

import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext

    private EntityManager entityManager;

    @Override
    public List<Cozinha> listar(){
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    @Override
    public Cozinha adicionarCozinha(Cozinha cozinha){
        return entityManager.merge(cozinha);
    }

    @Override
    public Cozinha buscarCozinhaPorId(Long id){
        return entityManager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public void removerCozinha(Long id){
        final Cozinha cozinha = buscarCozinhaPorId(id);

        if (cozinha == null) {
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(cozinha);
    }

}
