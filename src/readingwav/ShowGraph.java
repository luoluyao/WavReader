/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingwav;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Hans
 */
public class ShowGraph extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        ReadingWav read = new ReadingWav();
        int i = 0;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> lineChart = 
                new LineChart<>(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        for (Byte b : read.readWav()) {
            series.getData().add(new XYChart.Data(i, b));
            i++;
        }
        lineChart.getData().add(series);
        root.getChildren().add(lineChart);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
