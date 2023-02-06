package com.padawanbr.alfredfood.infrastructure.service.report;

import com.padawanbr.alfredfood.domain.filter.VendaDiariaFilter;
import com.padawanbr.alfredfood.domain.service.VendaQueryService;
import com.padawanbr.alfredfood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;


    @Override
    public byte[] emitirRelatorioVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
        try {
            final var resource = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var params = new HashMap<String, Object>();
            params.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaQueryService.consultarVendasDiarias(vendaDiariaFilter, timeOffset);

            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            final JasperPrint jasperPrint = JasperFillManager.fillReport(resource, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception ex) {
            throw new ReportException("Não foi possivel emitir relatório de vendas diárias", ex);
        }

    }


}
