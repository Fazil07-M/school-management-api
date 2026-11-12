# School API

## 1. Layihənin təsviri

School API — təhsil müəssisəsinin idarə olunması üçün Java və Spring Boot texnologiyaları əsasında hazırlanmış REST API tətbiqidir.

Layihənin əsas məqsədi Spring ekosisteminin imkanlarını praktiki şəkildə tətbiq etmək, verilənlər bazası ilə işləməyi, tranzaksiyaların idarə olunmasını, keşləmə mexanizmlərini və verilənlər bazasının miqrasiyasını nümayiş etdirməkdir.

## 2. İstifadə olunan texnologiyalar

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate ORM 7
- PostgreSQL 16
- Liquibase
- Ehcache 3
- JCache
- Gradle
- Docker
- MapStruct
- Lombok

## 3. Əsas funksionallıq

Layihədə aşağıdakı funksionallıqlar həyata keçirilmişdir:

- **İstifadəçilərin idarə olunması:** istifadəçilər və pasport məlumatları ilə işləmək.
- **Departamentlərin idarə olunması:** departamentlərin və əməkdaşların idarə edilməsi.
- **Tələbə və kursların idarə olunması:** tələbələrin kurslara qeydiyyatı və əlaqələrin idarə edilməsi.
- **Müəllif və kitabların idarə olunması:** müəlliflər və kitablar arasında əlaqələrin qurulması.
- **REST API:** HTTP sorğuları vasitəsilə CRUD əməliyyatlarının həyata keçirilməsi.
- **DTO və MapStruct:** Entity və DTO obyektləri arasında çevrilmə.
- **Lombok:** təkrarlanan kodun azaldılması.

## 4. Verilənlər bazası və Liquibase

Layihədə PostgreSQL verilənlər bazasından istifadə olunur.

Liquibase vasitəsilə verilənlər bazasının strukturu versiyalaşdırılır və dəyişikliklər idarə olunur.

Əsas imkanlar:

- ChangeSet-lərin yaradılması və icrası.
- Cədvəllərin və əlaqələrin yaradılması.
- Foreign Key məhdudiyyətlərinin əlavə edilməsi.
- SQL vasitəsilə əlavə məhdudiyyətlərin yaradılması.
- Verilənlər bazasının dəyişiklik tarixçəsinin saxlanılması.
- Rollback və Tag mexanizmləri.

Hibernate üçün `ddl-auto=validate` rejimindən istifadə edilir. Bu rejim Entity strukturu ilə verilənlər bazasının strukturunun uyğunluğunu yoxlayır.

## 5. Hibernate Cache

Layihədə Hibernate keşləmə mexanizmləri tətbiq edilmişdir.

### Second-Level Cache

- Entity məlumatlarının keşdə saxlanılması.
- Ehcache və JCache inteqrasiyası.
- Təkrar sorğularda verilənlər bazasına müraciətlərin azaldılması.
- Cache Hit, Miss və Put göstəricilərinin yoxlanılması.

### Query Cache

- Sorğu nəticələrinin keşlənməsi.
- Təkrar sorğular zamanı keşdən istifadə.
- Query Cache Hit və Miss göstəricilərinin yoxlanılması.

## 6. Transaction Propagation

Spring Transaction Propagation mexanizmləri praktiki olaraq yoxlanılmışdır.

- **REQUIRED:** mövcud tranzaksiyaya qoşulur və ya yeni tranzaksiya yaradır.
- **REQUIRES_NEW:** müstəqil yeni tranzaksiya yaradır.
- **MANDATORY:** mövcud tranzaksiya tələb edir, tranzaksiya olmadıqda xəta yaradır.

Bu mexanizmlər əməkdaşların maaşının dəyişdirilməsi, departamentlər arasında köçürülməsi və tələbələrin kurslara qeydiyyatı kimi əməliyyatlarda tətbiq edilmişdir.

## 7. Transaction Isolation

PostgreSQL verilənlər bazasında müxtəlif Isolation Level səviyyələri yoxlanılmışdır.

- **READ_COMMITTED:** tranzaksiya yalnız təsdiqlənmiş dəyişiklikləri oxuyur.
- **REPEATABLE_READ:** eyni tranzaksiya daxilində təkrar oxumalar zamanı məlumatların sabitliyini təmin edir.
- **SERIALIZABLE:** paralel tranzaksiyaların nəticələrini ardıcıl icraya uyğunlaşdırır.

Aşağıdakı problemlər praktiki sınaqlarla araşdırılmışdır:

1. Non-Repeatable Read
2. Lost Update
3. Write Skew

Sınaqların nəticələri müxtəlif Isolation Level səviyyələrinin davranışını müqayisə etmək üçün istifadə edilmişdir.

## 8. Layihənin işə salınması

### Tələblər

Layihəni işə salmaq üçün aşağıdakılar tələb olunur:

- JDK 25
- Docker
- PostgreSQL 16
- Git

### Verilənlər bazasının hazırlanması

Docker vasitəsilə PostgreSQL konteyneri başladılmalıdır.

Mövcud konteyneri başlatmaq üçün:

```bash
docker start school-api-postgres
```

Verilənlər bazasının bağlantı parametrləri `application.properties` faylında konfiqurasiya edilməlidir.

İstifadə olunan verilənlər bazası:

- Database: `school_db`
- Host: `localhost`
- Port: `5433`

### Layihənin başladılması

Layihənin əsas qovluğunda terminal açın və aşağıdakı əmri icra edin:

Windows:

```bash
.\gradlew.bat