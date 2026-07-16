package com.ornek.gorevtakip.controller;

import com.ornek.gorevtakip.dto.GorevDto;
import com.ornek.gorevtakip.entity.Gorev;
import com.ornek.gorevtakip.exception.KayitBulunamadiException;
import com.ornek.gorevtakip.service.GorevService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web katmanı testleri - MockMvc (İlan B-ğ: web testleri).
 */
@WebMvcTest(GorevController.class)
class GorevControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GorevService gorevService;

    private GorevDto ornekDto() {
        Gorev gorev = new Gorev("Rapor hazırla", "Aylık faaliyet raporu");
        gorev.setId(1L);
        return GorevDto.fromEntity(gorev);
    }

    @Test
    @DisplayName("GET /api/gorevler -> 200 ve JSON listesi döner")
    void tumunuGetir() throws Exception {
        when(gorevService.tumunuGetir(null)).thenReturn(List.of(ornekDto()));

        mockMvc.perform(get("/api/gorevler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baslik").value("Rapor hazırla"));
    }

    @Test
    @DisplayName("POST /api/gorevler -> 201 Created döner")
    void olustur() throws Exception {
        when(gorevService.olustur(any())).thenReturn(ornekDto());

        mockMvc.perform(post("/api/gorevler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"baslik\":\"Rapor hazırla\",\"aciklama\":\"Aylık faaliyet raporu\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/gorevler/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST boş başlık -> 400 Bad Request (validasyon)")
    void olustur_validasyonHatasi() throws Exception {
        mockMvc.perform(post("/api/gorevler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"baslik\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET olmayan id -> 404 Not Found")
    void getir_bulunamadi() throws Exception {
        when(gorevService.getir(eq(99L)))
                .thenThrow(new KayitBulunamadiException("Görev bulunamadı: id=99"));

        mockMvc.perform(get("/api/gorevler/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mesaj").value("Görev bulunamadı: id=99"));
    }
}
