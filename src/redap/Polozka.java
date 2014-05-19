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

public class Polozka {
    private String popis;
    private int pocet;
    private double jcena;
    private int id;
    
    public Polozka(){        
    }
    
    public Polozka (String popis, double jcena, int pocet){
        this.popis=popis;
        this.jcena=jcena;
        this.pocet=pocet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public void setJcena(double jcena) {
        this.jcena = jcena;
    }
    
}
