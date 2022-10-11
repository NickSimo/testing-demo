package com.example.demo.controller;

import com.example.demo.entity.Monitoraggio;
import com.example.demo.entity.NDatasetPerTemaResponse;
import com.example.demo.entity.TipoGrafico;
import com.example.demo.service.MonitoraggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MonitoraggioController {

    private final MonitoraggioService monitoraggioService;
    RestTemplate restTemplate;

    @GetMapping(value = "/monitoraggio")
    public List<Monitoraggio> monitoraggio(@RequestParam String tipo, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA) throws ParseException {
        switch (TipoGrafico.valueOf(tipo)) {
            case RESOURCE_DOWNLOAD:
            case RESOURCE_VIEW:
            case ORGANIZATION_VIEW:
            case DATASET_VIEW:
            case PAGE_VIEW:
                return monitoraggioService.estrai(tipo, dataDa, dataA);
            case TEMI_VIEW:
                return monitoraggioService.temiPiuCliccati();
            case DATASET_TEMA:
                return monitoraggioService.datasetPerTema();
            case FORMATI_USED:
                return monitoraggioService.formatiPiuUtilizzati();
            case ORGANIZATION_DATASET:
                return monitoraggioService.datasetPerOrganizzazione();
            case LICENZE_USED:
                return monitoraggioService.licenzeUtilizzate();
            default:
                return null;
        }
    }

    @GetMapping(value = "/esporta")
    public void esporta(HttpServletResponse response, @RequestParam String formato, @RequestParam String tipo, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA) throws Exception {
        monitoraggioService.esporta(response, formato, tipo, dataDa, dataA);
    }


}
