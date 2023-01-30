package com.padawanbr.alfredfood.api.controller;


import com.padawanbr.alfredfood.api.mapper.FormaPagamentoDomainMapper;
import com.padawanbr.alfredfood.api.model.request.FormaPagamentoRequest;
import com.padawanbr.alfredfood.api.model.request.RestauranteRequest;
import com.padawanbr.alfredfood.api.model.response.EstadoDTO;
import com.padawanbr.alfredfood.api.model.respos.FormaPagamentoDTO;
import com.padawanbr.alfredfood.api.mapper.FormaPagamentoModelMapper;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.FormaPagamento;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.FormaPagamentoRepository;
import com.padawanbr.alfredfood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelMapper formaPagamentoModelMapper;
    @Autowired
    private FormaPagamentoDomainMapper formaPagamentoDomainMapper;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @GetMapping
    private List<FormaPagamentoDTO> listar() {
        return formaPagamentoModelMapper.toCollectionModel(formaPagamentoRepository.findAll());
    }


    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = formaPagamentoService.consultar(formaPagamentoId);

        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoModelMapper.toModel(formaPagamento);

        if (formaPagamento != null) {
            return ResponseEntity.ok(formaPagamentoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid FormaPagamentoRequest request) {
        try {
            final FormaPagamento formaPagamento = formaPagamentoDomainMapper.toDomainObject(request);
            final FormaPagamento formaPagamentoSalvo = formaPagamentoService.salvar(formaPagamento);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(formaPagamentoSalvo.getId()).toUri();

            return ResponseEntity.created(location).body(formaPagamentoModelMapper.toModel(formaPagamentoSalvo));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }


    @PutMapping("/{formaPagamentoID}")
    public ResponseEntity<?> atualizar(@PathVariable Long formaPagamentoID,
                                       @RequestBody @Valid FormaPagamentoRequest request) {
        try {
            FormaPagamento formaPagamento = formaPagamentoService.consultar(formaPagamentoID);

            formaPagamentoDomainMapper.copyToDomainObject(request, formaPagamento);

            final FormaPagamento formaPagamentoSalvo = formaPagamentoService.salvar(formaPagamento);
            return ResponseEntity.ok(formaPagamentoModelMapper.toModel(formaPagamentoSalvo));

        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Cidade> remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}
