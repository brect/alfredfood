package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Pedido;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
