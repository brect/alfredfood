package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.EstadoDomainMapper;
import com.padawanbr.alfredfood.api.mapper.EstadoModelMapper;
import com.padawanbr.alfredfood.api.model.request.EstadoRequest;
import com.padawanbr.alfredfood.api.model.response.EstadoDTO;
import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.repository.EstadoRepository;
import com.padawanbr.alfredfood.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService cadastroEstado;

    @Autowired
    private EstadoModelMapper estadoModelMapper;

    @Autowired
    private EstadoDomainMapper estadoDomainMapper;


    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.consultar(estadoId);

        return ResponseEntity.ok(estadoModelMapper.toModel(estado));
    }

    @GetMapping
    private List<EstadoDTO> listar() {
        return estadoModelMapper.toCollectionModel(estadoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoRequest request) {
        Estado estado = estadoDomainMapper.toDomainObject(request);

        estado = cadastroEstado.salvar(estado);

        return estadoModelMapper.toModel(estado);
    }


    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long estadoId,
                                            @RequestBody EstadoRequest request) {
        Estado estadoAtual = cadastroEstado.consultar(estadoId);

        estadoDomainMapper.copyToDomainObject(request, estadoAtual);

        final Estado estadoSalvo = cadastroEstado.salvar(estadoAtual);

        return ResponseEntity.ok(estadoModelMapper.toModel(estadoSalvo));
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstado.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


}
