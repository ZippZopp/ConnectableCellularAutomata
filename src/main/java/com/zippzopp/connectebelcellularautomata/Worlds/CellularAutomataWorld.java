package com.zippzopp.connectebelcellularautomata.Worlds;

import com.zippzopp.connectebelcellularautomata.CellularAutomataRuleSet;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.Position;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.RuleSetPosition;


import java.util.*;

public class CellularAutomataWorld implements Iterable<RuleSetPosition> {

    public Set<CellularAutomataField> getFields() {
        return cellularAutomataFields;
    }

    final private Set<CellularAutomataField> cellularAutomataFields;
    private List<CellularAutomataRuleSet> world;
    private final int width;
    private final int height;

    public CellularAutomataWorld(int height, int width) {
        this.height = height;
        this.width = width;
        initWorld();
        cellularAutomataFields = new HashSet<>();
    }

    private void initWorld() {
        world = new ArrayList<>(Collections.nCopies(width * height, CellularAutomataRuleSet.EMPTY));
    }


    public void update(){
        initWorld();
        for(CellularAutomataField field : cellularAutomataFields) {
            for(Position position : field){
                setCell(position.x(),position.y(), determineRuleSetForField(field.getRuleSet(), getCell(position.x(), position.y())));
            }
        }
    }

    private static CellularAutomataRuleSet determineRuleSetForField(CellularAutomataRuleSet newRuleSet, CellularAutomataRuleSet oldRuleSet){
        return newRuleSet;
    }

    public CellularAutomataRuleSet getCell(int x, int y) {
        if(!(isInBound(x, width) && isInBound(y, height)))
            throw new IllegalArgumentException("width and height needs to be in bound");

        return world.get(toIndex(x, y));
    }

    public CellularAutomataRuleSet setCell(int x, int y, CellularAutomataRuleSet ruleSet) {
        if(!(isInBound(x, width) && isInBound(y, height)))
            throw new IllegalArgumentException("width and height needs to be in bound");

        return world.set(toIndex(x, y), ruleSet);
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

    public boolean addField(CellularAutomataField newField) {
       return cellularAutomataFields.add(newField);
    }
}
