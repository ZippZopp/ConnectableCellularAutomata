package com.zippzopp.connectebelcellularautomata.Worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The World class represents a grid of boolean values, where each value represents the state of an element in the world.
 * It provides methods to access and manipulate the world.
 */


public class BooleanWorld {
    private List<Boolean> world;
    private int width;
    private int height;

    public BooleanWorld(int height, int width) {
        this.height = height;
        this.width = width;
        world = new ArrayList<>(Collections.nCopies(width * height, false));
    }

    /**
     * Gets if an element exists at the coordinates.
     * @param x coordinate.
     * @param y coordinate.
     * @return True if it exists and within bounds, False if not or it's out of bounds.
     */
    public boolean doesElementExist(int x, int y) {
        int index = toIndex(x, y);
        return isInBound(x, width) && isInBound(y, height) && world.get(index);
    }

    /**
     * Sets the value at the given coordinates too true.
     * @param x coordinate.
     * @param y coordinate.
     * @return True if the value was set, false if the coordinates were out of bounds.
     */
    public boolean setTrue(int x, int y) {
        return setValue(x,y,true);
    }

    /**
     * Sets the value at the given coordinates too false.
     * @param x coordinate.
     * @param y coordinate.
     * @return True if the value was set, false if the coordinates were out of bounds.
     */
    public boolean setFalse(int x, int y) {
        return setValue(x,y,false);
    }

    /**
     * Sets the value at the given coordinates.
     * @param x coordinate.
     * @param y coordinate.
     * @param value The value to set.
     * @return True if the value was set, false if the coordinates were out of bounds.
     */
    public boolean setValue(int x, int y, boolean value) {
        if (isInBound(x, width) && isInBound(y, height)) {
            int index = toIndex(x, y);
            world.set(index, value);
            return true;
        }
        return false;
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
     * Changes the size of the world.
     * @param newHeight The new height of the world.
     * @param newWidth The new width of the world.
     */
    public void changeSize(int newHeight, int newWidth) {
        List<Boolean> newWorld = new ArrayList<>(Collections.nCopies(newWidth * newHeight, false));
        for (int y = 0; y < Math.min(height, newHeight); y++) {
            for (int x = 0; x < Math.min(width, newWidth); x++) {
                newWorld.set(y * newWidth + x, doesElementExist(x, y));
            }
        }
        width = newWidth;
        height = newHeight;
        world = newWorld;
    }

    /**
     * Copies the current world state into a new World instance.
     * @return A new World instance with the same state.
     */
    public BooleanWorld copy() {
        BooleanWorld newBooleanWorld = new BooleanWorld(this.height, this.width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean value = doesElementExist(x, y);
                newBooleanWorld.setValue(x, y, value);
            }
        }
        return newBooleanWorld;
    }
}

