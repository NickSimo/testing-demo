package com.example.demo.entity.rowmapper;

import com.example.demo.entity.Licenza;
import com.example.demo.entity.Monitoraggio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormatiUtilizzatiRowMapper implements RowMapper<Monitoraggio> {
    @Override
    public Monitoraggio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Monitoraggio monitoraggio = new Monitoraggio();
        monitoraggio.setNumero(rs.getInt("count"));
        monitoraggio.setDescrizione(rs.getString("formato"));
        return monitoraggio;
    }
}
