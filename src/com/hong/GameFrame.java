package com.hong;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
    private final GamePanel panel;

    public GameFrame()
    {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Snake");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
