package redap;

import java.awt.*;
import java.awt.print.*;
import java.awt.Graphics.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * vytvoří vzor faktury k tisku
 * @author Fragolka
 */

public class Tisk implements Printable {

    Faktura faktura;
    LinkedList<Polozka> polozky;
    int strLength;

    public Tisk(Faktura faktura) {
        this.faktura = faktura;
        polozky = (LinkedList) faktura.getPolozky();
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        PageFormat pf = new PageFormat();
        Paper paper = new Paper();
        pf.setOrientation(PageFormat.PORTRAIT);
        paper = pf.getPaper();
        paper.setImageableArea(0, 0, 530, 842);
        pf.setPaper(paper);
        if (pj.printDialog()) /*
         * if user clicked OK to the print dialog
         */ {
            try {
                pj.print();
            } catch (Exception ex) {
                System.out.println("Error in Printing:" + ex);
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pi)
            throws PrinterException {
        if (pi >= 1)/*
         * if page index is greater than one then no more page to print
         */ {
            return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) g;
        /*
         * map graphics g2 to the imageable area of the page
         */
        g2.translate(pf.getImageableX(), pf.getImageableY());

        /*
         * *REDAP Zlín, s.r.o. Zádveřice 254 763 12 Vizovice IČO: 46975675 DIČ:
         * CZ46975675 Telefon: 571 891 903 Společnost zapsána v obchodním
         * rejstříku vedeného Krajským obchodním soudem v Brně dne 5.10.1992,
         * oddíl C, vložka 7655
         *
         * Číslo účtu:	182 866 672 / 0300
         */
        String font = "Arial CE";
        g2.setFont(new Font(font, Font.BOLD, 12));
        g2.drawString("REDAP Zlín, s.r.o.", 57, 85);
        g2.setFont(new Font(font, Font.PLAIN, 9));
        g2.drawString("Zádveřice 254", 57, 97);
        g2.drawString("763 12 Vizovice", 57, 109);
        g2.setFont(new Font(font, Font.PLAIN, 10));
        g2.drawString("IČO: 46975675", 57, 121);
        g2.drawString("DIČ: CZ46975675", 57, 133);
        g2.setFont(new Font(font, Font.PLAIN, 9));
        g2.drawString("Telefon: 571 891 903", 57, 145);
        g2.setFont(new Font(font, Font.PLAIN, 8));
        g2.drawString("Společnost zapsána v obchodním rejstříku", 57, 157);
        g2.drawString("vedeného Krajským obchodním soudem", 57, 169);
        g2.drawString("v Brně dne 5.10.1992, oddíl C, vložka 7655", 57, 181);
        g2.setFont(new Font(font, Font.PLAIN, 10));
        g2.drawString("Číslo účtu:  182 866 672 / 0300", 57, 205);

        /*
         * FAKTURA číslo faktury: xxxxxx
         */
        g2.setFont(new Font(font, Font.BOLD, 14));
        strLength = (int) g2.getFontMetrics().getStringBounds("FAKTURA", g2).getWidth();
        g2.drawString("FAKTURA", 525 - strLength, 67);
        g2.setFont(new Font(font, Font.BOLD, 10));
        strLength = (int) g2.getFontMetrics().getStringBounds(Integer.toString(faktura.getCisloFaktury()), g2).getWidth();
        g2.drawString(Integer.toString(faktura.getCisloFaktury()), 525 - strLength, 109);
        g2.setFont(new Font(font, Font.PLAIN, 9));
        g2.drawString("Číslo faktury", 340, 109);

        /*
         * Odběratel: Jméno Ulice a p.č. PSČ, město
         *
         * IČO DIČ
         */
        g2.setFont(new Font(font, Font.BOLD, 10));
        g2.drawString("Odběratel:", 340, 133);
        g2.setFont(new Font(font, Font.PLAIN, 10));
        g2.drawString(faktura.getKlient().getJmeno(), 340, 145);
        g2.drawString(faktura.getKlient().getUlice(), 340, 157);
        g2.drawString(faktura.getKlient().getPSC(), 340, 169);
        g2.drawString("IČO: " + faktura.getKlient().getICO(), 340, 193);
        g2.drawString("DIČ: " + faktura.getKlient().getDIC(), 340, 205);

        /*
         * DATA Datum zdanitelnoho plnění: xx.xx.xxxx Datum objednávky: Datum
         * vystavení faktury: xx.xx.xxxx Způsob dopravy: Datum spaltnost:
         * xx.xx.xxxx Zhotovil:
         */
        g2.setFont(new Font(font, Font.ITALIC, 8));
        g2.drawString("Datum zdanitelného plnění:", 57, 229);
        g2.drawString("Datum vystavení faktury:", 57, 241);
        g2.drawString("Datum splatnosti:", 57, 253);
        g2.drawString("Datum objednávky:", 291, 229);
        g2.drawString("Způsob dopravy:", 291, 241);
        g2.drawString("Zhotovil:", 291, 253);
        g2.setFont(new Font(font, Font.PLAIN, 10));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        strLength = (int) g2.getFontMetrics().getStringBounds(format.format(faktura.getDatumPlneni()).toString(), g2).getWidth();
        g2.drawString(format.format(faktura.getDatumPlneni()).toString(), 289 - strLength, 229);
        strLength = (int) g2.getFontMetrics().getStringBounds(format.format(faktura.getVystaveniFaktury()).toString(), g2).getWidth();
        g2.drawString(format.format(faktura.getVystaveniFaktury()).toString(), 289 - strLength, 241);
        strLength = (int) g2.getFontMetrics().getStringBounds(format.format(faktura.getDatumSplatnosti()), g2).getWidth();
        g2.drawString(format.format(faktura.getDatumSplatnosti()), 289 - strLength, 253);
        strLength = (int) g2.getFontMetrics().getStringBounds("Martina Bužková", g2).getWidth();
        g2.drawString("Martina Bužková", 524 - strLength, 253);

        /*
         * Popis sloupců se zarovnáním na střed POLOŽKA Č. | POČET | POPIS |
         * CENA/JEDN. | ČÁSTKA BEZ DPH
         */

        g2.setFont(new Font(font, Font.BOLD, 7));
        strLength = (int) g2.getFontMetrics().getStringBounds("POLOŽKA Č.", g2).getWidth();
        g2.drawString("POLOŽKA Č.", 82 - strLength / 2, 277);
        strLength = (int) g2.getFontMetrics().getStringBounds("POČET", g2).getWidth();
        g2.drawString("POČET", 138 - strLength / 2, 277);
        strLength = (int) g2.getFontMetrics().getStringBounds("POPIS", g2).getWidth();
        g2.drawString("POPIS", 282 - strLength / 2, 277);
        strLength = (int) g2.getFontMetrics().getStringBounds("CENA/JEDN.", g2).getWidth();
        g2.drawString("CENA/JEDN.", 426 - strLength / 2, 277);
        strLength = (int) g2.getFontMetrics().getStringBounds("ČÁSTKA BEZ DPH", g2).getWidth();
        g2.drawString("ČÁSTKA BEZ DPH", 490 - strLength / 2, 277);

        /*
         * Součet:
         * DPH 20%:
         *
         * Částka k prolacení:
         */
        g2.setFont(new Font(font, Font.ITALIC, 10));
        strLength = (int) g2.getFontMetrics().getStringBounds("Součet: ", g2).getWidth();
        g2.drawString("Součet: ", 455 - strLength, 649);
        strLength = (int) g2.getFontMetrics().getStringBounds("DPH 20%: ", g2).getWidth();
        g2.drawString("DPH 20%: ", 455 - strLength, 661);
        strLength = (int) g2.getFontMetrics().getStringBounds("Částka k prolacení: ", g2).getWidth();
        g2.drawString("Částka k prolacení: ", 455 - strLength, 685);

        g2.drawString("Převzal dne:________________", 57, 697);
        System.out.println("1");
        
        /*
         * Vložení polozek do tabulky
         */

        g2.setFont(new Font(font, Font.PLAIN, 10));

        for (Polozka p : polozky) {
            g2.drawString(Integer.toString(polozky.indexOf(p) + 1),
                    75, 289 + 12 * polozky.indexOf(p));
            strLength = (int) g2.getFontMetrics().getStringBounds(Integer.toString(p.getPocet()), g2).getWidth();
            g2.drawString(Integer.toString(p.getPocet()),
                    167 - strLength, 289 + 12 * polozky.indexOf(p));
            g2.drawString(p.getPopis(), 170, 289 + 12 * polozky.indexOf(p));
            g2.drawString(formatCisla(p.getJCena(),g2),454 - strLength,
                    289 + 12 * polozky.indexOf(p));
            g2.drawString(formatCisla(p.getCena(),g2),
                    523 - strLength, 289 + 12 * polozky.indexOf(p));
        }

        /*
         * Vložení celkové ceny, DPH a částky s DPH
         */
        g2.drawString(formatCisla(faktura.getCelkCenu(),g2), 523 - strLength, 649);

        g2.drawString(formatCisla(faktura.getDPH(),g2), 523 - strLength, 661);

        g2.setFont(new Font(font, Font.BOLD, 10));
        
        g2.drawString(formatCisla(faktura.getCenuSDPH(),g2), 523 - strLength, 685);


        /*
         * Tabulka
         */
        //hlavní obdélník
        g2.drawRect(56, 219, 469, 420);
        //příčné čáry
        g2.drawLine(56, 231, 525, 231);
        g2.drawLine(56, 243, 525, 243);
        g2.drawLine(56, 255, 525, 255);
        g2.drawLine(56, 267, 525, 267);
        g2.drawLine(56, 279, 525, 279);
        //svislé čáry
        g2.drawLine(290, 219, 290, 255);
        g2.drawLine(107, 267, 107, 639);
        g2.drawLine(169, 267, 169, 639);
        g2.drawLine(396, 267, 396, 639);
        g2.drawLine(456, 267, 456, 639);

        g2.drawRect(456, 639, 69, 48);
        g2.drawLine(456, 651, 525, 651);
        g2.drawLine(456, 663, 525, 663);
        g2.drawLine(456, 675, 525, 675);

        return Printable.PAGE_EXISTS;
    }

    /**
     * Zarovnání v pravo,
     * formát čísla # ###,00
     */
    private String formatCisla(Double d, Graphics g2) {
        String number = String.format("%,.2f", d);
        strLength = (int) g2.getFontMetrics().getStringBounds(number, g2).getWidth();
        return number;
    }
}
