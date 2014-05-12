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
    private int id;
    public Klient (String jmeno, String ulice, String psc, String dic,
            String ico, double sleva){
        this.jmeno=jmeno;
        this.ulice=ulice;
        this.psc=psc;
        this.dic=dic;
        this.ico=ico;
        this.sleva=sleva;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public void setSleva(double sleva) {
        this.sleva = sleva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
