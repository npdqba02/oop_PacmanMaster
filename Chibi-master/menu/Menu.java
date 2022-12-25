/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import java.awt.*;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import chibi.Model;
import chibi.Chibi;
import javax.swing.JTextArea;


 
/**
 *
 * @author asus
 */
public class Menu {
     Frame frame ;
     TextField textField ;
     Button buttonStart;
     Button buttonExit;
     
     
     public Menu(){
         
        
        frame = new Frame("Trò chơi Chibi-pacman");
        buttonStart = new Button("Start");
        buttonStart.setBounds(150, 200, 80, 50);
        buttonExit = new Button("Exit");
        buttonExit.setBounds(150, 300, 80, 50);
        
        
        
        
        
        
        buttonStart.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Chibi chib = new Chibi();
		chib.setVisible(true);
		chib.setTitle("Chibi");
		chib.setSize(380,420);
		chib.setDefaultCloseOperation(EXIT_ON_CLOSE);
		chib.setLocationRelativeTo(null);
            }
        });
        
        
        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        
        
        frame.add(buttonStart);
        frame.add(buttonExit);
        
        frame.setSize(380, 420);
        frame.setLayout(null);
        frame.setVisible(true);
        
     }
     
     
}
