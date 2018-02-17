/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.model;

import com.olc2.bean.Output;
import com.olc2.list.OutputList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author henry
 */
public class OutputTableModel extends AbstractTableModel {
    private OutputList listOutput;
    private final String[] columnNames = 
            new String[]{"No.", "Archivo", "Línea", "Columna", "Salida"};
    public OutputTableModel(OutputList listOutput){
        this.listOutput = listOutput;
        this.fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return this.listOutput.size();
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
        Output output = this.listOutput.get(rowIndex);
        switch(columnIndex){
            case 0:
                value = String.valueOf(rowIndex+1);
                break;
            case 1:
                value = output.getFileName();
                break;
            case 2:
                value = String.valueOf(output.getRow());
                break;
            case 3:
                value = String.valueOf(output.getColumn());
                break;
            case 4:
                value = output.getText();
                break;
        }
        return value;
    }
}
