package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.UsuarioModelMapper;
import com.padawanbr.alfredfood.api.model.response.FormaPagamentoDTO;
import com.padawanbr.alfredfood.api.model.response.UsuarioDTO;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioModelMapper usuarioModelMapper;


    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        final Restaurante restaurante = restauranteService.consultar(restauranteId);

        return usuarioModelMapper.toCollectionModel(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<?> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        restauranteService.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
