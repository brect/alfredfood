package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;


    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante salvar(Restaurante restaurante) {

        Long idCozinha = restaurante.getCozinha().getId();
        final Cozinha cozinha = cadastroCozinhaService.buscar(idCozinha)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com código %d", idCozinha)));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);

    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id).orElseThrow();
    }

    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar entidade");
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Problema ao remover restaurante");
        }
    }

}

