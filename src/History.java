/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viswanathgs
 */
public class History {
    final int MAXHISTORY = 20;
    int historySize;
    Transaction[] transHistory;

    public History () {
        historySize = 0;
        transHistory = new Transaction[MAXHISTORY];
    }

    void addToHistory (Transaction trans) {
        if (historySize < MAXHISTORY) {
            transHistory[historySize] = trans;
            historySize++;
        }
        else {
            for (int i = 0; i < historySize - 1; i++) {
                transHistory[i] = transHistory[i+1];
            }
            transHistory[historySize-1] = trans;
        }
    }
}
