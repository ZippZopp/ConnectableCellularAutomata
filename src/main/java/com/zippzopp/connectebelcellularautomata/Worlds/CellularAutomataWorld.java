package com.zippzopp.connectebelcellularautomata.Worlds;

import com.zippzopp.connectebelcellularautomata.CellularAutomataRuleSet;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.Position;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.RuleSetPosition;


import java.util.*;

public class CellularAutomataWorld implements Iterable<RuleSetPosition> {



    private List<CellularAutomataRuleSet> world;
    private final int width;
    private final int height;

    public CellularAutomataWorld(int height, int width) {
        this.height = height;
        this.width = width;
        initWorld();
    }

    private void initWorld() {
        world = new ArrayList<>(Collections.nCopies(width * height, CellularAutomataRuleSet.EMPTY));
    }


    public CellularAutomataRuleSet getCell(int x, int y) {
        return isInWorldBound(x, y) ? world.get(toIndex(x, y)) : CellularAutomataRuleSet.EMPTY;
    }

    private boolean isInWorldBound(int x, int y) {
        return isInBound(x, width) && isInBound(y, height);
    }

    public void setCells(int x, int y,int radius, CellularAutomataRuleSet ruleSet) {
        for(int relativeToMiddleX = -radius; relativeToMiddleX <= radius; relativeToMiddleX++) {
            for(int relativeToMiddleY = -radius; relativeToMiddleY <= radius; relativeToMiddleY++) {
                final int currentX = x+relativeToMiddleX;
                final int currentY = y+relativeToMiddleY;
                if(isInWorldBound(currentX,currentY) && (radius+0.5 >= Math.sqrt(relativeToMiddleX*relativeToMiddleX + relativeToMiddleY*relativeToMiddleY))){
                    world.set(toIndex(currentX, currentY), ruleSet);
                }
            }
        }
    }

    /**
     * Checks if a value is within bounds (0 inclusive to maxBound exclusive).
     * @param value The value to check.
     * @param maxBoundExcluded The upper bound (exclusive).
     * @return True if in bounds, false otherwise.
     */
    private static boolean isInBound(int value, int maxBoundExcluded) {
        return value >= 0 && value < maxBoundExcluded;
    }

    /**
     * Converts 2D coordinates to a 1D index.
     * @param x coordinate.
     * @param y coordinate.
     * @return The index in the 1D array.
     */
    private int toIndex(int x, int y) {
        return y * width + x;
    }


    @Override
    public Iterator<RuleSetPosition> iterator() {
        return new Iterator<>(){
            private int currentX = 0;
            private int currentY = 0;
            @Override
            public boolean hasNext() {
                return (currentY < height) && (currentX < width);
            }

            @Override
            public RuleSetPosition next() {
                Position position = new Position(currentX, currentY);
                currentX++;
                if (currentX >= width) {
                    currentX = 0;
                    currentY++;
                }
                return new RuleSetPosition(position, getCell(position.x(), position.y()));
            }
        };
    }

}
