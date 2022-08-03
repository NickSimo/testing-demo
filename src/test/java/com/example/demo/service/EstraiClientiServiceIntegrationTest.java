package com.example.demo.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class EstraiClientiServiceIntegrationTest {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private EstraiClientiService service;

    @Test
    public void estraiTuttiIClientiIntegrationTest_TrueSe_GliArrayDaRepositoryEServiceCombaciano() {
        assertArrayEquals(repository.estraiTuttiIClienti().toArray(), service.estraiTuttiIClienti().toArray());
    }

    @Test
    public void estrazioneClientePerCodiceFiscaleIntegrationTest_TrueSe_IlClienteDaRepositoryEServiceCombacia() {
        Cliente modelloRepo = repository.estrazioneClientePerCodiceFiscale("CROSPS12D19L78T");
        Cliente modelloService = service.estrazioneClientePerCodiceFiscale("CROSPS12D19L78T");

        assertTrue(modelloRepo.getId() == (modelloService.getId()));
        assertTrue(modelloRepo.getNome().equals(modelloService.getNome()));
        assertTrue(modelloRepo.getCognome().equals(modelloService.getCognome()));
        assertTrue(modelloRepo.getCodice_fiscale().equals(modelloService.getCodice_fiscale()));
        assertTrue(modelloRepo.getIndirizzo_residenza().equals(modelloService.getIndirizzo_residenza()));
        assertTrue(modelloRepo.getData_di_nascita().equals(modelloService.getData_di_nascita()));
    }

    @Test
    public void estraiUltimiCinqueClientiInseritiIntegrationTest_TrueSe_GliArrayDaRepositoryEServiceCombaciano() {
        assertArrayEquals(repository.estraiUltimiCinqueClientiInseriti().toArray(),
                service.estraiUltimiCinqueClientiInseriti().toArray());
    }

    @Test
    public void estraiClientiMaggiorenniInseritiIntegrationTest_TrueSe_GliArrayDaRepositoryEServiceCombaciano() {
        assertArrayEquals(repository.estraiClientiMaggiorenniInseriti("2022-01-01").toArray(),
                service.estraiClientiMaggiorenniInseriti().toArray());
    }
}