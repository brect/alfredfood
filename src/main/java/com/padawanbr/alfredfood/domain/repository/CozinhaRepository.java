package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

     List<Cozinha> listar();
     Cozinha adicionarCozinha(Cozinha cozinha);
     Cozinha buscarCozinhaPorId(Long id);
     void removerCozinha(Long id);

}
