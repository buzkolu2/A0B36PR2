/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;
import java.awt.GridLayout;
import javax.swing.*;
/**
 * vzor pro seznam
 * @author Fragolka
 */
public class Seznam {
    DefaultListModel seznam;
    JList list;
    JScrollPane panel;
    
    
    public Seznam(){
        seznam=new DefaultListModel();
        list=new JList(seznam);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel = new JScrollPane(list,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
    }
    
    /**
     * 
     * @param nazev přidá do seznamu prvek s názvem název
     */
    public void pridejPrvek(String nazev){
        seznam.addElement(nazev);
    }
    /**
     * 
     * @param index odebere ze seznamu prvek s číslem index
     */
    
    public void odeberPrvek(int index){
        seznam.remove(index);
    }
    
    /**
     * 
     * @return vrátí vybraný prvek
     */
    
    public int vybranyPrvek(){
        return list.getSelectedIndex();
    }
    /**
     * 
     * @return vrátí název vybraneého prvku
     */
    
    public String NazevVybranehoPrvku(){
        return seznam.getElementAt(list.getSelectedIndex()).toString();
    }
}
