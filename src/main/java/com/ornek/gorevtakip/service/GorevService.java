package com.ornek.gorevtakip.service;

import com.ornek.gorevtakip.dto.GorevDto;
import com.ornek.gorevtakip.dto.GorevIstek;
import com.ornek.gorevtakip.entity.GorevDurumu;

import java.util.List;

public interface GorevService {

    List<GorevDto> tumunuGetir(GorevDurumu durum);

    GorevDto getir(Long id);

    GorevDto olustur(GorevIstek istek);

    GorevDto guncelle(Long id, GorevIstek istek);

    GorevDto durumGuncelle(Long id, GorevDurumu yeniDurum);

    void sil(Long id);
}
