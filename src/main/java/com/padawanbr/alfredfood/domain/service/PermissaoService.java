package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.PermissaoNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Permissao;
import com.padawanbr.alfredfood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao consultar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}
