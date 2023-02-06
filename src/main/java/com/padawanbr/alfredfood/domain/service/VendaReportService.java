package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirRelatorioVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);
}
