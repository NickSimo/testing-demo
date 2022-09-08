package com.example.demo.controller;

import com.example.demo.entity.Monitoraggio;
import com.example.demo.service.EstraiClientiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MonitoraggioController {

    private final EstraiClientiService estraiClientiService;

    @GetMapping(value = "/monitoraggio")
    public List<Monitoraggio> monitoraggio(@RequestParam String tipo) {
        System.out.println("Chiamata monitoraggio ricevuta");
        switch (tipo){
            case "risorsePiuScaricate":
                return  asList(
                        new Monitoraggio("Risorsa piu scaricata 1", 50),
                        new Monitoraggio("Risorsa piu scaricata 2", 31),
                        new Monitoraggio("Risorsa piu scaricata 3", 30),
                        new Monitoraggio("Risorsa piu scaricata 4", 21),
                        new Monitoraggio("Risorsa piu scaricata 5", 20),
                        new Monitoraggio("Risorsa piu scaricata 6", 5));
            case "risorsePiuVisualizzate":
                return  asList(
                        new Monitoraggio("Risorsa piu visualizzata 1", 50),
                        new Monitoraggio("Risorsa piu visualizzata 2", 31),
                        new Monitoraggio("Risorsa piu visualizzata 3", 30),
                        new Monitoraggio("Risorsa piu visualizzata 4", 21),
                        new Monitoraggio("Risorsa piu visualizzata 5", 20),
                        new Monitoraggio("Risorsa piu visualizzata 6", 5));
            case "datasetPiuVisualizzati":
                return  asList(
                        new Monitoraggio("Dataset piu visualizzato 1", 50),
                        new Monitoraggio("Dataset piu visualizzato 2", 31),
                        new Monitoraggio("Dataset piu visualizzato 3", 30),
                        new Monitoraggio("Dataset piu visualizzato 4", 21),
                        new Monitoraggio("Dataset piu visualizzato 5", 20),
                        new Monitoraggio("Dataset piu visualizzato 6", 5));
            case "organizzazioniPiuVisualizzate":
                return  asList(
                        new Monitoraggio("Organizzazione piu visualizzata 1", 50),
                        new Monitoraggio("Organizzazione piu visualizzata 2", 31),
                        new Monitoraggio("Organizzazione piu visualizzata 3", 30),
                        new Monitoraggio("Organizzazione piu visualizzata 4", 21),
                        new Monitoraggio("Organizzazione piu visualizzata 5", 20),
                        new Monitoraggio("Organizzazione piu visualizzata 6", 5));
            case "paginePiuVisualizzate":
                return  asList(
                        new Monitoraggio("pagina piu visualizzata 1", 50),
                        new Monitoraggio("pagina piu visualizzata 2", 31),
                        new Monitoraggio("pagina piu visualizzata 3", 30),
                        new Monitoraggio("pagina piu visualizzata 4", 21),
                        new Monitoraggio("pagina piu visualizzata 5", 20),
                        new Monitoraggio("pagina piu visualizzata 6", 5));
            default:
                return asList(
                        new Monitoraggio("Tipo: {" + tipo + "} non riconosciuto", 0));
        }
    }

}
