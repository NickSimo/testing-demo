package com.example.demo.service;

import com.example.demo.controller.MonitoraggioController;
import com.example.demo.entity.*;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MonitoraggioRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoraggioService {

    private final MonitoraggioRepository monitoraggioRepository;

    public List<Monitoraggio> estrai(String tipo, String dataDa, String dataA) throws ParseException {
        if (dataDa != null && dataA != null) {
            return monitoraggioRepository.estraiConRangeDate(tipo, parse(dataDa), parse(dataA));
        }
        return monitoraggioRepository.estrai(tipo);
    }

    public void esporta(HttpServletResponse response, String formato, String tipo, String dataDa, String dataA) throws JRException, IOException, ParseException {
        FormatoFile formatoFileEnum = FormatoFile.valueOf(formato);

        List<Monitoraggio> datiOutput = estrai(tipo, dataDa, dataA);
        List<DatoGraficoDto> dati = toDto(datiOutput);

        InputStream reportStream = MonitoraggioController.class.getClassLoader().getResourceAsStream("reportLod.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(reportStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> parametri = new HashMap<>();
        String imagePath = Objects.requireNonNull(MonitoraggioController.class.getClassLoader().getResource("layout_set_logo.png")).toString();
        parametri.put("PATH_IMG", imagePath);
        parametri.put("TITOLO_GRAFICO", TipoGrafico.valueOf(tipo).getDescrizione());


        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dati);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametri, beanColDataSource);

        switch (formatoFileEnum) {
            case PDF:
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
                break;
            case XLS:
                JRXlsxExporter exporter = new JRXlsxExporter();
                SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
                reportConfigXLS.setSheetNames(new String[]{"sheet1"});
                exporter.setConfiguration(reportConfigXLS);
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");
                response.setContentType("application/octet-stream");
                exporter.exportReport();
                break;
            default:
                break;
        }
    }

    private List<DatoGraficoDto> toDto(List<Monitoraggio> dati) {
        Function<Monitoraggio, DatoGraficoDto> fn = x -> {
            DatoGraficoDto dto = new DatoGraficoDto();
            dto.setTitolo(x.getDescrizione());
            dto.setValore(x.getNumero());
            return dto;
        };
        return dati.stream().map(fn).collect(Collectors.toList());
    }

    private Date parse(String data) throws ParseException {
        if (data != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(data);
        }
        return null;

    }

    public static void main(String[] args) {
        System.out.println(TipoGrafico.valueOf("RESOURCE_DOWNLOAD").getDescrizione());
    }


}
