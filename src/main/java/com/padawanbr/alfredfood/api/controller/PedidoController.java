package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.PedidoDomainMapper;
import com.padawanbr.alfredfood.api.mapper.PedidoModelMapper;
import com.padawanbr.alfredfood.api.mapper.PedidoResumidoModelMapper;
import com.padawanbr.alfredfood.api.model.request.PedidoRequest;
import com.padawanbr.alfredfood.api.model.response.PedidoDTO;
import com.padawanbr.alfredfood.api.model.response.PedidoResumidoDTO;
import com.padawanbr.alfredfood.core.data.PageableTranslator;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.Usuario;
import com.padawanbr.alfredfood.domain.filter.PedidoFilter;
import com.padawanbr.alfredfood.domain.service.PedidoService;
import com.padawanbr.alfredfood.infrastructure.specification.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.ImmutableMap;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoModelMapper pedidoModelMapper;
    @Autowired
    private PedidoResumidoModelMapper pedidoResumidoModelMapper;
    @Autowired
    private PedidoDomainMapper pedidoDomainMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pesquisar(Pageable pageable, PedidoFilter pedidoFilter) {

        final Specification<Pedido> pedidoSpecification = PedidoSpecs.usandoFiltro(pedidoFilter);

        pageable = converterPageable(pageable);
        
        final Page<Pedido> pedidosPage = pedidoService.pesquisar(pedidoSpecification, pageable);

        final List<PedidoResumidoDTO> pedidoResumido = pedidoResumidoModelMapper.toCollectionModel(pedidosPage.getContent());

        Page<PedidoResumidoDTO> pedidosPaginados = new PageImpl<>(pedidoResumido, pageable, pedidosPage.getTotalElements());

        return ResponseEntity.ok(pedidosPaginados);
    }

    private Pageable converterPageable(Pageable pageable) {
        var mapper = ImmutableMap.of(
                "codigo", "codigo",
                "nomeCliente", "cliente.nome",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapper);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> findById(@PathVariable("id") Long id) {
        final Pedido pedido = pedidoService.consultar(id);
        return ResponseEntity.ok(pedidoModelMapper.toModel(pedido));
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid PedidoRequest request) {
        try {
            final Pedido novoPedido = pedidoDomainMapper.toDomainObject(request);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            final Pedido pedidoSalvo = pedidoService.emitir(novoPedido);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pedidoSalvo.getId()).toUri();

            return ResponseEntity.created(location).body(pedidoModelMapper.toModel(pedidoSalvo));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

}
