# HiasMaps-final

Gruppe 18

Zum Ausführen des Programms wird jdk Version 11 und Gradle Version 5+ benötigt. 
Anleitung zur Verwendung des Programms:
1) Datei entzippen. 
2) In das Verzeichnis des entzippten Ordners navigieren.
3) Verzeichnis im Terminal öffnen.
4) Mit dem Command:
GRADLE_OPTS="-Xms<whatever>m -Xmx<whatever>m" 
spezifiziert man, wie viel Arbeitsspeicher der Applikation zugeteilt wird.
5) Mit dem Command:
gradle run --args='path_to_map port'
kompiliert und startet man die Applikation. Path steht dabei für den Pfad der Karte und Port für den Port auf dem der Server erreichbar sein soll.
6) Im Browser die URL "localhost:port" eintippen

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Von der Map unterstütze Features:
- Setzen eines Wegpunktes durch einen Linksclick (Es können maximal 2 Wegpunkte existieren)
- Löschen der aktuell ausgewählten Wegpunkte (Nachdem bereits zwei Wegpunkte gespeichert wurden, einen Dritten wählen)
- Berrechnung der kürzesten Route zwischen zwei Wegpunkten (Durch Verwendung des Suchbuttons)




 
