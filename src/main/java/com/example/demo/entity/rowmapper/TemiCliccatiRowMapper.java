package com.example.demo.entity.rowmapper;

import com.example.demo.entity.Licenza;
import com.example.demo.entity.Tema;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemiCliccatiRowMapper implements RowMapper<Tema> {
    @Override
    public Tema mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tema tema = new Tema();
        tema.setId(rs.getString("dataset_id"));
        tema.setUrl(rs.getString("url"));
        tema.setNumero(rs.getInt("total"));
        return tema;
    }
}
