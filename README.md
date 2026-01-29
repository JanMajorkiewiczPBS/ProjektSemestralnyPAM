# 📱 Projekt Semestralny - Dziennik Aktywności Fizycznej

Aplikacja mobilna Android służąca do monitorowania aktywności fizycznej użytkownika poprzez wykorzystanie wbudowanych sensorów urządzenia. Aplikacja zbiera dane z trzech różnych źródeł (krokomierz, GPS, aparat fotograficzny), prezentuje je w czytelnej formie oraz umożliwia ich trwałe przechowywanie.

## 📋 Spis treści

- [Opis aplikacji](#-opis-aplikacji)
- [Użyte sensory](#-użyte-sensory)
- [Funkcjonalności](#-funkcjonalności)
- [Wymagania techniczne](#-wymagania-techniczne)
- [Zrzuty ekranu](#-zrzuty-ekranu)
- [Instrukcja uruchomienia](#-instrukcja-uruchomienia)
- [Architektura](#-architektura)

## 🎯 Opis aplikacji

**Dziennik Aktywności Fizycznej** to aplikacja Android umożliwiająca użytkownikowi:

- **Monitorowanie kroków** - automatyczne zliczanie kroków za pomocą wbudowanego sensora krokomierza
- **Śledzenie lokalizacji** - rejestracja pozycji GPS podczas aktywności
- **Dokumentowanie aktywności** - robienie zdjęć związanych z sesją treningową
- **Zapisywanie sesji** - trwałe przechowywanie danych o aktywnościach w bazie danych
- **Przeglądanie historii** - wyświetlanie zapisanych sesji z wykresami i statystykami
- **Udostępnianie danych** - możliwość eksportu podsumowania aktywności przez e-mail lub inne aplikacje

Aplikacja została zbudowana zgodnie z wymaganiami projektu semestralnego, wykorzystując nowoczesne technologie Android takie jak Jetpack Compose, Navigation Compose, Room Database oraz architekturę MVVM z wzorcem UiState.

## 📡 Użyte sensory

Aplikacja wykorzystuje **trzy źródła danych** z urządzenia mobilnego:

### 1. **Step Counter Sensor** (Krokomierz)
- **Typ**: `Sensor.TYPE_STEP_COUNTER`
- **Funkcjonalność**: Automatyczne zliczanie kroków użytkownika
- **Uprawnienia**: `ACTIVITY_RECOGNITION` (Android 10+)
- **Implementacja**: `HomeViewModel` implementuje `SensorEventListener`

### 2. **GPS / Location Services** (Lokalizacja)
- **Typ**: `FusedLocationProviderClient` (Google Play Services)
- **Funkcjonalność**: Śledzenie pozycji geograficznej użytkownika
- **Uprawnienia**: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- **Implementacja**: Ciągłe aktualizacje lokalizacji z priorytetem `HIGH_ACCURACY`

### 3. **Camera** (Aparat fotograficzny)
- **Typ**: `ActivityResultContracts.TakePicturePreview()`
- **Funkcjonalność**: Robienie zdjęć związanych z sesją aktywności
- **Uprawnienia**: `CAMERA`
- **Implementacja**: Zapis zdjęć do pamięci wewnętrznej urządzenia

## ✨ Funkcjonalności

### Ekran główny (HomeScreen)
- ✅ Wyświetlanie aktualnej liczby kroków w czasie rzeczywistym
- ✅ Wyświetlanie aktualnej lokalizacji GPS (szerokość/długość geograficzna)
- ✅ Przycisk do robienia zdjęć aparatem
- ✅ Podgląd zrobionego zdjęcia
- ✅ Zapisywanie sesji aktywności (kroki + lokalizacja + zdjęcie)
- ✅ Ręczne zwiększanie kroków (opcja testowa)
- ✅ Automatyczny reset kroków po zapisaniu sesji
- ✅ Przejście do ekranu szczegółów

### Ekran szczegółów (DetailsScreen)
- ✅ Lista wszystkich zapisanych sesji aktywności
- ✅ Statystyki: łączna liczba kroków ze wszystkich sesji
- ✅ Wykres słupkowy pokazujący kroki w poszczególnych dniach
- ✅ Wyświetlanie szczegółów każdej sesji:
  - Timestamp (znacznik czasu)
  - Liczba kroków
  - Współrzędne geograficzne
  - Zdjęcie (jeśli dostępne)
- ✅ Usuwanie wszystkich sesji
- ✅ Udostępnianie podsumowania aktywności (e-mail, SMS, inne aplikacje)

## 🛠️ Wymagania techniczne

### Wymagania systemowe
- **Minimalna wersja Android**: API 24 (Android 7.0 Nougat)
- **Docelowa wersja Android**: API 34 (Android 14)
- **JDK**: 17
- **Kotlin**: 1.9.22

### Technologie i biblioteki
- **UI Framework**: Jetpack Compose
- **Navigation**: Navigation Compose 2.8.0 (type-safe routes)
- **Architektura**: MVVM (Model-View-ViewModel)
- **State Management**: StateFlow + UiState pattern
- **Baza danych**: Room Database 2.6.1
- **Lokalizacja**: Google Play Services Location 21.3.0
- **Uprawnienia**: Accompanist Permissions 0.34.0
- **Obrazy**: Coil Compose 2.6.0
- **Serializacja**: Kotlinx Serialization JSON 1.6.3

### Wymagane uprawnienia
- `ACTIVITY_RECOGNITION` - do zliczania kroków
- `ACCESS_FINE_LOCATION` - do precyzyjnej lokalizacji GPS
- `ACCESS_COARSE_LOCATION` - do przybliżonej lokalizacji
- `CAMERA` - do robienia zdjęć

## 📸 Zrzuty ekranu

### Ekran główny
![Ekran główny](screenshots/ekran_glowny.png)
*Ekran główny z wyświetlaną liczbą kroków, lokalizacją GPS oraz przyciskami do robienia zdjęć i zapisywania sesji*

### Ekran szczegółów
![Ekran szczegółów](screenshots/widok_sesji.png)
*Ekran szczegółów z listą sesji, statystykami i wykresem aktywności*

### Udostępnianie danych
![Udostępnianie](screenshots/wysylanie_sesji.png)
*Dialog udostępniania podsumowania aktywności przez e-mail lub inne aplikacje*

### Funkcja robienia zdjęcia na treningu
![Udostępnianie](screenshots/zdjecie.png)
*Przykład robienia zdjęcia do treningu*

### Widok główny ze statystykami
![Udostępnianie](screenshots/widok_glowny2.png)
*Widok główny z przykładowymi statystykami (można testować nabijanie kroków przez przycisk)*

> **Uwaga**: Zrzuty ekranu należy dodać do folderu `screenshots/` w katalogu głównym projektu.

## 🚀 Instrukcja uruchomienia

### Wymagania wstępne
1. **Android Studio** - wersja Hedgehog (2023.1.1) lub nowsza
2. **JDK 17** - zainstalowany i skonfigurowany w Android Studio
3. **Android SDK** - API Level 34
4. **Urządzenie Android** lub **Emulator** z Androidem 7.0+ (API 24+)

### Kroki instalacji

#### 1. Sklonuj repozytorium
```bash
git clone <url-repozytorium>
cd ProjektSemestralny
```

#### 2. Otwórz projekt w Android Studio
1. Uruchom Android Studio
2. Wybierz **File → Open**
3. Wybierz folder `ProjektSemestralny`
4. Poczekaj na zakończenie synchronizacji Gradle

#### 3. Skonfiguruj projekt
1. Android Studio automatycznie wykryje i pobierze wymagane zależności
2. Jeśli pojawią się błędy, wykonaj **File → Sync Project with Gradle Files**
3. Upewnij się, że używasz JDK 17:
   - **File → Project Structure → SDK Location**
   - Sprawdź **JDK location**

#### 4. Uruchom aplikację

**Na emulatorze:**
1. Utwórz emulator: **Tools → Device Manager → Create Device**
2. Wybierz urządzenie z Androidem 7.0+ (API 24+)
3. Uruchom emulator
4. Kliknij **Run** (Shift+F10) lub **Run → Run 'app'**

**Na urządzeniu fizycznym:**
1. Włącz **Opcje programisty** na urządzeniu Android
2. Włącz **Debugowanie USB**
3. Podłącz urządzenie do komputera przez USB
4. Zatwierdź połączenie debugowania na urządzeniu
5. Wybierz urządzenie z listy w Android Studio
6. Kliknij **Run** (Shift+F10)

### Pierwsze uruchomienie

1. Po uruchomieniu aplikacji pojawi się ekran z prośbą o uprawnienia
2. Kliknij **"Request Permissions"** i przyznaj wszystkie wymagane uprawnienia:
   - Lokalizacja (dokładna i przybliżona)
   - Aparat
   - Rozpoznawanie aktywności (Android 10+)
3. Po przyznaniu uprawnień aplikacja automatycznie rozpocznie:
   - Zliczanie kroków
   - Śledzenie lokalizacji GPS

### Testowanie aplikacji

1. **Test kroków**:
   - Użyj przycisku **"+1 Step (Test)"** do ręcznego zwiększania kroków
   - Lub po prostu chodź z urządzeniem

2. **Test zapisywania sesji**:
   - Zrób zdjęcie przyciskiem **"Take Photo"**
   - Kliknij **"Save Session"** - sesja zostanie zapisana, a kroki zresetowane

3. **Przeglądanie historii**:
   - Kliknij **"View Saved Sessions"**
   - Zobaczysz listę wszystkich zapisanych sesji ze statystykami i wykresami

4. **Udostępnianie danych**:
   - Na ekranie szczegółów kliknij **"Share summary"**
   - Wybierz aplikację (e-mail, SMS, itp.) do udostępnienia danych

## Architektura

### Struktura projektu
```
app/
├── src/main/
│   ├── java/com/example/projektsemestralny/
│   │   ├── data/              # Warstwa danych
│   │   │   ├── ActivitySession.kt      # Encja Room
│   │   │   ├── ActivitySessionDao.kt   # DAO dla sesji
│   │   │   └── AppDatabase.kt          # Baza danych Room
│   │   ├── ui/
│   │   │   ├── navigation/             # Nawigacja
│   │   │   │   ├── Destinations.kt     # Type-safe routes
│   │   │   │   └── NavGraph.kt         # Graf nawigacji
│   │   │   ├── screens/                # Ekrany Compose
│   │   │   ├── HomeScreen.kt           # Ekran główny
│   │   │   └── DetailsScreen.kt       # Ekran szczegółów
│   │   └── theme/                      # Motywy UI
│   ├── viewmodels/
│   │   ├── HomeUiState.kt              # Stan UI
│   │   └── HomeViewModel.kt            # Logika biznesowa
│   ├── utils/
│   │   └── FileUtil.kt                 # Narzędzia do plików
│   └── MainActivity.kt                 # Główna aktywność
```

### Wzorce architektoniczne

- **MVVM (Model-View-ViewModel)**: Separacja logiki biznesowej od UI
- **UiState Pattern**: Centralizacja stanu UI w jednym obiekcie
- **Repository Pattern**: Abstrakcja dostępu do danych (Room Database)
- **Observer Pattern**: StateFlow do reaktywnych aktualizacji UI

### Przepływ danych

```
Sensor/GPS/Camera → HomeViewModel → HomeUiState → Compose UI
                              ↓
                        Room Database
```

