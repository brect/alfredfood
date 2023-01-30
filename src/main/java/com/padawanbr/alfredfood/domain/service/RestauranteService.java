package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.EstadoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.exception.RestauranteNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestauranteService {

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;


    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long idCozinha = restaurante.getCozinha().getId();
        final Cozinha cozinha = cozinhaService.buscar(idCozinha);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new RestauranteNaoEncontradoException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Problema ao remover restaurante");
        }
    }

    @Transactional
    public void ativar(Long idRestaurante){
        final Restaurante restaurante = buscar(idRestaurante);
        restaurante.desativar();
    }

    @Transactional
    public void desativar(Long idRestaurante){
        final Restaurante restaurante = buscar(idRestaurante);
        restaurante.desativar();
    }

}

