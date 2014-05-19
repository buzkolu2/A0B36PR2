package redap;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 * okno editace faktury - NEPOUŽÍVÁ SE, NAHRAZENO EDITACÍ V TABULCE
 *
 * @author Fragolka
 */
public class EditovatFakturu extends Frame {

    static Seznam klienti, polozky;
    JFormattedTextField datumPlneni;
    static VlozitText datum;
    Button novaPolozka, smazPolozku, ok, zmenitOdberatele;
    static List<Polozka> novePolozkyFaktura;
    static SimpleDateFormat format;
    private Faktura faktura;
    private int index;

    EditovatFakturu(Faktura faktura, int idx) {
        index = idx;

        this.faktura = faktura;
        klienti = new Seznam();

        if (faktura.getKlient() != null) {
            klienti.pridejPrvek(faktura.getKlient().getJmeno());
        }
        klienti.list.setEnabled(false);

        polozky = new Seznam();
        novePolozkyFaktura = faktura.getPolozky();
        for (Polozka p : novePolozkyFaktura) {
            polozky.pridejPrvek(p.getPopis());
        }

        format = new SimpleDateFormat("dd.MM.yyyy");
        datum = new VlozitText("Datum zdanitelného plnění: ");
        datum.vstup.setText(format.format(faktura.getDatumPlneni()).toString());

        zmenitOdberatele = new Button("Změnit odběratele");
        zmenitOdberatele.setBackground(Color.orange);
        zmenitOdberatele.addActionListener(new ZmenitOdbrerateleAL());

        novaPolozka = new Button("Přidat položku");
        novaPolozka.setBackground(Color.orange);
        novaPolozka.addActionListener(new EditovatFakturu.PridatPolozkuAL());

        smazPolozku = new Button("Odebrat položku");
        smazPolozku.setBackground(Color.orange);
        smazPolozku.addActionListener(new EditovatFakturu.SmazatPolozkuAL());

        ok = new Button("Uložit");
        ok.setBackground(Color.orange);
        ok.addActionListener(new EditovatFakturu.OkAL());


        //vzhled okna
        this.setTitle("Editace faktury č. " + Integer.toString(faktura.getId()));
        this.setSize(400, 400);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        Panel pKlient = new Panel();
        Panel klientButton = new Panel();
        klientButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        klientButton.add(zmenitOdberatele);
        pKlient.setLayout(new BorderLayout(10, 10));
        pKlient.add(new Label("Odběratel"), BorderLayout.NORTH);
        pKlient.add(klientButton, BorderLayout.WEST);
        pKlient.add(klienti.panel, BorderLayout.CENTER);

        Panel pPolozek = new Panel();
        Panel polozkaButton = new Panel();
        polozkaButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        polozkaButton.add(novaPolozka);
        Panel smazButton = new Panel();
        smazButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        smazButton.add(smazPolozku);

        Panel polozkaTlacitka = new Panel();
        polozkaTlacitka.setLayout(new GridLayout(2, 1));
        polozkaTlacitka.add(polozkaButton);
        polozkaTlacitka.add(smazButton);

        pPolozek.setLayout(new BorderLayout(10, 10));
        pPolozek.add(new Label("Položky"), BorderLayout.NORTH);
        pPolozek.add(polozkaTlacitka, BorderLayout.WEST);
        pPolozek.add(polozky.panel, BorderLayout.CENTER);

        Panel grid1 = new Panel();
        grid1.setLayout(new GridLayout(2, 1));
        grid1.add(pKlient);
        grid1.add(pPolozek);

        Panel pDatum = new Panel();
        pDatum.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pDatum.add(datum.nazev);
        pDatum.add(datum.vstup);

        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        okButton.add(ok);

        Panel grid2 = new Panel();
        grid2.setLayout(new GridLayout(2, 1));
        grid2.add(pDatum);
        grid2.add(okButton);

        Label cisFaktura = new Label("Fakutra č. " + Integer.toString(faktura.getId()));
        cisFaktura.setFont(new Font("Arial CE", Font.BOLD, 14));

        this.setLayout(new BorderLayout(10, 10));
        this.add(grid1, BorderLayout.CENTER);
        this.add(grid2, BorderLayout.SOUTH);
        this.add(cisFaktura, BorderLayout.NORTH);

    }

    /**
     * zdostupní list
     */
    class ZmenitOdbrerateleAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            klienti.list.setEnabled(true);
            klienti.odeberPrvek(0);
            for (Klient k : Menu.klienti) {
                klienti.pridejPrvek(k.getJmeno());
            }
        }
    }

    /**
     * přidá novou položky na fakturu
     */
    class PridatPolozkuAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            new NovaPolozka(2).setVisible(true);
        }
    }

    /**
     * odstaní položku o indexu i z faktury
     */
    class SmazatPolozkuAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int idx = EditovatFakturu.polozky.vybranyPrvek();
            if (idx >= 0) {
                EditovatFakturu.polozky.odeberPrvek(idx);
                EditovatFakturu.novePolozkyFaktura.remove(idx);
            }
        }
    }

    /**
     * přepíše faktura a uloží
     */
    class OkAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                int idx = EditovatFakturu.klienti.vybranyPrvek();
                if (idx < 0 && klienti.list.isEnabled()) {
                    JOptionPane.showMessageDialog(null,
                            "Není vybrán klient", "Chyba", JOptionPane.OK_OPTION);
                } else {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        format.setLenient(false);
                        faktura.setDatumPlneni(format.parse(EditovatFakturu.datum.vstup.getText().trim()));

                        //pokud byl upraven klient, ulož nového klienta
                        //a přepiš list v hlavní okně
                        if (klienti.list.isEnabled()) {
                            OknoMenu.faktury.odeberPrvek(EditovatFakturu.this.index);
                            OknoMenu.faktury.seznam.add(EditovatFakturu.this.index,
                                    Integer.toString(faktura.getId())
                                    + ", " + Menu.klienti.get(idx).getJmeno());
                            faktura.setKlient(Menu.klienti.get(idx));
                        }

                        faktura.setPolozky(novePolozkyFaktura);
                        
                        Database.getInstance().updateFaktura(faktura);
                        //zavřít okno                        
                        EditovatFakturu.this.dispose();

                    } catch (Exception pe) {
                        JOptionPane.showMessageDialog(null,
                                "Chybné datum", "Chyba", JOptionPane.OK_OPTION);
                    }
                }
            } catch (NumberFormatException ex) {
                System.err.println(ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Chyba", JOptionPane.OK_OPTION);
            }


        }
    }
}
