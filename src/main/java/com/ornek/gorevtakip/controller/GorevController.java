package com.ornek.gorevtakip.controller;

import com.ornek.gorevtakip.dto.GorevDto;
import com.ornek.gorevtakip.dto.GorevIstek;
import com.ornek.gorevtakip.entity.GorevDurumu;
import com.ornek.gorevtakip.service.GorevService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gorevler")
public class GorevController {

    private final GorevService gorevService;

    public GorevController(GorevService gorevService) {
        this.gorevService = gorevService;
    }

    @GetMapping
    public List<GorevDto> tumunuGetir(@RequestParam(required = false) GorevDurumu durum) {
        return gorevService.tumunuGetir(durum);
    }

    @GetMapping("/{id}")
    public GorevDto getir(@PathVariable Long id) {
        return gorevService.getir(id);
    }

    @PostMapping
    public ResponseEntity<GorevDto> olustur(@Valid @RequestBody GorevIstek istek) {
        GorevDto olusan = gorevService.olustur(istek);
        return ResponseEntity
                .created(URI.create("/api/gorevler/" + olusan.getId()))
                .body(olusan);
    }

    @PutMapping("/{id}")
    public GorevDto guncelle(@PathVariable Long id, @Valid @RequestBody GorevIstek istek) {
        return gorevService.guncelle(id, istek);
    }

    @PatchMapping("/{id}/durum")
    public GorevDto durumGuncelle(@PathVariable Long id, @RequestParam GorevDurumu yeniDurum) {
        return gorevService.durumGuncelle(id, yeniDurum);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sil(@PathVariable Long id) {
        gorevService.sil(id);
    }
}
