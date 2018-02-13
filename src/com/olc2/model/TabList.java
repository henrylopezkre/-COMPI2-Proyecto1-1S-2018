/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.model;

import com.olc2.cswing.CTab;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henry
 */
public class TabList {
    private static int tabID;
    private List<CTab> tabList;
    private static TabList instancia;
    public static synchronized TabList getInstancia(){
        if(instancia == null){
            instancia = new TabList();
        }
        return instancia;
    }
    public TabList(){
        tabList = new ArrayList();
        tabID++;
    }
    public void add(CTab tab){
        tabList.add(tab);
    }
    public void remove(int index){
        tabList.remove(tabList.get(index));
    }
    public int index(CTab tab){
        int index = -1;
        for(int i = 0; i < size(); i++){
            if(tab.getTabID() == tabList.get(i).getTabID()){
                index = i;
                break;
            }
        }
        return index;
    }
    public CTab get(int index){
        return tabList.get(index);
    }
    public int size(){
        return tabList.size();
    }
    public int getTabID(){
        return tabID++;
    }
}
