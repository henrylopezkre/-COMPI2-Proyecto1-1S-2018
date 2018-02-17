/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.extended.painter.BorderPainter;
import com.alee.extended.painter.TexturePainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import com.olc2.list.TabList;
import java.awt.Color;

/**
 *
 * @author henry
 */
public class CTab extends WebPanel {
    private int tabID;
    private CTabText text;
    private WebTabbedPane parent;
    private ImageIcon iconClose, iconCloseHover, iconClosePressed, iconBlankPage;
    public CTab(Component parent, int tabID, String title){
        super();
        this.setName(String.valueOf(tabID));
        this.tabID = tabID;
        this.parent = (WebTabbedPane)parent;
        this.iconBlankPage = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_blank_page_20px.png"));
        this.iconClose = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_close_14px.png"));
        this.iconCloseHover = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_close_hover_18px.png"));
        this.iconClosePressed = new ImageIcon(getClass().getResource("/com/olc2/resources/ic_close_pressed_18px.png"));
        this.setPreferredSize(new Dimension(150, 20));
        this.setBackground(parent.getParent().getBackground());
        this.setToolTip(title);
        this.add(text = new CTabText(title), BorderLayout.WEST);
        this.add(new CTabCloseButton(), BorderLayout.EAST);
    }
    public int getTabID(){
        return tabID;
    }
    public CTabText getCTabText(){
        return this.text;
    }
    @Override
    public void setForeground(Color color){
        if(text instanceof CTabText){
            text.setForeground(color);
        }
    }
    private class CTabText extends WebLabel{
        public CTabText(String title){
            this.setPreferredSize(new Dimension(130, 20));
            this.setText(title.isEmpty() ? "Nueva pestaña" : title);
            this.setToolTip(title.isEmpty() ? "Nueva pestaña" : title);
            this.setMargin(0, 0, 0, 0);
            this.setHorizontalTextPosition(WebLabel.RIGHT);
            this.setIcon(iconBlankPage);
            this.addMouseListener(tabMouseListener);  
        }
        private MouseListener tabMouseListener = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Component component = e.getComponent();
                if (component instanceof CTabText) {
                    CTabText text = (CTabText) component;
                    parent.setSelectedIndex(TabList.getInstancia().index((CTab)text.getParent()));
                    
                    /*parent.setBackgroundPainterAt(parent.getSelectedIndex(), tp4);
                    CTab tab = (CTab)parent.getTabComponentAt(parent.getSelectedIndex());
                    tab.getCTabText().setForeground(Color.WHITE);*/
                }
            }
        };
    }
    private class CTabCloseButton extends JButton{
        public CTabCloseButton(){
            this.setIcon(iconClose);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setMargin(new Insets(0,0,0,0));
            this.setPreferredSize(new Dimension(20, 20));
            this.setUI(new BasicButtonUI());
            this.setContentAreaFilled(false);
            this.setFocusable(true);
            this.setBorder(BorderFactory.createEtchedBorder());
            this.setBorderPainted(false);
            this.addMouseListener(closeButtonMouseListener);
            this.setRolloverEnabled(true);
        }
        private MouseListener closeButtonMouseListener = new MouseAdapter() {       
            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setIcon(iconClose);
                    button.revalidate();
                    button.repaint();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setIcon(iconCloseHover);
                    button.revalidate();
                    button.repaint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e){
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setIcon(iconClosePressed);
                    button.revalidate();
                    button.repaint();
                }
            }
            @Override
            public void mouseClicked(MouseEvent e){
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setIcon(iconClose);
                    button.revalidate();
                    button.repaint();
                    int i = TabList.getInstancia().index((CTab)button.getParent());
                    TabList.getInstancia().remove(i);
                    parent.removeTabAt(i);
                }
                
            }
        };
    }
}

