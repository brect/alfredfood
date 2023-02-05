package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.ProdutoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Produto;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto consultar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    public List<Produto> consultarAtivos(Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restaurante.getNome()));
    }
    public List<Produto> consultarTodos(Restaurante restaurante) {
        return produtoRepository.findAllByRestaurante(restaurante)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restaurante.getNome()));
    }

}
