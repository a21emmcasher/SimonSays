Simon Says - Explicació del Codi
Aquest projecte és una implementació del joc "SimonSays" a Android en llenguatge Kotlin. En aquest joc, el dispositiu mostra una seqüència de colors i sons, i el jugador ha de repetir aquesta seqüència prement els botons en el mateix ordre. A mesura que el jugador encerta, la seqüència es fa més llarga i desafiadora.

Estructura del Codi:
- Definició de Botons i Variables
    buttons: Array que conté les referències als botons a la interfície d'usuari.
    sequence: Mutable List que emmagatzema la seqüència de colors que el jugador ha de repetir.
    playerSequenceIndex: Índex que rastreja la posició del jugador a la seqüència actual.
    score: Variable que registra la puntuació del jugador.
    isPlayerTurn: Indicador que determina si és el torn del jugador.
    level: Comptador que representa el nivell actual del joc.
    soundPool: Instància de SoundPool que s'utilitza per reproduir sons.
    soundIds: Array que emmagatzema els IDs dels sons carregats a SoundPool.

- Configuració Inicial
Al mètode onCreate(), s'inicialitzen els botons, es carreguen els sons a SoundPool i s'estableixen els listeners de clic per als botons.
  - Inici del Joc
El mètode startGame() inicialitza les variables del joc i comença una partida nova.
El mètode addToSequence() afegeix un color nou a la seqüència i actualitza la puntuació del jugador.
El mètode displaySequence() mostra la seqüència de colors al jugador.

  - Reproducció de Sons
El mètode playSound() reprodueix el so corresponent a un botó específic utilitzant SoundPool i els ID de so carregats prèviament.
  - Interacció de l'Usuari
Al mètode onClick(), es manegen les interaccions de l'usuari quan prem un botó. Si el jugador pressiona els botons en l'ordre correcte, avança al nivell següent. Si no, es reinicia el joc.

  - Efectes Visuals
El mètode highlightButton() canvia el color de fons d'un botó per simular l'efecte visual d'il·luminació (es crida a l'onclick quan estàs jugant i en displaySequence, per veure quan ho fa la màquina).

  - Alliberament de Recursos
Al mètode onDestroy(), s'allibera el SoundPool per evitar fuites de memòria quan l'activitat es destrueix.

- Funcionament del Joc
El joc sigui el següent flux:
1. En iniciar, es mostra un botó aleatori al jugador i se'n reprodueix el so.
2. El jugador ha de repetir la seqüència prement els botons en el mateix ordre.
3. Si el jugador l'encerta, s'afegeix un nou color a la seqüència i s'avança al nivell següent.
4. Si el jugador s'equivoca, el joc s'acaba i es mostra la puntuació final.
5. El jugador pot començar un nou joc prement qualsevol botó.
