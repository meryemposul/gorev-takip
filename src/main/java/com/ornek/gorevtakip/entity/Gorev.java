package com.ornek.gorevtakip.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gorevler")
public class Gorev {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String baslik;

    @Column(length = 500)
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GorevDurumu durum = GorevDurumu.BEKLIYOR;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist
    void prePersist() {
        if (olusturmaTarihi == null) {
            olusturmaTarihi = LocalDateTime.now();
        }
    }

    public Gorev() {
    }

    public Gorev(String baslik, String aciklama) {
        this.baslik = baslik;
        this.aciklama = aciklama;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }

    public GorevDurumu getDurum() { return durum; }
    public void setDurum(GorevDurumu durum) { this.durum = durum; }

    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) { this.olusturmaTarihi = olusturmaTarihi; }
}
