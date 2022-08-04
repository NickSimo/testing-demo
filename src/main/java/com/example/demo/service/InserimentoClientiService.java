package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InserimentoClientiService {

    private final ClienteRepository clienteRepository;

    public void inserisciNuovoCliente(Cliente clienteModello) {
        clienteRepository.inserisciNuovoCliente(clienteModello);
    }
}