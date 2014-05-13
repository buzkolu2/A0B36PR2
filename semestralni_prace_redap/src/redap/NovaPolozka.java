/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * okno pro vytvoření nové položky
 * @author Fragolka
 */
public class NovaPolozka extends Frame{
    VlozitText popis;
    VlozitCislo jCena, pocet;
    Button ok;
    int zdroj;
    
    NovaPolozka(int i){
        zdroj=i;
        
        popis=new VlozitText("Popis");
        jCena=new VlozitCislo("Cena za kus");
        pocet=new VlozitCislo("Počet kusů");
        
        ok=new Button("Vytvořit");
        ok.setBackground(Color.orange);
        ok.addActionListener(new OkAL());
        
              
        //vzhled okna
        this.setTitle("Nová položka");
        this.setSize(400,400);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
            } 
        });
        
        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        okButton.add(ok);
        
        this.setLayout(new GridLayout(4,1));
        this.add(popis.panel);
        this.add(jCena.panel);
        this.add(pocet.panel);
        this.add(okButton);
    }
    
    class OkAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                Polozka polozka = new Polozka (popis.vstup.getText(),
                        Double.parseDouble(jCena.vstup.getText()),
                        Integer.parseInt(pocet.vstup.getText()));
                if (zdroj==1){
                NovaFaktura.polozky.pridejPrvek(pocet.vstup.getText()+
                        " ks "+popis.vstup.getText());
                NovaFaktura.polozkyFaktura.add(polozka);
                } else {EditovatFakturu.polozky.pridejPrvek(pocet.vstup.getText()+
                        " ks "+popis.vstup.getText());
                        EditovatFakturu.novePolozkyFaktura.add(polozka);
                }
            } catch (NumberFormatException ex) {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Počet kusů musí být celé číslo",
                            "Chyba", JOptionPane.OK_OPTION);
                }
            NovaPolozka.this.dispose();  
        }
    }
    
}