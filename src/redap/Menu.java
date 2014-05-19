/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.util.*;
import javax.swing.JOptionPane;

/**
 * spravuje informace o fakturách a klientech, udržuje listy klientů a faktur, ukládá změny do databáze
 *
 * @author Fragolka
 */
public class Menu {

    static List<Faktura> faktury;
    static List<Klient> klienti;

    /**
     * načte uložený seznam faktur a klientů z databáze
     */
    public Menu() {
        try {
            faktury = Database.getInstance().getAllFaktura();
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }


    }
    
    /**
     * přidá fakturu do listu a do databáze
     */
    public static void pridatFakturu(Faktura f, Klient k){
        try {
            f.setId(Database.getInstance().insertFaktura(f));
            for (Polozka p: f.getPolozky()){
                Database.getInstance().insertPolozka(p, f);
            }
            faktury = Database.getInstance().getAllFaktura();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
    }
    
    /**
     * odstraní fakturu z listu a databáze
     */
    public static void odstranitFakturu(Faktura f){
        try {
            Database.getInstance().deleteFaktura(f);
            faktury = Database.getInstance().getAllFaktura();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
    }
    
    /*
     * Přidá klienta do listu klientů a uloží jej do databáze
     */
    public static void pridatKlienta(Klient k){
        try {
            Database.getInstance().insertKlient(k);
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
    }
    
    /*
     * Odstraní klienta z listu a databáze
     */
    public static void odstranitKlienta(Klient k){
        try {
            Database.getInstance().deleteKlient(k);
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
    }
    
    
}
