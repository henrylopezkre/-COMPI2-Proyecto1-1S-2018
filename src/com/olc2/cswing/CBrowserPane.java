/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.extended.image.WebImage;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollBar;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;

/**
 *
 * @author henry
 */
public class CBrowserPane extends WebPanel {
    private WebToolBar toolBarBrowser;
    private WebScrollPane scrollPanePage;
    private CEditorPane editorPane;
    public CBrowserPane(){
        this.editorPane = new CEditorPane();
        this.toolBarBrowser = new WebToolBar();
        this.toolBarBrowser.setRollover(true);
        this.scrollPanePage = new WebScrollPane(this.editorPane);
        this.setBackground(new Color(255, 255, 255));
        //Panel Brower Layout
        GroupLayout panelBrowserLayout = new GroupLayout(this);
        this.setLayout(panelBrowserLayout);
        panelBrowserLayout.setHorizontalGroup(
            panelBrowserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarBrowser, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
            .addComponent(scrollPanePage)
        );
        panelBrowserLayout.setVerticalGroup(
            panelBrowserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBrowserLayout.createSequentialGroup()
                .addComponent(toolBarBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPanePage, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );
        //ToolBar Browser
        toolBarBrowser.setShadeWidth(5);
        toolBarBrowser.setMargin(2, 0, 2, 0);
        toolBarBrowser.setFloatable(false);
        toolBarBrowser.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        toolBarBrowser.setBorderColor(new Color(85, 142, 239));
        toolBarBrowser.setToolbarStyle(ToolbarStyle.attached);
        toolBarBrowser.setLayout(new BoxLayout(toolBarBrowser, BoxLayout.LINE_AXIS));
        
        //Button Back
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonBack = new WebButton();
        buttonBack.setPreferredSize(30, 30);
        buttonBack.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_back_20px.png")));
        buttonBack.setRolloverDecoratedOnly(true);
        buttonBack.setDrawFocus(false);
        toolBarBrowser.add(buttonBack);
        
        //Button Next
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonNext = new WebButton();
        buttonNext.setPreferredSize(30, 30);
        buttonNext.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_next_20px.png")));
        buttonNext.setRolloverDecoratedOnly(true);
        buttonNext.setDrawFocus(false);
        toolBarBrowser.add(buttonNext);
        
        //Button Refresh
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonRefresh = new WebButton();
        buttonRefresh.setPreferredSize(30, 30);
        buttonRefresh.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_refresh_20px.png")));
        buttonRefresh.setRolloverDecoratedOnly(true);
        buttonRefresh.setDrawFocus(false);
        toolBarBrowser.add(buttonRefresh);
        
        //TextField Address
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebTextField textFieldAddress = new WebTextField();
        textFieldAddress.setLeadingComponent(new WebImage(getClass().getResource("/com/olc2/resources/ic_search_18px.png")));
        textFieldAddress.setInputPrompt("Ingrese una dirección");
        textFieldAddress.setMargin(2, 5, 2, 5);
        textFieldAddress.setHideInputPromptOnFocus(false);
        textFieldAddress.setRound(3);
        
        //Button Favs
        WebToggleButton buttonFavs = new WebToggleButton();
        buttonFavs.setFocusable(false);
        buttonFavs.setUndecorated(true);
        buttonFavs.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));      
        buttonFavs.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_fav_20px.png")));
        buttonFavs.addMouseListener(buttonFavMouseListener);
        textFieldAddress.setTrailingComponent(buttonFavs);
        toolBarBrowser.add(Box.createHorizontalGlue());
        toolBarBrowser.add(textFieldAddress);
        
         //Button History
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonHistory = new WebButton();
        buttonHistory.setPreferredSize(30, 30);
        buttonHistory.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_history_20px.png")));
        buttonHistory.setRolloverDecoratedOnly(true);
        buttonHistory.setDrawFocus(false);
        toolBarBrowser.add(buttonHistory);
        
        //Button Settings
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonSettings = new WebButton();
        buttonSettings.setPreferredSize(30, 30);
        buttonSettings.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_settings_20px_blue.png")));
        buttonSettings.setRolloverDecoratedOnly(true);
        buttonSettings.setDrawFocus(false);
        toolBarBrowser.add(buttonSettings);
        toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
    }
    private MouseListener buttonFavMouseListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getComponent() instanceof WebToggleButton){
                WebToggleButton button = (WebToggleButton)e.getComponent();
                button.setIcon(new ImageIcon(getClass().getResource(button.isSelected() ? "/com/olc2/resources/ic_fav_pressed_20px.png" : "/com/olc2/resources/ic_fav_20px.png")));
            }
        };
    };
}
