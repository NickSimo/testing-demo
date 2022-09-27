package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Monitoraggio;
import com.example.demo.entity.rowmapper.ClienteRowMapper;
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


}
