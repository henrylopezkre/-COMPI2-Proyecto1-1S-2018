/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.bean;

import java.util.Enumeration;

/**
 *
 * @author henry
 */
public class Error {
    private String fileName, description;
    private int row, column;
    public enum Type{
        LEXICAL, SYNTACTIC, SEMANTIC
    };
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Error(String fileName, int row, int column, Type type, String description) {
        this.fileName = fileName;
        this.row = row;
        this.column = column;
        this.type = type;
        this.description = description;
    }
}
