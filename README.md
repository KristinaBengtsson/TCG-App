# TCG-App
En webbaseret (og på sigt mobilvenlig) applikation til samlere af Trading Card Games (TCG),  Pokémon-kort i fokus.

## Pokémon-kortsøgning

Start `Main` med Java 17+ og Maven-afhængighederne fra `pom.xml`. Åbn
http://localhost:7070/cards, eller vælg **Search cards** i appens menu.
Søgningen er tilgængelig for både gæster og logget-ind brugere.

- Søg efter kortnavn, sætnavn, kortnummer, kort-ID, Pokédex-nummer eller kunstner.
- Vælg kortsprog, fx engelsk eller japansk. Søg med navnet på det valgte sprog.
- En tom søgning viser kataloget, 24 kort ad gangen. Klik på et kort for detaljer.
- **Only cards with images** er slået til fra start. Slå det fra for også at
  søge i kort, hvor TCGdex mangler billeder. API-parameteren hedder `onlyWithImages=true`.
- Kort og billeder kommer fra [TCGdex](https://tcgdex.dev), uden API-nøgle.

TCGdex har forskellig [dækning pr. sprog](https://tcgdex.dev/status).
Der er ingen garanti for alle verdens udgivelser, oversættelser, billeder eller
trykvarianter. Dansk er ikke et understøttet kortsprog. Søgning foretages i ét
kortsprog ad gangen; appen oversætter ikke søgeord. Denne integration søger i
kataloget; den gemmer endnu ikke kort i brugerens samling og viser ikke priser.

### Opbygning

`PokemonCardService` bruger Javas `HttpClient` til TCGdex REST API. API-kald har
forbindelses- og svartimeout. `PokemonCardController` stiller følgende ruter til rådighed:

- `GET /api/pokemon/cards?q=Pikachu&field=name&language=en&page=1`
- `GET /api/pokemon/cards/base1-4?language=en`

Søgefelter: `name`, `set.name`, `localId`, `id`, `dexId`, `illustrator`.
Resultater sorteres stabilt efter kort-ID. TCGdex leverer ikke et samlet antal
resultater i dette svar; efter en fuld side kan den næste derfor være tom.
Succesfulde svar må caches i browseren i fem minutter. Fejl caches ikke.

### Test

Kør `mvn test`. Testene bruger en lokal HTTP-server og kræver ikke adgang til
TCGdex. De dækker søgeparametre, Unicode, validering, kortdetaljer, timeouts,
API-fejl og de offentlige Javalin-ruter.

Manuel kontrol: søg efter `Pikachu` (engelsk), `ピカチュウ` (japansk),
Pokédex-nummer `25`, og en tekst uden match. Prøv næste/forrige side, åbn et kort,
og kontrollér fejlbeskeden uden netforbindelse.
