package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha cadastrar(Cozinha cozinha) {
        return cozinhaRepository.adicionarCozinha(cozinha);
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.removerCozinha(id);
        } catch (EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar entidade");
        }catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Problema ao remover cozinha");
        }
    }
}

