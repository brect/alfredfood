package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.model.request.FotoProdutoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                             FotoProdutoRequest request){

        final var nomeArquivo = UUID.randomUUID().toString().concat("_").concat(request.getMultipartFile().getName());
        final Path arquivoFoto = Path.of("/Users/limasbr/Desktop/upload", nomeArquivo);

        try {
            request.getMultipartFile().transferTo(arquivoFoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
