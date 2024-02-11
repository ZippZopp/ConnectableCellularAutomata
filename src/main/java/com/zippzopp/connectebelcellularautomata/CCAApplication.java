package com.zippzopp.connectebelcellularautomata;

import com.zippzopp.connectebelcellularautomata.UtilityClasses.Position;
import com.zippzopp.connectebelcellularautomata.UtilityClasses.RuleSetPosition;
import com.zippzopp.connectebelcellularautomata.Worlds.BooleanWorld;
import com.zippzopp.connectebelcellularautomata.Worlds.CellularAutomataField;
import com.zippzopp.connectebelcellularautomata.Worlds.CellularAutomataWorld;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CCAApplication extends Application {
    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 60;
    private static final int CELL_SIZE = 10;

    private static final int WINDOW_WIDTH = CELL_WIDTH*CELL_SIZE;
    private static final int WINDOW_HEIGHT = CELL_HEIGHT*CELL_SIZE;


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

        setUpMouseClickedHandler(canvas, gc, paused);
        setUpKeyPressedHandler(canvas, paused);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        startAnimationTimer(gc, paused);
    }

    private void setUpMouseClickedHandler(Canvas canvas, GraphicsContext gc, boolean[] paused) {
        canvas.setOnMouseClicked(event -> {
            int x = (int) event.getX() / CELL_SIZE;
            int y = (int) event.getY() / CELL_SIZE;

            if (event.getButton() == MouseButton.PRIMARY && paused[0]) {
                handlePrimaryClick(x, y, gc);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                handleSecondaryClick(x, y, gc);
            }
        });
    }

    private void handlePrimaryClick(int x, int y, GraphicsContext gc) {
        if (!cellularAutomataWorld.getCell(x, y).isEmpty()) {
            booleanWorld.setTrue(x, y);
        }
        drawGrid(gc);
    }

    private void handleSecondaryClick(int x, int y, GraphicsContext gc) {
        addNewField(x, y, gc,ruleSet);
    }

    private void addNewField(int x, int y, GraphicsContext gc, CellularAutomataRuleSet ruleSet) {
        CellularAutomataField newField = new CellularAutomataField(x, y, 10, 10, ruleSet);
        cellularAutomataWorld.addField(newField);
        drawGrid(gc); // Redraw to show the new field
    }

    private void setUpKeyPressedHandler(Canvas canvas, boolean[] paused) {
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) { // Use SPACE bar to toggle pause
                paused[0] = !paused[0];
            }else if(event.getCode() == KeyCode.DIGIT1){
                ruleSet = CellularAutomataRuleSet.GAME_OF_LIVE;
                System.out.println("Selected: "+ruleSet);
            }else if(event.getCode() == KeyCode.DIGIT2){
                ruleSet = CellularAutomataRuleSet.HIGH_LIFE;
                System.out.println("Selected: "+ruleSet);
            }else if(event.getCode() == KeyCode.DIGIT3){
                ruleSet = CellularAutomataRuleSet.SEEDS;
                System.out.println("Selected: "+ruleSet);
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
        cellularAutomataWorld.update();
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
                    gc.setFill(Color.BLACK);
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }


        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        for (CellularAutomataField field : cellularAutomataWorld.getFields()) {
            gc.strokeRect(field.getX() * CELL_SIZE, field.getY() * CELL_SIZE, field.getWidth() * CELL_SIZE, field.getHeight() * CELL_SIZE);
        }
    }

}
