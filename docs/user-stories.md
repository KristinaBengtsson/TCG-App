# User stories og acceptkriterier

## User stories:

### US1 — Login
Som bruger vil jeg kunne logge ind med brugernavn og password, så jeg kan tilgå min profil.
#### Acceptkriterier:
- Givet at jeg indtaster et gyldigt brugernavn og korrekt password, får jeg adgang til min profil.
- Givet at brugernavn eller password er forkert, får jeg en neutral fejlbesked, der ikke afslører, hvilken af de to felter der er forkert.
- Givet at jeg har forsøgt at logge ind 3 gange forgæves, bliver kontoen midlertidigt låst.

### US2 — Nulstil password
Som bruger vil jeg kunne nulstille mit password, så jeg kan logge ind igen, hvis jeg har glemt det.
#### Acceptkriterier:
- Givet at jeg anmoder om nulstilling med min registrerede email, modtager jeg en mail med et nulstilling link.
- Givet at linket er brugt eller udløbet, kan det ikke bruges igen.
- Givet at jeg vælger et nyt password, skal det opfylde de samme regler for længde og tegn som ved oprettelse (US2)

### US3 — Rediger profil
Som bruger vil jeg kunne ændre mit brugernavn, email og password, så mine kontooplysninger altid er opdaterede.
#### Acceptkriterier:
- Givet at jeg ændrer mit brugernavn til et der ikke allerede er i brug, bliver ændringen gemt, og jeg kan logge ind med det nye brugernavn fremover.
- Givet at jeg ændrer mit brugernavn til et der allerede er i brug, får jeg en fejlbesked, og ændringen bliver ikke gennemført.
- Givet at jeg ændrer mit password, skal jeg indtaste mit nuværende password først, og det nye password skal opfylde de samme krav til længde og tegn som ved oprettelse (US2).
- Givet at jeg har gennemført en profilændring, modtager jeg en bekræftelse på skærmen.

### US4 — Slet konto
Som bruger vil jeg kunne slette min konto permanent, så mine data ikke længere er gemt i systemet.
#### Acceptkriterier:
- Givet at jeg vælger at slette min konto, bliver jeg bedt om at bekræfte handlingen ved at indtaste password igen, før slutningen gennemføres.
- Givet at jeg bekræfter sletningen, bliver min profil, samling, wishlist og kommentarer permanent fjernet fra systemet.
  (Note til gruppen: Vi skal blive enige om kommentarer skal slettes helt eller anonymiseres (fx vises som “slettet bruger”), hvis andre har svaret på dem, hænger sammen med US5)

### US5 — Opret konto
Som bruger vil jeg kunne oprette en konto, så jeg kan få min egen profil.
#### Acceptkriterier:
- Hvis brugernavnet allerede er i brug, får jeg en fejlbesked.
- Hvis mit password ikke opfylder kravene til længde og tegn, får jeg en fejlbesked der forklarer kravene.
- Hvis jeg opfylder kravene, bliver min konto oprettet.

### US6 — Tilføj kort til samling
Tilføj kort til samling Som bruger vil jeg kunne tilføje kort til min samling, så jeg kan holde styr på, hvilke kort jeg ejer.
#### Acceptkriterier:
- Når jeg tilføjer et kort, skal jeg angive kortets stand fra en foruddefineret liste, fx Mint/NM/Damaged. (Hænger sammen med at kunne søge på kort US10)
- Jeg skal angive antallet af kort.
- Givet at jeg tilføjer et kort, som jeg allerede ejer i samme stand, bliver antallet opdateret frem for overskrevet.
- Når kortet er tilføjet, kan jeg se det i min samling.

### US7 — Rediger kort i samling
Som bruger vil jeg kunne redigere kort i min samling, så oplysningerne om min samling er korrekte.
#### Acceptkriterier:
- Jeg kan ændre antallet af et kort.
- Jeg kan ændre kortets stand.
- Når jeg gemmer ændringerne, bliver de opdaterede oplysninger vist i min samling.
- Samlingens totalværdi opdateres automatisk, hvis ændringen påvirker værdien.

### US8 — Slet kort i samling
Fjern kort fra samling Som bruger vil jeg kunne fjerne kort fra min samling, så min samling kun indeholder kort, jeg ejer.
#### Acceptkriterier:
- Jeg kan vælge et kort i min samling og fjerne det.
- Når kortet er fjernet, vises det ikke længere i min samling.
- Samlingens totalværdi opdateres automatisk efter fjernelsen. (Afhænger af at US6 er implementeret først)

