# School Management API

Java və Spring Boot texnologiyaları əsasında hazırlanmış təhsil müəssisəsinin idarə edilməsi üçün REST API layihəsi.

## İstifadə olunan texnologiyalar

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- PostgreSQL
- MapStruct
- Gradle

## Tələblər

Layihəni işə salmaq üçün aşağıdakılar quraşdırılmalıdır:

- JDK 25
- PostgreSQL
- Git

## Verilənlər bazasının yaradılması

PostgreSQL-də `school_db` adlı verilənlər bazası yaradın:

```sql
CREATE DATABASE school_db;
```

Verilənlər bazasına qoşulma parametrləri `src/main/resources/application.properties` faylında yerləşir.

Standart parametrlər:

- Host: localhost
- Port: 5432
- Database: school_db
- Username: postgres

Lazım olduqda qoşulma parametrlərini öz PostgreSQL konfiqurasiyanıza uyğun dəyişdirin.

## Layihənin işə salınması

Əvvəlcə GitHub-dan layihəni klonlayın:

```bash
git clone REPOSITORY_URL
```

Layihənin qovluğuna daxil olun:

```bash
cd testders
```

Windows üçün:

```powershell
.\gradlew.bat bootRun
```

Linux / macOS üçün:

```bash
./gradlew bootRun
```

Layihə uğurla başladıqdan sonra API aşağıdakı ünvanda əlçatan olacaq:

```text
http://localhost:8080/test-demo
```

## REST API Endpoint-ləri

Endpoint-lərin tam siyahısı növbəti bölmədə təqdim olunacaq.

## Qeyd

Layihə PostgreSQL verilənlər bazasından istifadə edir. Tətbiqi işə salmazdan əvvəl verilənlər bazasının yaradıldığına və qoşulma parametrlərinin düzgün olduğuna əmin olun.


## REST API Endpoint-ləri

Bütün sorğular aşağıdakı əsas URL vasitəsilə göndərilir:

`http://localhost:8080/test-demo`

### 1. User API — `/api/users`

| HTTP Method | Endpoint | Təsvir |
|---|---|---|
| POST | `/api/users` | Yeni istifadəçi yaradır |
| GET | `/api/users` | Bütün istifadəçiləri qaytarır |
| GET | `/api/users/{id}` | ID üzrə istifadəçini tapır |
| GET | `/api/users/email/{email}` | Email üzrə istifadəçini tapır |
| GET | `/api/users/gender/{gender}` | Gender üzrə istifadəçiləri filtrləyir |
| GET | `/api/users/without-passport` | Pasportu olmayan istifadəçiləri qaytarır |
| PUT | `/api/users/{id}` | İstifadəçi məlumatlarını yeniləyir |
| DELETE | `/api/users/{id}` | İstifadəçini silir |
| POST | `/api/users/{userId}/passport` | İstifadəçiyə pasport əlavə edir |
| GET | `/api/users/{userId}/passport` | İstifadəçinin pasportunu qaytarır |
| DELETE | `/api/users/{userId}/passport` | İstifadəçinin pasportunu silir |

