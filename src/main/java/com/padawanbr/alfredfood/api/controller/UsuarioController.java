package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.UsuarioDomainMapper;
import com.padawanbr.alfredfood.api.mapper.UsuarioModelMapper;
import com.padawanbr.alfredfood.api.model.request.SenhaRequest;
import com.padawanbr.alfredfood.api.model.request.UsuarioComSenhaRequest;
import com.padawanbr.alfredfood.api.model.request.UsuarioRequest;
import com.padawanbr.alfredfood.api.model.response.UsuarioDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EstadoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Usuario;
import com.padawanbr.alfredfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioDomainMapper usuarioDomainMapper;
    @Autowired
    private UsuarioModelMapper usuarioModelMapper;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioModelMapper.toCollectionModel(usuarioService.consultarTodos());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.consultar(usuarioId);
        return ResponseEntity.ok(usuarioModelMapper.toModel(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionar(@RequestBody @Valid UsuarioComSenhaRequest request) {
        try {
            final Usuario usuario = usuarioDomainMapper.toDomainObject(request);
            final Usuario usuarioSalvo = usuarioService.salvar(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelMapper.toModel(usuarioSalvo));
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long usuarioId,
                                              @RequestBody @Valid UsuarioRequest request) {
        Usuario usuarioAtual = usuarioService.consultar(usuarioId);

        usuarioDomainMapper.copyToDomainObject(request, usuarioAtual);

        final Usuario usuarioSalvo= usuarioService.salvar(usuarioAtual);

        try {
            return ResponseEntity.ok(usuarioModelMapper.toModel(usuarioSalvo));
        } catch (EstadoNaoEncontradoException ex) {
            throw new BussinesException(ex.getMessage(), ex);
        }
    }


    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaRequest request) {
        usuarioService.alterarSenha(usuarioId, request.getSenhaAtual(), request.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}
