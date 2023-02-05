package com.padawanbr.alfredfood.infrastructure.specification;

import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter pedidoFilter){
        return (root, query, criteriaBuilder) -> {

            root.fetch("cliente");
            root.fetch("restaurante").fetch("cozinha");

            var predicates = new ArrayList<>();

            if (pedidoFilter.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), pedidoFilter.getClienteId()));
            }

            if (pedidoFilter.getRestauranteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), pedidoFilter.getRestauranteId()));
            }

            if (pedidoFilter.getDataCriacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoInicio()));
            }

            if (pedidoFilter.getDataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
