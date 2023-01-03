package com.padawanbr.alfredfood.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

}