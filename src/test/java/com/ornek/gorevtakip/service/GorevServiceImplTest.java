package com.ornek.gorevtakip.service;

import com.ornek.gorevtakip.dto.GorevDto;
import com.ornek.gorevtakip.dto.GorevIstek;
import com.ornek.gorevtakip.entity.Gorev;
import com.ornek.gorevtakip.entity.GorevDurumu;
import com.ornek.gorevtakip.exception.KayitBulunamadiException;
import com.ornek.gorevtakip.repository.GorevRepository;
import com.ornek.gorevtakip.service.impl.GorevServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Birim testleri - JUnit 5 + Mockito (İlan B-m: Unit test, B-ı: TDD).
 */
@ExtendWith(MockitoExtension.class)
class GorevServiceImplTest {

    @Mock
    private GorevRepository gorevRepository;

    private GorevService gorevService;

    @BeforeEach
    void setUp() {
        gorevService = new GorevServiceImpl(gorevRepository);
    }

    private Gorev ornekGorev(Long id) {
        Gorev gorev = new Gorev("Rapor hazırla", "Aylık faaliyet raporu");
        gorev.setId(id);
        return gorev;
    }

    @Test
    @DisplayName("Görev oluşturulduğunda repository.save çağrılır ve DTO döner")
    void olustur_basarili() {
        GorevIstek istek = new GorevIstek();
        istek.setBaslik("Rapor hazırla");
        istek.setAciklama("Aylık faaliyet raporu");

        when(gorevRepository.save(any(Gorev.class))).thenReturn(ornekGorev(1L));

        GorevDto sonuc = gorevService.olustur(istek);

        assertNotNull(sonuc);
        assertEquals(1L, sonuc.getId());
        assertEquals("Rapor hazırla", sonuc.getBaslik());
        verify(gorevRepository, times(1)).save(any(Gorev.class));
    }

    @Test
    @DisplayName("Var olan görev id ile getirilebilir")
    void getir_basarili() {
        when(gorevRepository.findById(1L)).thenReturn(Optional.of(ornekGorev(1L)));

        GorevDto sonuc = gorevService.getir(1L);

        assertEquals("Rapor hazırla", sonuc.getBaslik());
        assertEquals(GorevDurumu.BEKLIYOR, sonuc.getDurum());
    }

    @Test
    @DisplayName("Olmayan görev istendiğinde KayitBulunamadiException fırlatılır")
    void getir_bulunamadi() {
        when(gorevRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(KayitBulunamadiException.class, () -> gorevService.getir(99L));
    }

    @Test
    @DisplayName("Durum güncelleme, görevin durumunu değiştirir")
    void durumGuncelle_basarili() {
        Gorev gorev = ornekGorev(1L);
        when(gorevRepository.findById(1L)).thenReturn(Optional.of(gorev));
        when(gorevRepository.save(any(Gorev.class))).thenAnswer(c -> c.getArgument(0));

        GorevDto sonuc = gorevService.durumGuncelle(1L, GorevDurumu.TAMAMLANDI);

        assertEquals(GorevDurumu.TAMAMLANDI, sonuc.getDurum());
    }

    @Test
    @DisplayName("Tüm görevler listelenir ve DTO'ya dönüştürülür")
    void tumunuGetir_basarili() {
        when(gorevRepository.findAll()).thenReturn(List.of(ornekGorev(1L), ornekGorev(2L)));

        List<GorevDto> sonuc = gorevService.tumunuGetir(null);

        assertEquals(2, sonuc.size());
        verify(gorevRepository).findAll();
    }

    @Test
    @DisplayName("Silme işleminde önce kayıt bulunur sonra silinir")
    void sil_basarili() {
        Gorev gorev = ornekGorev(1L);
        when(gorevRepository.findById(1L)).thenReturn(Optional.of(gorev));

        gorevService.sil(1L);

        verify(gorevRepository).delete(gorev);
    }
}
