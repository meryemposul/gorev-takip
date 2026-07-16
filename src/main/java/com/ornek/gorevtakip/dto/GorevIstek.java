package com.ornek.gorevtakip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GorevIstek {

    @NotBlank(message = "Başlık boş olamaz")
    @Size(max = 100, message = "Başlık en fazla 100 karakter olabilir")
    private String baslik;

    @Size(max = 500, message = "Açıklama en fazla 500 karakter olabilir")
    private String aciklama;

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
}
