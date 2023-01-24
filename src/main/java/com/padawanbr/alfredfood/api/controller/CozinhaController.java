package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.repository.CozinhaRepository;
import com.padawanbr.alfredfood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cadastroCozinha;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cozinha>> listar() {
        return ResponseEntity.ok(cozinhaRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cozinha> findById(@PathVariable("id") Long id) {
        final Cozinha cozinha = cadastroCozinha.buscar(id);
        return ResponseEntity.ok(cozinha);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void salvar(@RequestBody @Valid Cozinha cozinha) {
        cadastroCozinha.salvar(cozinha);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cozinha> atualizar(@PathVariable("id") Long id,
                                             @RequestBody @Valid Cozinha cozinhaRequest) {

        final Cozinha cozinhaAtual = cadastroCozinha.buscar(id);

        BeanUtils.copyProperties(cozinhaRequest, cozinhaAtual, "id");

        try {
            final Cozinha cozinha = cadastroCozinha.salvar(cozinhaAtual);

            return ResponseEntity.ok(cozinha);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }

    }

    @DeleteMapping
    public void remover(Long id) {
        cadastroCozinha.excluir(id);
    }

//
//    @DeleteMapping
//    public ResponseEntity<Cozinha> remover(Long id) {
//        try {
//            cadastroCozinha.excluir(id);
//            return ResponseEntity.notFound().build();
//        } catch (EntidadeNaoEncontradaException ex) {
//            return ResponseEntity.notFound().build();
//        } catch (EntidadeEmUsoException ex) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }
}
