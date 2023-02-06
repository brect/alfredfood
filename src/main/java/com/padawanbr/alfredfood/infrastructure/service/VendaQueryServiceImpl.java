package com.padawanbr.alfredfood.infrastructure.service;

import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.model.Pedido;
import com.padawanbr.alfredfood.domain.model.dto.VendaDiaria;
import com.padawanbr.alfredfood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        final CriteriaQuery<VendaDiaria> query = criteriaBuilder.createQuery(VendaDiaria.class);

        final Root<Pedido> root = query.from(Pedido.class);

        var localDateExpression = criteriaBuilder.function(
                "date", Date.class, root.get("dataCriacao"));

        final CompoundSelection<VendaDiaria> vendaDiariaCompoundSelection = criteriaBuilder.construct(
                VendaDiaria.class,
                localDateExpression,
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.sum(root.get("valorTotal")));

        query.select(vendaDiariaCompoundSelection);
        query.groupBy(localDateExpression);

        return entityManager.createQuery(query).getResultList();
    }
}
