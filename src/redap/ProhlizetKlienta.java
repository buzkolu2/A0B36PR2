/**
 * Okno prohlížení klienta s možností přepnutí na editaci
 */
package redap;

import java.awt.*;
import java.awt.event.*;

/**
 * Okno prohlížení klienta s možností přepnutí na editaci
 * @author Fragolka
 */
public class ProhlizetKlienta extends Frame {
    Button editovat;
    Klient klient;
    int idx;
    ProhlizetKlienta(Klient klient, int idx){
        
        this.klient=klient;
        this.idx=idx;
        this.setTitle("Nový klient");
        this.setSize(400,400);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
            } 
        });
        
        
        this.setLayout(new GridLayout(6,2));
        this.add(new Label ("Jméno klienta:"));
        Label jmeno = new Label(klient.getJmeno());
        jmeno.setFont(new Font("Arial CE",Font.BOLD,14));
        this.add(jmeno);
        this.add(new Label("Ulice a p.č.:"));
        this.add(new Label(klient.getUlice()));
        this.add(new Label("Město a PSČ:"));
        this.add(new Label(klient.getPSC()));
        this.add(new Label("IČO:"));
        this.add(new Label(klient.getICO()));
        this.add(new Label("DIČ:"));
        this.add(new Label(klient.getDIC()));        
        
        editovat=new Button("Editovat");
        editovat.setBackground(Color.orange);
        editovat.addActionListener(new EditovatAL());
        
        Panel editace = new Panel();
        editace.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        editace.add(editovat);
        this.add(editace);
        
    }
    
    class EditovatAL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            new EditovatKlienta(klient,idx).setVisible(true);
            ProhlizetKlienta.this.dispose();
        }
    }
    
}
