package com.example.demo.controller;

import com.example.demo.FakeDatabaseConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FakeDatabaseConfiguration.class)
public
class ClientiControllerE2E
{
	@Autowired
	private MockMvc mockMvc;

	@Test
	public
	void estrazioneListaClienti_OK()
	throws Exception
	{
		mockMvc.perform(get("/clienti/elenco"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("[0].nome").value("Mario"))
		       .andExpect(jsonPath("[0].cognome").value("Rossi"));
	}
}
