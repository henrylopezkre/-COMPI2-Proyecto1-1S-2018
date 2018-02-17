/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.slide.SlideTransitionEffect;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.extended.transition.effects.Direction;
import com.alee.extended.transition.effects.curtain.CurtainTransitionEffect;
import com.alee.extended.transition.effects.curtain.CurtainType;
import com.alee.laf.button.WebButton;
import com.alee.utils.SwingUtils;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import com.olc2.model.TabList;

/**
 *
 * @author Sorge
 */
public class CTabbedPaneUI extends WebTabbedPaneUI{
   boolean pressed = false, hover = false;
   Rectangle rectNew;
   Graphics2D g2d;
   ImageIcon iconNew = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_new_14px.png"));
   ImageIcon iconNewHover = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_new_hover_14px.png"));
   ImageIcon iconNewPressed = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_new_pressed_14px.png"));
   
   @Override
   protected void installListeners() {
      super.installListeners();
      tabPane.addMouseListener(newButtonMouseListener);
      tabPane.addMouseMotionListener(newButtonMouseMotionListener);
   }
    @Override
    protected void paintTab(Graphics g, int tabPlacement,
                           Rectangle[] rects, int tabIndex,
                           Rectangle iconRect, Rectangle textRect) {
        if(tabIndex == tabPane.getTabCount()-1){
            g2d = (Graphics2D)g;
            g2d.setColor(new Color(86, 140, 241));
            rectNew = new Rectangle(rects[tabIndex].x+rects[tabIndex].width,
                 rects[tabIndex].y, 22, 26);
            /*g2d.drawRect(rects[tabIndex].x+rects[tabIndex].width,
                 rects[tabIndex].y, 22, 26);*/
            if(hover){
                g2d.drawImage(iconNewHover.getImage(), rectNew.x+5, rectNew.y+8, (WebTabbedPane)tabPane);
            }else{
                g2d.drawImage(iconNew.getImage(), rectNew.x+5, rectNew.y+8, (WebTabbedPane)tabPane);
            }
            if(pressed){
                g2d.drawImage(iconNewPressed.getImage(), rectNew.x+5, rectNew.y+8, (WebTabbedPane)tabPane);
            }else{
                g2d.drawImage(iconNew.getImage(), rectNew.x+5, rectNew.y+8, (WebTabbedPane)tabPane);
            }
            
        }
        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
        /*ImageIcon imageIcon = new ImageIcon(getClass().getResource("/com/prueba/resources/ic_close_16dp.png"));
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(imageIcon.getImage(),  textRect.x + textRect.width - 14, textRect.y, tabPane);
        System.out.println("X: " + (textRect.x+textRect.width) + " Y: " + textRect.y);
        
      /*Font f = g.getFont();
      g.setColor(new Color(70, 130, 180));
      g.setFont(new Font("Eras Medium ITC", Font.BOLD, 12));
      FontMetrics fm = g.getFontMetrics(g.getFont());
      int charWidth = fm.charWidth('X');
      int maxAscent = fm.getMaxAscent();
      g.drawString("x", textRect.x + textRect.width - 7, textRect.y + textRect.height - 3);
      g.draw3DRect(textRect.x+textRect.width-9,
                 textRect.y+textRect.height-maxAscent, charWidth+2, maxAscent-1, true);
      xRect = new Rectangle(textRect.x+textRect.width-5,
                 textRect.y+textRect.height-maxAscent, charWidth+2, maxAscent-1);
      g.setFont(f);*/
    }
  
    private MouseListener newButtonMouseListener = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e) {
            if (rectNew.contains(e.getPoint())) {
                pressed = true;
                hover = false;
                g2d.dispose();
                ((WebTabbedPane)tabPane).revalidate();
                ((WebTabbedPane)tabPane).repaint();
            }else{
                pressed = false;
                hover = false;
                g2d.dispose();
                ((WebTabbedPane)tabPane).revalidate();
                ((WebTabbedPane)tabPane).repaint();
            }
        }
        public void mouseClicked(MouseEvent e){
            if(rectNew.contains(e.getPoint())){
                tabPane.addTab("", new CBrowserPane());
                int i = TabList.getInstancia().getTabID();
                tabPane.setTabComponentAt(tabPane.getTabCount()-1, new CTab(tabPane, i, "")); 
                TabList.getInstancia().add(new CTab(tabPane, i, ""));
                tabPane.setSelectedIndex(tabPane.getTabCount()-1);
                pressed = false;
                hover = false;
                g2d.dispose();
                ((WebTabbedPane)tabPane).revalidate();
                ((WebTabbedPane)tabPane).repaint();
            }
        }
    };
    private MouseMotionListener newButtonMouseMotionListener = new MouseAdapter(){
        @Override
        public void mouseMoved(MouseEvent e){
            if (rectNew.contains(e.getPoint())) {
                hover = true;
                pressed = false;
                g2d.dispose();
                ((WebTabbedPane)tabPane).revalidate();
                ((WebTabbedPane)tabPane).repaint();
            }else{
                hover = false;
                pressed = false;
                g2d.dispose();
                ((WebTabbedPane)tabPane).revalidate();
                ((WebTabbedPane)tabPane).repaint();
            }
            /*System.out.println("Hover: " + hover + " Pressed: " + pressed);
            ((WebTabbedPane)tabPane).revalidate();
            ((WebTabbedPane)tabPane).repaint();*/
        }
    };
}
