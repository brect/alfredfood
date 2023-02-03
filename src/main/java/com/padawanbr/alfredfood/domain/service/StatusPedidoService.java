package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.lang.String;

@Service
public class StatusPedidoService {


    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String pedidoId) {
        final Pedido pedido = pedidoService.consultarCodigo(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String pedidoId) {
        final Pedido pedido = pedidoService.consultarCodigo(pedidoId);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String pedidoId) {
        final Pedido pedido = pedidoService.consultarCodigo(pedidoId);
        pedido.entregar();
    }


}
