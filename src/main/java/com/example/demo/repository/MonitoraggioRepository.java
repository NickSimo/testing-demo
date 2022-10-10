package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Licenza;
import com.example.demo.entity.Monitoraggio;
import com.example.demo.entity.rowmapper.ClienteRowMapper;
import com.example.demo.entity.rowmapper.FormatiUtilizzatiRowMapper;
import com.example.demo.entity.rowmapper.LicenzeRowMapper;
import com.example.demo.entity.rowmapper.MonitoraggioRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MonitoraggioRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Monitoraggio> estrai(String tipo) {
        try {
            return jdbcTemplate.query("select page_url_short, page_title, max(id_page_hit) as \"id\", sum(hits) as \"sumHits\"\n" +
                    "from page_hit\n" +
                    "where event_type='" + tipo + "' and (not page_hit.page_title ilike '%errore%')\n" +
                    "group by page_url_short, page_title\n" +
                    "order by sum(hits) desc", new MonitoraggioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Monitoraggio> estraiConRangeDate(String tipo, Date dataDa, Date dataA) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String cond1 = "and date >= '"+simpleDateFormat.format(dataDa)+"'";
            String cond2 = "and date <= '"+simpleDateFormat.format(dataA)+"'";
            String sql = "select page_url_short, page_title, max(id_page_hit) as \"id\", sum(hits) as \"sumHits\"\n" +
                    "    from page_hit\n" +
                    "    where event_type='" + tipo + "' and (not page_hit.page_title ilike '%errore%')" + cond1 + " " + cond2 +
                    "    group by page_url_short, page_title\n" +
                    "    order by sum(hits) desc";
            System.out.println(sql);
            return jdbcTemplate.query(sql, new MonitoraggioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Licenza> estraiLicenze() {
        try {
            return jdbcTemplate.query("select p.license_id as id,count(p.id) as count\n" +
                    "    from package p\n" +
                    "    where p.state = 'active'and\n" +
                    "    p.license_id is not null and\n" +
                    "    p.license_id != ''\n" +
                    "    group by p.license_id\n" +
                    "    order by count(p.id) desc", new LicenzeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Monitoraggio> estraiFormatiUtilizzati() {
        try {
            return jdbcTemplate.query("select format as formato, count(id) as count\n" +
                    "from resource r\n" +
                    "where r.state = 'active' and\n" +
                    "format is not null\n" +
                    "and format != ''\n" +
                    "group by format\n" +
                    "order by count(id) desc;", new FormatiUtilizzatiRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
