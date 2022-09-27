package com.example.demo.controller;

import com.example.demo.entity.DatoGraficoDto;
import com.example.demo.entity.FormatoFile;
import com.example.demo.entity.Monitoraggio;
import com.example.demo.entity.TipoGrafico;
import com.example.demo.service.MonitoraggioService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.demo.entity.FormatoFile.PDF;
import static com.example.demo.entity.FormatoFile.XLS;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MonitoraggioController {

    private final MonitoraggioService monitoraggioService;

    @GetMapping(value = "/monitoraggio")
    public List<Monitoraggio> monitoraggio(@RequestParam String tipo, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA) throws ParseException {
        System.out.println("Chiamata monitoraggio ricevuta");
        return monitoraggioService.estrai(tipo, parse(dataDa), parse(dataA));
    }

    //@RequestParam String tipoGrafico, @RequestParam String formatoFile, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA,
    @GetMapping(value = "/esporta")
    public void esporta( HttpServletResponse response) throws Exception {
        Date da = parse("0001-01-01");
        Date a = parse("9999-12-31");
        TipoGrafico tipoGraficoEnum = TipoGrafico.valueOf("RISORSE_PIU_SCARICATE");
        FormatoFile formatoFileEnum = FormatoFile.valueOf("PDF");

        List<Monitoraggio> datiOutput = monitoraggioService.estrai("RISORSE_PIU_SCARICATE", da, a);
        List<DatoGraficoDto> dati = toDto(datiOutput);
        Collections.reverse(dati);

        switch ("PDF") {
            case "PDF":
                esportaPdf(response, tipoGraficoEnum, dati);
                break;
            case "XLS":
                esportaXls(response, tipoGraficoEnum, dati);
                break;
            default:
                throw new RuntimeException("formato file");

        }
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
    }

    @GetMapping(value = "/dummy")
    public void dummy( HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
    }

    private Date parse(String data) throws ParseException {
        if (data != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(data);
        }
        return null;

    }

    private void esportaXls(HttpServletResponse response, TipoGrafico tipoGrafico, List<DatoGraficoDto> dati) throws Exception {
        byte[] data = generateReport(response, tipoGrafico, dati, XLS);
    }

    private void esportaPdf(HttpServletResponse response, TipoGrafico tipoGrafico, List<DatoGraficoDto> dati) throws Exception {
        byte[] data = generateReport(response, tipoGrafico, dati, PDF);
    }

    private byte[] generateReport(HttpServletResponse response, TipoGrafico tipoGrafico, List<DatoGraficoDto> dati, FormatoFile formatoFile) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Map<String, Object> parametri = new HashMap<>();
        parametri.put("TITOLO_GRAFICO", "TITOLO GRAFICO");
//        String imagePath = Objects.requireNonNull(MonitoraggioController.class.getClassLoader().getResource("C:\\Users\\SIMONCELLIN\\IdeaProjects\\testing-demo\\src\\main\\resources\\layout_set_logo.png")).toString();
//        parametri.put("PATH_IMG", imagePath);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dati);
        InputStream reportStream = MonitoraggioController.class.getClassLoader().getResourceAsStream("reportLod.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(reportStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametri, beanColDataSource);

        if (formatoFile.equals(PDF)) {
            JRPdfExporter exporterPdf = new JRPdfExporter();
            exporterPdf.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

            exporterPdf.exportReport();
        } else if (formatoFile.equals(XLS)) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

            exporter.exportReport();
        } else {
            throw new RuntimeException("formatoFile");
        }

        return baos.toByteArray();
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

}
