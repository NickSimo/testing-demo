package com.example.demo.controller;

import com.example.demo.entity.Cliente;
import com.example.demo.service.EstraiClientiService;
import com.example.demo.service.InserimentoClientiService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clienti")
public class ClientiController {

    private final EstraiClientiService estraiClientiService;
    private final InserimentoClientiService inserimentoClientiService;

    @GetMapping(value = "/elenco")
    public List<Cliente> clienti() {
        return estraiClientiService.estraiTuttiIClienti();
    }

    @GetMapping(value = "/estrazione-per-cf")
    public Cliente clienti(@RequestParam String cf) {
        return estraiClientiService.estrazioneClientePerCodiceFiscale(cf);
    }

    @GetMapping(value = "/ultimi-cinque-clienti")
    public List<Cliente> ultimiCinqueClienti() {
        return estraiClientiService.estraiUltimiCinqueClientiInseriti();
    }

    @GetMapping(value = "/clienti-maggiorenni")
    public List<Cliente> clientiMaggiorenni() {
        return estraiClientiService.estraiClientiMaggiorenniInseriti();
    }

    @PostMapping(value = "/inserimento-cliente", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public void inserimantoNuovoCliente(@RequestBody Cliente clienteModello) {
        inserimentoClientiService.inserisciNuovoCliente(clienteModello);
    }
}
