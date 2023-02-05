package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.ProdutoDomainMapper;
import com.padawanbr.alfredfood.api.mapper.ProdutoModelMapper;
import com.padawanbr.alfredfood.api.model.request.ProdutoRequest;
import com.padawanbr.alfredfood.api.model.response.ProdutoDTO;
import com.padawanbr.alfredfood.api.model.response.RestauranteDTO;
import com.padawanbr.alfredfood.domain.model.Produto;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.service.ProdutoService;
import com.padawanbr.alfredfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoModelMapper produtoModelMapper;
    @Autowired
    private ProdutoDomainMapper produtoDomainMapper;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean ativo) {
        final Restaurante restaurante = restauranteService.consultar(restauranteId);

        List<Produto> produtos = null;

        if (ativo) {
            produtos = produtoService.consultarAtivos(restaurante);
        } else {
            produtos = produtoService.consultarTodos(restaurante);
        }

        return produtoModelMapper.toCollectionModel(produtos);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        final Produto produto = produtoService.consultar(restauranteId, produtoId);

        return ResponseEntity.ok(produtoModelMapper.toModel(produto));
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@PathVariable Long restauranteId,
                                       @RequestBody @Valid ProdutoRequest request) {
        Restaurante restaurante = restauranteService.consultar(restauranteId);

        Produto produto = produtoDomainMapper.toDomainObject(request);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoModelMapper.toModel(produto));
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                       @RequestBody @Valid ProdutoRequest request) {
        Produto produtoAtual = produtoService.consultar(restauranteId, produtoId);

        produtoDomainMapper.copyToDomainObject(request, produtoAtual);

        produtoAtual = produtoService.salvar(produtoAtual);

        return ResponseEntity.ok(produtoModelMapper.toModel(produtoAtual));
    }

}
