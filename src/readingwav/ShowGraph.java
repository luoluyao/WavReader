/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingwav;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    public void start(Stage primaryStage) throws IOException {

        StackPane root = new StackPane();
        ReadingWav read = new ReadingWav();
        int[] wavRead = read.readWav();
//        int i = 0;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();

//        for (int i = 45; i < wavRead.length; i += 8) {
        int j = wavRead.length;
        for (int i : wavRead) {

            series.getData().add(new XYChart.Data((ReadingWav.duration / wavRead.length) * j, i));
            j--;
        }

        lineChart.getData()
                .add(series);
        root.getChildren()
                .add(lineChart);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle(
                "Hello World!");
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
