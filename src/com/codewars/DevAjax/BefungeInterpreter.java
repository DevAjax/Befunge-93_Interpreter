package com.codewars.DevAjax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BefungeInterpreter {
    char[][] instructonsArray = new char[25][80];
    char direction = '>';
    int pointerX = 0;
    int pointerY = 0;
    StringBuilder output = new StringBuilder();
    List<Integer> stack = new ArrayList<Integer>();


    public String interpret(String code) {
        setInstructonsArray(code);
        while (instructonsArray[pointerY][pointerX] != '@') {
            this.instruction(pointerY, pointerX);
        }
        return output.toString();
    }

    public void setInstructonsArray(String code) {
        String lines[] = code.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                instructonsArray[i][j] = lines[i].charAt(j);
            }
        }
    }

    public void instruction(int pointerY, int pointerX) {
        if (Character.isDigit(instructonsArray[pointerY][pointerX])) {
            stack.add(Character.getNumericValue(instructonsArray[pointerY][pointerX]));
            step();
            return;
        }
        int tmp;
        double tmpdouble;
        int y;
        int x;
        int v;
        switch (instructonsArray[pointerY][pointerX]) {
            case '+':
                tmp = stack.get(stack.size() - 1) + stack.get(stack.size() - 2);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(tmp);
                step();
                break;
            case '-':
                tmp = stack.get(stack.size() - 2) - stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(tmp);
                step();
                break;
            case '*':
                tmp = stack.get(stack.size() - 1) * stack.get(stack.size() - 2);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(tmp);
                step();
                break;
            case '/':
                if (stack.get(stack.size() - 1) == 0) {
                    stack.add(0);
                    break;
                }
                tmpdouble = (double) stack.get(stack.size() - 2) / (double) stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add((int) tmpdouble);
                step();
                break;
            case '%':
                if (stack.get(stack.size() - 1) == 0) {
                    stack.add(0);
                    step();
                    break;
                }
                tmp = stack.get(stack.size() - 2) % stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(tmp);
                step();
                break;
            case '!':
                if (stack.get(stack.size() - 1) == 0) {
                    stack.remove(stack.size() - 1);
                    stack.add(1);
                    step();
                } else {
                    stack.remove(stack.size() - 1);
                    stack.add(0);
                    step();
                }
                break;
            case '`':
                if (stack.get(stack.size() - 2) > stack.get(stack.size() - 1)) {
                    stack.add(1);
                    step();
                } else {
                    stack.add(0);
                    step();
                }
                stack.remove(stack.size() - 2);
                stack.remove(stack.size() - 2);
                break;
            case '>':
                this.direction = '>';
                step();
                break;
            case '<':
                this.direction = '<';
                step();
                break;
            case '^':
                this.direction = '^';
                step();
                break;
            case 'v':
                this.direction = 'v';
                step();
                break;
            case '?':
                this.randomDirectory();
                step();
                break;
            case '_':
                if (stack.get(stack.size() - 1) == 0 || stack.size() == 0) {
                    this.direction = '>';
                    step();
                } else {
                    this.direction = '<';
                    step();
                }
                stack.remove(stack.size() - 1);
                break;
            case '|':
                if (stack.get(stack.size() - 1) == 0 || stack.size() == 0) {
                    this.direction = 'v';
                    step();
                } else {
                    this.direction = '^';
                    step();
                }
                stack.remove(stack.size() - 1);
                break;
            case '\"':
                stringMode();
                break;
            case ':':
                if (stack.size() == 0) {
                    stack.add(0);
                    step();
                    break;
                } else {
                    stack.add(stack.get(stack.size() - 1));
                    step();
                    break;
                }
            case '\\':
                if (stack.size() == 1) {
                    stack.add(stack.get(0));
                    stack.set(0, 0);
                }
                tmp = stack.get(stack.size() - 1);
                stack.set(stack.size() - 1, stack.get(stack.size() - 2));
                stack.set(stack.size() - 2, tmp);
                step();

                break;

            case '$':
                stack.remove(stack.size() - 1);
                step();
                break;
            case '.':
                try {
                    output.append(stack.get(stack.size() - 1));
                    stack.remove(stack.size() - 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                } finally {
                    step();
                }
                break;
            case ',':
                output.append(Character.toString((char) stack.get(stack.size() - 1).intValue()));
                stack.remove(stack.size() - 1);
                step();
                break;
            case '#':
                step();
                step();
                break;
            case 'p':
                y = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                x = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                v = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                instructonsArray[y][x] = (char) v;
                step();
                break;
            case 'g':
                y = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                x = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add((int) instructonsArray[y][x]);
                step();
                break;
            default:
                step();
                break;
        }
    }

    public void stringMode() {
        step();
        while (instructonsArray[pointerY][pointerX] != '\"') {
            stack.add((int) instructonsArray[pointerY][pointerX]);
            step();
        }
        step();
    }

    public void step() {
        switch (direction) {
            case '<':
                this.moveLeft();
                break;
            case '>':
                this.moveRight();
                break;
            case 'v':
                this.moveDown();
                break;
            case '^':
                this.moveUp();
                break;
        }
    }

    public void moveLeft() {
        if (this.pointerX == 0) {
            pointerX = 79;
            return;
        }
        this.pointerX--;
    }

    public void moveRight() {
        if (this.pointerX == 79) {
            pointerX = 0;
            return;
        }
        this.pointerX++;
    }

    public void moveUp() {
        if (this.pointerY == 0) {
            pointerY = 24;
            return;
        }
        this.pointerY--;
    }

    public void moveDown() {
        if (this.pointerY == 24) {
            pointerY = 0;
            return;
        }
        this.pointerY++;
    }

    public void randomDirectory() {
        char[] dirs = {'^', 'v', '>', '<'};
        Random generator = new Random();
        this.direction = dirs[generator.nextInt(3) + 0];
    }
}