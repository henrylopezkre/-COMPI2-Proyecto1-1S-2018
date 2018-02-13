/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olc2.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 *
 * @author henry
 */
public class DecorationExample
{
    public static void main ( final String[] args )
    {
        final JFrame f = new JFrame ();
        final JButton button = new JButton ( "test" );
        button.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                f.getRootPane ().setWindowDecorationStyle ( JRootPane.FRAME );
                f.setUndecorated ( true );
            }
        } );
        f.add ( button );
        f.setSize ( 500, 500 );
        f.setLocationRelativeTo ( null );
        f.setVisible ( true );
    }
}
