package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.repository.EstadoRepository;
import com.padawanbr.alfredfood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }

        return ResponseEntity.notFound().build();
    }

    private List<Estado> listar(){
        return estadoRepository.findAll();
    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Estado adicionar(@RequestBody Estado estado) {
//        return cadastroEstado.salvar(estado);
//    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,
                                            @RequestBody Estado estado) {
        Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);

        if (estadoAtual.isPresent()) {
            BeanUtils.copyProperties(estado, estadoAtual, "id");

            final Estado estadoSalvo = cadastroEstado.salvar(estadoAtual.get());
            return ResponseEntity.ok(estadoSalvo);
        }

        return ResponseEntity.notFound().build();
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
