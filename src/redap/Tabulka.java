/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 * tabulka obsahující položky na faktuře
 * @author Fragolka
 */
public class Tabulka {

    JTable tabulka;
    DefaultTableModel model;
    JScrollPane spane;
    List<Polozka> polozky;

    Tabulka(Faktura faktura) {
        int radky = faktura.getPolozky().size();
        model = new DefaultTableModel(radky, 5);
        tabulka = new JTable(radky, 5);
        polozky = faktura.getPolozky();


        Panel tab = new Panel();
        tab.setLayout(new BorderLayout());
        tab.add(tabulka.getTableHeader(), BorderLayout.PAGE_START);

        //hlavička tabulky
        tabulka.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabulka.getColumnModel().getColumn(0).setHeaderValue("POŽKA Č.");
        tabulka.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabulka.getColumnModel().getColumn(1).setHeaderValue("POČET");
        tabulka.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabulka.getColumnModel().getColumn(2).setHeaderValue("POPIS");
        tabulka.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabulka.getColumnModel().getColumn(3).setHeaderValue("CENA/JEDN.");
        tabulka.getColumnModel().getColumn(4).setPreferredWidth(50);
        tabulka.getColumnModel().getColumn(4).setHeaderValue("ČÁSTKA BEZ DPH");

        //zarovná sloupec CENA/JEDN (3) a ČÁSTKA BEZ DPH (4) doprava
        DefaultTableCellRenderer vpravo = new DefaultTableCellRenderer();
        vpravo.setHorizontalAlignment(JLabel.RIGHT);
        tabulka.getColumnModel().getColumn(3).setCellRenderer(vpravo);
        //tabulka.getColumnModel().getColumn(4).setCellRenderer(vpravo);
        tabulka.getColumnModel().getColumn(4).setCellRenderer(vpravo);


        //zarovná sloupec POLOŽKA Č. (0) a POČET (1) na střed
        DefaultTableCellRenderer nastred = new DefaultTableCellRenderer();
        nastred.setHorizontalAlignment(JLabel.CENTER);
        tabulka.getColumnModel().getColumn(0).setCellRenderer(nastred);
        tabulka.getColumnModel().getColumn(1).setCellRenderer(nastred);
        
        //načtení položek do tabulky
        int i;
        for (Polozka p : polozky) {
            i = polozky.indexOf(p);
            tabulka.setValueAt(i + 1, i, 0);
            tabulka.setValueAt(p.getPocet(), i, 1);
            tabulka.setValueAt(p.getPopis(), i, 2);
            tabulka.setValueAt(String.format("%,.2f", p.getJCena()), i, 3);
            tabulka.setValueAt(String.format("%,.2f", p.getCena()), i, 4);
        }

        spane = new JScrollPane(tabulka);
        tabulka.setBackground(Color.yellow);
    }
}
