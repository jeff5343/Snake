package com.hong;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Snake
{
    private static final int INITIAL_LENGTH = 3;
    private ArrayList<int[]> snakeCells;
    private ArrayList<int[]> inputs;
    private int xDir;
    private int yDir;
    private boolean ateApple;

    public Snake()
    {
        reset();
    }

    public void move()
    {
        if(inputs.size()>0)
        {
            if(xDir != -inputs.get(0)[0])
                xDir = inputs.get(0)[0];
            if(yDir != -inputs.get(0)[1])
                yDir = inputs.get(0)[1];
            inputs.remove(0);
        }

        int[] head = snakeCells.get(0);
        int[] tail = snakeCells.get(snakeCells.size()-1);
        snakeCells.set(0, new int[]{head[0]+xDir, head[1]+yDir});
        for(int i=snakeCells.size()-1; i>0; i--)
        {
            if(i==1)
                snakeCells.set(i, head);
            else
                snakeCells.set(i, snakeCells.get(i - 1));
        }

        if(ateApple)
        {
            snakeCells.add(tail);
            ateApple = false;
        }
    }

    public void addDirection(int xDir, int yDir)
    {
        inputs.add(new int[]{xDir, yDir});
    }

    public boolean isHeadCollidingWithBody()
    {
        for(int i=1; i<snakeCells.size()-1; i++)
        {
            if(Arrays.equals(snakeCells.get(0), snakeCells.get(i)))
                return true;
        }
        return false;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.green);
        for(int[] cell : snakeCells)
            g.drawRect(
                    cell[0]*GamePanel.PIXEL_SIZE,
                    cell[1]*GamePanel.PIXEL_SIZE,
                    GamePanel.PIXEL_SIZE,
                    GamePanel.PIXEL_SIZE
            );
    }

    public void eatApple()
    {
        ateApple = true;
    }

    public void reset()
    {
        snakeCells = new ArrayList<>();
        inputs = new ArrayList<>();
        for(int i=0; i<INITIAL_LENGTH; i++)
            snakeCells.add(new int[]{(GamePanel.BOARD_WIDTH/2-1)-i, GamePanel.BOARD_HEIGHT/2-1});

        xDir=1;
        yDir=0;
        ateApple = false;
    }

    public int[] getHeadPosition()
    {
        return snakeCells.get(0);
    }

    public ArrayList<int[]> getSnakeCells() {
        return snakeCells;
    }
}
