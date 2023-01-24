package com.padawanbr.alfredfood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.ValidatitionException;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import com.padawanbr.alfredfood.domain.repository.RestauranteRepository;
import com.padawanbr.alfredfood.domain.service.RestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
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
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            final Restaurante restauranteSalvo = restauranteService.salvar(restaurante);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(restauranteSalvo.getId()).toUri();

            return ResponseEntity.created(location).body(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            final Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual);
            return ResponseEntity.ok(restauranteSalvo);

        } catch (EntidadeNaoEncontradaException ex) {
            throw new BussinesException(ex.getMessage());
        }
    }

//    @PatchMapping("/{restauranteId}")
//    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
//                                              @RequestBody Map<String,
//                                                      Object> params,
//                                              HttpServletRequest httpServletRequest) {
//        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);
//
//        if (restauranteAtual == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        merge(params, restauranteAtual, httpServletRequest);
//
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteId, restauranteAtual);
//    }


    @PatchMapping("/{restauranteId}")
    public  ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");

        return atualizar(restauranteId, restauranteAtual);
    }

    private void validate(Restaurante restauranteAtual, String objetctName) {
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(restauranteAtual, objetctName);
        smartValidator.validate(restauranteAtual, beanPropertyBindingResult);

        if (beanPropertyBindingResult.hasErrors()) {
            throw new ValidatitionException(beanPropertyBindingResult);
        }
    }

    private void merge(Map<String, Object> params, Restaurante restauranteAtual, HttpServletRequest httpServletRequest) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(params, Restaurante.class);

            params.forEach((chave, valor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, chave);
                field.setAccessible(true);

                Object object = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteAtual, object);
            });
        } catch (IllegalArgumentException ex) {
            Throwable throwable = ExceptionUtils.getRootCause(ex);

            throw new HttpMessageNotReadableException(ex.getMessage(), throwable, servletServerHttpRequest);
        }
    }
}
