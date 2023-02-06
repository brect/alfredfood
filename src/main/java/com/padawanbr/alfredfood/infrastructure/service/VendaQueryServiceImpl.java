package com.padawanbr.alfredfood.infrastructure.service;

import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.StatusPedido;
import com.padawanbr.alfredfood.domain.model.dto.VendaDiaria;
import com.padawanbr.alfredfood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        final CriteriaQuery<VendaDiaria> query = criteriaBuilder.createQuery(VendaDiaria.class);

        final Root<Pedido> root = query.from(Pedido.class);

        final var convertTzExpression = criteriaBuilder.function("convert_tz",
                Date.class,
                root.get("dataCriacao"),
                criteriaBuilder.literal("+00:00"),
                criteriaBuilder.literal(timeOffset));

        var localDateExpression = criteriaBuilder.function(
                "date", Date.class, convertTzExpression);

        final CompoundSelection<VendaDiaria> vendaDiariaCompoundSelection = criteriaBuilder.construct(
                VendaDiaria.class,
                localDateExpression,
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.sum(root.get("valorTotal")));

        var predicates = new ArrayList<>();

        if (vendaDiariaFilter.getRestauranteId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("restaurante"), vendaDiariaFilter.getRestauranteId()));
        }
        if (vendaDiariaFilter.getDataCriacaoInicio() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getRestauranteId()));
        }
        if (vendaDiariaFilter.getDataCriacaoFim() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getRestauranteId()));
        }

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(vendaDiariaCompoundSelection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(localDateExpression);

        return entityManager.createQuery(query).getResultList();
    }
}
