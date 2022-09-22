package com.example.demo.controller;

import com.example.demo.entity.Monitoraggio;
import com.example.demo.service.EstraiClientiService;
import com.example.demo.service.MonitoraggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MonitoraggioController {

    private final MonitoraggioService monitoraggioService;

    @GetMapping(value = "/monitoraggio")
    public List<Monitoraggio> monitoraggio(@RequestParam String tipo, @RequestParam String dataDa, @RequestParam String dataA) throws ParseException {
        System.out.println("Chiamata monitoraggio ricevuta");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return monitoraggioService.estrai(tipo, simpleDateFormat.parse(dataDa),simpleDateFormat.parse(dataA));

    }

}
