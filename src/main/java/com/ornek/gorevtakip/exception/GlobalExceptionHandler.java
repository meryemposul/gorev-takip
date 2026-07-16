package com.ornek.gorevtakip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KayitBulunamadiException.class)
    public ResponseEntity<Map<String, Object>> kayitBulunamadi(KayitBulunamadiException ex) {
        return hataYaniti(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> dogrulamaHatasi(MethodArgumentNotValidException ex) {
        String mesaj = ex.getBindingResult().getFieldErrors().stream()
                .map(h -> h.getField() + ": " + h.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Doğrulama hatası");
        return hataYaniti(HttpStatus.BAD_REQUEST, mesaj);
    }

    private ResponseEntity<Map<String, Object>> hataYaniti(HttpStatus durum, String mesaj) {
        Map<String, Object> govde = new HashMap<>();
        govde.put("zaman", LocalDateTime.now().toString());
        govde.put("durum", durum.value());
        govde.put("mesaj", mesaj);
        return ResponseEntity.status(durum).body(govde);
    }
}
