/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

/**
 * tabulka obsahující položky na faktuře, editace tabulky se projeví zápisem do databáze
 *
 * @author Fragolka
 */
public class Tabulka implements TableModelListener {

    JTable tabulka;
    DefaultTableModel model;
    JScrollPane spane;
    List<Polozka> polozky;

    Tabulka(Faktura faktura) {
        int radky = faktura.getPolozky().size();
        model = new DefaultTableModel(radky, 5);
        tabulka = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3 ? true : false;
            }
        };
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

        model.addTableModelListener(this);

        spane = new JScrollPane(tabulka);
        tabulka.setBackground(Color.yellow);
    }

    /**
     * přidá nový řádek, aktualizuje seznam polozek a databázi
     */
    public void addingRow() {
        int count = tabulka.getModel().getRowCount();
        this.model.addRow(new Object[]{count + 1, 0, "popis", 0, 0});
        polozky.add(new Polozka());
        try {
            int idx = Database.getInstance().insertPolozka(polozky.get(count));
            polozky.get(count).setId(idx);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
    }

    /**
     * vymaže vybraný řádek tabulky, aktualizuje seznam polozek a databázi
     */
    public void deleteRow() {
        int row = tabulka.getSelectedRow();
        this.model.removeRow(row);
        try {
            Database.getInstance().deletePolozka(polozky.get(row));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
        }
        polozky.remove(row);
    }

    /**
     * reaguje na změny v tabulce, dané změny uloží do seznamu položek a změní položku v databázi
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel) e.getSource();
        if (e.getType() == TableModelEvent.UPDATE) {
            String data = (String) model.getValueAt(row, column);
            if (column == 1) {
                try {
                    int pocet = new Integer(data);
                    polozky.get(row).setPocet(pocet);
                    model.setValueAt(String.format("%,.2f", polozky.get(row).getCena()), row, 4);
                    Database.getInstance().updatePolozka(polozky.get(row));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Polozka neni cele cislo", "Chyba", JOptionPane.OK_OPTION);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
                }
            }

            if (column == 2) {
                try {
                    polozky.get(row).setPopis(data);
                    Database.getInstance().updatePolozka(polozky.get(row));
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
                }
            }

            if (column == 3) {
                try {
                    double price = new Double(data.replace(",", "."));
                    polozky.get(row).setJcena(price);
                    model.setValueAt(String.format("%,.2f", polozky.get(row).getCena()), row, 4);
                    Database.getInstance().updatePolozka(polozky.get(row));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cena neni cislo", "Chyba", JOptionPane.OK_OPTION);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Nelze pripojit k databazi", "Chyba", JOptionPane.OK_OPTION);
                }
            }
        }

    }
}
