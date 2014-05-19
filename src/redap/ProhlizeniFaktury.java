/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.print.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Prohlizeni faktury, možnost její editace v tabulce a vytisknutí
 *
 */
public class ProhlizeniFaktury extends Frame {

    Faktura faktura;
    Tabulka tabulka;
    int idx;
    Label soucetC,sumaC,dphC;

    ProhlizeniFaktury(Faktura faktura, int idx) {
        this.faktura = faktura;
        this.idx = idx;
        int radky = faktura.getPolozky().size();
        this.setTitle("Faktura č." + Integer.toString(faktura.getId()));
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(400, 300));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });


        Label fakturaLabel = new Label("FAKTURA č. "
                + Integer.toString(faktura.getId()));
        fakturaLabel.setFont(new Font("Arial CE", Font.BOLD, 14));
        fakturaLabel.setAlignment(Label.LEFT);

        Button print = new Button("Tiskni");
        print.setBackground(Color.orange);
        print.setFont(new Font("Arial CE", Font.BOLD, 14));
        print.addActionListener(new TiskniAL());
        Panel printPanel = new Panel();
        printPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        printPanel.add(print);

        Button edituj = new Button("Ulož změny");
        edituj.setBackground(Color.orange);
        edituj.setFont(new Font("Arial CE", Font.BOLD, 14));
        edituj.addActionListener(new EditujAL());
        Panel editPanel = new Panel();
        editPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        editPanel.add(edituj);
        
        Button plus = new Button("+");
        plus.setBackground(Color.orange);
        plus.setFont(new Font("Arial CE", Font.BOLD, 14));
        plus.addActionListener(new PridejPolozkuAL());
        Panel plusPanel = new Panel();
        plusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        plusPanel.add(plus);
        
        Button minus = new Button("-");
        minus.setBackground(Color.orange);
        minus.setFont(new Font("Arial CE", Font.BOLD, 14));
        minus.addActionListener(new UberPolozkuAL());
        plusPanel.add(minus);

        Panel horniRadek = new Panel();
        horniRadek.setLayout(new GridLayout(1, 4));
        horniRadek.add(fakturaLabel);
        horniRadek.add(plusPanel);
        horniRadek.add(editPanel);
        horniRadek.add(printPanel);

        Firma redap = new Firma();

        Panel firma = new Panel();
        firma.setPreferredSize(new Dimension(200, 200));
        //firma.setBackground(Color.BLUE);
        firma.setLayout((new GridLayout(7, 1)));
        firma.add(new Label(redap.getNazev()));
        firma.add(new Label(redap.getUlice()));
        firma.add(new Label(redap.getPSC()));
        firma.add(new Label("IČO: " + redap.getICO()));
        firma.add(new Label("DIČ: " + redap.getDIC()));
        firma.add(new Label("Telefo: " + redap.getTel()));
        firma.add(new Label("Číslo účtu: " + redap.getUcet()));
        Panel levySloupec = new Panel();
        levySloupec.setLayout(new FlowLayout(FlowLayout.LEFT));
        levySloupec.add(firma);

        Panel odberatel = new Panel();
        odberatel.setPreferredSize(new Dimension(200, 200));
        odberatel.setLayout(new GridLayout(7, 1));
        odberatel.add(new Label("Odběratel"));
        if (faktura.getKlient() != null) {
            odberatel.add(new Label(faktura.getKlient().getJmeno()));
            odberatel.add(new Label(faktura.getKlient().getUlice()));
            odberatel.add(new Label(faktura.getKlient().getPSC()));
            odberatel.add(new Label(" "));
            odberatel.add(new Label("IČO: " + faktura.getKlient().getICO()));
            odberatel.add(new Label("DIČ: " + faktura.getKlient().getDIC()));
        }
        Panel pravySloupec = new Panel();
        pravySloupec.setLayout(new FlowLayout(FlowLayout.LEFT));
        pravySloupec.add(odberatel);

        Panel zahlavi = new Panel();
        zahlavi.setLayout(new GridLayout(1, 2));
        zahlavi.add(levySloupec);
        zahlavi.add(pravySloupec);

        Panel hlavicka = new Panel();
        hlavicka.setLayout(new BorderLayout());
        hlavicka.add(horniRadek, BorderLayout.NORTH);
        hlavicka.add(zahlavi, BorderLayout.CENTER);

        tabulka = new Tabulka(faktura);

        Panel zapati = new Panel();
        zapati.setLayout(new GridLayout(4, 3));
        Label soucet = new Label("Součet:");
        soucet.setFont(new Font("Arial CE", Font.BOLD, 12));
        soucet.setAlignment(Label.RIGHT);
        soucetC = new Label(String.format("%,.2f", faktura.getCelkCenu()));
        soucetC.setFont(new Font("Arial CE", Font.BOLD, 12));
        soucetC.setAlignment(Label.RIGHT);
        Label dph = new Label("DPH 20%");
        dph.setFont(new Font("Arial CE", Font.BOLD, 12));
        dph.setAlignment(Label.RIGHT);
        dphC = new Label(String.format("%,.2f", faktura.getDPH()));
        dphC.setFont(new Font("Arial CE", Font.BOLD, 12));
        dphC.setAlignment(Label.RIGHT);
        Label suma = new Label("Částka s DPH");
        suma.setFont(new Font("Arial CE", Font.BOLD, 12));
        suma.setAlignment(Label.RIGHT);
        sumaC = new Label(String.format("%,.2f", faktura.getCenuSDPH()));
        sumaC.setFont(new Font("Arial CE", Font.BOLD, 12));
        sumaC.setAlignment(Label.RIGHT);

        zapati.add(soucet);
        zapati.add(soucetC);
        zapati.add(dph);
        zapati.add(dphC);
        zapati.add(suma);
        zapati.add(sumaC);

        this.setLayout(new BorderLayout());
        this.add(hlavicka, BorderLayout.NORTH);
        this.add(tabulka.spane, BorderLayout.CENTER);
        this.add(zapati, BorderLayout.SOUTH);
    }

    /**
     * otevře okno pro tisk
     */
    class TiskniAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tisk ps;
            ps = new Tisk(faktura);
        }
    }

    /**
     * uloží změny na faktuře
     */
    class EditujAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            faktura.setPolozky(tabulka.polozky);
            try {
                Database.getInstance().updateFaktura(faktura);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
            }
            soucetC.setText(String.format("%,.2f", faktura.getCelkCenu()));
            sumaC.setText(String.format("%,.2f", faktura.getCenuSDPH()));
            dphC.setText(String.format("%,.2f", faktura.getDPH()));
        }
    }
    
    /**
     * přidá nový řádek v tabulce
     */
    class PridejPolozkuAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabulka.addingRow();
        }
    }
    
    /**
     * odebere vybraný řádek
     */
    class UberPolozkuAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabulka.deleteRow();
        }
    }
}
