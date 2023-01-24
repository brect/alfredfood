package com.padawanbr.alfredfood.domain.model;

import com.padawanbr.alfredfood.core.validation.Groups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Estado {

    @NotNull(groups = Groups.EstadoID.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

}