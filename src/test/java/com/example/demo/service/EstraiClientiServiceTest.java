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
	ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

	EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

	@Test
	public void estrazione_2_clienti(){

		List<Cliente> clienti = new ArrayList<>();
		clienti.add(new Cliente());
		clienti.add(new Cliente());
		Mockito.when(clienteRepository.estraiTuttiIClienti()).thenReturn(clienti);

		List<Cliente> clientiEstratti = estraiClientiService.estraiTuttiIClienti();

		Assert.assertEquals(2, clientiEstratti.size());
	}

	@Test
	public void estrazione_per_cf(){

		Cliente cliente = new Cliente();
		cliente.setNome("mario");
		cliente.setCognome("rossi");
		Mockito.when(clienteRepository.estrazioneClientePerCodiceFiscale(Mockito.anyString())).thenReturn(cliente);

		Cliente clienteEstratto = estraiClientiService.estrazioneClientePerCodiceFiscale("SMNNCL97M28L781U");

		Assert.assertEquals("mario", clienteEstratto.getNome());
		Assert.assertEquals("rossi", clienteEstratto.getCognome());
	}

	@Test(expected = InputErratoException.class)
	public void estrazione_cf_null(){
		estraiClientiService.estrazioneClientePerCodiceFiscale(null);
	}

	@Test(expected = InputErratoException.class)
	public void estrazione_cf_stringa_vuota(){
		estraiClientiService.estrazioneClientePerCodiceFiscale("");
	}
}
