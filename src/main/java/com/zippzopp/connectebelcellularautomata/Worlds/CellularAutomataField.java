package com.zippzopp.connectebelcellularautomata.Worlds;

import com.zippzopp.connectebelcellularautomata.CellularAutomataRuleSet;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.Position;

import java.util.Iterator;

public class CellularAutomataField implements Iterable<Position> {

    private CellularAutomataRuleSet ruleSet;
    private final int x;

    private final int y;

    private int width;

    private int height;

    public CellularAutomataField(int x, int y, int width, int height, CellularAutomataRuleSet ruleSet) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ruleSet = ruleSet;
    }

    public void changePosition(int x, int y){
        if(x<0 || y<0)
            throw new IllegalArgumentException("x and y needs to be positiv");
        this.width = x;
        this.height = y;
    }

    public void changeSize(int width, int height){
        if(width<0 || height<0)
            throw new IllegalArgumentException("width and height needs to be positiv");

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }




    public int getX() {
        return x;
    }

    public CellularAutomataRuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(CellularAutomataRuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public Iterator<Position> iterator() {
        return new Iterator<>() {
            private int currentX = x;
            private int currentY = y;

            @Override
            public boolean hasNext() {
                return (currentY - y) < height && (currentX - x) < width;
            }

            @Override
            public Position next() {
                Position position = new Position(currentX, currentY);
                currentX++;
                if (currentX - x >= width) {
                    currentX = x;
                    currentY++;
                }
                return position;
            }
        };
    }
}
