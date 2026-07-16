package com.ornek.gorevtakip.repository;

import com.ornek.gorevtakip.entity.Gorev;
import com.ornek.gorevtakip.entity.GorevDurumu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GorevRepository extends JpaRepository<Gorev, Long> {

    List<Gorev> findByDurumOrderByOlusturmaTarihiDesc(GorevDurumu durum);
}
