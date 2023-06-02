import java.util.ArrayList;
/**
 * 2. Semester
 * Abgabe 01 - NimmSpiel
 *
 * @author Julian Andrle
 * @author Vivien Weber
 * @author Sarah Schwarzer
 */


/**
 * Spielzüge
 */
public class NimmLoeser extends NimmZufall {
    // Array-Liste (in dem nur int-Arrays gespeichert werden),
    // die die Züge speichert, die den Verlustzustand herbeiführen
    ArrayList<int[]> winningMoves = new ArrayList<>();
    // Array-Liste mit allen aktuell möglichen Zügen
    ArrayList<int[]> possibleMoves = new ArrayList<>();

    /**
     * Speichert alle möglichen Spielzüge von dem mitgegebenem Array
     *
     * @param state      Array eines Spielfeldes
     * @param currRow    aktuelle Reihe
     * @param currAmount aktuelle Anzahl
     */
    public void saveAllMoves(int[] state, int currRow, int currAmount) {
        // Stoppt Rekursion, wenn die aktuelle Reihe der Länge des Arrays entspricht
        if (currRow == state.length) return;
        // der aktuelle Zug wird in der Array-Liste der möglichen Spielzüge gespeichert
        if (state[currRow] != 0) this.possibleMoves.add(new int[]{currRow, currAmount});
        // wenn das Array in der aktuellen Reihe kleiner gleich ist als die Anzahl, rekursiver Aufruf mit Reihe +1
        if (state[currRow] <= currAmount) saveAllMoves(state, currRow + 1, 1);
            // ansonsten rekursiver Aufruf mit Anzahl +1
        else if (state[currRow] > currAmount) saveAllMoves(state, currRow, currAmount + 1);
    }

    /**
     * Methode, die bestimmt, ob es eine Gewinn- oder Verlustposition
     * und den Spielzug speichert, wenn es eine Gewinnposition ist
     *
     * @param state Array eines Spielfeldes
     * @param index Index für die Rekursion
     * @return ob es eine Gewinn- oder Verlustposition ist
     */
    public boolean isWinningPosition(int[] state, int index) {
        // temporäres (geklontes) Array des Spielfeldes
        int[] temp = state.clone();
        // ob die Anzahl aller Elemente aller Reihen gerade sind (Initialisierung)
        boolean hasOnlyUnevenOneRows;
        boolean hasDoubleRow;
        int oneCount;
        int existingRowsCount;

        // Ausführung, solange der Index kleiner ist als die Länge der Array-Liste
        while (index < possibleMoves.size()) {
            hasOnlyUnevenOneRows = false;
            hasDoubleRow = false;
            oneCount = 0;
            existingRowsCount = 0;
            // der Spielzug am aktuellen Index wird in ein Array geklont
            int[] currentMove = possibleMoves.get(index).clone();
            // Ausführung des Zuges
            temp[currentMove[0]] -= currentMove[1];

            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == 1) oneCount++;
                if (temp[i] != 0) existingRowsCount++;
                for (int j = i + 1; j < temp.length; j++) {
                    // kontrolliert, ob es mind. zwei identische Reihen gibt mit Elementen > 1
                    if (temp[i] == temp[j] && temp[i] > 1) {
                        hasDoubleRow = true;
                        break;
                    }
                }
            }
            if (existingRowsCount % 2 == 1) hasDoubleRow = false;
            if (oneCount % 2 == 1 && oneCount > 1) hasOnlyUnevenOneRows = true;
            if (existingRowsCount == 1 && oneCount == 1) hasOnlyUnevenOneRows = true;

            if (!hasDoubleRow && !hasOnlyUnevenOneRows) {
                // ... rekursiver Aufruf mit Index +1
                return isWinningPosition(state, index + 1);
                // aktueller Zug wird als Gewinnzug gespeichert
            } else {
                winningMoves.add(currentMove);
                index++;
            }

        }
        return !winningMoves.isEmpty();
    }

    /**
     * Zug des Computers
     *
     * @param state int[], je Zeile (0-basiert) ein Eintrag mit der Anzahl der Elemente
     * @return Zug des Computers
     */
    @Override
    public int[] computeMove(int[] state) {
        winningMoves.clear();
        saveAllMoves(state, 0, 1);
        if (isWinningPosition(state, 0)) {
            possibleMoves.clear();
            System.out.println("System wird gewinnen.");
            return winningMoves.get((int) (Math.random() * winningMoves.size()));
        }
        possibleMoves.clear();
        System.out.println("System könnte verlieren.");
        return super.computeMove(state);
    }

    /**
     * Main mit Spielaufruf
     *
     * @param args
     */
    public static void main(String[] args) {
        de.medieninf.ads.ADSTool.startNimmGame(new NimmLoeser());

        //saveAllMoves(new int []{1,2,3,4,5}, 0, 1);
    }
}
