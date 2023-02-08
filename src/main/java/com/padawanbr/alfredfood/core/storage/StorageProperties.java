package com.padawanbr.alfredfood.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("alfredfood.storage")
public class StorageProperties {

    private Local local;
    private S3 s3;

    @Getter
    @Setter
    public class S3 {
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private String region;
        private String diretorioFotos;
    }

    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;
    }
}
