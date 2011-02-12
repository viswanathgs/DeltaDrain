/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viswanathgs
 */
public class Transaction {
    int amount;
    long timestamp;
    String comment;

    public Transaction (int amt, String cmt) {
        amount = amt;
        comment = cmt;
        timestamp = System.currentTimeMillis();
        // Conversion from UTC to IST. IST is 5 hr 30 mins ahead of UTC.
        // timestamp += (5*60 + 30) * 60 * 1000;
    }

    public Transaction () {
        amount = 0;
        comment = "";
        timestamp = 0;
    }
}
