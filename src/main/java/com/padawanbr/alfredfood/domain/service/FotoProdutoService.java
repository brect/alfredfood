package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.FotoProdutoNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.FotoProduto;
import com.padawanbr.alfredfood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
        final Optional<FotoProduto> fotoProdutoSalvo = produtoRepository.findFotoById(fotoProduto.getRestauranteId(), fotoProduto.getProdutoId());

        final String nomeArquivoAtualizado = fotoStorageService.getNomeArquivo(fotoProduto.getNomeArquivo());

        String nomeArquivoExistente = null;

        if (fotoProdutoSalvo.isPresent()) {
            nomeArquivoExistente = fotoProdutoSalvo.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoSalvo.get());
        }

        fotoProduto.setNomeArquivo(nomeArquivoAtualizado);
        final FotoProduto fotoSalva = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        final FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        fotoStorageService.atualizar(novaFoto, nomeArquivoExistente);

        return fotoSalva;
    }

    public FotoProduto consultar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(
                () -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId)
        );
    }
}
