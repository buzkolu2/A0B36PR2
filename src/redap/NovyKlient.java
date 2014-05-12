/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * okno pro vytvoření nového klienta
 * @author Fragolka
 */
public class NovyKlient extends JFrame {
    VlozitText nazev, ulice, psc, ico, dic;
    VlozitCislo sleva;
    Button ok;
    
    NovyKlient(){
        nazev=new VlozitText("Jméno klienta");
        ulice=new VlozitText("Ulice a p.č.");
        psc=new VlozitText("Město a PSČ");
        ico=new VlozitText("IČO");
        dic=new VlozitText("DIČ");
        sleva=new VlozitCislo("Sleva v %");
        
        ok=new Button("Vytvořit");
        ok.setBackground(Color.orange);
        ok.addActionListener(new OkAL());
        
        //vzhled okna
        this.setTitle("Nový klient");
        this.setSize(400,400);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
            } 
        });
        
        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        okButton.add(ok);
        
        this.setLayout(new GridLayout(7,1));
        this.add(nazev.panel);
        this.add(ulice.panel);
        this.add(psc.panel);
        this.add(ico.panel);
        this.add(dic.panel);
        this.add(sleva.panel);
        this.add(okButton);
        
        

        
    }
    
            
    class OkAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {

                Klient novyKlient=new Klient(nazev.vstup.getText(),
                        ulice.vstup.getText(),
                        psc.vstup.getText(),
                        ico.vstup.getText(),
                        dic.vstup.getText(),
                        Double.parseDouble(sleva.vstup.getText()));
                        
                Menu.pridatKlienta(novyKlient);
                               
                OknoMenu.klienti.pridejPrvek(nazev.vstup.getText());
                
                try {
                    ObjectOutputStream out=new ObjectOutputStream(
                            new FileOutputStream("klienti"));
                    out.writeObject(Menu.klienti);
                    out.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
                    
                } catch (NumberFormatException ex) {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Chyba", JOptionPane.OK_OPTION);
                }
            
                NovyKlient.this.dispose();
                
        }
    }
    
}
