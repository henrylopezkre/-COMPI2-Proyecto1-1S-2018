/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.cswing;

import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.list.FileListViewType;
import com.alee.extended.list.WebFileList;
import com.alee.extended.panel.SingleAlignPanel;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.extended.panel.TwoSidesPanel;
import com.alee.extended.window.PopOverDirection;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.olc2.bean.Error;
import com.olc2.bean.Error.Type;
import com.olc2.bean.Fav;
import com.olc2.bean.Output;
import com.olc2.list.ErrorList;
import com.olc2.list.FavList;
import com.olc2.list.OutputList;
import com.olc2.model.ErrorTableModel;
import com.olc2.model.FavListModel;
import com.olc2.model.OutputTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 *
 * @author henry
 */
public class CBrowserPane extends WebPanel {
    private WebScrollPane scrollPanePage, scrollPaneListDocs, scrollPaneDocs, scrollPaneConsole, scrollPaneListFavs;
    private WebToolBar toolBarBrowser, toolBarFavs, toolBarOptions;
    private WebButtonPopup buttonPopupListFavs;
    private CEditorPane editorPanePage, editorPaneDocs;
    private WebCollapsiblePane panelOptions;
    private WebFileList fileListDocs;
    private WebDocumentPane documentPaneDocs;
    private Color colorForeOptions, colorBackOptions;
    private WebSplitPane splitPaneDocs;
    private WebTable tableOutput, tableErrors;
    private WebPanel panelOptionContainer;
    private WebToggleButton buttonFav, buttonSettings;
    private WebButton buttonNewFav, buttonFavList;
    private WebList listFavs;
    private WebPopOver popOverFav;
    private WebTextField textFieldURL;
    private WebLabel labelFav;
    public CBrowserPane(){       
        this.colorForeOptions = new Color(255, 255, 255);
        this.colorBackOptions = new Color(44,124,203);
        this.editorPanePage = new CEditorPane();
        this.toolBarBrowser = new WebToolBar();
        this.toolBarFavs = new WebToolBar();
        this.toolBarOptions = new WebToolBar();
        this.toolBarBrowser.setRollover(true);
        this.scrollPanePage = new WebScrollPane(this.editorPanePage);
        this.scrollPanePage.setBorder(BorderFactory.createEmptyBorder());
        this.scrollPaneConsole = new WebScrollPane(null);
        this.scrollPaneConsole.setBorder(BorderFactory.createEmptyBorder());
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
        textFieldURL = new WebTextField();
        textFieldURL.setLeadingComponent(new WebImage(getClass().getResource("/com/olc2/resources/ic_search_18px.png")));
        //textFieldURL.setBackground(new Color(45, 45, 45));
        //textFieldURL.setForeground(new Color(206, 206, 206));
        textFieldURL.setInputPrompt("Ingrese una dirección");
        textFieldURL.setMargin(2, 5, 2, 5);
        textFieldURL.setHideInputPromptOnFocus(false);
        textFieldURL.setRound(3);
        
        //Button Favs
        buttonFav = new WebToggleButton();
        buttonFav.setFocusable(false);
        buttonFav.setUndecorated(true);
        buttonFav.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));      
        buttonFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_fav_20px.png")));
        buttonFav.addActionListener(buttonFavActionListener);
        textFieldURL.setTrailingComponent(buttonFav);
        toolBarBrowser.add(Box.createHorizontalGlue());
        toolBarBrowser.add(textFieldURL);
        
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
        buttonSettings = new WebToggleButton();
        buttonSettings.setPreferredSize(30, 30);
        buttonSettings.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_settings_20px.png")));
        buttonSettings.setRolloverDecoratedOnly(true);
        buttonSettings.setDrawFocus(false);
        buttonSettings.addMouseListener(buttonSettingsMouseListener);
        toolBarBrowser.add(buttonSettings);
        //toolBarBrowser.add(Box.createRigidArea(new Dimension(5, 0)));
        
        //TextField FavName
        WebTextField textFieldFavName = new WebTextField();
        textFieldFavName.setInputPrompt("Nombre del favorito");
        textFieldFavName.setHideInputPromptOnFocus(false);
        textFieldFavName.setPreferredSize(new Dimension(200, 30));
        
        
        //TextField FavURL
        WebTextField textFieldFavURL = new WebTextField();
        textFieldFavURL.setPreferredSize(new Dimension(200, 30));
        textFieldFavURL.setEditable(false);
        
        
        //Button SaveFav
        WebButton buttonSaveFav = new WebButton();
        buttonSaveFav.setPreferredSize(75, 30);
        buttonSaveFav.setBottomBgColor(new Color(46, 134, 193));
        buttonSaveFav.setTopBgColor(new Color(46, 134, 193));
        buttonSaveFav.setBottomSelectedBgColor(new Color(46, 134, 193));
        buttonSaveFav.setTopSelectedBgColor(new Color(46, 134, 193));
        buttonSaveFav.setForeground(new Color(255, 255, 255));
        buttonSaveFav.setSelectedForeground(new Color(255, 255, 255));
        buttonSaveFav.addActionListener(buttonSaveFavActionListener);
        buttonSaveFav.setText("Listo");
        buttonSaveFav.setDrawFocus(false);
        
        //Button RemoveFav
        WebButton buttonRemoveFav = new WebButton();
        buttonRemoveFav.setPreferredSize(75, 30);
        buttonRemoveFav.addActionListener(buttonRemoveFavActionListener);
        buttonRemoveFav.setText("Eliminar");
        buttonRemoveFav.setDrawFocus(false);
        
        //ButtonGroup Options
        WebButtonGroup buttonGroupFav = new WebButtonGroup(true, buttonSaveFav, buttonRemoveFav);
        //buttonGroupFav.setButtonsDrawSides(false, false, false, false);
        buttonGroupFav.setButtonsMargin(2);
        buttonGroupFav.setOrientation(SwingConstants.HORIZONTAL);
        
        //PopOver Fav
        popOverFav = new WebPopOver(buttonFav);
        popOverFav.setModal(false);
        popOverFav.setMargin(10);
        popOverFav.setMovable(false);
        popOverFav.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        popOverFav.setPreferredDirection(PopOverDirection.down);
        popOverFav.setLayout(new VerticalFlowLayout());
        popOverFav.add(new SingleAlignPanel(new WebLabel("Se añadió a Favoritos").setFontSize(14), 
                SingleAlignPanel.LEFT).setMargin(5));
        popOverFav.add(new TwoSidesPanel(new WebLabel("Nombre").setMargin(5, 5, 5, 10), textFieldFavName).setMargin(5));
        popOverFav.add(new TwoSidesPanel(new WebLabel("URL").setMargin(5, 5, 5, 10), textFieldFavURL).setMargin(5));
        popOverFav.add(new SingleAlignPanel(buttonGroupFav, SingleAlignPanel.RIGHT).setMargin(5));
        
        //ToolBar Favs
        toolBarFavs.setShadeWidth(5);
        toolBarFavs.setMargin(2, 0, 2, 0);
        toolBarFavs.setFloatable(false);
        toolBarFavs.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        toolBarFavs.setToolbarStyle(ToolbarStyle.attached);
        
        //Fav
        /*WebLinkLabel linkLabelFav = new WebLinkLabel();
        linkLabelFav.setHighlight(false);
        linkLabelFav.setForeground(Color.BLACK);
        linkLabelFav.setLink("Google", "http://www.google.com", true);
        linkLabelFav.setForeground(new Color(206, 206, 206));
        linkLabelFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_settings_20px.png")));
        toolBarFavs.add(Box.createRigidArea(new Dimension(5, 0)));
        toolBarFavs.add(linkLabelFav);*/
                     
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 1", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 2", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 3", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 4", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 5", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 6", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 7", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 8", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 9", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 10", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 11", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 12", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 13", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 14", "C:/Holaputomundo.html"));
        FavList.getInstancia().add(new Fav("ic_favlist_16px.png", "Item 15", "C:/Holaputomundo.html"));
       
                        
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
        
        //Table Errors
        this.tableErrors = new WebTable();
        this.tableErrors.setSelectionBackground(new Color(176, 197, 227));
        this.tableErrors.setSelectionForeground(new Color(0,0,0));
        
        //Table Output
        this.tableOutput = new WebTable();
        this.tableOutput.setSelectionBackground(new Color(176, 197, 227));
        this.tableOutput.setSelectionForeground(new Color(0,0,0));
        
        //FileList Docs
        this.fileListDocs = new WebFileList ();
        
        //ScrollPane ListDocs
        this.scrollPaneListDocs = new WebScrollPane(fileListDocs);
        this.scrollPaneListDocs.setBorder(BorderFactory.createEmptyBorder());
        
        this.splitPaneDocs = new WebSplitPane (WebSplitPane.HORIZONTAL_SPLIT, 
                this.scrollPaneListDocs, this.documentPaneDocs);
        this.splitPaneDocs.setOneTouchExpandable(false);
        this.splitPaneDocs.setPreferredSize(new Dimension(250, 200));
        this.splitPaneDocs.setDrawDividerBorder(true);
        this.splitPaneDocs.setDividerBorderColor(new Color(189, 195, 199));
        this.splitPaneDocs.setResizeWeight(0.0);
        this.splitPaneDocs.setDividerLocation(200);
        this.splitPaneDocs.setDividerSize(8);
        this.splitPaneDocs.setContinuousLayout(true);   
        
        this.panelOptionContainer = new WebPanel();
        this.panelOptionContainer.setPreferredSize(new Dimension(250, 200));
        this.panelOptionContainer.add(this.splitPaneDocs);
        
        this.panelOptions.setContent(panelOptionContainer);
                       
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
        
        this.loadFavorites();
        
    }
    private ActionListener buttonFavActionListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            buttonFav.setSelected(true);
            buttonFav.setIcon(new ImageIcon(getClass().getResource(buttonFav.isSelected() 
                    ? "/com/olc2/resources/ic_fav_pressed_20px.png" 
                    : "/com/olc2/resources/ic_fav_20px.png")));
            
            //toolBarFavs.add(new WebButton("Nueva pestaña"));
            //toolBarFavs.revalidate();
            //toolBarFavs.updateUI();
            //System.out.println(buttonFav.isSelected());
            popOverFav.show(buttonFav);
            
        };
    };
    private MouseListener buttonSettingsMouseListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            buttonSettings.setIcon(new ImageIcon(getClass().getResource(buttonSettings.isSelected() 
                    ? "/com/olc2/resources/ic_settings_pressed_20px.png" 
                    : "/com/olc2/resources/ic_settings_20px.png")));
            panelOptions.setExpanded(!panelOptions.isVisible());
            panelOptions.setVisible(!panelOptions.isVisible());
            loadListDocs("C:\\Users\\henry\\Documents\\Prueba", ".html");
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
            this.panelOptionContainer.remove(this.scrollPaneConsole);
            this.panelOptionContainer.add(this.splitPaneDocs);
        }else{
            if(name.equals("Salida")){
                OutputList o = new OutputList();
                o.add(new Output("Archivo", 1, 6, "Alguna línea"));
                this.tableOutput.setModel(new OutputTableModel(o));
                this.tableOutput.setPreferredScrollableViewportSize(new Dimension(200, 200));
            }else{
                ErrorList e = new ErrorList();
                e.add(new Error("Archivo", 1, 6, Type.Semántico, "Algun error"));
                this.tableErrors.setModel(new ErrorTableModel(e));
                this.tableErrors.setPreferredScrollableViewportSize(new Dimension(200, 200));
            }
            this.scrollPaneConsole.setViewportView(name.equals("Salida") ? this.tableOutput : this.tableErrors);
            this.panelOptionContainer.remove(this.splitPaneDocs);
            this.panelOptionContainer.add(this.scrollPaneConsole);
        }
        this.panelOptionContainer.updateUI();
    }
    private void loadListDocs(String path, String ext){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                //FileList Docs
                fileListDocs.setSelectionBackground(new Color(176, 197, 227));
                fileListDocs.setFileListViewType(FileListViewType.icons);
                fileListDocs.setPreferredColumnCount(2);
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
                fileListDocs.updateUI();
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
                        editorPaneDocs.setEditable(false);
                        scrollPaneDocs = new WebScrollPane(editorPaneDocs);
                        try {
                            editorPaneDocs.getEditorKit().read(new FileReader(fileListDocs.getSelectedFile().getPath()),
                                    editorPaneDocs.getDocument(), 0);
                        } catch (IOException | BadLocationException ex) {
                            Logger.getLogger(CBrowserPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String fileName = fileListDocs.getSelectedFile().getName();
                        documentPaneDocs.openDocument(new DocumentData(fileName, 
                                getIconDocs(fileName), fileName, getParent().getBackground(), scrollPaneDocs));
                    }else{
                        documentPaneDocs.setSelected(fileListDocs.getSelectedFile().getName());
                    }
                }
            }
        });
    };
    
    private ImageIcon getIconDocs(String fileName){
        String icon = "";
        if(fileName.endsWith("html")){
            icon = "ic_html_20px.png";
        }else if(fileName.endsWith("js")){
            icon = "ic_js_20px.png";
        }else{
            icon = "ic_css_20px.png";
        }
        return new ImageIcon(getClass().getResource("/com/olc2/resources/" + icon));
    }
    
    private ListCellRenderer listCellRendererFavs = new ListCellRenderer() {
        @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                WebButton button = null;
                if(value instanceof WebButton){
                    button = (WebButton)value;
                    button.setIconTextGap(5);
                    button.setMargin(new Insets(0,0,0,0));
                    button.setPreferredSize(new Dimension(150,25));
                    button.setHorizontalAlignment(SwingConstants.LEADING);
                    button.setRolloverDecoratedOnly(true);
                    button.setDrawFocus(false);
                }
                return button;
        }
    };
    
    private MouseListener listFavsMouseListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            listFavs.clearSelection();
            buttonPopupListFavs.hidePopup();
        }
    }; 
    
    private ActionListener buttonSaveFavActionListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            popOverFav.dispose();
        }
    };
    
    private ActionListener buttonRemoveFavActionListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonFav.setSelected(false);
            buttonFav.setIcon(new ImageIcon(getClass().getResource(buttonFav.isSelected() 
                    ? "/com/olc2/resources/ic_fav_pressed_20px.png" 
                    : "/com/olc2/resources/ic_fav_20px.png")));
            popOverFav.dispose();
        }
    };
    
    private void loadFavorites(){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                toolBarFavs.removeAll();
                //Label Fav
                labelFav = new WebLabel();
                labelFav.setIconTextGap(2);
                labelFav.setMargin(new Insets(0,5,0,2));
                labelFav.setMaximumSize(new Dimension(16,16));
                labelFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_fav_folder_18px.png")));
                labelFav.setFontSizeAndStyle(11, Font.BOLD);
                labelFav.setText("Favoritos");
                
                //Button FavList
                buttonFavList = new WebButton();
                buttonFavList.setIconTextGap(5);
                buttonFavList.setMargin(new Insets(0,0,0,0));
                buttonFavList.setMaximumSize(new Dimension(16,16));
                buttonFavList.setMoveIconOnPress(false);
                buttonFavList.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/ic_favlist_16px.png")));
                buttonFavList.setRolloverDecoratedOnly(true);
                buttonFavList.setDrawFocus(false);
                buttonFavList.setVisible(false);
                
                toolBarFavs.add(labelFav);
                toolBarFavs.addSeparator();
                toolBarFavs.addToEnd(buttonFavList);
                
                Fav fav = null;
                for(int i = 0; i < FavList.getInstancia().size(); i++){
                    if(i < 10){
                        fav = FavList.getInstancia().get(i);
                        addToFavorites(fav.getIcon(), fav.getName(), fav.getPathFile());
                    }           
                }
                if(FavList.getInstancia().size() > 10){
                    //List Favs
                    listFavs = new WebList(new FavListModel());
                    listFavs.setCellRenderer(listCellRendererFavs);
                    listFavs.setMultiplySelectionAllowed(false);
                    listFavs.setVisibleRowCount(10);
                    listFavs.addMouseListener(listFavsMouseListener);
                    buttonFavList.setVisible(true);
                }
                
                //ScrollPane ListFavs
                scrollPaneListFavs = new WebScrollPane(listFavs);
                scrollPaneListFavs.setBorder(BorderFactory.createEmptyBorder());

                //ButtonPopup FavList
                buttonPopupListFavs = new WebButtonPopup(buttonFavList, PopupWay.downLeft);
                buttonPopupListFavs.setContent(scrollPaneListFavs);
                
                toolBarFavs.revalidate();
                toolBarFavs.updateUI();
            }
        });
    }
    
    private void addToFavorites(String icon, String name, String pathFile){
        buttonNewFav = new WebButton();
        buttonNewFav.setIconTextGap(5);
        buttonNewFav.setMargin(new Insets(0,0,0,0));
        buttonNewFav.setPreferredSize(new Dimension(121,25));
        buttonNewFav.setMoveIconOnPress(false);
        buttonNewFav.setIcon(new ImageIcon(getClass().getResource("/com/olc2/resources/" + icon)));
        buttonNewFav.setText(name);       
        buttonNewFav.setRolloverDecoratedOnly(true);
        buttonNewFav.setDrawFocus(false);
        buttonNewFav.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                textFieldURL.setText(pathFile);
            }
        });
        toolBarFavs.add(buttonNewFav);
    }
}
