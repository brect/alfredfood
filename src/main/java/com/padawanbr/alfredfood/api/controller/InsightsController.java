package com.padawanbr.alfredfood.api.controller;


import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.service.VendaQueryService;
import com.padawanbr.alfredfood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> consultarVendasDiarias(VendaDiariaFilter filter,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return ResponseEntity.ok(vendaQueryService.consultarVendasDiarias(filter, timeOffset));
    }

    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<?> consultarVendasDiariasPDF(VendaDiariaFilter filter,
                                                        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        final var relatorioVendasDiarias = vendaReportService.emitirRelatorioVendasDiarias(filter, timeOffset);

        final var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(relatorioVendasDiarias);
    }


}