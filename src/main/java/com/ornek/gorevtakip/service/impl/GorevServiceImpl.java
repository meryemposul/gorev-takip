package com.ornek.gorevtakip.service.impl;

import com.ornek.gorevtakip.dto.GorevDto;
import com.ornek.gorevtakip.dto.GorevIstek;
import com.ornek.gorevtakip.entity.Gorev;
import com.ornek.gorevtakip.entity.GorevDurumu;
import com.ornek.gorevtakip.exception.KayitBulunamadiException;
import com.ornek.gorevtakip.repository.GorevRepository;
import com.ornek.gorevtakip.service.GorevService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GorevServiceImpl implements GorevService {

    private final GorevRepository gorevRepository;

    public GorevServiceImpl(GorevRepository gorevRepository) {
        this.gorevRepository = gorevRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GorevDto> tumunuGetir(GorevDurumu durum) {
        List<Gorev> gorevler = (durum == null)
                ? gorevRepository.findAll()
                : gorevRepository.findByDurumOrderByOlusturmaTarihiDesc(durum);
        return gorevler.stream().map(GorevDto::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GorevDto getir(Long id) {
        return GorevDto.fromEntity(bul(id));
    }

    @Override
    public GorevDto olustur(GorevIstek istek) {
        Gorev gorev = new Gorev(istek.getBaslik(), istek.getAciklama());
        return GorevDto.fromEntity(gorevRepository.save(gorev));
    }

    @Override
    public GorevDto guncelle(Long id, GorevIstek istek) {
        Gorev gorev = bul(id);
        gorev.setBaslik(istek.getBaslik());
        gorev.setAciklama(istek.getAciklama());
        return GorevDto.fromEntity(gorevRepository.save(gorev));
    }

    @Override
    public GorevDto durumGuncelle(Long id, GorevDurumu yeniDurum) {
        Gorev gorev = bul(id);
        gorev.setDurum(yeniDurum);
        return GorevDto.fromEntity(gorevRepository.save(gorev));
    }

    @Override
    public void sil(Long id) {
        gorevRepository.delete(bul(id));
    }

    private Gorev bul(Long id) {
        return gorevRepository.findById(id)
                .orElseThrow(() -> new KayitBulunamadiException("Görev bulunamadı: id=" + id));
    }
}
