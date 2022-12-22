package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

     List<Restaurante> listarRestaurantes();
     Restaurante adicionarRestaurante(Restaurante restaurante);
     Restaurante buscarRestaurantePorId(Long id);
     void removerRestaurante(Restaurante restaurante);

}
