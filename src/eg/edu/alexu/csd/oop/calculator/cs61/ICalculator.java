package eg.edu.alexu.csd.oop.calculator.cs61;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICalculator implements Calculator{

    private List<String> history;
    private int currentExp;
    private int MAXSIZE;
    private String file = "history.txt";

    ICalculator()
    {
        this.history = new ArrayList<>();
        this.MAXSIZE = 5;
        this.currentExp = -1;
    }

    @Override
    public void input(String s) {

        //add the new expression to the history list
        history.add(s);

        if (history.size() > MAXSIZE)
        {
            //remove the OLDEST expression
            history.remove(0);
        }
        currentExp = history.size() - 1;
    }

    @Override
    public String getResult() {
        String pattern = "([\\-]?[0-9]+[.]?[0-9]*)[\\s]?([\\-|\\+|\\*|\\/])[\\s]*([\\-]?[0-9]+[.]?[0-9]*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(current());
        if (m.find() && (m.group(1).length() + m.group(2).length() + m.group(3).length() == current().length()))
        {
            if (m.group(2).equals("+"))
            {
                return String.valueOf(this.add(Double.valueOf(m.group(1)), Double.valueOf(m.group(3))));
            }
            else if (m.group(2).equals("-"))
            {
                return String.valueOf(this.subtract(Double.valueOf(m.group(1)), Double.valueOf(m.group(3))));
            }
            else if (m.group(2).equals("*"))
            {
                return String.valueOf(this.multiply(Double.valueOf(m.group(1)), Double.valueOf(m.group(3))));

            }
            else if (m.group(2).equals("/"))
            {
                try {
                return String.valueOf(this.division(Double.valueOf(m.group(1)), Double.valueOf(m.group(3))));}
                catch (Exception e)
                {
                    history.remove(currentExp);
                    currentExp--;
                    throw new NumberFormatException("DIVISON BY ZERO !!!");
                }

            }
        }
        else
        {
            history.remove(currentExp);
            currentExp--;
            throw new NullPointerException("Wrong Format");
        }
        return null;
    }

    @Override
    public String current() {
        if (history.size() == 0)
        {
            return null;
        }
        return history.get(this.currentExp);
    }

    @Override
    public String prev() {
        if (this.currentExp > 0)
        {
            this.currentExp--;
            return history.get(this.currentExp);
        }
        else
        {
            //throw new NullPointerException();
            return null;
        }


    }

    @Override
    public String next() {
        if (this.currentExp < this.history.size() - 1)
        {
            this.currentExp++;
            return history.get(this.currentExp);
        }
        else
        {
            //throw new NullPointerException();
            return null;
        }
    }

    @Override
    public void save() {
        try {
            PrintWriter ptr = new PrintWriter(file, "UTF-8");
            ptr.println(history.size());
            ptr.println(this.currentExp);
            for (int i = 0; i < history.size(); i++)
            {
                ptr.println(history.get(i));
            }
            ptr.close();
        }
        catch (Exception e)
        {
            throw new NullPointerException("file isnt found");
        }
    }

    @Override
    public void load() {
        try {
            Scanner ptr = new Scanner(new File(file));
            int size = Integer.parseInt(ptr.nextLine());
            this.currentExp = Integer.parseInt(ptr.nextLine());
            history.clear();
            for (int i = 0; i < size; i++)
            {
                history.add(ptr.nextLine());
            }
            ptr.close();
        }
        catch (Exception e)
        {
            throw new NullPointerException("file isnt found");
        }

    }
    private double add(double a, double b) {
        return a + b;
    }

    private double multiply(double a, double b) {
        return a*b;
    }

    private double subtract(double a, double b) {
        return a - b;
    }

    private double division (double a, double b) throws NumberFormatException{
        if (b == 0)
            throw new NumberFormatException("DIVISON BY ZERO !!!");
        return a / b;
    }
}
