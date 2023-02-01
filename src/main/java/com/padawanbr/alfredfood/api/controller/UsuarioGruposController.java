package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.FormaPagamentoModelMapper;
import com.padawanbr.alfredfood.api.mapper.GruposModelMapper;
import com.padawanbr.alfredfood.api.model.response.GrupoDTO;
import com.padawanbr.alfredfood.domain.model.Usuario;
import com.padawanbr.alfredfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGruposController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GruposModelMapper gruposModelMapper;


    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
        final Usuario usuario = usuarioService.consultar(usuarioId);

        return gruposModelMapper.toCollectionModel(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<?> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.desassociarFormaPagamento(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<?> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarFormaPagamento(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }


}
