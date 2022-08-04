package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

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
public class InserimentoClientiServiceIntegrationTest {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private InserimentoClientiService service;

    @Test
    public void inserisciNuovoClienteTest_TrueSe_NuovoClienteInseritoCorrettamente() throws ParseException {
        Cliente modello = new ModelloNuovoCliente().getNuovoCliente();

        service.inserisciNuovoCliente(modello);

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
