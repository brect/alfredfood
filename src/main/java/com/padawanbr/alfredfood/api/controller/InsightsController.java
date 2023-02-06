package com.padawanbr.alfredfood.api.controller;


import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insights")
public class InsightsController {

    @Autowired
    private VendaQueryService vendaQueryService;


    @GetMapping("/vendas-diarias")
    private ResponseEntity<?> consultarVendasDiarias(VendaDiariaFilter filter,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
        return ResponseEntity.ok(vendaQueryService.consultarVendasDiarias(filter, timeOffset));
    }
}
