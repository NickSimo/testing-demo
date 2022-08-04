package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;

public class EstraiClientiServiceTest {
    private ClienteRepository mockRepository = Mockito.mock(ClienteRepository.class);
    private EstraiClientiService service = new EstraiClientiService(mockRepository);

    @Test
    public void estraiTuttiIClientiTest_TrueSe_ClientiGestitiCorrettamente() throws ParseException {
        List<Cliente> listaClienti = setupListaClienti();

        Mockito.when(mockRepository.estraiTuttiIClienti()).thenReturn(listaClienti);

        assertArrayEquals(listaClienti.toArray(), service.estraiTuttiIClienti().toArray());
    }

    @Test
    public void estrazioneClientePerCodiceFiscaleTest_TrueSe_ClienteGestitoCorrettamente() throws ParseException {
        Cliente modello = setupModello(3L, new String[] { "Ciro", "Esposito", "CROSPS12D19L78T", "via del corso 2" });

        Mockito.when(mockRepository.estrazioneClientePerCodiceFiscale("CROSPS12D19L78T")).thenReturn(modello);

        assertSame(modello, service.estrazioneClientePerCodiceFiscale("CROSPS12D19L78T"));
    }

    @Test
    public void estrazioneClientePerCodiceFiscaleEmptyTest_TrueSe_ThrowsInputExceptionGestitoCorrettamente() {
        assertThrows(InputErratoException.class,
                () -> {
                    service.estrazioneClientePerCodiceFiscale("");
                });
    }

    @Test
    public void estrazioneClientePerCodiceFiscaleNullTest_TrueSe_ThrowsInputExceptionGestitoCorrettamente() {
        assertThrows(InputErratoException.class,
                () -> {
                    service.estrazioneClientePerCodiceFiscale(null);
                });
    }

    @Test
    public void estraiUltimiCinqueClientiInseritiTest_TrueSe_ClientiGestitiCorrettamente() throws ParseException {
        List<Cliente> listaClienti = setupListaClienti();

        Mockito.when(mockRepository.estraiUltimiCinqueClientiInseriti()).thenReturn(listaClienti);

        assertArrayEquals(listaClienti.toArray(), service.estraiUltimiCinqueClientiInseriti().toArray());
    }

    private List<Cliente> setupListaClienti() throws ParseException {
        List<Cliente> listaClienti = new ArrayList<>();

        listaClienti
                .add(setupModello(1L, new String[] { "Mario", "Rossi", "RSSMRO12D19L78T", "via alberto dominutti 6" }));
        listaClienti
                .add(setupModello(2L, new String[] { "Ciro", "Esposito", "CROSPS12D19L78T", "via del corso 2" }));

        return listaClienti;
    }

    private Cliente setupModello(Long id, String[] strings) throws ParseException {
        Cliente modello = new Cliente();
        Date dataEsempio = (Date) new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01");

        modello.setId(id);
        modello.setNome(strings[0]);
        modello.setCognome(strings[1]);
        modello.setCodice_fiscale(strings[2]);
        modello.setIndirizzo_residenza(strings[3]);
        modello.setData_di_nascita(dataEsempio);
        return modello;
    }
}
