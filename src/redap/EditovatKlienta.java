/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * okno editace klienta
 *
 * @author Fragolka
 */
public class EditovatKlienta extends Frame {

    VlozitText nazev, ulice, psc, ico, dic;
    VlozitCislo sleva;
    Button ok;
    int idx;
    static Klient klient;

    EditovatKlienta(Klient klient, int idx) {
        this.klient = klient;
        this.idx = idx;
        nazev = new VlozitText("Jméno klienta");
        nazev.vstup.setText(klient.getJmeno());

        ulice = new VlozitText("Ulice a p.č.");
        ulice.vstup.setText(klient.getUlice());

        psc = new VlozitText("Město a PSČ");
        psc.vstup.setText(klient.getPSC());

        ico = new VlozitText("IČO");
        ico.vstup.setText(klient.getICO());

        dic = new VlozitText("DIČ");
        dic.vstup.setText(klient.getDIC());

        sleva = new VlozitCislo("Sleva v %");
        sleva.vstup.setText(Double.toString(klient.getSleva()));

        ok = new Button("Vytvořit");
        ok.setBackground(Color.orange);
        ok.addActionListener(new OkAL());

        //vzhled okna
        this.setTitle("Editovat klienta");
        this.setSize(400, 400);
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        okButton.add(ok);

        this.setLayout(new GridLayout(7, 1));
        this.add(nazev.panel);
        this.add(ulice.panel);
        this.add(psc.panel);
        this.add(ico.panel);
        this.add(dic.panel);
        this.add(sleva.panel);
        this.add(okButton);

    }

    /**
     * vytvoří nového klienta a vymění jej za stávajcího
     */
    class OkAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {

//                Klient novyKlient = new Klient(nazev.vstup.getText(),
//                        ulice.vstup.getText(),
//                        psc.vstup.getText(),
//                        ico.vstup.getText(),
//                        dic.vstup.getText(),
//                        Double.parseDouble(sleva.vstup.getText()));
//                
//                Menu.klienti.set(idx, novyKlient);
                
                       
                klient.setJmeno(nazev.vstup.getText());
                klient.setUlice(ulice.vstup.getText());
                klient.setPsc(psc.vstup.getText());
                klient.setDic(dic.vstup.getText());
                klient.setIco(ico.vstup.getText());
                klient.setSleva( Double.parseDouble(sleva.vstup.getText()));
                
                Database.getInstance().updateKlient(klient);
                
                OknoMenu.klienti.odeberPrvek(idx);
                OknoMenu.klienti.seznam.add(idx, klient.getJmeno());

//                try {                    
//                    ObjectOutputStream out = new ObjectOutputStream(
//                            new FileOutputStream("klienti"));
//                    out.writeObject(Menu.klienti);
//                    out.close();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }

            } catch (Exception ex) {
                System.err.println(ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Chyba", JOptionPane.OK_OPTION);
            }
            
            //zavřít okno
            EditovatKlienta.this.dispose();

        }
    }
}
