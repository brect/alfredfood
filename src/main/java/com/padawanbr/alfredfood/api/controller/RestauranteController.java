package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.api.mapper.RestauranteDomainMapper;
import com.padawanbr.alfredfood.api.mapper.RestauranteModelMapper;
import com.padawanbr.alfredfood.api.model.request.RestauranteRequest;
import com.padawanbr.alfredfood.api.model.response.RestauranteDTO;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.CidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.CozinhaNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.RestauranteRepository;
import com.padawanbr.alfredfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private RestauranteModelMapper restauranteModelMapper;

    @Autowired
    private RestauranteDomainMapper restauranteDomainMapper;

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        RestauranteDTO restauranteDTO = restauranteModelMapper.toModel(restaurante);

        if (restaurante != null) {
            return ResponseEntity.ok(restauranteDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar-primeiro")
    public ResponseEntity<Restaurante> buscarPrimeiro() {
        final Optional<Restaurante> restaurante = restauranteRepository.buscarPrimeiro();

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> buscar(@Param("nome") String nome) {

        final List<Restaurante> restaurantes = restauranteRepository.findComFreteGratis(nome);

        return restaurantes;
    }

    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteModelMapper.toCollectionModel(restauranteService.listar());
    }


    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid RestauranteRequest request) {
        try {

            Restaurante restaurante = restauranteDomainMapper.toDomainObject(request);

            final Restaurante restauranteSalvo = restauranteService.salvar(restaurante);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(restauranteSalvo.getId()).toUri();

            return ResponseEntity.created(location).body(restauranteModelMapper.toModel(restauranteSalvo));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody @Valid RestauranteRequest request) {
        try {
            Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

            restauranteDomainMapper.copyToDomainObject(request, restauranteAtual);

            final Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual);
            return ResponseEntity.ok(restauranteModelMapper.toModel(restauranteSalvo));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<?> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restauranteId}/desativado")
    public ResponseEntity<?> desativar(@PathVariable Long restauranteId) {
        restauranteService.desativar(restauranteId);
        return ResponseEntity.noContent().build();
    }
}
