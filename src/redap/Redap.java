/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

/**
 * hlavni metoda programu, načte číslo faktury, spustí menu a jeho okno
 */
public class Redap {
    
    public static void main(String[] args) {
        Menu menu = new Menu();
        new OknoMenu().setVisible(true);
    }
}
