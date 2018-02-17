/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.model;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.olc2.bean.Fav;
import com.olc2.list.FavList;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

/**
 *
 * @author henry
 */
public class FavListModel extends AbstractListModel {
    private FavList favList;
    public FavListModel(FavList favList){
        this.favList = favList;
    }
    @Override
    public int getSize() {
        return this.favList.size();
    }

    @Override
    public Object getElementAt(int index) {
        Fav fav = this.favList.get(index);
        return new WebButton(fav.getName(), new ImageIcon(getClass().getResource("/com/olc2/resources/" + fav.getIcon())));
    }
    
}
