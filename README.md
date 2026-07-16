# Görev Takip Sistemi

Sözleşmeli Bilişim Personeli (Java Yazılım Geliştirme Uzmanı) ilanındaki özel şartlara birebir uyacak şekilde tasarlanmış, bir günde tamamlanabilir örnek proje. Spring Boot ile geliştirilmiş katmanlı bir REST API ve bu API'yi tüketen jQuery tabanlı basit bir web arayüzü içerir.

## Çalıştırma

Gereksinimler: JDK 17+ ve Maven 3.8+.

```bash
mvn spring-boot:run
```

Tarayıcıda açın: http://localhost:8080

- Web arayüzü: `http://localhost:8080/`
- REST API: `http://localhost:8080/api/gorevler`
- H2 konsolu: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:gorevdb`, kullanıcı: `sa`)

Testleri çalıştırmak için:

```bash
mvn test
```

## REST API Uç Noktaları

| Metot  | Yol                          | Açıklama                              |
|--------|------------------------------|---------------------------------------|
| GET    | /api/gorevler                | Tüm görevleri listeler (`?durum=` filtresi opsiyonel) |
| GET    | /api/gorevler/{id}           | Tek görev getirir                     |
| POST   | /api/gorevler                | Yeni görev oluşturur (201 Created)    |
| PUT    | /api/gorevler/{id}           | Görevi günceller                      |
| PATCH  | /api/gorevler/{id}/durum     | Durum değiştirir (`?yeniDurum=TAMAMLANDI`) |
| DELETE | /api/gorevler/{id}           | Görevi siler (204 No Content)         |

Örnek istek:

```bash
curl -X POST http://localhost:8080/api/gorevler \
  -H "Content-Type: application/json" \
  -d '{"baslik":"Rapor hazırla","aciklama":"Aylık faaliyet raporu"}'
```

## İlan Şartları ile Eşleşme

| İlan Maddesi | Projedeki Karşılığı |
|---|---|
| a-b) Java 8+ ile geliştirme | Java 17, Stream API, lambda, method reference (`GorevServiceImpl`) |
| c) Kurumsal web tabanlı uygulama | Spring Boot web uygulaması + statik web arayüzü |
| ç) ORM (Hibernate), Spring Data JPA | `Gorev` entity + `GorevRepository extends JpaRepository` |
| d) Spring MVC, Spring Boot | `@RestController`, `@Service`, Spring Boot 3 |
| e) MSSQL Server ve T-SQL | `mssql-jdbc` bağımlılığı ve `application.properties` içinde hazır MSSQL profili (geliştirmede H2) |
| f) Web servisleri, REST | JSON tabanlı REST API, doğru HTTP durum kodları (200/201/204/400/404) |
| g) Çok katmanlı mimari | Controller → Service (arayüz + impl) → Repository → Entity, DTO katmanı |
| ğ) Performans, güvenlik, testler | Bean Validation, merkezi hata yönetimi (`@RestControllerAdvice`), MockMvc web testleri |
| h) Versiyon kontrol (GIT) | `.gitignore` hazır; `git init && git add . && git commit -m "ilk sürüm"` |
| ı) OOP, Tasarım Desenleri, TDD, Refactoring | Arayüze programlama, DTO + statik fabrika metodu, constructor injection, testler |
| i) jQuery, Javascript, HTML, CSS | `static/index.html` — jQuery/AJAX ile API tüketen arayüz |
| j) CI-CD, Jenkins | `Jenkinsfile` (derle → test → paketle boru hattı) |
| k) Maven | `pom.xml` ile bağımlılık yönetimi |
| m) Unit test | JUnit 5 + Mockito servis testleri, MockMvc controller testleri |

Not: Jasper Reports (madde l) kapsam dışı bırakıldı; ikinci gün eklenecek en doğal genişletme, görev listesinin JasperReports ile PDF raporu olarak dışa aktarılmasıdır (`GET /api/gorevler/rapor`).

## Proje Yapısı

```
src/main/java/com/ornek/gorevtakip
├── GorevTakipApplication.java     # Uygulama girişi
├── controller/GorevController     # Sunum katmanı (REST)
├── service/GorevService (+impl)   # İş katmanı
├── repository/GorevRepository     # Veri erişim katmanı (Spring Data JPA)
├── entity/Gorev, GorevDurumu      # JPA varlıkları
├── dto/GorevDto, GorevIstek       # Veri taşıma nesneleri
└── exception/...                  # Merkezi hata yönetimi
src/main/resources/static/index.html   # jQuery web arayüzü
src/test/java/...                      # JUnit 5 + Mockito + MockMvc testleri
Jenkinsfile                            # CI-CD boru hattı
```

## Mülakatta Anlatım İpuçları

- Neden DTO kullandığını (entity'yi dışarı açmamak, katman izolasyonu) anlatabilmek.
- `@Transactional(readOnly = true)` ile okuma işlemlerinde performans farkını bilmek.
- MockMvc testinin uygulamayı ayağa kaldırmadan web katmanını test ettiğini vurgulamak.
- H2 → MSSQL geçişinin sadece konfigürasyon değişikliği olduğunu (Hibernate dialect) göstermek.
