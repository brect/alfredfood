package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.CozinhaDomainMapper;
import com.padawanbr.alfredfood.api.mapper.CozinhaModelMapper;
import com.padawanbr.alfredfood.api.model.request.CozinhaRequest;
import com.padawanbr.alfredfood.api.model.response.CozinhaDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.repository.CozinhaRepository;
import com.padawanbr.alfredfood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelMapper cozinhaModelMapper;
    @Autowired
    private CozinhaDomainMapper cozinhaDomainMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listar(Pageable pageable) {

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        final List<CozinhaDTO> cozinhas = cozinhaModelMapper.toCollectionModel(cozinhasPage.getContent());

        Page<CozinhaDTO> cozinhaPaginadas = new PageImpl<>(cozinhas, pageable, cozinhasPage.getTotalElements());

        return ResponseEntity.ok(cozinhaPaginadas);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaDTO> findById(@PathVariable("id") Long id) {
        final Cozinha cozinha = cadastroCozinha.consultar(id);
        return ResponseEntity.ok(cozinhaModelMapper.toModel(cozinha));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaDTO> salvar(@RequestBody @Valid CozinhaRequest request) {

        final Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaDomainMapper.toDomainObject(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(cozinhaSalva.getId()).toUri();

        return ResponseEntity.created(location).body(cozinhaModelMapper.toModel(cozinhaSalva));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable("id") Long id,
                                             @RequestBody @Valid CozinhaRequest request) {

        final Cozinha cozinhaAtual = cadastroCozinha.consultar(id);

        cozinhaDomainMapper.copyToDomainObject(request, cozinhaAtual);

        try {
            final Cozinha cozinha = cadastroCozinha.salvar(cozinhaAtual);

            return ResponseEntity.ok(cozinhaModelMapper.toModel(cozinha));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }

    }

    @DeleteMapping
    public void remover(@PathVariable("id") Long id) {
        cadastroCozinha.excluir(id);
    }

}
