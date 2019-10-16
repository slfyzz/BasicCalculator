package eg.edu.alexu.csd.oop.calculator.cs61;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Gui {
    private double Height, Width;
    private Label formula;
    private Calculator calc;
    private boolean equaloperator = false;
    private TextField expression;

    public Pane intializeCalc(double height, double width)
    {
        Height = height;
        Width = width;
        calc = new ICalculator();

        GridPane root = new GridPane();

        formula = new Label("");
        expression = new TextField();
        root.setPrefSize(Height,Width);
        formula.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        formula.setPrefHeight(Height / 4);
        formula.setWrapText(true);
        formula.setTextAlignment(TextAlignment.JUSTIFY);
        formula.setStyle("-fx-font: 18 arial;");
        formula.setMaxHeight(Double.MAX_VALUE);

        expression.setPromptText("Enter the Expression...");
        expression.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        expression.setPrefHeight(Height / 4);
        expression.setStyle("-fx-font: 24 arial;");
        expression.setMaxHeight(Double.MAX_VALUE);
        expression.setOnAction(e ->{
            try {
                calc.input(expression.getText());
                formula.setText(String.valueOf(calc.getResult()));
            }
            catch (Exception c)
            {
                formula.setText("Mathematical ERROR");
            }
            equaloperator = true;
        });

        // GridPane.setConstraints(formula, 0, 0);
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(25);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(25);
        ColumnConstraints col3Constraints = new ColumnConstraints();
        col3Constraints.setPercentWidth(25);
        root.getColumnConstraints().addAll(col1Constraints,col2Constraints,col3Constraints);
        root.add(formula,0,1,4,1);
        root.add(expression,0,0,4,1);
        root.setStyle("-fx-background-color: #ffffff");

        for (int i = 0; i < 3; i++)
        {

            for (int j = 0; j < 3; j++)
            {
                makeButton(String.valueOf(i * 3 + j + 1),0.25,1,1,root,j,i + 5,Color.WHITE);
            }
        }
        makeButton("0",0.5,3,1,root,0,8,Color.WHITE);
        makeButton(".",0.25,1,1,root,3,8,Color.valueOf("#9477af"));
        makeButton("*",0.25,1,1,root,3,3,Color.valueOf("#9477af"));
        makeButton("/",0.25,1,1,root,3,4,Color.valueOf("#9477af"));
        makeButton("+",0.25,1,1,root,3,5,Color.valueOf("#9477af"));
        makeButton("-",0.25,1,1,root,3,6,Color.valueOf("#9477af"));
        makeButton("=",0.25,1,1,root,3,7,Color.valueOf("#ffa500"));
        makeButton("Save", 0.25, 1,1,root,0,4,Color.valueOf("#8bd9ef"));
        makeButton("Load",0.25,1,1,root,1,4,Color.valueOf("#8bd9ef"));
        makeButton("<-",0.25,1,1,root,0,3,Color.valueOf("#8bd9ef"));
        makeButton("->",0.25,1,1,root,1,3,Color.valueOf("#8bd9ef"));
        makeButton("DEL",0.25,1,1,root,2,3,Color.valueOf("#8bd9ef"));
        makeButton("ANS",0.25,1,1,root,2,4,Color.valueOf("#8bd9ef"));


        return root;
    }



    private void makeButton(String s, double percent, int colSpan, int rowSpan, GridPane root, int col, int row, Color color)
    {
        Button dot = new Button(s);
        dot.setOnAction(e ->{
            if (s.equals("=")){
                if (calc.current() != null)
                {
                    System.out.println("booom");
                }
                try {
                    calc.input(expression.getText());
                    formula.setText(String.valueOf(calc.getResult()));
                }
                catch (Exception c)
                {
                    if (c.getClass() == NumberFormatException.class)
                        formula.setText("Mathematical ERROR");
                    else
                        formula.setText("INVALID INPUT");
                }
                equaloperator = true;
            }
            else if (s.equals("Save") || s.equals("Load") || s.equals("DEL") || s.equals("ANS") || s.equals("->") || s.equals("<-")) {
                if (s.equals("DEL"))
                {
                    if (!expression.getText().equals("") && !equaloperator)
                        expression.setText(expression.getText().substring(0,expression.getText().length()-1));
                    else if (equaloperator)
                    {
                        formula.setText("");
                        if (!expression.getText().equals(""))
                        {
                            expression.setText(expression.getText().substring(0,expression.getText().length()-1));
                        }
                        equaloperator = false;
                    }
                }
                else if (s.equals("ANS"))
                {
                    if (calc.current()!= null) {
                        expression.setText(calc.getResult());
                    }
                    else
                    {
                        AlertBox("it seems that you haven't entered an expression yet");
                    }
                }
                else if (s.equals("->"))
                {
                    String nextStr = calc.next();
                    if (nextStr != null) {
                        expression.setText(nextStr);
                        formula.setText(calc.getResult());
                        equaloperator = true;
                    }
                    else
                    {
                        AlertBox("there is no next expression");
                    }
                }
                else if (s.equals("<-"))
                {
                    String prevStr = calc.prev();
                    if (prevStr != null) {
                        expression.setText(prevStr);
                        formula.setText(calc.getResult());
                        equaloperator = true;
                    }
                    else
                    {
                        AlertBox("there is no previous expression");
                    }

                }
                else if (s.equals("Save"))
                {
                    try {
                        calc.save();
                    }catch (Exception c)
                    {
                        AlertBox("there is nothing to save");
                    }
                }
                else if (s.equals("Load"))
                {
                    try {
                        calc.load();
                        if (calc.current() != null) {
                            expression.setText(calc.current());
                            formula.setText(calc.getResult());
                        }
                        else
                            throw new Exception("there is no current");
                    } catch (Exception c)
                    {
                        AlertBox("there is nothing to load");
                    }
                }
            }
            else
            {
                if (equaloperator)
                {
                    formula.setText("");
                    expression.setText("");
                    equaloperator = false;
                }
                if (calc.current() != null && (s.equals("+") || s.equals("/")|| s.equals("*")) && expression.getText().equals(""))
                {
                    expression.setText(calc.getResult());
                }
                expression.setText(expression.getText() + s);
            }
        });

        dot.setOnMouseEntered(e->{
            dot.setBackground(new Background(new BackgroundFill(Color.valueOf("#b0b0d8"),new CornerRadii(200), Insets.EMPTY)));
        });

        dot.setOnMouseExited(e -> {
            dot.setBackground(new Background(new BackgroundFill(color, new CornerRadii(200), Insets.EMPTY)));
        });

        root.add(dot, col, row, colSpan, rowSpan);

        dot.setBackground(new Background(new BackgroundFill(color,new CornerRadii(200), Insets.EMPTY)));
        if (s.equals("Save") || s.equals("Load") || s.equals("DEL") || s.equals("ANS"))
        {
            dot.setStyle("-fx-font: 18 arial;");
        }
        else {
            dot.setStyle("-fx-font: 24 arial;");
        }
        dot.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(200), new BorderWidths(2))));
        dot.setPrefWidth(percent * Width + (colSpan-1) * 7);
        dot.setPrefHeight(Height / 5);
        dot.setMaxWidth(Double.MAX_VALUE);
        dot.setMaxHeight(Double.MAX_VALUE);
        dot.setWrapText(true);
        dot.setTextAlignment(TextAlignment.JUSTIFY);

    }
    void AlertBox(String Error)
    {
        Stage window = new Stage();
        window.setTitle("ERROR !!");
        VBox vBox = new VBox();

        Label alert = new Label(Error);
        alert.setStyle("-fx-font: 16 arial;");
        alert.setWrapText(true);

        Button back = new Button("return");
        back.setOnAction(e -> window.close());
        back.setStyle("-fx-font: 16 arial;");

        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(alert, back);

        Scene scene = new Scene(vBox, 250,200);
        window.setScene(scene);
        window.showAndWait();
    }
}
