/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

/**
 * obsahuje informace o firmě
 * @author Fragolka
 */
public class Firma {
    private final String NAZEV, ULICE, PSC, DIC, TEL, UCET, ICO, REJSTRIK;
    
    public Firma(){
        NAZEV="REDAP Zlín, s.r.o.";
        ULICE="Zádveřice 254";
        PSC="762 12 Vizovice";
        DIC="CZ46975675";
        TEL="571 891 903";
        UCET="182 866 672 / 0300";
        ICO="46975675";
        REJSTRIK="Společnost zapsána v obchodním rejstříku vedeného "
                + "Krajským obchodním soudem v Brně dne 5. 10. 1992, "
                + "oddíl C, vložka 7655";
    }
    /**
     * @return nazev firmy REDAP
     */
    
    public String getNazev (){
        return NAZEV;
    }
    /**
     * 
     * @return Ulice a p.č. firmy REDAP
     */
    public String getUlice (){
        return ULICE;
    }
    /**
     * 
     * @return PSČ firmy Redap
     */
        
    public String getPSC (){
        return PSC;
    }
    
    /**
     * 
     * @return DIČ firmy
     */
            
        public String getDIC (){
        return DIC;
    }
        
    /**
     * 
     * @return telefonní číslo firmy
     */    
         
    public String getTel (){
        return TEL;
    }
    
    /**
     * 
     * @return číslo účtu firmy Redap
     */
                
    public String getUcet (){
        return UCET;
    }
    /**
     * 
     * @return IČO firmy Redap
     */
    
    public String getICO (){
        return ICO;
    }
    /**
     * 
     * @return informace o zápisu do obchodního rejstříku
     */
    
    public String getRejstrik (){
        return REJSTRIK;
    }
    
}
