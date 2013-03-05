/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

/**
 *  vytvoří novou položku na fakturu obsahující jméno položky, její cenu, a
 * počet
 * @author Fragolka
 */

public class Polozka implements java.io.Serializable {
    private String popis;
    private int pocet;
    private double jcena;
    
    public Polozka (String popis, double jcena, int pocet){
        this.popis=popis;
        this.jcena=jcena;
        this.pocet=pocet;
    }
    
    public String getPopis () {
        return this.popis;
    }
    
    public double getJCena() {
        return this.jcena;
    }
    
    public int getPocet() {
        return this.pocet;
    }
    
    public double getCena() {
        return (jcena*pocet);
    }
            
}
