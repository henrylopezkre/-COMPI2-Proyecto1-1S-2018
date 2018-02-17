/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.bean;

/**
 *
 * @author henry
 */
public class Output {
    private String fileName, text;
    private int row, column;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Output(String fileName, int row, int column, String text) {
        this.fileName = fileName;
        this.row = row;
        this.column = column;
        this.text = text;
    }
    
    
}
