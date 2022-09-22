package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Monitoraggio;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MonitoraggioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoraggioService {

    private final MonitoraggioRepository monitoraggioRepository;
    public List<Monitoraggio> estrai(String tipo, Date dataDa, Date dataA) {
        if(dataDa!= null && dataA != null){
            return monitoraggioRepository.estraiConRangeDate(tipo, dataDa,dataA);
        }
        return monitoraggioRepository.estrai(tipo);
    }



}
