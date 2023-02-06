package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter);
}
