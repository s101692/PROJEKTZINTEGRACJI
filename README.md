# Mieszkania — hurtownia danych o cenach mieszkań

Aplikacja Spring Boot, która integruje dane o cenach mieszkań z **GUS BDL** (JSON)
ze **stopami referencyjnymi NBP** (XML) i buduje zintegrowaną tabelę faktów
w bazie MySQL/MariaDB.

## Stack technologiczny
- Java 17, Spring Boot 4.1, Spring Data JPA / Hibernate
- MySQL 8 / MariaDB 10.4 (XAMPP)
- Maven (wrapper w repozytorium), Docker + docker-compose
- Thymeleaf do frontendu

## Wymagania
- JDK 17+ (działa też na nowszych, np. 25)
- Maven — nie trzeba instalować, jest wrapper `mvnw`
- Baza: lokalna MySQL/MariaDB (np. XAMPP) **albo** Docker Desktop

## Uruchomienie lokalne (XAMPP / MariaDB)
1. Uruchom MySQL w XAMPP (port 3306).
2. Utwórz bazę — Hibernate tworzy tabele, ale **nie tworzy samej bazy**:
   ```sql
   CREATE DATABASE projekt_mieszkania CHARACTER SET utf8mb4;
   ```
3. Sprawdź `src/main/resources/application.yaml` (domyślnie: użytkownik `root`,
   puste hasło, `localhost:3306`).
4. Uruchom aplikację:
   ```bash
   ./mvnw spring-boot:run
   ```
   lub w IntelliJ: Run na klasie `MieszkaniaApplication`.
5. Aplikacja startuje na http://localhost:8080. Możliwy jest dostęp do strony za pomocą przeglądarki, 
jednak wymagana jest autoryzacja. 

Przy starcie `DataLoader` zasila bazę danymi ze źródeł, a `HarmonogramService`
odświeża je cyklicznie co 60 sekund.

## Uruchomienie w Dockerze
Nie trzeba nic budować ręcznie — obraz buduje JAR w środku (build multi-stage),
a aplikacja czeka, aż baza będzie gotowa (healthcheck).

```bash
docker compose up --build
```
- baza `projekt_mieszkania` tworzy się automatycznie w kontenerze MySQL,
- aplikacja: http://localhost:8080, baza wystawiona na porcie 3306.

Zatrzymanie:
```bash
docker compose down        # zatrzymuje kontenery
docker compose down -v     # dodatkowo kasuje dane bazy (wolumen)
```

## Źródła danych
- **GUS BDL API** — ceny mieszkań per województwo (JSON, deserializacja Jacksonem).
- **NBP stopy referencyjne** — plik `src/main/resources/stopy_nbp.xml`,
  parsowany dwoma metodami: DOM + XPath oraz SAX.

## Struktura projektu
```
src/main/java/org/example/integracjaprojekt/
├── model/        encje JPA (StopaReferencyjna, CenaMieszkania, FaktZintegrowany, Uzytkownik)
├── repository/   repozytoria Spring Data JPA
├── dto/          DTO odpowiedzi GUS
├── service/      pobieranie (GUS/NBP), integracja, harmonogram, autoryzacja
├── controller/   kontrolery do routingu
├── security/     obsługa tokenu i logowania
└── config/       DataLoader — zasilanie bazy przy starcie, oraz SecurityConfig - zapewnianie wymogu autoryzacji
```
