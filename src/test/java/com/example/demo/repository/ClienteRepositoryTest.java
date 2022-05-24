package com.example.demo.repository;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public
class ClienteRepositoryTest
{

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private
	JdbcTemplate jdbcTemplate;

	@Test
	public void estraiTuttiIClienti_5ClientiPresenti(){

		List<Cliente> clientiEstratti = clienteRepository.estraiTuttiIClienti();

		Assert.assertEquals(5, clientiEstratti.size());
		Assert.assertEquals("Mario", clientiEstratti.get(0).getNome());
		Assert.assertEquals("Ciro", clientiEstratti.get(1).getNome());
		Assert.assertEquals("Gaetano", clientiEstratti.get(2).getNome());
		Assert.assertEquals("Marco", clientiEstratti.get(3).getNome());
		Assert.assertEquals("Andrea", clientiEstratti.get(4).getNome());
	}

	@Test
	public void estraiPerCF_ClienteEstratto(){

		Cliente clienteEstratto = clienteRepository.estrazioneClientePerCodiceFiscale("RSSMRO12D19L78T");

		Assert.assertNotNull(clienteEstratto);
		Assert.assertEquals("Mario",  clienteEstratto.getNome());
		Assert.assertEquals("Rossi",  clienteEstratto.getCognome());
		Assert.assertEquals("RSSMRO12D19L78T",  clienteEstratto.getCodice_fiscale());
		Assert.assertEquals("via alberto dominutti 6",  clienteEstratto.getIndirizzo_residenza());
	}

	@Test
	public void estraiPerCF_ClienteNonEsistente(){

		Cliente clienteEstratto = clienteRepository.estrazioneClientePerCodiceFiscale("AAAAAAAAAA");

		Assert.assertNull(clienteEstratto);
	}
}
