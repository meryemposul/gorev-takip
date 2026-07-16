package com.ornek.gorevtakip.dto;

import com.ornek.gorevtakip.entity.Gorev;
import com.ornek.gorevtakip.entity.GorevDurumu;

import java.time.LocalDateTime;

public class GorevDto {

    private Long id;
    private String baslik;
    private String aciklama;
    private GorevDurumu durum;
    private LocalDateTime olusturmaTarihi;

    public static GorevDto fromEntity(Gorev gorev) {
        GorevDto dto = new GorevDto();
        dto.id = gorev.getId();
        dto.baslik = gorev.getBaslik();
        dto.aciklama = gorev.getAciklama();
        dto.durum = gorev.getDurum();
        dto.olusturmaTarihi = gorev.getOlusturmaTarihi();
        return dto;
    }

    public Long getId() { return id; }
    public String getBaslik() { return baslik; }
    public String getAciklama() { return aciklama; }
    public GorevDurumu getDurum() { return durum; }
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
}
