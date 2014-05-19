/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * okno pro vytvoření nové položky
 *
 * @author Fragolka
 */
public class NovaPolozka extends Frame {

    VlozitText popis;
    VlozitCislo jCena, pocet;
    Button ok;
    int zdroj;
    static Faktura f;

    NovaPolozka(int i) {
        zdroj = i;
        this.f = f;
        popis = new VlozitText("Popis");
        jCena = new VlozitCislo("Cena za kus");
        pocet = new VlozitCislo("Počet kusů");

        ok = new Button("Vytvořit");
        ok.setBackground(Color.orange);
        ok.addActionListener(new OkAL());


        //vzhled okna
        this.setTitle("Nová položka");
        this.setSize(400, 400);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        okButton.add(ok);

        this.setLayout(new GridLayout(4, 1));
        this.add(popis.panel);
        this.add(jCena.panel);
        this.add(pocet.panel);
        this.add(okButton);
    }

    /*
     * Uloží položku
     */
    class OkAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int pocetKs = Integer.parseInt(pocet.vstup.getText());
                Polozka polozka = new Polozka(popis.vstup.getText(),
                        Double.parseDouble(jCena.vstup.getText()),
                        pocetKs);
                if (zdroj == 1) {
                    NovaFaktura.polozky.pridejPrvek(pocet.vstup.getText()
                            + " ks " + popis.vstup.getText());
                    polozka.setId(Database.getInstance().insertPolozka(polozka));

                    NovaFaktura.polozkyFaktura.add(polozka);
                } else {
                    EditovatFakturu.polozky.pridejPrvek(pocet.vstup.getText()
                            + " ks " + popis.vstup.getText());
                    EditovatFakturu.novePolozkyFaktura.add(polozka);
                }
                NovaPolozka.this.dispose();
            } catch (NumberFormatException pe) {
                JOptionPane.showMessageDialog(null, "Počet kusů není celé číslo", "Chyba", JOptionPane.OK_OPTION);
            } catch (Exception pe) {
                JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
            }
        }
    }
}
