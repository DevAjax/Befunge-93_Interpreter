package com.codewars.DevAjax;

public class Main {

    public static void main(String[] args) {
        System.out.println(">987v>.v\nv456<  :\n>321 ^ _@");
        String lines[] = ">987v>.v\nv456<  :\n>321 ^ _@".split("\\r?\\n");
        System.out.println();
        System.out.println(lines[0]);
        System.out.println(lines[1]);
        System.out.println(lines[2]);
        System.out.println();
        BefungeInterpreter bf = new BefungeInterpreter();
        bf.setInstructonsArray(">987v>.v\nv456<  :\n>321 ^ _@");
        System.out.println();
        System.out.println(   new BefungeInterpreter().interpret(">987v>.v\nv456<  :\n>321 ^ _@"));
        System.out.println(new String("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@").length());
      //  System.out.println(   new BefungeInterpreter().interpret("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@"));

    }

}
