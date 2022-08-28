package com.hong;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Apple
{
    private final ArrayList<int[]> boardCellsPositions = new ArrayList<>();
    private int[] pos;

    public Apple()
    {
        reset();
        for(int i=0; i<GamePanel.BOARD_WIDTH; i++)
        {
            for(int j=0; j<GamePanel.BOARD_HEIGHT; j++)
                boardCellsPositions.add(new int[]{i, j});
        }
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.red);
        g.drawRect(
                pos[0]*GamePanel.PIXEL_SIZE,
                pos[1]*GamePanel.PIXEL_SIZE,
                GamePanel.PIXEL_SIZE,
                GamePanel.PIXEL_SIZE
        );
    }

    public void respawn(ArrayList<int[]> snakeCellsPositions)
    {
        ArrayList<int[]> emptyCellsPositions = new ArrayList<>(boardCellsPositions);
        for(int[] snakeCellPos : snakeCellsPositions)
            emptyCellsPositions.removeIf(pos -> Arrays.equals(pos, snakeCellPos));
        pos = emptyCellsPositions.get((int) (Math.random() * emptyCellsPositions.size()));
    }

    public void reset()
    {
        pos = new int[]{GamePanel.BOARD_WIDTH/2 ,GamePanel.BOARD_HEIGHT/2+10};
    }

    public int[] getPos()
    {
        return pos;
    }
}
