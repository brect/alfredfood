package com.padawanbr.alfredfood.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

}
