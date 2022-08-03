package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.rowmapper.ClienteRowMapper;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

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

    public List<Cliente> estraiUltimiCinqueClientiInseriti() {
        try {
            return jdbcTemplate.query("SELECT * FROM clienti ORDER BY id DESC LIMIT 5", new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Cliente> estraiClientiMaggiorenniInseriti(String dataControllo) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM clienti WHERE DATEADD(year, 18, data_di_nascita) < '" + dataControllo + "'",
                    new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Cliente> estraiClientiMaggiorenniInseriti() {
        return estraiClientiMaggiorenniInseriti(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
