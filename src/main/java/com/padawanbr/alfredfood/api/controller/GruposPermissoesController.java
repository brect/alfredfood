package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.GruposDomainMapper;
import com.padawanbr.alfredfood.api.mapper.GruposModelMapper;
import com.padawanbr.alfredfood.api.mapper.PermissaoModelMapper;
import com.padawanbr.alfredfood.api.model.request.GrupoRequest;
import com.padawanbr.alfredfood.api.model.response.GrupoDTO;
import com.padawanbr.alfredfood.api.model.response.PermissaoDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EstadoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Grupo;
import com.padawanbr.alfredfood.domain.service.GruposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GruposPermissoesController {


    @Autowired
    private GruposService gruposService;

    @Autowired
    private PermissaoModelMapper permissaoModelMapper;


    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
        final Grupo grupo = gruposService.consultar(grupoId);
        return permissaoModelMapper.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<?> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        gruposService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<?> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        gruposService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
