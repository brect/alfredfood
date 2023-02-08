package com.padawanbr.alfredfood.infrastructure.service.storage;

import com.padawanbr.alfredfood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${alfredfood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path path = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(),
                    Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar arquivo");
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path path = getArquivoPath(nomeArquivo);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir arquivo");
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }


}
