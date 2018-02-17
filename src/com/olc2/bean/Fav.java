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
public class Fav {
    private String icon, name, pathFile;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public Fav(String icon, String name, String pathFile) {
        this.icon = icon;
        this.name = name;
        this.pathFile = pathFile;
    }
    
}
