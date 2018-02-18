/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.list;

import com.olc2.bean.Fav;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henry
 */
public class FavList implements IList<Fav> {
    private List<Fav> favList;
    private static FavList instancia;
    public static synchronized FavList getInstancia(){
        if(instancia == null){
            instancia = new FavList();
        }
        return instancia;
    }
    public FavList(){
        this.favList = new ArrayList();
    }
    @Override
    public void add(Fav object){
        favList.add(object);
    }
    @Override
    public void remove(int index){
        favList.remove(favList.get(index));
    }
    @Override
    public Fav get(int index){
        return favList.get(index);
    }
    @Override
    public int size(){
        return favList.size();
    }
}
