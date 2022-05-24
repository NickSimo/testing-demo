package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public
class EstraiClientiServiceTest
{

	private ClienteRepository    clienteRepositoryMock = Mockito.mock(ClienteRepository.class);
	private EstraiClientiService estraiClientiService  = new EstraiClientiService(clienteRepositoryMock);

	@Test
	public void elencoClienti_2ClientiEstratti(){

		List<Cliente> listaClienti = new ArrayList<>();
		listaClienti.add(new Cliente());
		listaClienti.add(new Cliente());
		Mockito.when(clienteRepositoryMock.estraiTuttiIClienti()).thenReturn(listaClienti);

		List<Cliente> listaClientiEstratti = estraiClientiService.estraiTuttiIClienti();

		Assert.assertEquals(2, listaClientiEstratti.size());
	}

	@Test
	public void elencoClienti_NessunClientiEstratti(){

		Mockito.when(clienteRepositoryMock.estraiTuttiIClienti()).thenReturn(new ArrayList<>());

		List<Cliente> listaClientiEstratti = estraiClientiService.estraiTuttiIClienti();

		Assert.assertEquals(0, listaClientiEstratti.size());
	}

	@Test
	public void estrazioneClientePerCf_ClienteEstratto(){

		Cliente marioRossi = new Cliente();
		marioRossi.setNome("mario");
		marioRossi.setCognome("rossi");
		Mockito.when(clienteRepositoryMock.estrazioneClientePerCodiceFiscale("XXXX")).thenReturn(marioRossi);

		Cliente clienteEstratto = estraiClientiService.estrazioneClientePerCodiceFiscale("XXXX");

		Assert.assertEquals("mario", clienteEstratto.getNome());
		Assert.assertEquals("rossi", clienteEstratto.getCognome());
	}

	@Test
	public void estrazioneClientePerCf_ClienteNonTrovato(){

		Mockito.when(clienteRepositoryMock.estrazioneClientePerCodiceFiscale("XXXX")).thenReturn(null);

		Cliente clienteEstratto = estraiClientiService.estrazioneClientePerCodiceFiscale("XXXX");

		Assert.assertNull(clienteEstratto);
	}

	@Test(expected = InputErratoException.class)
	public void estrazioneClientePerCf_CfSpazi(){

		estraiClientiService.estrazioneClientePerCodiceFiscale("");
	}

	@Test(expected = InputErratoException.class)
	public void estrazioneClientePerCf_CfNull(){

		estraiClientiService.estrazioneClientePerCodiceFiscale(null);
	}
}
