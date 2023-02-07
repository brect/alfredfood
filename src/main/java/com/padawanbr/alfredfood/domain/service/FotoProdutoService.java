package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        final Optional<FotoProduto> fotoProdutoSalvo = produtoRepository.findFotoById(fotoProduto.getRestauranteId(), fotoProduto.getProdutoId());

        if (fotoProdutoSalvo.isPresent()){
            produtoRepository.delete(fotoProdutoSalvo.get());
        }

        return produtoRepository.save(fotoProduto);
    }
}
