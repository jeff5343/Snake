package com.hong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable
{
    public static final int PIXEL_SIZE = 10;
    public static final int BOARD_WIDTH = 50;
    public static final int BOARD_HEIGHT = 50;
    private final JLabel scoreLabel;
    private final JButton respawnButton;
    private Thread gameThread;

    private final Snake snake;
    private final Apple apple;

    private boolean gameIsRunning;

    public GamePanel()
    {
        gameIsRunning = true;

        snake = new Snake();
        apple = new Apple();

        scoreLabel = new JLabel("dun",JLabel.CENTER);
        scoreLabel.setForeground(Color.lightGray);
        scoreLabel.setBounds(BOARD_WIDTH*PIXEL_SIZE/2-50, BOARD_HEIGHT*PIXEL_SIZE/2-75, 100, 50);
        scoreLabel.setVisible(false);
        respawnButton = new JButton("restart");
        respawnButton.addActionListener(e -> restartGame());
        respawnButton.setBounds(BOARD_WIDTH*PIXEL_SIZE/2-50, BOARD_HEIGHT*PIXEL_SIZE/2-25, 100, 50);
        respawnButton.setVisible(false);

        this.add(scoreLabel);
        this.add(respawnButton);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(BOARD_WIDTH*PIXEL_SIZE, BOARD_HEIGHT*PIXEL_SIZE));
        this.addKeyListener(new GameKeyListener());
        this.setLayout(null);
        this.setVisible(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void move()
    {
        snake.move();
    }

    private void checkCollisions()
    {
        int[] snakePos = snake.getHeadPosition();
        int[] applePos = apple.getPos();

        if((snakePos[0] > BOARD_WIDTH-1 || snakePos[0] < 0 || snakePos[1] > BOARD_HEIGHT-1 || snakePos[1] < 0)
                || snake.isHeadCollidingWithBody())
        {
            endGame();
        }

        if(snakePos[0] == applePos[0] && snakePos[1] == applePos[1])
        {
            apple.respawn(snake.getSnakeCells());
            snake.eatApple();
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Image image = createImage(this.getWidth(), this.getHeight());
        draw(image.getGraphics());
        g.drawImage(image, 0, 0, this);
    }

    private void draw(Graphics g)
    {
        snake.draw(g);
        apple.draw(g);
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 10.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta;
        while(gameIsRunning)
        {
            long now = System.nanoTime();
            delta = (now - lastTime) / ns;
            if(delta>=1)
            {
                move();
                checkCollisions();
                if(gameIsRunning)
                    repaint();
                lastTime = now;
            }
        }
    }

    private void endGame()
    {
        scoreLabel.setText("Score: " + snake.getSnakeCells().size());
        gameIsRunning = false;
        scoreLabel.setVisible(true);
        respawnButton.setVisible(true);
    }

    private void restartGame()
    {
        snake.reset();
        apple.respawn(snake.getSnakeCells());
        gameIsRunning = true;
        scoreLabel.setVisible(false);
        respawnButton.setVisible(false);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public class GameKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==87) // W
                snake.addDirection(0, -1);
            if(e.getKeyCode()==65) // A
                snake.addDirection(-1, 0);
            if(e.getKeyCode()==83) // S
                snake.addDirection(0, 1);
            if(e.getKeyCode()==68) // D
                snake.addDirection(1, 0);
        }
    }

}
