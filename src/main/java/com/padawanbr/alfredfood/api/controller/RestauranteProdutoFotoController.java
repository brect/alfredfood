package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.FotoProdutoModelMapper;
import com.padawanbr.alfredfood.api.model.request.FotoProdutoRequest;
import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.model.Produto;
import com.padawanbr.alfredfood.domain.service.FotoProdutoService;
import com.padawanbr.alfredfood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {


    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FotoProdutoService fotoProdutoService;
    @Autowired
    private FotoProdutoModelMapper fotoProdutoModelMapper;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @Valid FotoProdutoRequest request) throws IOException {

        final Produto produto = produtoService.consultar(restauranteId, produtoId);

        MultipartFile multipartFile = request.getFile();

        final FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(request.getDescricao());
        fotoProduto.setContentType(multipartFile.getContentType());
        fotoProduto.setTamanho(multipartFile.getSize());
        fotoProduto.setNomeArquivo(multipartFile.getName());


        final FotoProduto fotoProdutoSalvo = fotoProdutoService.salvar(fotoProduto, multipartFile.getInputStream());

        return ResponseEntity.ok(fotoProdutoModelMapper.toModel(fotoProdutoSalvo));

    }
}
