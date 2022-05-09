package com.example.demo.controller;

import com.example.demo.FakeDatabaseConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
@AutoConfigureMockMvc
public
class ClientiControllerE2E
{

	@Autowired
	MockMvc mockMvc;

	@Test
	public
	void estrazione_tutti_clienti()
	throws Exception
	{
		mockMvc.perform(get("/clienti/elenco"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("[0].nome").value("Mario"))
		       .andExpect(jsonPath("[1].nome").value("Ciro"))
		       .andExpect(jsonPath("[2].nome").value("Gaetano"))
		       .andExpect(jsonPath("[3].nome").value("Marco"))
		       .andExpect(jsonPath("[4].nome").value("Andrea"))
		;
	}

	@Test
	public
	void estrazione_per_cf()
	throws Exception
	{
		mockMvc.perform(get("/clienti/estrazione-per-cf?cf=BNCMRCS12D19L78T"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("nome").value("Andrea"))
		       .andExpect(jsonPath("cognome").value("Lo Russo"))
		       .andExpect(jsonPath("codice_fiscale").value("BNCMRCS12D19L78T"))
		       .andExpect(jsonPath("indirizzo_residenza").value("via Legnago 5"))
		;
	}

	@Test
	public
	void estrazione_per_cf_stringa_vuota()
	throws Exception
	{
		mockMvc.perform(get("/clienti/estrazione-per-cf?cf="))
		       .andExpect(status().isInternalServerError())
		;
	}
}
