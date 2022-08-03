package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.context.annotation.Import;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FakeDatabaseConfiguration.class)
public class ClientiControllerTestE2E {

    @Autowired
    private MockMvc mvc;

    @Test
    public void clientiE2EServiceTest_TrueSe_StatusOkAndElencoNomiCorretto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/clienti/elenco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].nome").value("Mario"))
                .andExpect(jsonPath("[1].nome").value("Ciro"))
                .andExpect(jsonPath("[2].nome").value("Gaetano"))
                .andExpect(jsonPath("[3].nome").value("Marco"))
                .andExpect(jsonPath("[4].nome").value("Andrea"));
    }

    @Test
    public void clientiParametricoE2EServiceTest_TrueSe_StatusOkAndElencoNomiCorretto() throws Exception {
        Cliente modello = setupCheckMario();

        mvc.perform(MockMvcRequestBuilders
                .get("/clienti/estrazione-per-cf?cf=" + modello.getCodice_fiscale()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value(modello.getNome()))
                .andExpect(jsonPath("cognome").value(modello.getCognome()))
                .andExpect(jsonPath("codice_fiscale").value(modello.getCodice_fiscale()))
                .andExpect(jsonPath("indirizzo_residenza").value(modello.getIndirizzo_residenza()));
    }

    @Test
    public void ultimiCinqueClientiE2EServiceTest_TrueSe_StatusOkAndElencoNomiCorretto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/clienti/ultimi-cinque-clienti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].nome").value("Andrea"))
                .andExpect(jsonPath("[1].nome").value("Marco"))
                .andExpect(jsonPath("[2].nome").value("Gaetano"))
                .andExpect(jsonPath("[3].nome").value("Ciro"))
                .andExpect(jsonPath("[4].nome").value("Mario"));
    }

    @Test
    public void clientiMaggiorenniE2EServiceTest_TrueSe_StatusOkAndElencoNomiCorretto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/clienti/clienti-maggiorenni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].nome").value("Mario"))
                .andExpect(jsonPath("[1].nome").value("Ciro"))
                .andExpect(jsonPath("[2].nome").value("Marco"))
                .andExpect(jsonPath("[3].nome").value("Andrea"));
    }

    private Cliente setupCheckMario() throws ParseException {
        Cliente modello = new Cliente();

        modello.setNome("Mario");
        modello.setCognome("Rossi");
        modello.setCodice_fiscale("RSSMRO12D19L78T");
        modello.setIndirizzo_residenza("via alberto dominutti 6");
        modello.setData_di_nascita(new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"));

        return modello;
    }
}
