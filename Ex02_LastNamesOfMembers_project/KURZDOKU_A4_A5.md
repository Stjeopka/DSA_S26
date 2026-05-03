# Kurzdokumentation Ex02 - Aufgabe 4 und 5

## Aufgabe 4: Effizientere Algorithmen

### (a) couldBeBetter1 vs isDoneBetter1

Beobachtung:
- couldBeBetter1 addiert in einer Schleife von i = 0 bis n immer +2.
- Fuer n >= 0 passiert das genau (n + 1)-mal.
- Fuer n < 0 wird die Schleife gar nicht ausgefuehrt und das Ergebnis ist 0.

Daher:
- Ergebnis fuer n >= 0: 2 * (n + 1)
- Ergebnis fuer n < 0: 0

Komplexitaet:
- couldBeBetter1: O(n)
- isDoneBetter1: O(1)

Warum schneller:
- Statt n+1 Iterationen wird direkt mit einer Formel gerechnet.

### (b) couldBeBetter2 vs isDoneBetter2

Beobachtung:
- In jeder aeusseren Iteration i wird temp genau i-mal addiert.
- Dadurch gilt rekursiv: result_i = i * result_(i-1), mit Startwert 1.
- Das ist genau die Fakultaet: result = n! (fuer n >= 0), bei n < 0 bleibt es 1.

Komplexitaet:
- couldBeBetter2: O(n^2) (verschachtelte Schleifen, 1 + 2 + ... + n)
- isDoneBetter2: O(n) (eine Schleife mit Multiplikation)

Warum schneller:
- Die innere Schleife wird entfernt. Dadurch sinkt die Anzahl Operationen von quadratisch auf linear.

### (c) couldBeBetter3 vs isDoneBetter3

Beobachtung:
- couldBeBetter3 ist die naive rekursive Fibonacci-Berechnung.
- Viele Teilprobleme werden mehrfach berechnet.

Komplexitaet:
- couldBeBetter3: exponentiell, z. B. O(2^n)
- isDoneBetter3: O(n) Zeit und O(1) Zusatzspeicher (iterativ mit zwei Variablen)

Warum schneller:
- Jeder Fibonacci-Wert wird nur einmal fortlaufend aufgebaut.
- Keine rekursiven Doppelberechnungen.

## Aufgabe 5(a): Warum indexbasierter BubbleSort auf Linked List schlechter ist

BubbleSort hat auf Arrays normalerweise O(n^2) Vergleiche.
Bei einer einfach verketteten Liste ist aber ein Zugriff per Index k nicht O(1), sondern O(k), weil man vom Kopf durchlaufen muss.

Wenn BubbleSort auf der Liste staendig getElement(i) und getElement(i+1) verwendet,
wird jeder Vergleich deutlich teurer (im Worst Case O(n) statt O(1)).

Dadurch wird aus
- O(n^2) Vergleichen * O(1) Zugriff (Array)

effektiv
- O(n^2) Vergleichen * O(n) Zugriff (Linked List)

also insgesamt O(n^3).

Die verbesserte Loesung in SimpleList.sort() laeuft stattdessen mit Node-Pointern durch die Liste,
damit jeder Schritt lokal O(1) kostet und die Gesamtlaufzeit wieder bei O(n^2) liegt.
