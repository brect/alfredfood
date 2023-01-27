package com.padawanbr.alfredfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.padawanbr.alfredfood.domain.model.Restaurante;

import java.util.List;

public abstract class CozinhaMixin {


    @JsonIgnore
    private List<Restaurante> restaurantes;

}
