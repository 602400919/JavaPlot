/*
 * PostscriptTerminal.java
 *
 * Created on October 16, 2007, 1:34 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.panayotis.gnuplot.terminal;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author teras
 */
public class ImageTerminal extends FileTerminal {
    BufferedImage img;
    
    public ImageTerminal() {
        super("png");
    }
    
    public void processOutput(InputStream stdout) {
        try {
            img = ImageIO.read(stdout);
        } catch (IOException ex) { }
    }
    
    public BufferedImage getImage() {
        return img;
    }
    
       
}
