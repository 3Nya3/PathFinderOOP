package org.launchcode;

import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Maze {
    Player p1 = new Player();
    private char[][] cords = new char[15][15];
    private int[][] cordsRefined = new int[15][15];
    private final Random RANDOM = new Random();
    private int sizeY = 15;
    private int sizeX = 15;
    private int errorY = 0;
    private int errorX = 0;
    private char goal;
    private int numSteps;

    public Maze() {

    }

    public void navigate(int minDistance) {
        boolean found = false;
        //System.out.println("Minimum distance = " + minDistance);
        //System.out.println("Player at X " + p1.getPosX() + "   Y " + p1.getPosY());
        int marked = 0;
        if (getCordsRefined((p1.getPosY() - 1), p1.getPosX()) == minDistance) {
            //System.out.println("Marked Up Compared " + minDistance + " with " + getCordsRefined((p1.getPosY() - 1), p1.getPosX()));
            setCords((p1.getPosY() - 1), p1.getPosX(), '●');
            p1.setPosY(p1.getPosY() - 1);
            marked++;
        }
        if (marked == 0 && getCordsRefined(p1.getPosY(), (p1.getPosX() + 1)) == minDistance) {
            //System.out.println("Marked Right, Compared " + minDistance + " with " + getCordsRefined(p1.getPosY(), (p1.getPosX() + 1)));
            setCords(p1.getPosY(), (p1.getPosX() + 1), '●');
            p1.setPosX(p1.getPosX() + 1);
            marked++;
        }
        if (marked == 0 && getCordsRefined((p1.getPosY() + 1), p1.getPosX()) == minDistance) {
            //System.out.println("Marked Down, Compared " + minDistance + " with " + getCordsRefined((p1.getPosY() + 1), p1.getPosX()));
            setCords((p1.getPosY() + 1), p1.getPosX(), '●');
            p1.setPosY(p1.getPosY() + 1);
            marked++;
        }
        if (marked == 0 && getCordsRefined(p1.getPosY(), (p1.getPosX() - 1)) == minDistance) {
            //System.out.println("Marked Left, Compared " + minDistance + " with " + getCordsRefined(p1.getPosY(), (p1.getPosX() - 1)));
            setCords(p1.getPosY(), (p1.getPosX() - 1), '●');
            p1.setPosX(p1.getPosX() - 1);
        }
        if (getCordsRefined(p1.getPosY(), p1.getPosX()) == 1) found = true;

        if (minDistance < -1) {
            //System.out.println("Something bad happened");
            found = true;
        }

        if (found) return;
        navigate(--minDistance);
    }

    public int random(int min, int max) {
        return RANDOM.nextInt(max) + min;
    }

    public void readFile(String location, int value) throws IOException {
        FileReader inputMaze = new FileReader(location);

        int i, x = 0, y = 0;
        while ((i = inputMaze.read()) != -1) {
            if (i != ' ' && x < 12) {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if (value == 1) {
                    if ((char) i == '.') setCordsRefined(y, x, 0);
                } else if (value == 2) if ((char) i == '.' || (char) i == 'C' || (char) i == 'X') setCordsRefined(y, x, 0);
                    x += 1;
            } else if (i != ' ') {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if (value == 1) {
                    if ((char) i == '.') setCordsRefined(y, x, 0);
                } else if (value == 2) if ((char) i == '.' || (char) i == 'C' || (char) i == 'X') setCordsRefined(y, x, 0);
                x += 1;
                x = 0;
                y += 1;
            }
        }
    }

    public void resetMaze() {
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if (getCords(y, x) != 'B') setCordsRefined(y, x, 0);
                if (getCords(y, x) != 'B' && getCords(y, x) != 'X' && getCords(y, x) != 'C') setCords(y, x, '.');
            }
        }
    }

    public void print(int value) {
        switch (value) {
            case 1:
                for (int y = 0; y < getSizeY(); y++) {
                    for (int x = 0; x < getSizeX(); x++) {
                        System.out.print(getCords(y, x));
                    }
                    System.out.println();
                }
                System.out.println("\n");
                break;
            case 2:
                for (int y = 0; y < getSizeY(); y++) {
                    for (int x = 0; x < getSizeX(); x++) {
                        System.out.print(getCordsRefined(y, x) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("\n");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    public void flood() {
        //System.out.println("Flooding");
        int initialX = p1.getPosX();
        int initialY = p1.getPosY();
        System.out.println("Initial position at: " + initialX + "   " + initialY);
        int distance = 1;
        boolean changable = false;
        setCordsRefined(p1.getPosY(), p1.getPosX(), 1);
        boolean filled = false;
        while (!filled) {
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == distance) {
                        System.out.println("Found value to be changed at X " + x + "   Y " + y);
                        //if(!changable)
                            distance++;
                        if (isValid("up", 2, y, x)) {
                            setCordsRefined(y - 1, x, distance);
                            System.out.println("Changed UP");
                            //changable = true;
                        }
                        if (isValid("right", 2, y, x)) {
                            setCordsRefined(y, x + 1, distance);
                            System.out.println("Changed RIGHT");
                            //changable = true;
                        }
                        if (isValid("down", 2, y, x)) {
                            setCordsRefined(y + 1, x, distance);
                            System.out.println("Changed DOWN");
                            //changable = true;
                        }
                        if (isValid("left", 2, y, x)) {
                            setCordsRefined(y, x - 1, distance);
                            System.out.println("Changed LEFT");
                            //changable = true;
                        }
                    }
                }
            }
            if (distance > 100) {
                System.out.println("IT took too many steps, is this wrong? If not change range.");
                filled = true;
            }
            //print(2);
            int temp = 0;
            System.out.println("Distance of " + distance);
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == 0) {
                        temp++;
                        //System.out.println("Maze is NOT Filled");
                        y = 90;
                        x = 90;

                    }
                }
            }
            if (temp == 0) filled = true;
        }
        setCords(initialY, initialX, 'S');
        //return true;
        //System.out.println("Hi initial values are " + initialX + "  " + initialY);
    }

    public char getCords(int y, int x) {
        return cords[y][x];
    }

    public void setCords(int y, int x, char value) {
        this.cords[y][x] = value;
    }

    public int getCordsRefined(int y, int x) {
        return cordsRefined[y][x];
    }

    public void setCordsRefined(int y, int x, int value) {
        this.cordsRefined[y][x] = value;
    }

    public void setGoal(char goal) {
        this.goal = goal;
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if (getCords(y, x) == getGoal()) {
                    //System.out.println("Player at X " + getPosX() + "   Y " + getPosY());
                    //System.out.println("Goal X " + x + " Goal Y " + y);
                    setPosY(y);
                    setPosX(x);
                }
            }
        }
    }

    public char getGoal() {
        return goal;
    }

    public boolean isValid(String direction, int value, int y, int x) {
        //System.out.println("Checking " + direction);
        if (y >= getSizeY() || x >= getSizeX()) return false;
        if (value == 1) {
            switch (direction) {
                case "up":
                    //System.out.println("Checking position at " + p1.getPosY() + "   " + p1.getPosX());
                    return getCords(y - 1, x) != 'B';
                case "right":
                    //System.out.println("Checking position at " + p1.getPosY() + "   " + p1.getPosX());
                    return getCords(y, x + 1) != 'B';
                case "down":
                    //System.out.println("Checking position at " + p1.getPosY() + "   " + p1.getPosX());
                    return getCords(y + 1, x) != 'B';
                case "left":
                    //System.out.println("Checking position at " + p1.getPosY() + "   " + p1.getPosX());
                    return getCords(y, x - 1) != 'B';
                case "center":
                    //System.out.println("Checking position at " + p1.getPosY() + "   " + p1.getPosX());
                    return getCords(y, x) != 'B';
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
        } else if (value == 2) {
            print(2);
            switch (direction) {
                case "up":
                    //System.out.println("Checking position at Y " + y + "  X " + x);
                    return getCordsRefined(y - 1, x) == 0;
                case "right":
                    //System.out.println("Checking position at Y " + y + "  X " + x);
                    return getCordsRefined(y, x + 1) == 0;
                case "down":
                    //System.out.println("Checking position at Y " + y + "  X " + x);
                    return getCordsRefined(y + 1, x) == 0;
                case "left":
                    //System.out.println("Checking position at Y " + y + "  X " + x);
                    return getCordsRefined(y, x - 1) == 0;
                case "center":
                    //System.out.println("Checking position at Y " + y + "  X " + x);
                    return getCordsRefined(y, x) == 0;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
        }
        return false;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public int getMinDistance() {
        //System.out.println("Player at X " + p1.getPosX() + "   Y " + p1.getPosY());
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if (getCords(y, x) == getGoal()) {
                    //System.out.println("Player at X " + p1.getPosX() + "   Y " + p1.getPosY());
                    setNumSteps(getCordsRefined(y, x) - 1);
                    return getCordsRefined(y, x) - 1;
                }
            }
        }
        return 0;
    }

    public int getPosY() {
        return p1.getPosY();
    }

    public void setPosY(int value) {
        p1.setPosY(value);
    }

    public void setPosX(int value) {
        p1.setPosX(value);
    }

    public int getPosX() {
        return p1.getPosX();
    }

    public void setPos() {
        Scanner input = new Scanner(System.in);
        System.out.println("Player at X: " + getPosX() + " and Y: " + getPosY());
        try {
            System.out.println("Please Enter X Position of the Player");
            int x = input.nextInt();
            System.out.println("Please Enter Y Position of the Player");
            int y = input.nextInt();
            if (getCords(y, x) == 'B' || getCordsRefined(y, x) == -1 || getCordsRefined(y, x) == 'C' || getCords(y, x) == 'X') {
                System.out.println("Invalid Location, please try again");
                setPos();
            } else if (getCords(y, x) != 'B') {
                System.out.println("Valid Location at X " + p1.getPosX() + "   Y " + p1.getPosY());
                setPosX(x);
                setPosY(y);
            }
        } catch (Exception e) {
            System.out.println("Invalid Location, please try a number");
            setPos();
        }
    }

    public int checkMaze() {
        int isolated = 0, hasGoal = 0, hasExit = 0;
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if (getCords(y, x) != 'B') {
                    if (getCords(y, x) == 'C') hasGoal = 1;
                    if (getCords(y, x) == 'X') hasExit = 1;
                    isolated = 0;
                    //System.out.println("Checking X " + x + "   Y " + y);
                    if (!isValid("up", 1, y, x)) isolated++;
                    if (!isValid("right", 1, y, x)) isolated++;
                    if (!isValid("down", 1, y, x)) isolated++;
                    if (!isValid("left", 1, y, x)) isolated++;
                    //System.out.println("Isolation of " + isolated);
                    if (isolated == 4) {
                        setErrorX(x);
                        setErrorY(y);
                        return 1;
                    }
                }
            }
        }
        if (hasExit == 0 && hasGoal == 0) return 4;
        if (hasGoal == 0) return 2;
        if (hasExit == 0) return 3;
        return 5;
    }

    public int getErrorY() {
        return errorY;
    }

    public void setErrorY(int errorY) {
        this.errorY = errorY;
    }

    public int getErrorX() {
        return errorX;
    }

    public void setErrorX(int errorX) {
        this.errorX = errorX;
    }
}