### US9 — Wishlist
Som bruger vil jeg kunne lave en wishlist, så jeg kan holde styr på de kort, jeg gerne vil have.
#### Acceptkriterier:
- Når jeg gemmer et kort til wishlisten, skal jeg kunne se det på listen.
- Hvis jeg fjerner et kort fra wishlisten, forsvinder det fra listen.
- Hvis jeg tilføjet et kort til min samling, som allerede står på min wishlist, bliver jeg spurgt om det skal fjernes fra wishlisten eller bliver stående.
- Givet at jeg ønsker flere eksemplarer af samme kort, kan jeg angive et ønsket antal på wishlisten.
- Givet at jeg deler min wishlist via link og vedkommende der tilgår linket er logget ind, kan vedkommende se den, uden at kunne redigere den.


### US10 — Vis Markedspris på kort
Som bruger vil jeg kunne se markedsprisen på kort, så jeg kan følge med i, hvad mine kort og min samling er værd.
#### Acceptkriterier:
- Givet at et kort har en registreret markedspris, vises prisen på kortets detaljeside.
- Hvis jeg har kortet i min samling, indgår pris x antal i samlingen totalværdi.
- Givet at et kort ikke har en registreret pris, vises “pris ikke tilgængelig” i stedet for en fejl.

### US11 — Vælg officielt master-set
Som bruger vil jeg kunne vælge et eksisterende officielt master-set, så jeg kan tracke min samling op imod et foruddefineret sæt.
#### Acceptkriterier:
- Givet at jeg browser listen af officielle master-sets, kan jeg se dem grupperet efter serie og generation.
- Givet at jeg vælger et officielt master-set, bliver det tilføjet min oversigt, så jeg kan følge min fremgang på det.
- Givet at jeg allerede ejer kort fra sættet i min samling, bliver de automatisk matchet ind i fremgangs visningen, jeg skal ikke tilføje dem igen manuelt.
- Givet at jeg ikke længere ønsker at tracke et master-set, kan jeg fjerne det fra min oversigt uden at det påvirker kortene i min samling.
- Officielle master-sets kan ikke redigeres eller slettes af almindelige brugere, i modsætning til egne master-sets.
- Givet at et sæt er 100% fuldført, markeres det visuelt som færdigt.

### US12 — Egne master-sets
Som bruger vil jeg kunne oprette mine egne master-sets, så jeg kan organisere kort efter egne kriterier.
#### Acceptkriterier:
- Givet at jeg opretter et nyt master-set, kan jeg give det et navn og selv vælge hvilke kort der indgår.
- Givet at jeg har et master-set, kan jeg se hvor stor en procentdel jeg allerede ejer af de inkluderede kort.
- Givet at jeg redigerer eller sletter mit eget master-set, påvirker det ikke officielle master-sets.
- Givet at et sæt er 100% fuldført, markeres det visuelt som færdigt.
- Givet at et kort  findes i 2 forskellige master sets, skal der være en option der dukker op, om at kortet eksisterer i flere sets og hvilket man ønsker at tilføje det til.

### US13 — Se fremgang på master-set
Som bruger vil jeg kunne se en visuel oversigt over min fremgang på et master-set, så jeg hurtigt kan se hvor tæt jeg er på at fuldføre det.
#### Acceptkriterier:
- Givet at jeg har et master-set (officielt eller eget), kan jeg se hvor mange kort ud af det totale antal jeg ejer.
- Givet at jeg ejet kort fra sættet, vises fremgangen som både et tal ( fx “142/151”) og en visuel indikator (fx en fremgang bjælke).
- Givet at jeg tilføjer eller fjerner et kort der indgår i et master-set, opdateres fremgangs visningen automatisk uden at jeg skal genindlæse siden.
- Givet at jeg ejer flere eksemplarer af samme kort, tæller kortet stadig kun en gang i fremgangen.

### US14 — Gæsteadgang
Som gæst vil jeg kunne bruge app'en uden konto, så jeg kan se kort og priser uden at oprette en konto.
#### Acceptkriterier:
- Givet at jeg er gæst, kan jeg søge efter kort og se kortdetaljer og markedspriser.
- Hvis jeg som gæst prøver at gemme, like, dislike eller kommentere, bliver jeg bedt om at oprette en konto eller logge ind.


### US15 — Søgning efter kort
Som bruger eller gæst vil jeg kunne søge efter kort, så jeg hurtigt kan finde det kort, jeg leder efter.
#### Acceptkriterier:
- Givet at jeg søger, kan jeg søge på sæt, kort navn, Pokédex-nummer, Pokémon/karakter og kunstner.
- Givet at jeg har søgeresultater, kan jeg filtrere og sortere dem, fx efter pris eller udgivelsesdato.
- Givet at søgningen ikke giver resultater, får jeg en tydelig besked om det.
- Søgefunktionen fungerer identisk for gæster og logget-ind brugere.

