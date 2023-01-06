package com.padawanbr.alfredfood.infrastructure.repository;

import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.CustomRestauranteRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements CustomRestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        var jpql = "from Restaurante where nome like :nome"
                + "and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";

        final List<Restaurante> result = entityManager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("taxaFreteInicial", taxaFreteInicial)
                .setParameter("taxaFreteFinal", taxaFreteFinal)
                .getResultList();

        return result;
    }

    @Override
    public List<Restaurante> findComplexo(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = new StringBuilder();
        var params = new HashMap<String, Object>();

        jpql.append("from Restaurante where 0 = 0 ");

        if (StringUtils.hasLength(nome)) {
            jpql.append("where nome like :nome ");
            params.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaFreteInicial ");
            params.put("taxaFreteInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFreteFinal");
            params.put("taxaFreteFinal", taxaFreteFinal);
        }

        final TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);

        params.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }
}
