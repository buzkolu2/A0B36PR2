/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * okno menu obsahující seznam klientů, seznam faktur a tlačítka přidat,
 * odebrat a prohlížet
 * @author Fragolka
 */
public class OknoMenu extends Frame {
    Button pridatFakturu, prohlizetFakturu, smazatFakturu,
            pridatKlienta, prohlizetKlienta, smazatKlienta;
    static Seznam faktury, klienti;
    
    OknoMenu(){
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            System.exit(1);
            } 
        });
        
        pridatFakturu=new Button("Nová faktura");
        pridatFakturu.setBackground(Color.orange);
        pridatFakturu.addActionListener(new PridatFakturuAL());
        
        prohlizetFakturu=new Button("Vybrat");
        prohlizetFakturu.setBackground(Color.orange);
        prohlizetFakturu.addActionListener(new ProhlizetFakturuAL());
        
        smazatFakturu=new Button("Smazat");
        smazatFakturu.setBackground(Color.orange);
        smazatFakturu.addActionListener(new SmazatFakturuAL());
        
        pridatKlienta=new Button ("Nový klient");
        pridatKlienta.setBackground(Color.orange);
        pridatKlienta.addActionListener(new PridatKlientaAL());
        
        prohlizetKlienta=new Button("Vybrat");
        prohlizetKlienta.setBackground(Color.orange);
        prohlizetKlienta.addActionListener(new ProhlizetKlientaAL());
        
        smazatKlienta=new Button("Smazat");
        smazatKlienta.setBackground(Color.orange);
        smazatKlienta.addActionListener(new SmazatKlientaAL());
        
        faktury = new Seznam();
        for (Faktura f : Menu.faktury){
            faktury.pridejPrvek(Integer.toString(f.getCisloFaktury())
                    +", "+f.getKlient().getJmeno());
        }
        
        klienti = new Seznam();
        for (Klient k : Menu.klienti){
            klienti.pridejPrvek(k.getJmeno());
        }
        
        Panel obrazek = new Obrazek();
        obrazek.setPreferredSize(new Dimension(400,81));
        obrazek.setMinimumSize(new Dimension(400,81));
        obrazek.setMaximumSize(new Dimension(400,81));
        
        this.setTitle("Menu");
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(400,300));
        this.setLayout(new BorderLayout(30,30));
        //this.setBackground(Color.yellow);
        
        Label fakt = new Label("FAKTURY");
        fakt.setFont(new Font("Arial CE", Font.BOLD,18));
        fakt.setAlignment(Label.CENTER);
        
        Panel flow1 = new Panel();
        flow1.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow1.add(pridatFakturu);
        
        Panel flow2 = new Panel();
        flow2.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow2.add(prohlizetFakturu);
        
        Panel flow3 = new Panel();
        flow3.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow3.add(smazatFakturu);
        
        Panel grid1 = new Panel();
        grid1.setPreferredSize(new Dimension(100,100));
        grid1.setLayout(new GridLayout(4,1));
        grid1.add(fakt);
        grid1.add(flow1);
        grid1.add(flow2);
        grid1.add(flow3);
        
        Label klien = new Label("KLIENTI");
        klien.setFont(new Font("Arial CE", Font.BOLD,18));
        klien.setAlignment(Label.CENTER);
        
        Panel flow4 = new Panel();
        flow4.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow4.add(pridatKlienta);
        
        Panel flow5 = new Panel();
        flow5.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow5.add(prohlizetKlienta);
        
        Panel flow6 = new Panel();
        flow6.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        flow6.add(smazatKlienta);
        
        Panel grid2 = new Panel();
        grid2.setPreferredSize(new Dimension(100,100));
        grid2.setLayout(new GridLayout(4,1));
        grid2.add(klien);
        grid2.add(flow4);
        grid2.add(flow5);
        grid2.add(flow6);
        
        Panel faktur=new Panel();
        faktur.setLayout(new BorderLayout());
        faktur.add(fakt,BorderLayout.NORTH);
        faktur.add(grid1,BorderLayout.WEST);
        faktur.add(faktury.panel, BorderLayout.CENTER);
        
        Panel klientu = new Panel();
        klientu.setLayout(new BorderLayout());
        klientu.add(klien,BorderLayout.NORTH);
        klientu.add(grid2,BorderLayout.WEST);
        klientu.add(klienti.panel, BorderLayout.CENTER);
        
        Panel grid3 = new Panel();
        grid3.setLayout(new GridLayout(2,1,10,10));
        grid3.add(faktur);
        grid3.add(klientu);
        
        
        this.add(obrazek, BorderLayout.NORTH);
        this.add(grid3, BorderLayout.CENTER);
        
        
        
    }
    
    class PridatFakturuAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            new NovaFaktura().setVisible(true);
        }
    }
    
    class ProhlizetKlientaAL implements ActionListener{
        public void actionPerformed (ActionEvent e){
            int idx=OknoMenu.klienti.vybranyPrvek();
            if (idx>=0){
                new ProhlizetKlienta(Menu.klienti.get(idx),idx).setVisible(true);
            }
        }
    }
    
    class SmazatFakturuAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            int idx=OknoMenu.faktury.vybranyPrvek();
            if (idx>=0){
                faktury.odeberPrvek(idx);
                Menu.faktury.remove(idx);
                try {
                    ObjectOutputStream out=new ObjectOutputStream(
                                    new FileOutputStream("faktury"));
                    out.writeObject(Menu.faktury);
                    out.close();
                }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
            }
            
        }
    }
    
    class ProhlizetFakturuAL implements ActionListener{
        public void actionPerformed (ActionEvent e){
            int idx=OknoMenu.faktury.vybranyPrvek();
            if (idx>=0){
                new ProhlizeniFaktury(Menu.faktury.get(idx),idx).setVisible(true);
            }
        }
    }
    
    class PridatKlientaAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            new NovyKlient().setVisible(true);
        }
    }
    
    class SmazatKlientaAL implements ActionListener {
        public void actionPerformed(ActionEvent e){
            int idx=OknoMenu.klienti.vybranyPrvek();
            if (idx>=0){
                klienti.odeberPrvek(idx);
                Menu.klienti.remove(idx);
                try {
                    ObjectOutputStream out=new ObjectOutputStream(
                                    new FileOutputStream("klienti"));
                    out.writeObject(Menu.klienti);
                    out.close();
                }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
            }
        }
    }
    
}
