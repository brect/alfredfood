package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface CustomProdutoRepository {
    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto fotoProduto);
}
