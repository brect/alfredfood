package com.padawanbr.alfredfood.api.controller;

import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    private List<Estado> listar(){
        return estadoRepository.listar();
    }
}
