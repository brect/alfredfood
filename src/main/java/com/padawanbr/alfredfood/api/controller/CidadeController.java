package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.exeptionhandler.CustomExceptionHandler;
import com.padawanbr.alfredfood.api.mapper.CidadeDomainMapper;
import com.padawanbr.alfredfood.api.mapper.CidadeModelMapper;
import com.padawanbr.alfredfood.api.model.request.CidadeRequset;
import com.padawanbr.alfredfood.api.model.response.CidadeDTO;
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

    @Autowired
    private CidadeDomainMapper cidadeDomainMapper;

    @Autowired
    private CidadeModelMapper cidadeModelMapper;

    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeModelMapper.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.consultar(cidadeId);
        return ResponseEntity.ok(cidadeModelMapper.toModel(cidade));
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> adicionar(@RequestBody @Valid CidadeRequset requset) {
        try {
            final Cidade cidade = cidadeDomainMapper.toDomainObject(requset);
            final Cidade cidadeSalva = cadastroCidade.salvar(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelMapper.toModel(cidadeSalva));
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long cidadeId,
                                       @RequestBody @Valid CidadeRequset requset) {
        Cidade cidadeAtual = cadastroCidade.consultar(cidadeId);

        cidadeDomainMapper.copyToDomainObject(requset, cidadeAtual);

        final Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual);

        try {
            return ResponseEntity.ok(cidadeModelMapper.toModel(cidadeSalva));
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
