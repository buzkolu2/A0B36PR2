/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;

/**
 * grafická struktura pro textové pole a jeho název
 * přijímá použe čísla
 * 
 * @author Fragolka
 */
public class VlozitCislo extends TextField {

    Label nazev;
    TextField vstup;
    Panel panel;

    VlozitCislo(String napis) {
        nazev = new Label(napis);
        nazev.setPreferredSize(new Dimension(100,20));
        nazev.setMinimumSize(new Dimension(100,20));
        nazev.setMaximumSize(new Dimension(100,20));
        
        vstup = new TextField(20);
        vstup.addKeyListener(new VstupTL());
        
        panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        panel.add(nazev);
        panel.add(vstup);
    }
    
    /**
     * 
     * @return vepsaný text v poli
     */

    public String getVstup() {
        return vstup.getText();
    }
    
    /**
     * omezení pro psaní pouze čísla a jedné desetinné tečky
     */

    private class VstupTL extends KeyAdapter {
        private boolean dot = false;
        
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != '.') {
                e.consume();
            } else if( c == '.' && !dot && getVstup().length() > 0){
                dot = true;
            } else if( c== '.' ){
                e.consume();
            }
        }
    }
}
