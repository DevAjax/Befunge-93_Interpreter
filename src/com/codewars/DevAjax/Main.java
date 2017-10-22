package com.codewars.DevAjax;

public class Main {

    public static void main(String[] args) {

        String string = "2>:3g\" \"-!v\\  g30          <\n" +
                " |!`\"&\":+1_:.:03p>03g+:\"&\"`|\n" +
                " @               ^  p3\\\" \":<\n" +
                "2 2345678901234567890123456789012345678";
        BefungeInterpreter bf = new BefungeInterpreter();
        System.out.println(bf.interpret(string));
    }

}
