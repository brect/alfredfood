package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class StatusPedidoService {


    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(Long pedidoId) {
        final Pedido pedido = pedidoService.consultar(pedidoId);
        verificaStatusPedido(pedido, StatusPedido.CRIADO, StatusPedido.CONFIRMADO);
        atualizaStatusPedido(pedido, StatusPedido.CONFIRMADO);
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        final Pedido pedido = pedidoService.consultar(pedidoId);
        verificaStatusPedido(pedido, StatusPedido.CRIADO, StatusPedido.CANCELADO);
        atualizaStatusPedido(pedido, StatusPedido.CANCELADO);
    }

    @Transactional
    public void entregar(Long pedidoId) {
        final Pedido pedido = pedidoService.consultar(pedidoId);
        verificaStatusPedido(pedido, StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE);
        atualizaStatusPedido(pedido, StatusPedido.ENTREGUE);
    }

    private void verificaStatusPedido(Pedido pedido, StatusPedido criado, StatusPedido confirmado) {
        if (!pedido.getStatus().equals(criado)) {
            getBussinesException(pedido, confirmado);
        }
    }

    private void atualizaStatusPedido(Pedido pedido, StatusPedido statusPedido) {
        pedido.setStatus(statusPedido);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }


    private void getBussinesException(Pedido pedido, StatusPedido cancelado) {
        throw new BussinesException(
                String.format("Status do pedido %s não pode ser alterado de %s para %s",
                        pedido.getId(),
                        pedido.getStatus().getDescricao(),
                        cancelado.getDescricao()));
    }

}
