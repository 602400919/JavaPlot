/*
 * SVGTerminal.java
 *
 * Created on October 24, 2007, 7:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.panayotis.gnuplot.terminal;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import javax.swing.JPanel;


/**
 *
 * @author teras
 */
public class SVGTerminal extends TextFileTerminal {
    
    /** Creates a new instance of SVGTerminal */
    public SVGTerminal() {
        this("");
    }
    
    /**
     * 
     * @param filename
     */
    public SVGTerminal(String filename) {
        super("svg", filename);
    }
    
    public String processOutput(InputStream stdout) {
        String out_status = super.processOutput(stdout);
        if (output!=null) {
            output = output.replace("currentColor", "black");
        }
        return out_status;
    }
    
    /**
     * 
     * @param width
     * @param height
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    public JPanel getPanel(int width, int height) throws ClassNotFoundException {
        /* Use reflection API to create the representation in SVG format */
        Object svgDisplayPanel = null;
        try {
            svgDisplayPanel = Class.forName("com.kitfox.svg.SVGDisplayPanel").newInstance();
            Object universe = Class.forName("com.kitfox.svg.SVGUniverse").newInstance();
            Object diagram = null;
            
            universe.getClass().getMethod("loadSVG", Reader.class, String.class).invoke(universe, new StringReader(output), "plot");
            URI uri = (URI) universe.getClass().getMethod("getStreamBuiltURI", String.class).invoke(universe, "plot");
            diagram = universe.getClass().getMethod("getDiagram", URI.class).invoke(universe, uri);
            svgDisplayPanel.getClass().getMethod("setDiagram", Class.forName("com.kitfox.svg.SVGDiagram")).invoke(svgDisplayPanel, diagram);
        } catch (NoSuchMethodException e) {
            throw new ClassNotFoundException(e.getMessage());
        } catch (InstantiationException e) {
            throw new ClassNotFoundException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ClassNotFoundException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
        
        return (JPanel)svgDisplayPanel;
    }
    
}
