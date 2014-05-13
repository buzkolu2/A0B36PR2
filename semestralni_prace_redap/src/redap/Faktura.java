/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.util.*;
import java.text.*;

/**
 * vytvoří novou fakturu
 * @author Fragolka
 */
public class Faktura implements java.io.Serializable {
    
    /**
     * faktura obsahuje údaje
     * @param klient        klient na faktuře
     * @param datumPlneni   datum plnění
     * @param polozky       seznam polozek na faktuře
     * 
     */

    private Klient klient;
    private Date datumPlneni, vystaveni;
    private List<Polozka> polozky;
    private int cisloFaktury;

    public Faktura(Klient klient, Date datumPlneni,
            LinkedList polozky) {
        this.klient = klient;
        this.datumPlneni = datumPlneni;
        vystaveni = new Date();
        this.polozky = polozky;
        Redap.cisloFaktury++;
        this.cisloFaktury = Redap.cisloFaktury;
    }
    
    /**
     * 
     * @return klienta uvedeného na faktuře
     */

    public Klient getKlient() {
        return klient;
    }
    
    /**
     * 
     * @param klient nastaví klienta na faktuře
     */
    public void setKlient(Klient klient){
        this.klient=klient;
    }
    
    /**
     * 
     * @return datum,kdy má být transakce uskutečněna
     */

    public Date getDatumPlneni() {
        return datumPlneni;
    }
    
    /**
     * 
     * @param datum datum, kdy má být transakce uskutečněna
     * 
     */
    public void setDatumPlneni(Date datum){
        this.datumPlneni=datum;
    }

    /**
     * @return datum vystaveni faktury - den, kdy byla vytvořena nová faktura
     */
    public Date getVystaveniFaktury() {
        return vystaveni;
    }

    /**
     * 
     * @return datum splatnosti nastavené automaticky 14 dní po datu vystavení
     */
    public Date getDatumSplatnosti() {
        Calendar c = Calendar.getInstance();
        c.setTime(vystaveni);
        c.add(Calendar.DAY_OF_MONTH, 14);
        return c.getTime();
    }

    /**
     * 
     * @return vrátí číslo faktury
     */
    public int getCisloFaktury() {
        return cisloFaktury;
    }
    
    /**
     * 
     * @return polozky uvedené na faktuře
     */

    public List getPolozky() {
        return polozky;
    }
    
    /**
     * 
     * @param polozky nastaví položky na faktuře
     */
    
    public void setPolozky (LinkedList<Polozka> polozky){
        this.polozky=polozky;
    }
    
    /**
     * 
     * @return spočítá a vrátí celkovou cenu bez DPH na faktuře
     */

    public double getCelkCenu() {
        double cena = 0;
        for (Polozka p : polozky) {
            cena = cena + p.getCena();
        }
        return cena;
    }

    /**
     * zaokrouhleno nahoru na jendo místo
     * @return částku odvedenou jako daň, tj. 20% z celkové ceny
     * 
     */
    public double getDPH() {
        double dan = 0.2 * getCelkCenu();
        return (Math.ceil(dan * 10) / 10);
    }
    
    /**
     * 
     * @return cenu poníženou o DPH
     */

    public double getCenuSDPH() {
        return (getCelkCenu() + getDPH());
    }
}
