package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstraiClientiService {

    @Autowired
    private final ClienteRepository clienteRepository;

    public List<Cliente> estraiTuttiIClienti() {
        return clienteRepository.estraiTuttiIClienti();
    }

    public Cliente estrazioneClientePerCodiceFiscale(String codiceFiscale) {
        if (codiceFiscale == null || codiceFiscale == "") {
            throw new InputErratoException();
        } else {
            return clienteRepository.estrazioneClientePerCodiceFiscale(codiceFiscale);
        }
    }

    public List<Cliente> estraiUltimiCinqueClientiInseriti() {
        return clienteRepository.estraiUltimiCinqueClientiInseriti();
    }

}
