package com.padawanbr.alfredfood.infrastructure.service.storage;

import com.padawanbr.alfredfood.core.storage.StorageProperties;
import com.padawanbr.alfredfood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;


    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }
}
