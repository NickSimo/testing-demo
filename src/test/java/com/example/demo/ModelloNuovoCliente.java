package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.demo.entity.Cliente;

public class ModelloNuovoCliente {
    public Cliente getNuovoCliente() throws ParseException {
        Cliente modello = new Cliente();

        modello.setId(5l);
        modello.setNome("Domenico");
        modello.setCognome("Bini");
        modello.setCodice_fiscale("BNIDMC12D19L78T");
        modello.setIndirizzo_residenza("Via vulcano 101");
        modello.setData_di_nascita(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"));

        return modello;
    }
}