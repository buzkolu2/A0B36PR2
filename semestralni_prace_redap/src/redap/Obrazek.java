/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * vytvoří obrázek lega
 */
public class Obrazek extends Panel{
    BufferedImage img;
    
    public void paint(Graphics g){
        g.drawImage(img,0,0,null);
    }

    public Obrazek(){
        try {
            img=ImageIO.read(new File("logo.jpg"));
        } catch(IOException e){
            
        }
    }
    
     
}
