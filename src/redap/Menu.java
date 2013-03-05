/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.io.*;
import java.util.*;

/**
 * spravuje informace o fakturách a klientech a ukládá je
 * @author Fragolka
 */
public class Menu {
    static List<Faktura> faktury;
    static List<Klient> klienti;
    /**
     * načte uložený seznam faktur a klientů
     */
    public Menu () {
        faktury=new LinkedList<Faktura> ();
        try {
            ObjectInputStream in=new ObjectInputStream(
                 new FileInputStream("faktury"));
            faktury=(LinkedList)in.readObject();
            in.close();
            }  catch (Exception ex){
                ex.printStackTrace();
                System.out.println("tady je chyba");
            }
        
        klienti=new LinkedList<Klient> ();
        try {
            ObjectInputStream in=new ObjectInputStream(
                 new FileInputStream("klienti"));
            klienti=(LinkedList)in.readObject();
            in.close();
            }  catch (Exception ex){
                ex.printStackTrace();
                System.out.println("tady je chyba");
            }
        
    }
    
}