### US16 — Automatisk wishlist
Som bruger vil jeg kunne generere en wishlist automatisk ud fra manglende kort i et master-set, så jeg nemt kan se hvad jeg mangler. (Afhængig af US4 og US7 virker)
#### Acceptkriterier:
- Når jeg vælger et master-set, skal systemet sammenligne kortene i sættet med min samling.
- Hvis der er kort i sættet som jeg mangler, skal jeg automatisk kunne tilføje dem til min wishlist.


## Nice to have / fremtidig inplementering

### US3+ 
- Givet at jeg ændrer min email, skal jeg bekræfte den nye mailadresse via et bekræftelseslink, før ændringen træder i kraft. (skal det sendes til den nye eller gamle email?)

### US4+ 
- Givet at min konto er slettet, kan jeg ikke længere logge ind med de gamle oplysninger.
- Givet at kontoen er slettet, modtager jeg en bekræftelsesmail om at sletningen er gennemført.

### US5+
- Når kontoen er oprettet, modtager jeg en bekræftelsesmail.

### US17 — Community: Likes, dislikes og kommentarer
Som bruger vil jeg kunne like, dislike og kommentere på kort, så der kan opstå et fællesskab omkring samlingen og kortene.
#### Acceptkriterier:
- Givet at jeg er logget ind, kan jeg like, dislike og kommentere på ethvert kort i databasen.
- Givet at jeg er logget ind, kan jeg like, dislike og svare på andre brugeres kommentarer.
- Givet at jeg fortryder min egen kommentar, kan jeg slette den.
- Givet at en kommentar indeholder ord fra en foruddefineret liste over upassende sprog, bliver kommentaren automatisk blokeret, før den offentliggøres.
  (Note til gruppen: Man kunne starte med en simpel ordliste-filtrering frem for en fuld moderations-model, men beholder ambitionen om at udbygge dette senere hvis tiden tillader det)

### US18 — Achievement-ikoner (Ønsker vi dette som gruppe?)
Som bruger vil jeg have et achievement-ikon på min profil for hvert master-set jeg har fuldført, så jeg kan vise mine præstationer som samler.
#### Acceptkriterier:
- Givet at et master-set (officielt eller eget) når 100% fremgang, tildeles jeg automatisk et achievement-ikon for det sæt (Skal der være tilhørende dato for hvornår man fik den?).
- Givet at jeg klikker på et achievement-ikon, bliver jeg sendt til settets detaljevisning.
- Givet at jeg besøger min profil, kan jeg se alle mine optjente achievement-ikoner.
- Givet at et tidligere fuldført sæt ikke længere er 100%, fjernes det tilhørende ikon automatisk på min profil.

### US19 — AI-genkendelse af kort
Som bruger vil jeg kunne tage et billede af et fysisk kort, så appen automatisk kan finde ud af, hvilket kort det er.
#### Acceptkriterier:
- Når jeg tager eller uploader et billede af et kort, skal systemet forsøge at finde kortets navn, sæt og nummer.
- Hvis systemet ikke er sikkert på, hvilket kort det er, skal jeg kunne søge efter og vælge kortet manuelt.
- Jeg skal kunne godkende eller afvise det kort, systemet foreslår, før det bliver tilføjet til min samling.

### US20 — Slå funktioner til og fra
Som bruger vil jeg kunne slå funktioner til og fra, så appen passer til mine behov.
#### Acceptkriterier:
- Givet at jeg slår community-funktioner (likes/dislikes/kommentarer) fra, er de skjult i min visning.
- Givet at jeg slår prisvisning fra, vises priser ikke længere for mig.
- Givet at jeg ændrer mine indstillinger, forbliver de gemt til jeg logger ind næste gang.

(Overvejelser jeg har tænkt over men de er langt ud i fremtiden synes jeg (Nasta))
Spørgsmål til gruppen, hører US9 i virkeligheden til her?

### US21 — Byt kort med andre brugere
Som bruger vil jeg kunne foreslå at bytte kort med andre brugere, så vi begge kan få de kort, vi mangler.
#### Acceptkriterier:
- Hvis jeg finder et kort i en anden brugers offentlige samling, skal jeg kunne sende et bytteforslag med et eller flere af mine egne kort.
- Den anden bruger skal kunne acceptere, afvise eller ændre forslaget.
- Når et bytte bliver accepteret, skal begge samlinger automatisk blive opdateret.
- Begge brugere skal kunne se en historik over deres gennemførte bytter.
  (Skal her tilføjes muligheden for at lave en handel der opretter forsendelse hvis man ikk e er i nærheden af hinanden?)

