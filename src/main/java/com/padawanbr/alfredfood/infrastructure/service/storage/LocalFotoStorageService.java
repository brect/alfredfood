package com.padawanbr.alfredfood.infrastructure.service.storage;

import com.padawanbr.alfredfood.core.storage.StorageProperties;
import com.padawanbr.alfredfood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public ConsultaFoto recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);

            return ConsultaFoto.builder()
                    .inputStream(
                            Files.newInputStream(path))
                    .build();
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
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }


}
