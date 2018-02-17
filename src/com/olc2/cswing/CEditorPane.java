/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.laf.text.WebEditorPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 *
 * @author henry
 */
public class CEditorPane extends WebEditorPane implements 
        Highlighter.HighlightPainter, CaretListener, MouseListener, MouseMotionListener {
    public JTextComponent component;
    private Color color;
    private Rectangle lastView;
    public CEditorPane(){
        //super();
        this.color = new Color(233, 239, 248);
        this.setSelectionColor(new Color(176, 197, 227));
        this.setEditorKit(new CEditorKit());
        //Add listeners so we know when to change highlighting
        CEditorPane.this.addCaretListener(this);
        CEditorPane.this.addMouseListener(this);
        CEditorPane.this.addMouseMotionListener(this);
        //Turn highlighting on by adding a dummy highlight
        try{
            CEditorPane.this.getHighlighter().addHighlight(0, 0, this);
        }catch(BadLocationException ble) {
            System.out.println(ble);
        }
    }
    
    //Paint index left bar
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(70, 130, 180, 128));
        g2d.fillRect(0, 0, 50, this.getHeight());
        /*g2d.setColor(new Color(128, 128, 128));
        g2d.fillRect(48, 0, 1, this.getHeight());*/
        g2d.setColor(new Color(131, 170, 238));
        g2d.fillRect(48, 0, 2, this.getHeight());
        /*g2d.setColor(new Color(179, 203, 236));
        g2d.fillRect(50, 0, 1, this.getHeight());*/
    }
    
    @Override
    public void setEditorKit(EditorKit e){
        super.setEditorKit(e);
    }
        
    @Override
    public void setText(String t){
        super.setText(t);
    }
       
    @Override
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        try{
            this.component = c;
            Rectangle r = c.modelToView(c.getCaretPosition());
            g.setColor(this.color);
            g.fillRect(53, r.y, c.getWidth(), r.height);
            if (lastView == null){
                lastView = r;
            }
        }catch(BadLocationException ble) {
            System.out.println(ble);
        }
    }
    
    private void resetHighlight(){
        //  Use invokeLater to make sure updates to the Document are completed,
        //  otherwise Undo processing causes the modelToView method to loop.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    int offset = CEditorPane.this.getCaretPosition();
                    Rectangle currentView = CEditorPane.this.modelToView(offset);
                    //  Remove the highlighting from the previously highlighted line
                    if (lastView != null && lastView.y != currentView.y){
                        CEditorPane.this.repaint(0, lastView.y, CEditorPane.this.getWidth(), lastView.height);
                        lastView = currentView;
                    }
                }catch(BadLocationException ble) {
                    System.out.println(ble);
                }
            }
        });
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        resetHighlight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        resetHighlight();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        resetHighlight();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Rectangle r = new Rectangle(50, this.getHeight());
        if(r.contains(e.getPoint())){
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
    }
    
    /**
    * Passes in the custom ViewFactory. Inherits from the StyledEditorKit since
    * that already comes with a default ViewFactory (while the DefaultEditorKit
    * does not).
    */
    public class CEditorKit extends StyledEditorKit {
        private static final long serialVersionUID = 1L;
        @Override
        public ViewFactory getViewFactory() {
            return new CViewFactory(super.getViewFactory());
        }
    }

    /**
    * Produces custom ParagraphViews, but uses the default ViewFactory for all
    * other elements.
    */
    class CViewFactory implements ViewFactory {
        private ViewFactory defaultViewFactory;
        CViewFactory(ViewFactory defaultViewFactory) {
            this.defaultViewFactory = defaultViewFactory;
        }
        @Override
        public View create(Element elem) {
            if (elem != null && elem.getName().equals(AbstractDocument.ParagraphElementName)) {
                return new CParagraphView(elem);
            }
            return defaultViewFactory.create(elem);
        }
    }

    /**
     * Paints a left hand child view with the line number for this Paragraph.
     */
    class CParagraphView extends ParagraphView {
        public final short MARGIN_WIDTH_PX = 53;
        private Element thisElement;
        private Font font;
        public CParagraphView(Element elem) {
            super(elem);
            thisElement = elem;
            this.setInsets((short) 0, (short) 0, (short) 0, (short) 0);
        }

        @Override
        protected void setInsets(short top, short left, short bottom, short right) {
            super.setInsets(top, (short) (left + MARGIN_WIDTH_PX), bottom, right);
        }

        @Override
        public void paintChild(Graphics g, Rectangle alloc, int index) {
            super.paintChild(g, alloc, index);
            // Allow of wrapped paragraph lines, but don't print redundant line
            // numbers.
            if (index > 0) {
                return;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Pad left so the numbers align
            int lineNumber = getLineNumber() + 1;
            String lnStr = String.format("%d", lineNumber);
            // Make sure we use a monospaced font.
            font = font != null ? font : new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize());
            g2d.setFont(font);
            int tam_digit = g2d.getFontMetrics().stringWidth(String.valueOf(lineNumber));
            //CENTER ALIGNMENT
            //int x = getLeftInset()/2 - tam_digit/2;
            //RIGHT ALIGNMENT
            int x = getLeftInset() - tam_digit - 9;
            int y = alloc.y + alloc.height - 3;
            /*if(getDocument().getDefaultRootElement().getElementCount()==lineNumber){
            g2d.setColor(Color.lightGray);
            g2d.drawRect(alloc.x, alloc.y, editor.getWidth()-57, alloc.height);
            }*/
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString(lnStr, x, y);
        }

        private int getLineNumber() {
            // According to the Document.getRootElements() doc, there will "typically"
            // only be one root element.
            Element root = getDocument().getDefaultRootElement();
            int len = root.getElementCount();
            for (int i = 0; i < len; i++) {
                if (root.getElement(i) == thisElement) {
                    return i;
                }
            }
            return 0;
        }
    }
}
