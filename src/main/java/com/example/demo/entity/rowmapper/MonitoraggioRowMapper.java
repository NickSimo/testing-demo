package com.example.demo.entity.rowmapper;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Monitoraggio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonitoraggioRowMapper implements RowMapper<Monitoraggio> {
    @Override
    public Monitoraggio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Monitoraggio monitoraggio = new Monitoraggio();
        monitoraggio.setNumero(rs.getInt("sumHits"));
        monitoraggio.setDescrizione(rs.getString("page_title"));
        return monitoraggio;
    }
}
