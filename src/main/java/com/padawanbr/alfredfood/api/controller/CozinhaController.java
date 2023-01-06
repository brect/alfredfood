package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.repository.CozinhaRepository;
import com.padawanbr.alfredfood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cozinha>> listar() {
        return ResponseEntity.ok(cozinhaRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cozinha> findById(@PathVariable("id") Long id) {

        final Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void salvar(@RequestBody Cozinha cozinha) {
        cadastroCozinha.salvar(cozinha);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cozinha> atualizar(@PathVariable("id") Long id,
                                             @RequestBody Cozinha cozinhaRequest) {

        final Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinhaRequest, cozinhaAtual.get(), "id");

            final Cozinha cozinha = cadastroCozinha.salvar(cozinhaAtual.get());

            return ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Cozinha> remover(Long id) {
        try {
            cadastroCozinha.excluir(id);
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
