package pacman;

import java.awt.*;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;



public class Menu extends JFrame{
    Frame frame;
    Button buttonStart;
    Button buttonExit;


    public Menu()  {
        frame = new Frame("Pacman");
        frame.setBackground(Color.PINK);

        buttonStart = new Button("Start");
        buttonStart.setBounds(150, 200, 80, 50);
        buttonExit = new Button("Exit");
        buttonExit.setBounds(150, 300, 80, 50);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Pacman pac = new Pacman();
                pac.setVisible(true);
                pac.setTitle("Pacman");
                pac.setSize(380, 420);
                pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
                pac.setLocationRelativeTo(null);
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
