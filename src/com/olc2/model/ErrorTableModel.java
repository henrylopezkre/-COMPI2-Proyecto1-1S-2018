/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.model;

import com.olc2.bean.Error;
import com.olc2.list.ErrorList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author henry
 */
public class ErrorTableModel extends AbstractTableModel {
    private ErrorList listError;
    private final String[] columnNames = 
            new String[]{"No.", "Archivo", "Línea", "Columna", "Tipo", "Descripción"};
    public ErrorTableModel(ErrorList listError){
        this.listError = listError;
        this.fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return this.listError.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override 
    public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String value = "";
        Error error = this.listError.get(rowIndex);
        switch(columnIndex){
            case 0:
                value = String.valueOf(rowIndex+1);
                break;
            case 1:
                value = error.getFileName();
                break;
            case 2:
                value = String.valueOf(error.getRow());
                break;
            case 3:
                value = String.valueOf(error.getColumn());
                break;
            case 4:
                value = error.getType().toString();
                break;
            case 5:
                value = error.getDescription();
                break;
        }
        return value;
    }
}
