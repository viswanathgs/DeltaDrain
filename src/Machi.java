/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viswanathgs
 */

public class Machi {
    final String[] TYPES = {"Bank", "Wokay", "Cheap-ass", "Bitch-face"};
    final int WHITE = 0, RED = -1, GREEN = 1;

    String name, type;
    int balance;
    int color;
    History history;

    public Machi (String tname, int tbalance) {
        name = tname;
        type = TYPES[1];
        balance = tbalance;
        color = WHITE;
        history = new History();
    }

    public Machi () {
        name = "";
        type = TYPES[1];
        balance = 0;
        color = WHITE;
        history = new History();
    }

    void setColor () {
        if (balance == 0)
            color = WHITE;
        else if (balance > 0)
            color = GREEN;
        else
            color = RED;
    }

    void calculateType() {
        type = TYPES[1];

        if (balance >= 200)
            type = TYPES[0];
        else if (balance <= -200)
            type = TYPES[3];
        
        for (int i = 0; i < history.historySize; i++) {
            if (history.transHistory[i].amount < 0 && history.transHistory[i].amount > -10) {
                type = TYPES[2];
                break;
            }
        }
    }
}
