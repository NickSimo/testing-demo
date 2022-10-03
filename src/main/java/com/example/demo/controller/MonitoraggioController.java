package com.example.demo.controller;

import com.example.demo.entity.Monitoraggio;
import com.example.demo.service.MonitoraggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class MonitoraggioController {

    private final MonitoraggioService monitoraggioService;

    @GetMapping(value = "/monitoraggio")
    public List<Monitoraggio> monitoraggio(@RequestParam String tipo, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA) throws ParseException {
        return monitoraggioService.estrai(tipo, dataDa, dataA);
    }

    @GetMapping(value = "/esporta")
    public void esporta(HttpServletResponse response, @RequestParam String formato, @RequestParam String tipo, @RequestParam(required = false) String dataDa, @RequestParam(required = false) String dataA) throws Exception {
        monitoraggioService.esporta(response, formato, tipo, dataDa, dataA);
    }


}
