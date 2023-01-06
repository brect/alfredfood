package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface CustomRestauranteRepository {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    List<Restaurante> findDinamicoJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    List<Restaurante> findCriteriaAPI(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
