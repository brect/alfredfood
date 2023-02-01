package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.RestauranteNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.*;
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

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long idCozinha = restaurante.getCozinha().getId();
        final Long idCidade = restaurante.getEndereco().getCidade().getId();

        final Cozinha cozinha = cozinhaService.consultar(idCozinha);
        final Cidade cidade = cidadeService.consultar(idCidade);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante consultar(Long restauranteId) {
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
        final Restaurante restaurante = consultar(idRestaurante);
        restaurante.ativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds){
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void desativar(Long idRestaurante){
        final Restaurante restaurante = consultar(idRestaurante);
        restaurante.desativar();
    }

    @Transactional
    public void desativar(List<Long> restauranteIds){
        restauranteIds.forEach(this::desativar);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        final Restaurante restaurante = consultar(restauranteId);
        final FormaPagamento formaPagamento = formaPagamentoService.consultar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        final Restaurante restaurante = consultar(restauranteId);
        final FormaPagamento formaPagamento = formaPagamentoService.consultar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = consultar(restauranteId);
        Usuario usuario = usuarioService.consultar(usuarioId);

        restaurante.removerResponsavel(usuario);
    }


    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = consultar(restauranteId);
        Usuario usuario = usuarioService.consultar(usuarioId);

        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        final Restaurante restaurante = consultar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        final Restaurante restaurante = consultar(restauranteId);
        restaurante.fechar();
    }
}

