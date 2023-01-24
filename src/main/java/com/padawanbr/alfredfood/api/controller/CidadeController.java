package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.exeptionhandler.CustomExceptionHandler;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.EstadoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.repository.CidadeRepository;
import com.padawanbr.alfredfood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cadastroCidade;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.consultar(cidadeId);
        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            cidade = cadastroCidade.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
                                       @RequestBody @Valid Cidade cidade) {
        Cidade cidadeAtual = cadastroCidade.consultar(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        final Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual);

        try {

            return ResponseEntity.ok(cidadeSalva);
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);

        return ResponseEntity.noContent().build();
    }


}
