package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.CidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.repository.CidadeRepository;
import com.padawanbr.alfredfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de estado com código %d";
    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade consultar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        final Estado estado = estadoService.consultar(estadoId);
        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

}

