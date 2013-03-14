/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * hlavni metoda programu, načte číslo faktury, spustí menu a jeho okno
 */
public class Redap {

    /**
     * cisloFaktury určuje číslo faktury, které se načítá
     * při každém otevření programu
     */
    public static int cisloFaktury;
    
    public static void main(String[] args) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("cisloFaktury"));
            cisloFaktury=in.readInt();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Redap.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Menu();
        new OknoMenu().setVisible(true);
    }
}
