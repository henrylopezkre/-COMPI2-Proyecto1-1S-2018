/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.list;

import com.olc2.bean.Error;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henry
 */
public class ErrorList {
    private List<Error> errorList;
    private static ErrorList instancia;
    public static synchronized ErrorList getInstancia(){
        if(instancia == null){
            instancia = new ErrorList();
        }
        return instancia;
    }
    public ErrorList(){
        this.errorList = new ArrayList();
    }
    public void add(Error error){
        errorList.add(error);
    }
    public void remove(int index){
        errorList.remove(errorList.get(index));
    }
    public Error get(int index){
        return errorList.get(index);
    }
    public int size(){
        return errorList.size();
    }
}
