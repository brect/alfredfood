package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.PedidoDomainMapper;
import com.padawanbr.alfredfood.api.mapper.PedidoModelMapper;
import com.padawanbr.alfredfood.api.mapper.PedidoResumidoModelMapper;
import com.padawanbr.alfredfood.api.model.request.PedidoRequest;
import com.padawanbr.alfredfood.api.model.response.PedidoDTO;
import com.padawanbr.alfredfood.api.model.response.PedidoResumidoDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.Usuario;
import com.padawanbr.alfredfood.domain.service.PedidoService;
import com.padawanbr.alfredfood.domain.service.StatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos/{pedidoId}")
public class StatusPedidoController {

    @Autowired
    private StatusPedidoService statusPedidoService;

    @PutMapping("/confirmacao")
    public ResponseEntity<?> confirmar(@PathVariable Long pedidoId){

        statusPedidoService.confirmar(pedidoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    public ResponseEntity<?> cancelamento(@PathVariable Long pedidoId){
        statusPedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    public ResponseEntity<?> entrega(@PathVariable Long pedidoId){
        statusPedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }

}
