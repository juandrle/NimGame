import de.medieninf.ads.NimmListener;

/**
 * Die Züge werden auf der Konsole ausgegeben
 * Implementiert NimmListener
 */
public class NimmZeiger implements NimmListener {
    int[] zustand;

    /**
     * Wie viele Elemente wurden in der wievielten Zeile weggenommen.
     * Ausgelöst durch einen Klick auf einen Knopf oder durch eine
     * Anforderung an computeMove.
     *
     * @param zeile   int, aus welcher Zeile (ab 0)
     * @param wieviel int, wieviele Elemente genommen
     */

    @Override
    public void taken(int zeile, int wieviel) {
        // Ausgabe des Zugs auf der Konsole
        System.out.print("Z " + (zeile + 1) + ":" + wieviel + " -> ");
        // Ausgabe des Zustandes auf der Konsole
        zustand[zeile] -= wieviel;
        for (int i = 0; i < zustand.length; i++) {
            if (zustand.length - 1 != i)
                System.out.print((i + 1) + ":" + zustand[i] + ", ");
            else
                System.out.println((i + 1) + ":" + zustand[i]);
        }
    }

    /**
     * Neues Spiel, ausgelöst durch 'Nochmal'-Knopf oder am Anfang.
     *
     * @param zustand int[], [1,2,3,4,5]
     */

    @Override
    public void newGame(int[] zustand) {
        this.zustand = new int[]{1, 2, 3, 4, 5};
        System.out.print("Start:     ");
        for (int i = 0; i < zustand.length; i++) {
            if (zustand.length - 1 != i)
                System.out.print((i + 1) + ":" + zustand[i] + ", ");
            else
                System.out.println((i + 1) + ":" + zustand[i]);
        }
    }

    /**
     * Ein Zugvorschlag soll berechnet werden. Der Zug muss gültig sein.
     * Wenn man nicht ziehen will, dann gibt man null zurück. Ansonsten
     * ein Feld bestehend aus der Zeile und der Anzahl der zu ziehenden Elemente.
     *
     * @param state int[], je Zeile (0-basiert) ein Eintrag mit der Anzahl der Elemente
     * @return int[], Zug, wobei [0] die Zeile (0-basiert) und [1] die Anzahl der zu
     * ziehenden Elemente ist.
     */

    @Override
    public int[] computeMove(int[] state) {
        return null; // wir wollen nicht ziehen
    }

    public static void main(String[] args) {
        de.medieninf.ads.ADSTool.startNimmGame(new NimmZeiger());
    }
}
