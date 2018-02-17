/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.list.FileListModel;
import com.alee.extended.list.FileListViewType;
import com.alee.extended.list.WebFileList;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.PaneData;
import com.alee.extended.tab.TabTitleComponentProvider;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.utils.FileUtils;
import com.alee.utils.file.FileComparator;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author henry
 */
public class CBrowserPane extends WebPanel {
    private WebToolBar toolBarBrowser, toolBarFavs, toolBarOptions;
    private WebScrollPane scrollPanePage, scrollPaneListDocs, scrollPaneDocs, scrollPaneConsole;
    private CEditorPane editorPanePage, editorPaneDocs;
    private WebCollapsiblePane panelOptions;
    private WebFileList fileListDocs;
    private WebDocumentPane documentPaneDocs;
    private Color colorForeOptions, colorBackOptions;
    private WebSplitPane splitPaneDocs;
    private WebTable tableOutput, tableErrors;
    public CBrowserPane(){       
        this.colorForeOptions = new Color(255, 255, 255);
        this.colorBackOptions = new Color(44,124,203);
        this.editorPanePage = new CEditorPane();
        this.toolBarBrowser = new WebToolBar();
        this.toolBarFavs = new WebToolBar();
        this.toolBarOptions = new WebToolBar();
        this.toolBarBrowser.setRollover(true);
        this.scrollPanePage = new WebScrollPane(this.editorPanePage);
        this.setBackground(new Color(255, 255, 255));
        
        //ToolBar Browser
        toolBarBrowser.setShadeWidth(5);
        toolBarBrowser.setMargin(2, 0, 2, 0);
        toolBarBrowser.setFloatable(false);
        toolBarBrowser.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        toolBarBrowser.setToolbarStyle(ToolbarStyle.attached);
        toolBarBrowser.setLayout(new BoxLayout(toolBarBrowser, BoxLayout.LINE_AXIS));
        
        //Button Back
        //toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
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
        //textFieldAddress.setBackground(new Color(45, 45, 45));
        //textFieldAddress.setForeground(new Color(206, 206, 206));
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
        WebToggleButton buttonSettings = new WebToggleButton();
        buttonSettings.setPreferredSize(30, 30);
        buttonSettings.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_settings_20px.png")));
        buttonSettings.setRolloverDecoratedOnly(true);
        buttonSettings.setDrawFocus(false);
        buttonSettings.addMouseListener(buttonSettingsMouseListener);
        toolBarBrowser.add(buttonSettings);
        //toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        
        //ToolBar Favs
        toolBarFavs.setShadeWidth(5);
        toolBarFavs.setMargin(2, 0, 2, 0);
        toolBarFavs.setFloatable(false);
        toolBarFavs.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        toolBarFavs.setToolbarStyle(ToolbarStyle.attached);
        toolBarFavs.setLayout(new BoxLayout(toolBarFavs, BoxLayout.LINE_AXIS));
        toolBarFavs.add(Box.createRigidArea(new Dimension(5, 0)));
        
        //Fav
        WebLinkLabel linkLabelFav = new WebLinkLabel();
        linkLabelFav.setHighlight(false);
        linkLabelFav.setForeground(Color.BLACK);
        linkLabelFav.setLink("Google", "http://www.google.com", true);
        //webLabel.set
        linkLabelFav.setForeground(new Color(206, 206, 206));
        linkLabelFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_settings_20px.png")));
        toolBarFavs.add(Box.createRigidArea(new Dimension(5, 0)));
        toolBarFavs.add(linkLabelFav);
        
        //Button Fav
        toolBarFavs.add(Box.createRigidArea(new Dimension(5, 0)));
        WebButton buttonFav = new WebButton();
        buttonFav.setIconTextGap(5);
        buttonFav.setMargin(new Insets(0,0,0,0));
        buttonFav.setMaximumSize(new Dimension(150,25));
        buttonFav.setMoveIconOnPress(false);
        buttonFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_refresh_20px.png")));
        buttonFav.setText("Esto es una prueba de sacpeasdfasfdjjjjjjjjjjjjjjjjjjjjjjjjjjj");       
        buttonFav.setRolloverDecoratedOnly(true);
        buttonFav.setDrawFocus(false);
        toolBarFavs.add(buttonFav);
        
        //ToolBar Options
        toolBarOptions.setShadeWidth(5);
        toolBarOptions.setMargin(0, 0, 0, 0);
        toolBarOptions.setFloatable(false);
        toolBarOptions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        toolBarOptions.setToolbarStyle(ToolbarStyle.attached);
        toolBarOptions.setLayout(new BoxLayout(toolBarOptions, BoxLayout.LINE_AXIS));
        toolBarOptions.add(Box.createRigidArea(new Dimension(5, 0)));
                            
        //ToggleButton CHTML
        WebToggleButton buttonCHTML = new WebToggleButton("CHTML");
        buttonCHTML.setBottomSelectedBgColor(colorBackOptions);
        buttonCHTML.setTopSelectedBgColor(colorBackOptions);
        buttonCHTML.addMouseListener(groupButtonMouseListener);
        buttonCHTML.setSelected(true);
        
        //ToggleButton CJS
        WebToggleButton buttonCJS = new WebToggleButton("CJS");
        buttonCJS.setBottomSelectedBgColor(colorBackOptions);
        buttonCJS.setTopSelectedBgColor(colorBackOptions);
        buttonCJS.addMouseListener(groupButtonMouseListener);
        
        //ToggleButton CCSS
        WebToggleButton buttonCCSS = new WebToggleButton("CCSS");
        buttonCCSS.setBottomSelectedBgColor(colorBackOptions);
        buttonCCSS.setTopSelectedBgColor(colorBackOptions);
        buttonCCSS.addMouseListener(groupButtonMouseListener);
        
        //ToggleButton Output
        WebToggleButton buttonOutput = new WebToggleButton("Salida");
        buttonOutput.setBottomSelectedBgColor(colorBackOptions);
        buttonOutput.setTopSelectedBgColor(colorBackOptions);
        buttonOutput.addMouseListener(groupButtonMouseListener);
        
        //ToggleButton Error
        WebToggleButton buttonError = new WebToggleButton("Errores");
        buttonError.setBottomSelectedBgColor(colorBackOptions);
        buttonError.setTopSelectedBgColor(colorBackOptions);
        buttonError.addMouseListener(groupButtonMouseListener);
        
        //ButtonGroup Options
        WebButtonGroup buttonGroupOptions = new WebButtonGroup(true, buttonCHTML, buttonCJS, 
                buttonCCSS, buttonOutput, buttonError);
        buttonGroupOptions.setButtonsDrawSides(false, false, false, false);
        buttonGroupOptions.setButtonsMargin(2);
        buttonGroupOptions.setButtonsSelectedForeground(colorForeOptions);
        buttonGroupOptions.setOrientation(SwingConstants.HORIZONTAL);
        
        //CollapsiblePane Options
        this.panelOptions = new WebCollapsiblePane();
        this.panelOptions.setTitle("Opciones");
        this.panelOptions.setTitleComponent(buttonGroupOptions);
        this.panelOptions.setTitlePanePostion(SwingConstants.TOP);
        this.panelOptions.setShowStateIcon(true);
        this.panelOptions.setExpanded(true);
        this.panelOptions.setVisible(false);
        
        //DocumentPane Docs
        this.documentPaneDocs = new WebDocumentPane();
        
        //FileList Docs
        this.fileListDocs = new WebFileList ();
        
        //ScrollPane ListDocs
        this.scrollPaneListDocs = new WebScrollPane(fileListDocs);
        
        this.splitPaneDocs = new WebSplitPane (WebSplitPane.HORIZONTAL_SPLIT, 
                this.scrollPaneListDocs, this.documentPaneDocs);
        this.splitPaneDocs.setOneTouchExpandable(false);
        this.splitPaneDocs.setPreferredSize(new Dimension(250, 200));
        this.splitPaneDocs.setDividerBorderColor(this.colorBackOptions);
        this.splitPaneDocs.setResizeWeight(0.0);
        this.splitPaneDocs.setDividerLocation(200);
        this.splitPaneDocs.setDividerSize(3);
        this.splitPaneDocs.setContinuousLayout(true);   
        
        this.panelOptions.setContent(splitPaneDocs);
                       
        //Panel Browser Layout
        GroupLayout panelBrowserLayout = new GroupLayout(this);
        this.setLayout(panelBrowserLayout);
        panelBrowserLayout.setHorizontalGroup(
            panelBrowserLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(toolBarBrowser, GroupLayout.DEFAULT_SIZE, 540, Toolkit.getDefaultToolkit().getScreenSize().width-5)
            .addComponent(toolBarFavs, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Toolkit.getDefaultToolkit().getScreenSize().width-5)
            .addComponent(scrollPanePage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Toolkit.getDefaultToolkit().getScreenSize().width-5)
            .addComponent(panelOptions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Toolkit.getDefaultToolkit().getScreenSize().width-5)
        );
        panelBrowserLayout.setVerticalGroup(
            panelBrowserLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBrowserLayout.createSequentialGroup()
                .addComponent(toolBarBrowser, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolBarFavs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPanePage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );       
    }
    private MouseListener buttonFavMouseListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getComponent() instanceof WebToggleButton){
                WebToggleButton button = (WebToggleButton)e.getComponent();
                button.setIcon(new ImageIcon(getClass().getResource(button.isSelected() ? "/com/olc2/resources/ic_fav_pressed_20px.png" : "/com/olc2/resources/ic_fav_20px.png")));
                toolBarFavs.add(new WebButton("Nueva pestaña"));
                toolBarFavs.revalidate();
                toolBarFavs.repaint();
            }
        };
    };
    private MouseListener buttonSettingsMouseListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            if(panelOptions.isVisible()){
                panelOptions.setExpanded(false);
                panelOptions.setVisible(false);
            }else{
                panelOptions.setVisible(true);
                panelOptions.setExpanded(true);
            }
            if(e.getComponent() instanceof WebToggleButton){
                WebToggleButton button = (WebToggleButton)e.getComponent();
                button.setIcon(new ImageIcon(getClass().getResource(button.isSelected() ? "/com/olc2/resources/ic_settings_pressed_20px.png" : "/com/olc2/resources/ic_settings_20px.png")));
            }
        };
    };
       
    private MouseListener groupButtonMouseListener = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
            String buttonName = ((WebToggleButton)e.getComponent()).getText();
            switch(buttonName){
                case "CHTML":
                    loadListDocs("C:\\Users\\henry\\Documents\\Prueba", ".html");
                    break;
                case "CJS":
                    loadListDocs("C:\\Users\\henry\\Documents\\Prueba", ".js");
                    break;
                case "CCSS":
                    loadListDocs("C:\\Users\\henry\\Documents\\Prueba", ".css");
                    break;
                case "Salida":
                    
                    break;
                case "Errores":

                    break;
            }
            changeView(buttonName);
        }
    };
    private void changeView(String name){
        if(name.startsWith("C")){
            if(panelOptions.getContent() == splitPaneDocs){
                panelOptions.setContent(splitPaneDocs);
            } 
        }else{
            panelOptions.setContent(new WebPanel());
        }
    }
    private void loadListDocs(String path, String ext){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                //FileList Docs
                fileListDocs.setFileListViewType(FileListViewType.icons);
                fileListDocs.setPreferredColumnCount(1);
                fileListDocs.setPreferredRowCount(10);
                fileListDocs.setDisplayedDirectory(new File(path));
                fileListDocs.setMultiplySelectionAllowed(false);
                FileFilter filterCCSS = new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.getName().toLowerCase().endsWith(ext);
                    }
                };
                fileListDocs.setFileFilter(filterCCSS);
                fileListDocs.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e){
                        loadDocs();
                    }
                });
                fileListDocs.revalidate();
                fileListDocs.repaint();
            }
        }); 
    }
    
    private void loadDocs(){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                if(!fileListDocs.getFileListModel().isEmpty()){
                    if(!documentPaneDocs.isDocumentOpened(fileListDocs.getSelectedFile().getName())){
                        editorPaneDocs = new CEditorPane();
                        scrollPaneDocs = new WebScrollPane(editorPaneDocs);
                        try {
                            editorPaneDocs.getEditorKit().read(new FileReader(fileListDocs.getSelectedFile().getPath()),
                                    editorPaneDocs.getDocument(), 0);
                        } catch (IOException | BadLocationException ex) {
                            Logger.getLogger(CBrowserPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        documentPaneDocs.openDocument(new DocumentData(fileListDocs.getSelectedFile().getName(), 
                                null, fileListDocs.getSelectedFile().getName(), getParent().getBackground(), scrollPaneDocs));
                    }else{
                        documentPaneDocs.setSelected(fileListDocs.getSelectedFile().getName());
                    }
                }
            }
        });
    };
}
