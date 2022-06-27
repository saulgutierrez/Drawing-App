/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dibujo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Greatest
 */
public class Dibujo extends JPanel implements ColorPaletteListener {
    BufferedImage image;
    Graphics2D g2D;
    
    public Dibujo() {
        super();
        init();
    }
    
    
    private void init() {
        setBackground(Color.BLACK);
        
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        g2D = image.createGraphics();
        g2D.setColor(getBackground());
        g2D.fillRect(0, 0, 640, 480);
        g2D.setColor(Color.GREEN);
        g2D.setStroke(new BasicStroke(2));
        
        MouseAdapter mouseHandler = new MouseAdapter() {
            private Point curPoint = new Point();
            @Override
            public void mousePressed(MouseEvent e) {
                curPoint.setLocation(e.getPoint());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                g2D.drawLine(curPoint.x, curPoint.y, e.getX(), e.getY());
                curPoint.setLocation(e.getPoint());
                repaint();
            }
            
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    Color color = g2D.getColor();
                    g2D.setColor(getBackground());
                    g2D.fillRect(0, 0, getWidth(), getHeight());
                    g2D.setColor(color);
                    repaint();
                }
            }
        });
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }
    
    @Override
    public void colorChanged(Color newColor) {
        g2D.setColor(newColor);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Dibujo");
        //frame.setMinimumSize(new Dimension(640, 480));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dibujo panel = new Dibujo();
        frame.add(panel, BorderLayout.CENTER);
        frame.add(new ColorPalette(panel), BorderLayout.EAST);
        frame.getContentPane().setBackground(Color.GRAY);
        
        BorderLayout layout = (BorderLayout) frame.getContentPane().getLayout();
        layout.setHgap(6);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        panel.requestFocus();
        });
    }
}
