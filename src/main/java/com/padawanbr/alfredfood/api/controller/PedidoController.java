package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.CidadeModelMapper;
import com.padawanbr.alfredfood.api.mapper.PedidoModelMapper;
import com.padawanbr.alfredfood.api.model.response.CozinhaDTO;
import com.padawanbr.alfredfood.api.model.response.PedidoDTO;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoModelMapper pedidoModelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PedidoDTO>> listar() {
        return ResponseEntity.ok(pedidoModelMapper.toCollectionModel(pedidoService.findAll()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> findById(@PathVariable("id") Long id) {
        final Pedido pedido = pedidoService.consultar(id);
        return ResponseEntity.ok(pedidoModelMapper.toModel(pedido));
    }

}
