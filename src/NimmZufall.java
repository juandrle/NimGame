/**
 * Zufälliger Zug
 */
public class NimmZufall extends NimmZeiger {

    /**
     * Ein Zugvorschlag soll berechnet werden. Der Zug muss gültig sein.
     * Wenn man nicht ziehen will, dann gibt man null zurück. Ansonsten
     * ein Feld bestehend aus der Zeile und der Anzahl der zu ziehenden Elemente.
     * @param state int[], je Zeile (0-basiert) ein Eintrag mit der Anzahl der Elemente
     * @return int[], Zug, wobei [0] die Zeile (0-basiert) und [1] die Anzahl der zu
     * ziehenden Elemente ist.
     */
    @Override
    public int[] computeMove(int[] state) {
        int row = (int)(Math.random() * 5); // random Zeile
        while (state[row] == 0) row = (int)(Math.random() * 5); // wenn nichts zu ziehen neue random Zeile
        return new int[]{row, (int) (Math.random() * state[row]) + 1}; // ziehe min 1 bis alle der Reihe
    }

    public static void main(String[] args) {
        de.medieninf.ads.ADSTool.startNimmGame(new NimmZufall());
    }
}
