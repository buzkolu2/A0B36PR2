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

/**
 * Prohlizeni faktury, možnost její editace a vytisknutí
 *
 */
public class ProhlizeniFaktury extends Frame {

    Faktura faktura;
    Tabulka tabulka;
    int idx;

    ProhlizeniFaktury(Faktura faktura, int idx) {
        this.faktura = faktura;
        this.idx = idx;
        int radky = faktura.getPolozky().size();
        this.setTitle("Faktura č." + Integer.toString(faktura.getCisloFaktury()));
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(400, 300));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });


        Label fakturaLabel = new Label("FAKTURA č. "
                + Integer.toString(faktura.getCisloFaktury()));
        fakturaLabel.setFont(new Font("Arial CE", Font.BOLD, 14));
        fakturaLabel.setAlignment(Label.LEFT);

        Button print = new Button("Tiskni");
        print.setBackground(Color.orange);
        print.setFont(new Font("Arial CE", Font.BOLD, 14));
        print.addActionListener(new TiskniAL());
        Panel printPanel = new Panel();
        printPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        printPanel.add(print);

        Button edituj = new Button("Editovat fakturu");
        edituj.setBackground(Color.orange);
        edituj.setFont(new Font("Arial CE", Font.BOLD, 14));
        edituj.addActionListener(new EditujAL());
        Panel editPanel = new Panel();
        editPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        editPanel.add(edituj);

        Panel horniRadek = new Panel();
        horniRadek.setLayout(new GridLayout(1, 3));
        horniRadek.add(fakturaLabel);
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
        //odberatel.setBackground(Color.red);
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
        //pravySloupec.setBackground(Color.ORANGE);
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
        zapati.setLayout(new GridLayout(4, 2));
        Label soucet = new Label("Součet:");
        soucet.setFont(new Font("Arial CE", Font.BOLD, 12));
        soucet.setAlignment(Label.RIGHT);
        Label soucetC = new Label(String.format("%,.2f", faktura.getCelkCenu()));
        soucetC.setFont(new Font("Arial CE", Font.BOLD, 12));
        soucetC.setAlignment(Label.RIGHT);
        Label dph = new Label("DPH 20%");
        dph.setFont(new Font("Arial CE", Font.BOLD, 12));
        dph.setAlignment(Label.RIGHT);
        Label dphC = new Label(String.format("%,.2f", faktura.getDPH()));
        dphC.setFont(new Font("Arial CE", Font.BOLD, 12));
        dphC.setAlignment(Label.RIGHT);
        Label suma = new Label("Částka s DPH");
        suma.setFont(new Font("Arial CE", Font.BOLD, 12));
        suma.setAlignment(Label.RIGHT);
        Label sumaC = new Label(String.format("%,.2f", faktura.getCenuSDPH()));
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
        this.add(new Tabulka(faktura).spane, BorderLayout.CENTER);
        this.add(zapati, BorderLayout.SOUTH);
    }

    /**
     * otevře okno pro tisk
     */
    class TiskniAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Tisk ps;
            ps = new Tisk(faktura);
        }
    }

    /**
     * otevře okno pro editace
     */
    class EditujAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            new EditovatFakturu(faktura, idx).setVisible(true);
            ProhlizeniFaktury.this.dispose();

        }
    }
}
