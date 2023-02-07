package com.padawanbr.alfredfood.infrastructure.repository;

import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.repository.CustomProdutoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements CustomProdutoRepository {


    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }
}
