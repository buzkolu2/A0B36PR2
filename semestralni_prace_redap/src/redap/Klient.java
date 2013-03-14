/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

/**
 * klient obsahuje jméno, adresu, DIČ a IČO
 * @author Fragolka
 */
public class Klient implements java.io.Serializable{
    private String jmeno, ulice, psc, dic, ico;
    private double sleva;
    public Klient (String jmeno, String ulice, String psc, String dic,
            String ico, double sleva){
        this.jmeno=jmeno;
        this.ulice=ulice;
        this.psc=psc;
        this.dic=dic;
        this.ico=ico;
        this.sleva=sleva;
    }
    
    /**
     * 
     * @return jmeno klienta
     */
    
    public String getJmeno (){
        return jmeno;
    }
    /**
     * 
     * @return ulici a p.č. klienta
     */
    
    public String getUlice (){
        return ulice;
    }
    /**
     * 
     * @return PSČ a město klienta
     */
    
    public String getPSC (){
        return psc;
    }
    
    /**
     * 
     * @return DIČ klienta
     */
    
    public String getDIC (){
        return dic;
    }
    /**
     * 
     * @return IČO klienta
     */
    
    public String getICO (){
        return this.ico;
    }
    
    /**
     * 
     * @return sleva pro klienta
     */
      
    public double getSleva(){
        return this.sleva;
    }
}
