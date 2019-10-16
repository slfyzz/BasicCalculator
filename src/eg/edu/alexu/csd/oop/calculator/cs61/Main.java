package eg.edu.alexu.csd.oop.calculator.cs61;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private double Height, Width;
    private Gui calculatorGui;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Height = 300;
        Width = 400;
        calculatorGui = new Gui();
        Parent root = calculatorGui.intializeCalc(Height, Width);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, Height, Width));
     //   primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}


