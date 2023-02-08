package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.FotoProdutoModelMapper;
import com.padawanbr.alfredfood.api.model.request.FotoProdutoRequest;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.model.Produto;
import com.padawanbr.alfredfood.domain.service.FotoProdutoService;
import com.padawanbr.alfredfood.domain.service.FotoStorageService;
import com.padawanbr.alfredfood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FotoProdutoService fotoProdutoService;
    @Autowired
    private FotoProdutoModelMapper fotoProdutoModelMapper;
    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = fotoProdutoService.consultar(restauranteId, produtoId);
        return ResponseEntity.ok(fotoProdutoModelMapper.toModel(fotoProduto));

    }

    @GetMapping
    public ResponseEntity<?> consultarArquivoFoto(@PathVariable Long restauranteId,
                                                  @PathVariable Long produtoId,
                                                  @RequestHeader(name = "Accept") String acceptHeader) {
        try {
            FotoProduto fotoProduto = fotoProdutoService.consultar(restauranteId, produtoId);

            final MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeAcceptList = MediaType.parseMediaTypes(acceptHeader);

            verificaCompatibilidadeMediaType(mediaType, mediaTypeAcceptList);

            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException | HttpMediaTypeNotAcceptableException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificaCompatibilidadeMediaType(MediaType mediaType, List<MediaType> mediaTypeAcceptList) throws HttpMediaTypeNotAcceptableException {
        boolean isAccept = mediaTypeAcceptList.stream()
                .anyMatch(
                        mediaTypeAccept -> mediaTypeAccept.isCompatibleWith(mediaType)
                );

        if (!isAccept) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAcceptList);
        }
    }

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

    @DeleteMapping
    public ResponseEntity<?> excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        fotoProdutoService.excluir(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
}
