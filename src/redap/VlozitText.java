/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;

/**
 * textové pole a jeho název
 * @author Fragolka
 */
public class VlozitText extends TextField {
    Label nazev;
    TextField vstup;
    Panel panel;
    VlozitText(String napis){
        nazev=new Label(napis);
        nazev.setPreferredSize(new Dimension(100,20));
        nazev.setMinimumSize(new Dimension(100,20));
        nazev.setMaximumSize(new Dimension(100,20));
        
        vstup=new TextField(50);
        
        panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        panel.add(nazev);
        panel.add(vstup);        
    }
    
   
}
