/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dibujo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class ColorPalette extends JPanel {
    private ColorPaletteListener listener;
    private int nSelected = -1;
    
    public ColorPalette(ColorPaletteListener listener) {
        super();
        this.listener = listener;
        init();
    }
    
    private void init() {
        setBackground(Color.GRAY);
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int n = ((e.getY() / 32))*2 + (e.getX() / 32);
                if(n < 16) {
                    listener.colorChanged(getColor(n));
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                int n = ((e.getY() / 32))*2 + (e.getX() / 32);
                if(n < 16) {
                    if(nSelected != n) {
                        nSelected = n;
                        repaint();
                    }
                } else if(nSelected != -1) {
                    nSelected = -1;
                    repaint();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(nSelected != -1) {
                    nSelected = -1;
                    repaint();
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }
    
    private static Color getColor(int code) {
        int rgb;
        if(code == 8)
            rgb = 0x404040;
        else {
            int i = 0x7f | ((code & 8) << 4);
            rgb = (((code & 4) * i) << (16-2)) +
                  (((code & 2) * i) << (8-1)) +
                  (((code & 1) * i));
        }
        return new Color(rgb);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for(int n = 0; n < 16; n++) {
            Color color = getColor(n);
            g.setColor(color);
            g.fillRect((n % 2)*32, (n / 2)*32, 32, 32);
        }
        if(nSelected != -1) {
            g.setColor(Color.BLACK);
            g.drawRect((nSelected % 2)*32 + 1, (nSelected / 2)*32 + 1, 32, 32);
            g.setColor(Color.WHITE);
            g.drawRect((nSelected % 2)*32, (nSelected / 2)*32, 32, 32);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(64, 280);
    }
}
