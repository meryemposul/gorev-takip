# Görev Takip Sistemi

Spring Boot ile geliştirilmiş, katmanlı mimaride bir görev yönetimi uygulaması. JSON tabanlı bir REST API ve bu API'yi tüketen jQuery tabanlı hafif bir web arayüzü içerir.

## Özellikler

- Görev oluşturma, listeleme, güncelleme ve silme (CRUD)
- Durum yönetimi: Bekliyor → Devam Ediyor → Tamamlandı
- Durum bazlı filtreleme
- Bean Validation ile girdi doğrulama ve merkezi hata yönetimi
- Doğru HTTP durum kodları (200 / 201 / 204 / 400 / 404)

## Teknolojiler

Java 17, Spring Boot 3, Spring Data JPA (Hibernate), H2 / MSSQL, Maven, JUnit 5, Mockito, MockMvc, jQuery.

## Çalıştırma

JDK 17+ ve Maven 3.8+ gereklidir.

```bash
mvn spring-boot:run
```

Uygulama http://localhost:8080 adresinde açılır. Geliştirme ortamında gömülü H2 veritabanı kullanılır; `application.properties` içindeki hazır profil ile MSSQL Server'a geçiş yalnızca konfigürasyon değişikliğidir.

Testler için:

```bash
mvn test
```

## REST API

| Metot  | Yol                      | Açıklama                                        |
|--------|--------------------------|-------------------------------------------------|
| GET    | /api/gorevler            | Görevleri listeler (`?durum=` filtresi opsiyonel) |
| GET    | /api/gorevler/{id}       | Tek görev getirir                               |
| POST   | /api/gorevler            | Yeni görev oluşturur                            |
| PUT    | /api/gorevler/{id}       | Görevi günceller                                |
| PATCH  | /api/gorevler/{id}/durum | Görev durumunu değiştirir                       |
| DELETE | /api/gorevler/{id}       | Görevi siler                                    |

Örnek istek:

```bash
curl -X POST http://localhost:8080/api/gorevler \
  -H "Content-Type: application/json" \
  -d '{"baslik":"Rapor hazırla","aciklama":"Aylık faaliyet raporu"}'
```

## Mimari
Katmanlar arası bağımlılıklar constructor injection ile yönetilir; entity'ler dış dünyaya DTO'lar üzerinden açılır. Servis katmanı Mockito ile birim testlerine, web katmanı MockMvc ile katman testlerine sahiptir.

## CI/CD

Depodaki `Jenkinsfile`, derleme → test → paketleme aşamalarından oluşan örnek bir Jenkins boru hattı tanımlar.
