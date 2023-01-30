package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.GruposDomainMapper;
import com.padawanbr.alfredfood.api.mapper.GruposModelMapper;
import com.padawanbr.alfredfood.api.model.request.GrupoRequest;
import com.padawanbr.alfredfood.api.model.response.GrupoDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EstadoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.Grupo;
import com.padawanbr.alfredfood.domain.service.GruposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GruposController {


    @Autowired
    private GruposService gruposService;

    @Autowired
    private GruposDomainMapper gruposDomainMapper;
    @Autowired
    private GruposModelMapper gruposModelMapper;


    @GetMapping
    public List<GrupoDTO> listar() {
        return gruposModelMapper.toCollectionModel(gruposService.consultarTodos());
    }


    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> buscar(@PathVariable Long grupoId) {
        Grupo grupo = gruposService.consultar(grupoId);
        return ResponseEntity.ok(gruposModelMapper.toModel(grupo));
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> adicionar(@RequestBody @Valid GrupoRequest request) {
        try {
            final Grupo grupo = gruposDomainMapper.toDomainObject(request);
            final Grupo grupoSalvo = gruposService.salvar(grupo);

            return ResponseEntity.status(HttpStatus.CREATED).body(gruposModelMapper.toModel(grupoSalvo));
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }


    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long grupoId,
                                               @RequestBody @Valid GrupoRequest request) {
        Grupo grupoAtual = gruposService.consultar(grupoId);

        gruposDomainMapper.copyToDomainObject(request, grupoAtual);

        final Grupo grupoSalvo= gruposService.salvar(grupoAtual);

        try {
            return ResponseEntity.ok(gruposModelMapper.toModel(grupoSalvo));
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<?> remover(@PathVariable Long grupoId) {
        gruposService.excluir(grupoId);
        return ResponseEntity.noContent().build();
    }
}
