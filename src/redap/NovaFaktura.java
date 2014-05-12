/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.LinkedList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * okno pro vytvoření nové faktury
 * @author Fragolka
 */
public class NovaFaktura extends Frame {
    static Seznam klienti, polozky;
    JFormattedTextField datumPlneni;
    static VlozitText datum;
    Button novaPolozka, smazPolozku, ok;
    static LinkedList <Polozka> polozkyFaktura;
    static SimpleDateFormat format;
    
    NovaFaktura () {
        
        format=new SimpleDateFormat("dd.MM.yyyy");
        //datumPlneni=new JFormattedTextField(format);
        //datumPlneni.setPreferredSize(new Dimension(100,20));
        
        datum = new VlozitText("Datum zdanitelného plnění: ");
        Date date=new Date();
        datum.vstup.setText(format.format(date).toString());
        
        klienti = new Seznam();
        for (Klient k : Menu.klienti){
            klienti.pridejPrvek(k.getJmeno());
        }
        
        polozky = new Seznam();
        
        polozkyFaktura=new LinkedList<Polozka>();
        
        novaPolozka=new Button ("Přidat položku");
        novaPolozka.setBackground(Color.orange);
        novaPolozka.addActionListener(new PridatPolozkuAL());
        
        smazPolozku=new Button( "Odebrat polozžku");
        smazPolozku.setBackground(Color.orange);
        smazPolozku.addActionListener(new SmazatPolozkuAL());
        
        ok=new Button("Vytvoř");
        ok.setBackground(Color.orange);
        ok.addActionListener(new OkAL());
        
        
        //vzhled okna
        this.setTitle("Nová faktura");
        this.setSize(400,400);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
       } 
    });
        Label hlavicka = new Label("Fakutra č. "+Integer.toString(Redap.cisloFaktury+1));
        hlavicka.setFont(new Font("Arial CE",Font.BOLD,14));
        
        Panel pKlient = new Panel();
        pKlient.setLayout(new BorderLayout(10,10));
        pKlient.add(new Label("Odběratel"), BorderLayout.NORTH);
        pKlient.add(klienti.panel, BorderLayout.CENTER);
        
        Panel pPolozek = new Panel();
        Panel polozkaButton = new Panel();
        polozkaButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        polozkaButton.add(novaPolozka);
        Panel smazButton = new Panel();
        smazButton.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        smazButton.add(smazPolozku);
        
        Panel polozkaTlacitka = new Panel();
        polozkaTlacitka.setLayout(new GridLayout(2,1));
        polozkaTlacitka.add(polozkaButton);
        polozkaTlacitka.add(smazButton);
        
        pPolozek.setLayout(new BorderLayout(10,10));
        pPolozek.add(new Label("Položky"), BorderLayout.NORTH);
        pPolozek.add(polozkaTlacitka, BorderLayout.WEST);
        pPolozek.add(polozky.panel, BorderLayout.CENTER);
        
        Panel grid1 = new Panel();
        grid1.setLayout(new GridLayout(2,1));
        grid1.add(pKlient);
        grid1.add(pPolozek);
        
        Panel pDatum = new Panel();
        pDatum.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        pDatum.add(datum.nazev);
        pDatum.add(datum.vstup);
        
        Panel okButton = new Panel();
        okButton.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        okButton.add(ok);
        
        Panel grid2 = new Panel();
        grid2.setLayout(new GridLayout(2,1));
        grid2.add(pDatum);
        grid2.add(okButton);
        
        this.setLayout(new BorderLayout(10,10));
        this.add(grid1, BorderLayout.CENTER);
        this.add(grid2, BorderLayout.SOUTH);
        this.add(hlavicka,BorderLayout.NORTH);

    }
    /**
     * přidání nové položky na fakturu
     */
    
    class PridatPolozkuAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            new NovaPolozka(1).setVisible(true);
        }
    }
    
    /**
     * smazání položky s indexem idx
     */
    
    class SmazatPolozkuAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            int idx=NovaFaktura.polozky.vybranyPrvek();
            if (idx>=0){
                try {
                    NovaFaktura.polozky.odeberPrvek(idx);
                    Database.getInstance().deletePolozka(NovaFaktura.polozkyFaktura.get(idx));
                    NovaFaktura.polozkyFaktura.remove(idx);
                } catch (Exception ex) {
                    Logger.getLogger(NovaFaktura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * uloží nově vytvořenou fakturu
     */
    
    class OkAL implements ActionListener {
        public void actionPerformed (ActionEvent e){
            try {
                int idx=NovaFaktura.klienti.vybranyPrvek();
                if (idx<0){
                    JOptionPane.showMessageDialog(null,
                            "Není vybrán klient", "Chyba", JOptionPane.OK_OPTION);
                } else {
                    try{
                        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
                        format.setLenient(false);
                        format.parse(NovaFaktura.datum.vstup.getText().trim());
                        Faktura novaFaktura=new Faktura(Menu.klienti.get(idx),
                            format.parse(NovaFaktura.datum.vstup.getText().trim()),
                            NovaFaktura.polozkyFaktura);
                        
                        Menu.pridatFakturu(novaFaktura, novaFaktura.getKlient());
                        //Menu.faktury.add(novaFaktura);
                        
                        OknoMenu.faktury.pridejPrvek(Integer.toString(novaFaktura.getCisloFaktury())
                                +", "+novaFaktura.getKlient().getJmeno());
                        
                        NovaFaktura.this.dispose();
                        
//                        try {
//                            ObjectOutputStream out=new ObjectOutputStream(
//                                    new FileOutputStream("faktury"));
//                            out.writeObject(Menu.faktury);
//                            out.close();
//                            
//                            ObjectOutputStream out2=new ObjectOutputStream(
//                                    new FileOutputStream("cisloFaktury"));
//                            out2.writeInt(Redap.cisloFaktury);
//                            out2.close();                            
//                        }
//                        catch (IOException ex){
//                            ex.printStackTrace();
//                        }
                    } catch (ParseException pe){
                        JOptionPane.showMessageDialog(null,
                                "Chybné datum", "Chyba", JOptionPane.OK_OPTION);
                    }
                }}
            catch (NumberFormatException ex) {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Chyba", JOptionPane.OK_OPTION);
                }
            
            
        }
        }
}
    

