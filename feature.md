# Feature: Modal do dodawania nowej publikacji

Poniższe instrukcje opisują, jak dodać modal (dialog) do tworzenia nowej publikacji z wyborem typu: General, Review, Ranking, Support, News. Instrukcja jest zaplanowana pod Compose Multiplatform i obecny układ modułów.

## Cel
- Umożliwić użytkownikowi dodanie publikacji z wyborem typu.
- Modal powinien być dostępny z `HomeScreen` (np. ikoną „+”/„add”).
- Zwrócić dane do ViewModelu i dodać nowy wpis do listy publikacji.

## Zakres zmian (proponowany)
- UI: nowy dialog + integracja z `HomeScreen`.
- State: nowy stan modalu i wybrany typ publikacji.
- Intent/Action: otwarcie, zamknięcie, zapis publikacji.
- Model: enum typów publikacji.

## Kroki implementacji

### 1) Model typu publikacji
- Dodaj enum (lub sealed class) z typami publikacji w `composeApp/src/commonMain/kotlin/` (np. `domain/model/PublicationType.kt`).
- Typy:
  - `GENERAL`
  - `REVIEW`
  - `RANKING`
  - `SUPPORT`
  - `NEWS`

### 2) Rozszerz stan i intencje ekranu Home
- W `HomeState` dodaj pola:
  - `isAddPublicationDialogVisible: Boolean`
  - `selectedPublicationType: PublicationType` (domyślnie `GENERAL`)
  - `newPublicationTitle: String`
  - `newPublicationBody: String`
- W `HomeIntent` dodaj akcje:
  - `OpenAddPublicationDialog`
  - `CloseAddPublicationDialog`
  - `SetPublicationType(type: PublicationType)`
  - `SetPublicationTitle(title: String)`
  - `SetPublicationBody(body: String)`
  - `SubmitNewPublication`

### 3) UI: dialog dodawania publikacji
- Utwórz nowy composable np. `AddPublicationDialog` w `composeApp/src/commonMain/kotlin/com/talkfrly/multiplatform/ui/components/publications/`.
- Dialog powinien zawierać:
  - Tytuł: „Nowa publikacja”.
  - Pole wyboru typu (SegmentedButtons, Chips lub Dropdown). Typy: General, Review, Ranking, Support, News.
  - `TextField` na tytuł.
  - `TextField` na treść/skrót (np. wielowierszowe).
  - Dwa przyciski: „Anuluj” i „Dodaj”.
- Walidacja minimalna:
  - `Dodaj` aktywny tylko gdy tytuł i treść nie są puste.

### 4) Integracja z HomeScreen
- Dodaj przycisk otwierający modal (np. w prawym górnym rogu obok ikony profilu):
  - `onClick -> OpenAddPublicationDialog`
- Renderuj dialog warunkowo:
  - Jeśli `state.isAddPublicationDialogVisible`, pokaż `AddPublicationDialog` i przekaż callbacki.
- Mapowanie akcji UI do `HomeIntent`.

### 5) Obsługa w ViewModel
- W `HomeViewModel` obsłuż nowe intencje:
  - `OpenAddPublicationDialog` -> ustaw `isAddPublicationDialogVisible = true`.
  - `CloseAddPublicationDialog` -> reset dialog state (zamknij + wyczyść pola).
  - `SetPublicationType/Title/Body` -> aktualizacja stanu.
  - `SubmitNewPublication` -> walidacja i dodanie publikacji do listy.
- W przypadku architektury z repository:
  - wywołaj metodę `publicationRepository.create(...)` i po sukcesie odśwież listę.

### 6) UX i szczegóły
- Po udanym dodaniu:
  - zamknij modal
  - wyczyść stan formularza
  - opcjonalnie przewiń listę do nowego elementu
- Przy błędzie:
  - pokaż komunikat (np. `Snackbar`) i pozostaw dane w polach.

## Akceptacja / Kryteria ukończenia
- Modal pojawia się po kliknięciu przycisku „Dodaj”.
- Użytkownik wybiera typ publikacji z pięciu dostępnych opcji.
- Przycisk „Dodaj” jest aktywny tylko przy wypełnionych polach.
- Po dodaniu publikacja pojawia się na liście bez potrzeby restartu ekranu.
- Stan dialogu jest resetowany po zamknięciu.

## Notatki
- W przyszłości można dodać upload obrazków i tagi.
- Warto rozważyć i18n (string resources) zamiast hardcode tekstów.
