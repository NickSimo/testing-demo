package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import com.example.demo.service.EstraiClientiService;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClientiControllerIntegrationTest {
    @Autowired
    private EstraiClientiService service;
    @Autowired
    private ClientiController controller;

    @Test
    public void clientiIntegrationTest_TrueSe_GliArrayServiceEControllerCombaciano() {
        assertArrayEquals(service.estraiTuttiIClienti().toArray(), controller.clienti().toArray());
    }

    @Test
    public void clientiParametricoIntegrationTest_TrueSe_IlClienteDaServiceEControllerCombacia() {
        Cliente modelloService = service.estrazioneClientePerCodiceFiscale("CROSPS12D19L78T");
        Cliente modelloController = controller.clienti("CROSPS12D19L78T");

        assertTrue(modelloService.getId() == (modelloController.getId()));
        assertTrue(modelloService.getNome().equals(modelloController.getNome()));
        assertTrue(modelloService.getCognome().equals(modelloController.getCognome()));
        assertTrue(modelloService.getCodice_fiscale().equals(modelloController.getCodice_fiscale()));
        assertTrue(modelloService.getIndirizzo_residenza().equals(modelloController.getIndirizzo_residenza()));

    }

    @Test
    public void estraiUltimiCinqueClientiInseritiIntegrationTest_TrueSe_GliArrayDaServiceEControllerCombaciano() {
        assertArrayEquals(service.estraiUltimiCinqueClientiInseriti().toArray(),
                controller.ultimiCinqueClienti().toArray());
    }

    @Test
    public void estraiClientiMaggiorenniInseritiIntegrationTest_TrueSe_GliArrayDaServiceEControllerCombaciano() {
        assertArrayEquals(service.estraiClientiMaggiorenniInseriti().toArray(),
                controller.clientiMaggiorenni().toArray());
    }
}