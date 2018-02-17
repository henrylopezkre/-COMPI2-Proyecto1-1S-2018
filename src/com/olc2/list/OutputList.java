/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.list;

import com.olc2.bean.Output;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henry
 */
public class OutputList {
    private List<Output> outputList;
    private static OutputList instancia;
    public static synchronized OutputList getInstancia(){
        if(instancia == null){
            instancia = new OutputList();
        }
        return instancia;
    }
    public OutputList(){
        this.outputList = new ArrayList();
    }
    public void add(Output output){
        outputList.add(output);
    }
    public void remove(int index){
        outputList.remove(outputList.get(index));
    }
    public Output get(int index){
        return outputList.get(index);
    }
    public int size(){
        return outputList.size();
    }
}
