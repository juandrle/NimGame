import java.util.ArrayList;

/**
 * Spielzüge
 */
public class NimmLoeser extends NimmZufall {
    // Array-Liste (in dem nur int-Arrays gespeichert werden),
    // die die Züge speichert, die den Verlustzustand herbeiführen
    ArrayList<int[]> winningMoves = new ArrayList<>();
    // Array-Liste mit allen aktuell möglichen Zügen
    ArrayList<int[]> possibleMoves = new ArrayList<>();

  /* normale Lösung
    @Override
    public int[] computeMove(int[] state) {
        for (int i = 0; i < state.length; i++)
            if (state[i] % 2 != 0)  //sucht nach Zeile ungerader Anzahl
                return new int[]{i, 1}; // macht die Zeile gerade
        int sum = 0;
        for (int part : state) sum += part;
        if (sum % 2 != 0)
            for (int i = 0; i < state.length; i++)
                if(state[i] > 2)
                    return new int[]{i, 2}; // hält es gerade
        return super.computeMove(state); // zufällig gezogen
    }*/

    /**
     * Speichert alle möglichen Spielzüge von dem mitgegebenem Array
     *
     * @param state Array eines Spielfeldes
     * @param currRow aktuelle Reihe
     * @param currAmount aktuelle Anzahl
     */
    public void saveAllMoves(int [] state, int currRow, int currAmount) {
        // Stoppt Rekursion, wenn die aktuelle Reihe der Länge des Arrays entspricht
        if (currRow == state.length) return;
        // der aktuelle Zug wird in der Array-Liste der möglichen Spielzüge gespeichert
        this.possibleMoves.add(new int[]{currRow, currAmount});
        // wenn das Array in der aktuellen Reihe kleiner gleich ist als die Anzahl, rekursiver Aufruf mit Reihe +1
        if (state[currRow] <= currAmount) saveAllMoves(state, currRow+1, 1);
        // ansonsten rekursiver Aufruf mit Anzahl +1
        else if (state[currRow] > currAmount)saveAllMoves(state, currRow, currAmount +1);
    }

    /**
     * Methode, die bestimmt ob es eine Gewinn- oder Verlustposition
     * und den Spielzug speichert, wenn es eine Gewinnposition ist
     *
     * @param state Array eines Spielfeldes
     * @param index Index für die Rekursion
     * @return ob es eine Gewinn- oder Verlustposition ist
     */
    public boolean isWinningPosition(int [] state, int index) {
        // temporäres (geklontes) Array des Spielfeldes
        int [] temp = state.clone();
        // ob die Anzahl aller Elemente aller Reihen gerade sind (Initialisierung)
        boolean isAllEven = true;
        // Ausführung, solange der Index kleiner ist als die Länge der Array-Liste
        while (index < possibleMoves.size()) {
            // der Spielzug am aktuellen Index wird in ein Array geklont
            int [] currentMove = possibleMoves.get(index).clone();
            // Ausführung des Zuges
            temp[currentMove[0]] -= currentMove[1];
            // durchläuft Reihen des Arrays und kontrolliert, ob die Reihen gerade sind
            for (int row : temp)
                if (row % 2 != 0) {
                    isAllEven = false;
                    break;
                }
            // wenn mindestens eine Reihe ungerade ist ...
            if (!isAllEven){
                // ... rekursiver Aufruf mit Index +1
                if (isWinningPosition(state,index+1)) {
                    return true;
                // ... zurückziehen des Zuges
                } else {
                    return false;
                }
            // aktueller Zug wird als Gewinnzug gespeichert
            } else {
                winningMoves.add(currentMove);
                System.out.println("System wird gewinnen");
                return true;
            }

        } System.out.println("System könnte verlieren");
        return false;
    }
    @Override
    public int[] computeMove(int[] state) {
        winningMoves.clear();
        saveAllMoves(state,0,1);
        if (isWinningPosition(state, 0)) {
            possibleMoves.clear();
            return winningMoves.get((int) (Math.random() * winningMoves.size()));
        }
        possibleMoves.clear();
        return super.computeMove(state);
    }

    public static void main(String[] args) {
        de.medieninf.ads.ADSTool.startNimmGame(new NimmLoeser());

        //saveAllMoves(new int []{1,2,3,4,5}, 0, 1);
    }
}
