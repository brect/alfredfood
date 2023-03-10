package com.padawanbr.alfredfood.api.model.request;

import com.padawanbr.alfredfood.core.validation.FileContentType;
import com.padawanbr.alfredfood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "5000KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public MultipartFile file;

    @NotBlank
    public String descricao;
}
