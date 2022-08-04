package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.ModelloNuovoCliente;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.rowmapper.ClienteRowMapper;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClienteRepositoryTest {
    private static final String CHECK_MARIO = "Cliente(id=0, nome=Mario, cognome=Rossi, codice_fiscale=RSSMRO12D19L78T, indirizzo_residenza=via alberto dominutti 6, data_di_nascita=2001-01-01)";
    private static final String CHECK_CIRO = "Cliente(id=1, nome=Ciro, cognome=Esposito, codice_fiscale=CROSPS12D19L78T, indirizzo_residenza=via del corso 2, data_di_nascita=2001-01-01)";
    private static final String CHECK_GAETANO = "Cliente(id=2, nome=Gaetano, cognome=Russo, codice_fiscale=RSSGTN12D19L78T, indirizzo_residenza=via centrale 1, data_di_nascita=2015-01-01)";
    private static final String CHECK_MARCO = "Cliente(id=3, nome=Marco, cognome=Bianchi, codice_fiscale=BNCMRCS12D19L787, indirizzo_residenza=via popolo 5, data_di_nascita=2001-01-01)";
    private static final String CHECK_ANDREA = "Cliente(id=4, nome=Andrea, cognome=Lo Russo, codice_fiscale=BNCMRCS12D19L78T, indirizzo_residenza=via Legnago 5, data_di_nascita=2001-01-01)";

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private JdbcTemplate template;

    @Test
    public void estraiTuttiIClientiTest_TrueSe_ClientiEstrattiCorrettamente() {
        List<Cliente> clienti = clienteRepository.estraiTuttiIClienti();

        assertTrue(clienti.get(0).toString().equals(CHECK_MARIO));
        assertTrue(clienti.get(1).toString().equals(CHECK_CIRO));
        assertTrue(clienti.get(2).toString().equals(CHECK_GAETANO));
        assertTrue(clienti.get(3).toString().equals(CHECK_MARCO));
        assertTrue(clienti.get(4).toString().equals(CHECK_ANDREA));
    }

    @Test
    public void estrazioneClientePerCodiceFiscaleTest_TrueSe_MarioEstrattoCorrettamente() {
        Cliente cliente = clienteRepository.estrazioneClientePerCodiceFiscale("RSSMRO12D19L78T");

        assertTrue(cliente.toString().equals(CHECK_MARIO));
    }

    @Test
    public void estraiUltimiCinqueClientiInseritiTest_TrueSe_ClientiEstrattiCombaciano() {
        List<Cliente> clienti = clienteRepository.estraiUltimiCinqueClientiInseriti();

        assertTrue(clienti.get(0).toString().equals(CHECK_ANDREA));
        assertTrue(clienti.get(1).toString().equals(CHECK_MARCO));
        assertTrue(clienti.get(2).toString().equals(CHECK_GAETANO));
        assertTrue(clienti.get(3).toString().equals(CHECK_CIRO));
        assertTrue(clienti.get(4).toString().equals(CHECK_MARIO));
    }

    @Test
    public void estraiClientiMaggiorenniInseritiTest_TrueSe_ClientiEstrattiCombaciano() {
        List<Cliente> clienti = clienteRepository.estraiClientiMaggiorenniInseriti("2022-01-01");

        assertTrue(clienti.get(0).toString().equals(CHECK_MARIO));
        assertTrue(clienti.get(1).toString().equals(CHECK_CIRO));
        assertTrue(clienti.get(2).toString().equals(CHECK_MARCO));
        assertTrue(clienti.get(3).toString().equals(CHECK_ANDREA));
    }

    @Test
    public void inserimentoClienteTest_TrueSe_InseritoCorrettamente() throws ParseException {
        Cliente modello = new ModelloNuovoCliente().getNuovoCliente();

        clienteRepository.inserisciNuovoCliente(modello);

        Cliente controllo = template.queryForObject(
                " SELECT * FROM Clienti ORDER BY id DESC LIMIT 1", new ClienteRowMapper());

        assertTrue(modello.getNome().equals(controllo.getNome()));
        assertTrue(modello.getCognome().equals(controllo.getCognome()));
        assertTrue(modello.getCodice_fiscale().equals(controllo.getCodice_fiscale()));
        assertTrue(modello.getIndirizzo_residenza().equals(controllo.getIndirizzo_residenza()));
        assertTrue(modello.getData_di_nascita().equals(controllo.getData_di_nascita()));

        template.update("DELETE FROM Clienti WHERE id = " + controllo.getId());
    }
}
