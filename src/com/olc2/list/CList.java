/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.list;

/**
 *
 * @author henry
 * @param <Object>
 */
public interface CList<Object> {
    public void add(Object object);
    public void remove(int index);
    public Object get(int index);
    public int size();
}
