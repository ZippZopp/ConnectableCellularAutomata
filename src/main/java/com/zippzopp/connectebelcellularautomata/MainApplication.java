package com.zippzopp.connectebelcellularautomata;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private final int width = 800; // Width of the window
    private final int height = 600; // Height of the window
    private final int cellSize = 10; // Size of each cell in the grid

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cellular Automata");

        Canvas canvas = new Canvas(width, height);
        drawGrid(canvas.getGraphicsContext2D());

        canvas.setOnMouseClicked(event -> {
            int x = (int) event.getX() / cellSize;
            int y = (int) event.getY() / cellSize;
            System.out.println("Mouse clicked at cell: [" + x + ", " + y + "]");
        });

        canvas.setOnMouseMoved(event -> {
            int x = (int) event.getX() / cellSize;
            int y = (int) event.getY() / cellSize;
            System.out.println("Mouse moved at cell: [" + x + ", " + y + "]");
        });

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawGrid(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
        for (int x = 0; x < width; x += cellSize) {
            for (int y = 0; y < height; y += cellSize) {
                gc.strokeRect(x, y, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}