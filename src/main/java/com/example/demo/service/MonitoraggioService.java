package com.example.demo.service;

import com.example.demo.controller.MonitoraggioController;
import com.example.demo.entity.*;
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
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoraggioService {

    private final MonitoraggioRepository monitoraggioRepository;

    private Properties configProperties = loadProperties();

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

    public List<Monitoraggio> datasetPerTema() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GroupListResponse> response = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/group_list", HttpMethod.GET, new HttpEntity<>(createHeaders()), GroupListResponse.class);
        List<Monitoraggio> result = new ArrayList<>();
        for (String group : response.getBody().getResult()) {
            ResponseEntity<GroupShowResponse> a = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/group_show?id=" + group, HttpMethod.GET, new HttpEntity<>(createHeaders()), GroupShowResponse.class);
            Monitoraggio nDatasetPerTemaResponse = new Monitoraggio();
            nDatasetPerTemaResponse.setDescrizione(group);
            nDatasetPerTemaResponse.setNumero(a.getBody().getResult().getPackage_count());
            result.add(nDatasetPerTemaResponse);
        }
        return result;
    }

    public List<Monitoraggio> datasetPerOrganizzazione() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrganizationListResponse> response = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/organization_list", HttpMethod.GET, new HttpEntity<>(createHeaders()), OrganizationListResponse.class);
        List<Monitoraggio> result = new ArrayList<>();
        for (String organization : response.getBody().getResult()) {
            ResponseEntity<OrganizationShowResponse> a = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/organization_show?id=" + organization, HttpMethod.GET, new HttpEntity<>(createHeaders()), OrganizationShowResponse.class);
            Monitoraggio nDatasetPerTemaResponse = new Monitoraggio();
            nDatasetPerTemaResponse.setDescrizione(organization);
            nDatasetPerTemaResponse.setNumero(a.getBody().getResult().getPackage_count());
            result.add(nDatasetPerTemaResponse);
        }
        return result;
    }

    public List<Monitoraggio> licenzeUtilizzate() {
        List<Licenza> licenze = monitoraggioRepository.estraiLicenze();
        RestTemplate restTemplate = new RestTemplate();
        List<Monitoraggio> result = new ArrayList<>();
        ResponseEntity<LicenseListResponse> listaLicenze = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/organization_show?id=", HttpMethod.GET, new HttpEntity<>(createHeaders()), LicenseListResponse.class);
        for (Licenza licenza : licenze) {
            Monitoraggio nDatasetPerTemaResponse = new Monitoraggio();
            nDatasetPerTemaResponse.setDescrizione(findTitleByLicenseId(licenza.getId(), listaLicenze.getBody()));
            nDatasetPerTemaResponse.setNumero(licenza.getNumero());
            result.add(nDatasetPerTemaResponse);
        }
        return result;
    }

    private String findTitleByLicenseId(String id, LicenseListResponse listaLicenze) {
        for (LicenseListResponse.ResultJsonElement resultJsonElement : listaLicenze.getResult()) {
            if (id.equalsIgnoreCase(resultJsonElement.getId())) {
                return resultJsonElement.getTitle();
            }
        }
        return "";
    }

    public List<Monitoraggio> formatiPiuUtilizzati() throws ParseException {
        return monitoraggioRepository.estraiFormatiUtilizzati();
    }

    public List<Monitoraggio> temiPiuCliccati() {
        List<Tema> temi = monitoraggioRepository.estraiTemiCliccati();
        RestTemplate restTemplate = new RestTemplate();
        List<Monitoraggio> result = new ArrayList<>();
        ResponseEntity<GroupListResponse> gruppi = restTemplate.exchange(configProperties.getProperty("server.path") + "api/3/action/group_list", HttpMethod.GET, new HttpEntity<>(createHeaders()), GroupListResponse.class);
        for (String gruppo : gruppi.getBody().getResult()) {
            Tema tema = abcd(configProperties.getProperty("theme.group.baseurl") + gruppo, temi);
            Monitoraggio nDatasetPerTemaResponse = new Monitoraggio();
            nDatasetPerTemaResponse.setDescrizione(tema.getId());
            nDatasetPerTemaResponse.setNumero(tema.getNumero());
            result.add(nDatasetPerTemaResponse);
        }
        return result;
    }

    private Tema abcd(String gruppo, List<Tema> temi) {
        for (Tema tema : temi) {
            if (gruppo.equalsIgnoreCase(tema.getUrl())) {
                return tema;
            }
        }
        return new Tema();
    }

    public static Properties loadProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        }catch(Exception e){
            return properties;
        }
        return properties;
    }

    HttpHeaders createHeaders() {
        return new HttpHeaders() {{
            String auth = configProperties.getProperty("server.username") + ":" + configProperties.getProperty("server.password");
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
