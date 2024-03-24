package com.zippzopp.connectebelcellularautomata;

import com.zippzopp.connectebelcellularautomata.UtilityClasses.Position;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.RuleSetPosition;
import com.zippzopp.connectebelcellularautomata.Worlds.BooleanWorld;
import com.zippzopp.connectebelcellularautomata.Worlds.CellularAutomataWorld;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CCAApplication extends Application {
    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 60;
    private static final int CELL_SIZE = 10;

    private static final int WINDOW_WIDTH = CELL_WIDTH*CELL_SIZE;
    private static final int WINDOW_HEIGHT = CELL_HEIGHT*CELL_SIZE;

    private int drawRuleSetRadius = 1;
    CellularAutomataRuleSet ruleSet;
    BooleanWorld booleanWorld;

    CellularAutomataWorld cellularAutomataWorld;

    public CCAApplication() {
        this.booleanWorld = new BooleanWorld(CELL_HEIGHT, CELL_WIDTH);
        this.cellularAutomataWorld = new CellularAutomataWorld(CELL_HEIGHT, CELL_WIDTH);
        ruleSet = CellularAutomataRuleSet.GAME_OF_LIVE;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cellular Automata");

        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(gc);

        canvas.setFocusTraversable(true);
        final boolean[] paused = {false};

        setUpMouseDraggedHandler(canvas, gc);
        setUpKeyPressedHandler(canvas, paused);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        startAnimationTimer(gc, paused);
    }

    private void setUpMouseDraggedHandler(Canvas canvas, GraphicsContext gc) {
        canvas.setOnMousePressed(getMouseEventEventHandler(gc));
        canvas.setOnMouseDragged(getMouseEventEventHandler(gc));
    }

    private EventHandler<MouseEvent> getMouseEventEventHandler(GraphicsContext gc) {
        return event -> {
            int x = (int) event.getX() / CELL_SIZE;
            int y = (int) event.getY() / CELL_SIZE;

            // Determine which mouse button is being held
            if (event.isPrimaryButtonDown()) {
                // Handle the primary button drag action
                handlePrimaryClick(x, y, gc);
            } else if (event.isSecondaryButtonDown()) {
                // Handle the secondary button drag action
                handleSecondaryClick(x, y, gc);
            }
        };
    }

    private void handlePrimaryClick(int x, int y, GraphicsContext gc) {
        if (!cellularAutomataWorld.getCell(x, y).isEmpty()) {
            booleanWorld.setTrue(x, y);
        }
        drawGrid(gc);
    }

    private void handleSecondaryClick(int x, int y, GraphicsContext gc) {
        drawRuleSet(x, y, gc);
    }



    private void drawRuleSet(int x,int y,GraphicsContext gc){
        cellularAutomataWorld.setCells(x,y,drawRuleSetRadius,ruleSet);
        drawGrid(gc);
    }

    private void setUpKeyPressedHandler(Canvas canvas, boolean[] paused) {
        canvas.setOnKeyPressed(event -> {



            if(event.getCode() == KeyCode.SPACE) { // Use SPACE bar to toggle pause
                paused[0] = !paused[0];
                System.out.println(paused[0] ? "PAUSED" : "UNPAUSED");
            }else if(event.getCode() == KeyCode.F1){
                ruleSet = CellularAutomataRuleSet.GAME_OF_LIVE;
                System.out.println("Selected: "+ruleSet);
            }else if(event.getCode() == KeyCode.F2){
                ruleSet = CellularAutomataRuleSet.HIGH_LIFE;
                System.out.println("Selected: "+ruleSet);
            }else if(event.getCode() == KeyCode.F3){
                ruleSet = CellularAutomataRuleSet.SEEDS;
                System.out.println("Selected: "+ruleSet);
            }else if (event.getCode().isDigitKey()) {
                drawRuleSetRadius = Character.getNumericValue(event.getText().charAt(0));
                System.out.println("drawRuleSetRadius set to: " + drawRuleSetRadius);
            }
        });
    }

    private void startAnimationTimer(GraphicsContext gc, boolean[] paused) {
        final long[] lastUpdate = {0};
        final long updateInterval = 500_000_000;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused[0] && now - lastUpdate[0] > updateInterval) {
                    updateWorld();
                    drawGrid(gc);
                    lastUpdate[0] = now;
                }
            }
        };

        timer.start();
    }


    private void updateWorld(){
        BooleanWorld oldWorld = booleanWorld.copy();
        for(RuleSetPosition ruleSetPosition : cellularAutomataWorld){
            Position position = ruleSetPosition.position();
            ruleSetPosition.ruleSet().changeWorld(oldWorld,booleanWorld,position.x(),position.y());
        }
    }

    private void drawGrid(GraphicsContext gc) {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);  // Clear the canvas


        // Then draw individual cells
        for (int x = 0; x < CELL_WIDTH; x++) {
            for (int y = 0; y < CELL_HEIGHT; y++) {
                if (booleanWorld.doesElementExist(x, y)) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(switch (cellularAutomataWorld.getCell(x, y)){
                        case EMPTY -> Color.BLACK;
                        case GAME_OF_LIVE -> Color.BLUE;
                        case HIGH_LIFE -> Color.ORANGE;
                        case SEEDS -> Color.GREEN;
                    });
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

}
