package com.padawanbr.alfredfood.domain.service;


import com.padawanbr.alfredfood.domain.exception.CozinhaNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CozinhaServiceIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void deve_atribuirID_quando_cadastrar_cozinha_com_sucesos(){

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Chinesa");

        final Cozinha cozinhaSalva = cozinhaService.salvar(cozinha);

        assertThat(cozinhaSalva).isNotNull();
        assertThat(cozinhaSalva.getId()).isNotNull();
    }

    @Test
    public void deve_falhar_quando_cadastrar_cozinha_sem_nome(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cozinhaService.salvar(cozinha);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deve_falhar_quando_excluir_cozinha_em_uso(){
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
                    cozinhaService.excluir(1L);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deve_falhar_quando_excluir_cozinha_inexistente(){

        CozinhaNaoEncontradaException erroEsperado =
                Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
                    cozinhaService.excluir(10L);
                });


        assertThat(erroEsperado).isNotNull();
    }

}
