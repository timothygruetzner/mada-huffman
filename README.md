# mada-huffman
#### Bearbeitet von Timothy Grützner

### Ihr Content
Der Klartext Ihrer mitgelieferten kodierten Datei enkodiert mit Ihrem Huffman Baum lautet:

```
Das Verfahren lohnt sich in der vorgestellten Form nur fuer Texte mit einer gewissen Laenge. Ansonsten ist der Overhead fuer die Erstellung der Tabelle zu gross.

Man koennte sich aber auch ueberlegen, die Tabelle geschickter abzuspeichern. Ansonsten: Gut gemacht!
```

## Running the tool
Das Programm wird über die `Main` Klasse gestartet. Die Steuerung der Funktionalit�t erfolgt anschliessend über "Program Arguments".

### `compress`
Liest das `input.txt` File, wendet die Huffman Kodierung an und schreibt das Resultat nach `output.dat` für den Inhalt und nach `dec_tab.txt` für den Huffman Baum.

Das `input.txt` File muss zu Beginn des Programmes existieren - die anderen werden generiert.

### `decompress`
Liest das den Content vom `output.dat` File und den Huffman Baum vom `dec_tab.txt` File. Existiert eines dieser Files nicht, bricht das Programm ab.
Anschliessend wird der mittels des gelesenen Baumes jedes Zeichen wieder dekodiert. 

Der dekodierte Text landet im `decompressed.txt` File.

### `cycle`
Führt die beiden oben beschriebenen Schritte nacheinander aus. Somit ist gut ersichtlich, dass `input.txt` und `decompress.txt` identisch herauskommen.

## Testläufe
Aus Testzwecken habe ich verschiedene Texte en- und dekodiert und die Platzersparnis beobachtet. Das Resultat ist in untenstehender Tabelle ersichtlich.

| Beschreibung des Files        | Raw-Size | Compressed Size (inkl. Tree)| Ersparnis |
| ------------------------------|:--------- |:--------------- |:-------------- |
| Lorem ipsum text mit > 200'000 Wörtern.      | ca. 1’448KB | ca. 764KB (davon 437 bytes tree) | 47% (!!) |
| Lorem ipsum text mit 50 Wörtern      | 295 bytes      |   428 bytes (davon 275 bytes tree) | keines (ca. 45% overhead)
| Eine Ihrer E-Mails an die Klasse mit 121 Wörtern | 901 bytes      |    1'033 bytes (davon 521 bytes tree) | keines (ca. 14% overhead)
| Ein Artikel zum IoT-Betriebssystem 'Contiki' (gerade für ein anderes Modul in Verwendung), 550 Wörter, auf Englisch | 3.8KB      |    2.8KB (davon 707 bytes tree) | ca. 26%

## Bemerkungen
Das korrekte Verhalten ist lediglich für Characters des ASCII Zeichensatzes garantiert.