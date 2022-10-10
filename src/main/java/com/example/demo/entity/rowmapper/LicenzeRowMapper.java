package com.example.demo.entity.rowmapper;

import com.example.demo.entity.Licenza;
import com.example.demo.entity.Monitoraggio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LicenzeRowMapper implements RowMapper<Licenza> {
    @Override
    public Licenza mapRow(ResultSet rs, int rowNum) throws SQLException {
        Licenza licenza = new Licenza();
        licenza.setId(rs.getString("id"));
        licenza.setNumero(rs.getInt("count"));
        return licenza;
    }
}
