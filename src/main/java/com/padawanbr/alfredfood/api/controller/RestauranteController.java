package com.padawanbr.alfredfood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.RestauranteRepository;
import com.padawanbr.alfredfood.domain.service.RestauranteService;
import com.padawanbr.alfredfood.infrastructure.specification.RestauranteComFreteGratisSpec;
import com.padawanbr.alfredfood.infrastructure.specification.RestauranteComNomeSemelhanteSpec;
import com.padawanbr.alfredfood.infrastructure.specification.RestauranteSpecs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> buscar(@Param("nome") String nome) {

        final RestauranteComFreteGratisSpec restauranteComFreteGratisSpec = new RestauranteComFreteGratisSpec();
        final RestauranteComNomeSemelhanteSpec restauranteComNomeSemelhanteSpec = new RestauranteComNomeSemelhanteSpec(nome);

        final List<Restaurante> restaurantes = restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));

        return restaurantes;

    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @PostMapping
    public ResponseEntity<?> adicionar (@RequestBody Restaurante restaurante){
        try {
            final Restaurante restauranteSalvo = restauranteService.salvar(restaurante);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(restauranteSalvo.getId()).toUri();

            return ResponseEntity.created(location).body(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
                final Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restauranteSalvo);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                       @RequestBody Map<String, Object> params) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        if (!restauranteAtual.isPresent()) {
            return ResponseEntity.notFound().build();
        }


        merge(params, restauranteAtual.get());

        return atualizar(restauranteId, restauranteAtual.get());
    }

    private void merge(Map<String, Object> params, Restaurante restauranteAtual) {

        ObjectMapper objectMapper = new ObjectMapper();

        Restaurante restauranteOrigem = objectMapper.convertValue(params, Restaurante.class);

        params.forEach((chave, valor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, chave);
            field.setAccessible(true);

            Object object = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, object, valor);
        });
    }
}
