package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.rowmapper.ClienteRowMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public List<Cliente> estraiTuttiIClienti() {
        try {
            return jdbcTemplate.query("SELECT * FROM clienti", new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Cliente estrazioneClientePerCodiceFiscale(String codiceFiscale) {
        try {
            return jdbcTemplate.queryForObject(
                    " SELECT * FROM Clienti WHERE codice_fiscale = '" + codiceFiscale + "'",
                    new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // public List<Cliente> estraiUltimi5ClientiInseriti() {
    // try {
    // return jdbcTemplate.query("SELECT TOP 5 * FROM clienti ORDER BY id DESC", new
    // ClienteRowMapper());
    // } catch (EmptyResultDataAccessException e) {
    // return null;
    // }
    // }
}
