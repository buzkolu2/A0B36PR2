/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * spravuje informace o fakturách a klientech a ukládá je
 *
 * @author Fragolka
 */
public class Menu {

    static List<Faktura> faktury;
    static List<Klient> klienti;

    /**
     * načte uložený seznam faktur a klientů
     */
    public Menu() {
        try {
            faktury = Database.getInstance().getAllFaktura();
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //        faktury=new LinkedList<Faktura> ();
        //        try {
        //            ObjectInputStream in=new ObjectInputStream(
        //                 new FileInputStream("faktury"));
        //            faktury=(LinkedList)in.readObject();
        //            in.close();
        //            }  catch (Exception ex){
        //                ex.printStackTrace();
        //                System.out.println("tady je chyba");
        //            }
        //        
        //        klienti=new LinkedList<Klient> ();
        //        try {
        //            ObjectInputStream in=new ObjectInputStream(
        //                 new FileInputStream("klienti"));
        //            klienti=(LinkedList)in.readObject();
        //            in.close();
        //            }  catch (Exception ex){
        //                ex.printStackTrace();
        //            }
        //            }


    }
    
    public static void pridatFakturu(Faktura f, Klient k){
        try {
            f.setId(Database.getInstance().insertFaktura(f));
            for (Polozka p: f.getPolozky()){
                Database.getInstance().insertPolozka(p, f);
            }
            faktury = Database.getInstance().getAllFaktura();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void odstranitFakturu(Faktura f){
        try {
            Database.getInstance().deleteFaktura(f);
            faktury = Database.getInstance().getAllFaktura();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void pridatKlienta(Klient k){
        try {
            Database.getInstance().insertKlient(k);
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void odstranitKlienta(Klient k){
        try {
            Database.getInstance().deleteKlient(k);
            klienti = Database.getInstance().getAllKlient();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
