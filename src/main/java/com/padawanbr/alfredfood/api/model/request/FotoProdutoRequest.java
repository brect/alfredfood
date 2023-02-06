package com.padawanbr.alfredfood.api.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoRequest {

    public MultipartFile multipartFile;
    public String descricao;
}
