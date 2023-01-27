package com.padawanbr.alfredfood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.padawanbr.alfredfood.api.model.mixin.CidadeMixin;
import com.padawanbr.alfredfood.api.model.mixin.CozinhaMixin;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
