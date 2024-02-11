package com.zippzopp.connectebelcellularautomata;

import com.zippzopp.connectebelcellularautomata.Worlds.BooleanWorld;

public enum CellularAutomataRuleSet {
    EMPTY,GAME_OF_LIVE,HIGH_LIFE, SEEDS;

    public void changeWorld(BooleanWorld oldBooleanWorld, BooleanWorld newBooleanWorld, int x, int y) {
        switch(this){
            case GAME_OF_LIVE -> gameOfLife(oldBooleanWorld, newBooleanWorld,x,y);
            case HIGH_LIFE -> highLife(oldBooleanWorld, newBooleanWorld,x,y);
            case SEEDS -> seeds(oldBooleanWorld, newBooleanWorld,x,y);
            // add new Rulesets here
        }

    }
    public static void seeds(BooleanWorld oldBooleanWorld, BooleanWorld newBooleanWorld, int x, int y) {
        int liveNeighbors = 0;

        // Count the live neighbors
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                if (oldBooleanWorld.doesElementExist(x + i, y + j)) {
                    liveNeighbors++;
                }
            }
        }

        // Apply Seeds rules: A cell becomes alive only if it has exactly 2 living neighbors
        boolean shouldLive = liveNeighbors == 2;

        // Update the state in the new world
        newBooleanWorld.setValue(x, y, shouldLive);
    }

    public static void highLife(BooleanWorld oldBooleanWorld, BooleanWorld newBooleanWorld, int x, int y) {
        int liveNeighbors = 0;

        // Count the live neighbors
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                if (oldBooleanWorld.doesElementExist(x + i, y + j)) {
                    liveNeighbors++;
                }
            }
        }

        // Apply HighLife rules
        boolean isAlive = oldBooleanWorld.doesElementExist(x, y);
        boolean shouldLive = (!isAlive && (liveNeighbors == 3 || liveNeighbors == 6)) ||
                (isAlive && (liveNeighbors == 2 || liveNeighbors == 3));

        // Update the state in the new world
        newBooleanWorld.setValue(x, y, shouldLive);
    }

    public static void gameOfLife(BooleanWorld oldBooleanWorld, BooleanWorld newBooleanWorld, int x, int y) {
        int liveNeighbors = 0;

        // Check all the neighbors around the cell
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip checking the cell itself
                if (i == 0 && j == 0) continue;
                // Check if the neighbor is alive
                if (oldBooleanWorld.doesElementExist(x + i, y + j)) {
                    liveNeighbors++;
                }
            }
        }
        // Apply the rules of Game of Life
        boolean isAlive = oldBooleanWorld.doesElementExist(x, y);
        boolean shouldLive = isAlive && (liveNeighbors == 2 || liveNeighbors == 3) ||
                !isAlive && liveNeighbors == 3;

        // Update the state in the new world
        newBooleanWorld.setValue(x, y, shouldLive);
    }

    public boolean isEmpty(){
        return this == EMPTY;
    }
}
