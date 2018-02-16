/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.gui;

import com.alee.extended.painter.PainterListener;
import com.alee.extended.painter.TexturePainter;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.TabStretchType;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.olc2.cswing.CBrowserPane;
import com.olc2.cswing.CTab;
import com.olc2.cswing.CTabbedPaneUI;
import com.olc2.model.TabList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
/**
 *
 * @author henry
 */
public class MainFrame extends WebFrame {

    /**
     * Creates new form MainFrame
     */
    private TexturePainter painterBackFrame, painterBackTabbedPane;
    MainFrame f;
    public MainFrame() {
        initComponents();
        f = this;
        this.painterBackFrame = new TexturePainter(new ImageIcon(getClass().getResource("/com/olc2/resources/frame.png")));
        this.painterBackTabbedPane = new TexturePainter(new ImageIcon(getClass().getResource("/com/olc2/resources/back.png")));
        //Personalización
        ComponentMoveAdapter.install(MainFrame.this, tabbedPaneBrowser);
        this.center();
        this.setIconImages(WebLookAndFeel.getImages());
        this.setInactiveShadeWidth(5);
        this.setShadeWidth(0);
        this.setShowTitleComponent(false);
        
        //this.setMiddleBg(new Color(38, 39, 43));
        //this.setTopBg(new Color(38, 39, 43));
        //this.setWindowOpacity(0.99f);
        //
         tabbedPaneBrowser.setUI(new CTabbedPaneUI());
        tabbedPaneBrowser.addChangeListener(tabPaneBrowserChangeListener);
        tabbedPaneBrowser.addTab("", new CBrowserPane());
        tabbedPaneBrowser.addTab("", new CBrowserPane());
        int i = TabList.getInstancia().getTabID();
        tabbedPaneBrowser.setTabComponentAt(0, new CTab(tabbedPaneBrowser, i, "Facebook"));
        TabList.getInstancia().add(new CTab(tabbedPaneBrowser, i, "Facebook"));
        i = TabList.getInstancia().getTabID();
        tabbedPaneBrowser.setTabComponentAt(1, new CTab(tabbedPaneBrowser, i, ""));
        ((WebTabbedPane)tabbedPaneBrowser).setTabOverlay(1);
        ((WebTabbedPane)tabbedPaneBrowser).setTabStretchType(TabStretchType.never);
        TabList.getInstancia().add(new CTab(tabbedPaneBrowser, i, ""));
        ((WebTabbedPane)tabbedPaneBrowser).setTabbedPaneStyle(TabbedPaneStyle.attached);
        ((WebTabbedPane)tabbedPaneBrowser).setSelectedIndex(0);
    }

    private ChangeListener tabPaneBrowserChangeListener = new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e){
            /*WebTabbedPane tabbedPane = (WebTabbedPane)e.getSource();
            tabbedPane.setBackgroundPainterAt(tabbedPane.getSelectedIndex(), painterBackTabbedPane);
            for(int i = 0; i < tabbedPane.getTabCount(); i++){
                Component component = tabbedPane.getTabComponentAt(i);
                if(component instanceof CTab){
                    CTab cTab = (CTab)component;
                    cTab.setForeground(i == tabbedPane.getSelectedIndex() ? Color.white : Color.black);
                }
            }*/
            
        };
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneBrowser = new WebTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbedPaneBrowser, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneBrowser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();
                WebLookAndFeel.setDecorateAllWindows(true);
                WebLookAndFeel.setAllowLinuxTransparency(true);
                WebFrame.setDefaultLookAndFeelDecorated(true);
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabbedPaneBrowser;
    // End of variables declaration//GEN-END:variables
}
