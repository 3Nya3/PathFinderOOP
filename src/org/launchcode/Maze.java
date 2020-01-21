package org.launchcode;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//****************************************************************************************
//<Class>
//This class uses the Player class to determine the shortest route
//<List of Identifiers>
//let cords = a two dimensional array which is used to store characters of the maze <type char[][]>
//let cordsRefined = a two dimensional array which is use to store integers converted from character array <type int[][]>
//let sizeY = an integer variable use to store the y coordinate size of the maze <type int>
//let sizeX = an integer variable use to store the x coordinate size of the maze <type int>
//let errorY = an integer variable used to store the y coordinate of the found error in the maze <type int>
//let errorX = an integer variable used to store the x coordinate of the found error in the maze <type int>
//let goal = a character variable used to store the value of the designation to search for <type char>
//let numSteps = a integer variable used to store the number of steps took to reach the destination <type int>
//****************************************************************************************
public class Maze {
    Player p1 = new Player();
    private char[][] cords = new char[15][15];
    private int[][] cordsRefined = new int[15][15];
    private int sizeY = 15;
    private int sizeX = 15;
    private int errorY = 0;
    private int errorX = 0;
    private char goal;
    private int numSteps;

    /**Default constructor method which instantiates a new Maze.
     */
    public Maze() {

    } //end of Maze (Default constructor)

    /**navigate method:
     * A recursive method which uses minDistance to navigate to the start from the destination using the two dimensional cordsRefined array
     * <p>
     * By checking each direction and their decrements, this method can plot out the shortest path
     * </p>
     *
     * List of Local Variables
     * found = a boolean variable used to track if the destination is found <type>boolean</type>>
     * marked = a integer variable to track if one of the directions is already marked <type>int</type>
     *
     * @param minDistance the minimum distance used to travel from the destination to the start <type>int</type>
     */
    public void navigate(int minDistance) {
        boolean found = false;
        int marked = 0;
        if (isValid("up", 1, p1.getPosY(), p1.getPosX()) && getCordsRefined((p1.getPosY() - 1), p1.getPosX()) == minDistance) {
            setCords((p1.getPosY() - 1), p1.getPosX(), '●');
            p1.setPosY(p1.getPosY() - 1);
            marked++;
        }
        if (isValid("right", 1, p1.getPosY(), p1.getPosX()) && marked == 0 && getCordsRefined(p1.getPosY(), (p1.getPosX() + 1)) == minDistance) {
            setCords(p1.getPosY(), (p1.getPosX() + 1), '●');
            p1.setPosX(p1.getPosX() + 1);
            marked++;
        }
        if (isValid("down", 1, p1.getPosY(), p1.getPosX()) && marked == 0 && getCordsRefined((p1.getPosY() + 1), p1.getPosX()) == minDistance) {
            setCords((p1.getPosY() + 1), p1.getPosX(), '●');
            p1.setPosY(p1.getPosY() + 1);
            marked++;
        }
        if (isValid("left", 1, p1.getPosY(), p1.getPosX()) && marked == 0 && getCordsRefined(p1.getPosY(), (p1.getPosX() - 1)) == minDistance) {
            setCords(p1.getPosY(), (p1.getPosX() - 1), '●');
            p1.setPosX(p1.getPosX() - 1);
        }
        if (getCordsRefined(p1.getPosY(), p1.getPosX()) == 1) found = true;
        if (minDistance < 0) {
            System.out.println("Something bad happened");
            System.exit(1);
            found = true;
        }
        if (found) return;
        navigate(--minDistance);
    } //end of navigate method

    /**readFile method:
     * Reads the maze.txt file and parses the values into both the two dimensional cords array and cordsRefined array
     *
     * List of Local Variables
     * inputMaze = a FileReader object used to read files <type>FileReader</type>>
     * i = an integer variable that represents the ASCII character that the FileReader reads <type>int</type>
     * x = an integer variable that is used to represent the x position of the two dimensional array <type>int</type>
     * y = an integer variable that is used to represent the y position of the two dimensional array <type>int</type>
     *
     * @param location the location of the maze.txt file <type>String</type>>
     * @param value    the type of reading that the readFile method should do <type>int</type>
     * @throws IOException the io exception
     */
    public void readFile(String location, int value) throws IOException {
        FileReader inputMaze = new FileReader(location);

        int i, x = 0, y = 0;
        while ((i = inputMaze.read()) != -1) {
            if (i != ' ' && x < 12) {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if (value == 1) {
                    if ((char) i == '.') setCordsRefined(y, x, 0);
                } else if (value == 2)
                    if ((char) i == '.' || (char) i == 'C' || (char) i == 'X') setCordsRefined(y, x, 0);
                x += 1;
            } else if (i != ' ') {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if (value == 1) {
                    if ((char) i == '.') setCordsRefined(y, x, 0);
                } else if (value == 2)
                    if ((char) i == '.' || (char) i == 'C' || (char) i == 'X') setCordsRefined(y, x, 0);
                x = 0;
                y += 1;
            }
        }
    } //end of readFile method

    /**print method:
     * Prints either the two dimensional cords or cordsRefined array depending on the value imputed
     *
     * @param value the type of maze the print method should print <type>int</type>
     */
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
    } //end of print method

    /**flood method:
     * Floods the two dimensional cordsRefined array with incremental integer values.
     *
     * List of Local Variables
     * distance = an integer variable used to count the steps the method can take each pass through <type>int</type>
     * filled = a boolean variable that is used to determine if the entire cordsRefined array has been filled <type>boolean</type>>
     * temp = an integer variable used to check if there is any empty spots left to fill (0's in the array) <type>int</type>
     */
    public void flood() {
        int distance = 1;
        setCordsRefined(p1.getPosY(), p1.getPosX(), 1);
        boolean filled = false;
        while (!filled) {
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == distance) {
                        if (isValid("up", 2, y, x)) {
                            setCordsRefined(y - 1, x, getCordsRefined(y, x) + 1);
                        }
                        if (isValid("right", 2, y, x)) {
                            setCordsRefined(y, x + 1, getCordsRefined(y, x) + 1);
                        }
                        if (isValid("down", 2, y, x)) {
                            setCordsRefined(y + 1, x, getCordsRefined(y, x) + 1);
                        }
                        if (isValid("left", 2, y, x)) {
                            setCordsRefined(y, x - 1, getCordsRefined(y, x) + 1);
                        }
                    }
                }
            }
            distance++;
            if (distance > 100) {
                System.out.println("It seems like not all paths of your maze is reachable, please change it");
                System.exit(1);
            }
            int temp = 0;
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == 0) {
                        temp++;
                        y = 90;
                        x = 90;

                    }
                }
            }
            if (temp == 0) filled = true;
        }
    }

    /**getCords method:
     * This accessor returns the value of cords given the y and x coordinates
     *
     * @param y the y coordinate <type>int</type>
     * @param x the x coordinate <type>int</type>
     * @return the cords <type>char</type>>
     */
    public char getCords(int y, int x) {
        return cords[y][x];
    } //end of getCords

    /**setCords method:
     * This setter sets the value of a given position af the cords array given the x, y coordinates and character value
     *
     * @param y the y coordinate <type>int</type>
     * @param x the x coordinate <type>int</type>
     * @param value the character the position of the two dimensional array should be set to <type>char</type>
     */
    public void setCords(int y, int x, char value) {
        this.cords[y][x] = value;
    } //end of setCords

    /**getCordsRefined method:
     * This accessor returns the value of cordsRefined given the y and x coordinates
     *
     * @param y the y coordinate <type>int</type>
     * @param x the x coordinate <type>int</type>
     * @return the cords <type>int</type>>
     */
    public int getCordsRefined(int y, int x) {
        return cordsRefined[y][x];
    } //end of getCordsRefined

    /**setCordsRefined method:
     * This setter sets the value of a given position af the cordsRefined array given the x, y coordinates and integer value
     *
     * @param y the y coordinate <type>int</type>
     * @param x the x coordinate <type>int</type>
     * @param value the integer the position of the two dimensional array should be set to <type>int</type>
     */
    public void setCordsRefined(int y, int x, int value) {
        this.cordsRefined[y][x] = value;
    } //end of setCordsRefined

    /**setGoal method:
     * Finds and sets the player position x and y to the goal, uses the character goal to determine the location in the array
     *
     * List of Local Variables
     * distance = an integer variable used to count the steps the method can take each pass through <type>int</type>
     * foundGoal = an integer value used to tell when the while loop should exit, wen goal is found <type>int</type>
     *
     * @param goal the goal character the method uses to search for in the two dimensional array <type>char</type>
     */
    public void setGoal(char goal) {
        this.goal = goal;
        int distance = 1, foundGoal = 0;
        while (foundGoal == 0) {
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCords(y, x) == getGoal() && getCordsRefined(y, x) == distance) {
                        setPosY(y);
                        setPosX(x);
                        setNumSteps(distance);
                        foundGoal = 1;
                        break;
                    }
                }
            }
            distance++;
            if (distance > 100) {
                System.out.println("Too too many steps, is this wrong? If not change range.");
                System.exit(1);
            }
        }
    } //end of setGoal

    /**getGoal method:
     * This accessor returns the character value of goal
     *
     * @return the goal character <type>char</type>
     */
    public char getGoal() {
        return goal;
    } //end of getGoal

    /**isValid method:
     * Given the x and y coordinates of which to search for and the direction to search for, find if that direction in the maze can to traveled to
     * <p>
     * Uses the getters of the two different two dimensional arrays to determine the values on that coordinate
     * </p>
     *
     * @param direction the direction of which to search for <type>String</type>
     * @param value     the type of array the method should be using to search for <type>int</type>
     * @param y         the y coordinate of the position in the array to search for <type>int</type>
     * @param x         the x coordinate of the position in the array to search for <type>int</type>
     * @return the boolean true or false if that position in the array can be traveled to <type>boolean</type>
     */
    public boolean isValid(String direction, int value, int y, int x) {
        try {
            if (value == 1) {
                switch (direction) {
                    case "up":
                        return getCords(y - 1, x) != 'B';
                    case "right":
                        return getCords(y, x + 1) != 'B';
                    case "down":
                        return getCords(y + 1, x) != 'B';
                    case "left":
                        return getCords(y, x - 1) != 'B';
                    case "center":
                        return getCords(y, x) != 'B';
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
            } else if (value == 2) {
                switch (direction) {
                    case "up":
                        return getCordsRefined(y - 1, x) == 0;
                    case "right":
                        return getCordsRefined(y, x + 1) == 0;
                    case "down":
                        return getCordsRefined(y + 1, x) == 0;
                    case "left":
                        return getCordsRefined(y, x - 1) == 0;
                    case "center":
                        return getCordsRefined(y, x) == 0;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
            } else if (value == 3) {
                    switch (direction) {
                        case "up":
                            return getCords(y - 1, x) != '.';
                        case "right":
                            return getCords(y, x + 1) != '.';
                        case "down":
                            return getCords(y + 1, x) != '.';
                        case "left":
                            return getCords(y, x - 1) != '.';
                        case "center":
                            return getCords(y, x) != '.';
                        default:
                            throw new IllegalStateException("Unexpected value: " + direction);
                    }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    } //end of isValid

    /**getSizeY method:
     * This accessor returns the value of sizeY
     *
     * @return the size y of the maze <type>int</type>
     */
    public int getSizeY() {
        return sizeY;
    } //end of getSizeY

    /**setSizeY method:
     * This setter sets the value of sizeY given value of y
     *
     * @param sizeY the size y of the maze <type>int</type>
     */
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    } //end of setSizeY

    /**getSizeX method:
     * This accessor returns the value of sizeX
     *
     * @return the size x of the maze <type>int</type>
     */
    public int getSizeX() {
        return sizeX;
    } //end of setSizeX

    /**setSizeX method:
     * This setter sets the value of sizeX given value of x
     *
     * @param sizeX the size x of the maze <type>int</type>
     */
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    } //end of setSizeX

    /**getNumSteps method:
     * This accessor returns the value of numSteps
     *
     * @return the number of steps <type>int</type>
     */
    public int getNumSteps() {
        return numSteps;
    } //end of getNumSteps

    /**setNumSteps method:
     * This setter sets the value of numSteps given an integer
     *
     * @param numSteps the number of steps <type>int</type>
     */
    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    } //end of setNumSteps

    /**getPosY method:
     * This accessor returns the y position of the player
     * <p>
     *     Accesses the Player class
     * </p>
     *
     * @return the y position of the player <type>int</type>
     */
    public int getPosY() {
        return p1.getPosY();
    } //end of getPosY

    /**setPosY method:
     * This setter sets the y position of the player given an integer
     * <p>
     *     Accesses the Player class
     * </p>
     *
     * @param value the y position of the player <type>int</type>
     */
    public void setPosY(int value) {
        p1.setPosY(value);
    } //end of setPosY

    /**setPosX method:
     * This setter sets the x position of the player given an integer
     * <p>
     *     Accesses the Player class
     * </p>
     *
     * @param value the x position of the player <type>int</type>
     */
    public void setPosX(int value) {
        p1.setPosX(value);
    } //end of setPosX

    /**getPosX method:
     * This accessor returns the x position of the player
     * <p>
     *     Accesses the Player class
     * </p>
     *
     * @return the x position of the player <type>int</type>
     */
    public int getPosX() {
        return p1.getPosX();
    } //end of getPosX

    /**checkAnswer method;
     * Checks the answer of the player, either yes or no
     *
     * List of Local Variables
     * input = an object of Scanner use to fetch the input of the user <type>Scanner</type>
     * x = a Sting variable that stores the user input and is used to check what they answered <type>String</type>
     *
     * @return the boolean true if yes and false if no <type>boolean</type>
     */
    public boolean checkAnswer() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter < y > if you want to continue or < n > to end the program");
        String x = input.next();
        while (true) {
            try {
                if (x.equals("y") || x.equals("n")) {
                    return x.equals("y");
                } else {
                    System.out.println("Not < y > or < n >. Please try again.");
                    x = input.next();
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again");
                checkAnswer();
            }
        }
    } //end of checkAnswer

    /**setPos method:
     * Sets the position of the player given the inputted x and y position of the player
     *
     * List of Local Variables
     * input = an object of Scanner use to fetch the input of the user <type>Scanner</type>
     * x = an integer variable that stores the x value of the position the user enters <type>int</type>
     * y = an integer variable that stores the x value of the position the user enters <type>int</type>
     */
    public void setPos() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter X Position of the Player");
        int x = input.nextInt();
        System.out.println("Please Enter Y Position of the Player");
        int y = input.nextInt();
        while (true) {
            try {
                if (getCords(y, x) == 'B' || getCordsRefined(y, x) == -1 || getCordsRefined(y, x) == 'C' || getCords(y, x) == 'X') {
                    System.out.println("Invalid Location, please try again");
                    System.out.println("Please Enter X Position of the Player");
                    x = input.nextInt();
                    System.out.println("Please Enter Y Position of the Player");
                    y = input.nextInt();
                } else if (getCords(y, x) != 'B') {
                    setPosX(x);
                    setPosY(y);
                    System.out.println("Valid Location at X " + p1.getPosX() + "   Y " + p1.getPosY());
                    break;
                } else System.out.println("Invalid Location, please try again");
            } catch (Exception e) {
                setPos();
            }
        }
    } //end of setPos

    /**checkMaze method:
     * Checks the maze before the path finding runs to cee if there are any questionable walls or conditions that might break the program
     * <p>
     *     If error is found, prints out error found and exits the program
     * </p>
     *
     * List of Local Variables
     * condition = an integer variable that is used to determine what error the maze has <type>int</type>
     * isolated = an integer variable that is used to determine if the maze has possible unreachable paths <type>int</type>
     * hasGoal = an integer variable used to count the amount of Goals the maze has and if it has none more than one <type>int</type>
     * hasExit = an integer variable used to count the amount of Exits the maze has and if it has none more than one <type>int</type>
     */
    public void checkMaze() {
        int condition = 0, isolated, hasGoal = 0, hasExit = 0;
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if(getCords(y, x) == 'B'){
                    isolated = 0;
                    if (!isValid("up", 3, y, x)) isolated++;
                    if (!isValid("right", 3, y, x)) isolated++;
                    if (!isValid("down", 3, y, x)) isolated++;
                    if (!isValid("left", 3, y, x)) isolated++;
                    if (isolated == 4) {
                        setErrorX(x);
                        setErrorY(y);
                        condition = 7;
                        break;
                    }
                } else if (getCords(y, x) != 'B') {
                    if (getCords(y, x) == 'C') hasGoal++;
                    if (getCords(y, x) == 'X') hasExit++;
                    isolated = 0;
                    if (!isValid("up", 1, y, x)) isolated++;
                    if (!isValid("right", 1, y, x)) isolated++;
                    if (!isValid("down", 1, y, x)) isolated++;
                    if (!isValid("left", 1, y, x)) isolated++;
                    if (isolated == 4) {
                        setErrorX(x);
                        setErrorY(y);
                        condition = 1;
                        break;
                    }
                }
            }
        }
        if(condition == 1 || condition == 7) {}
        else if (hasExit > 1 || hasGoal > 1) condition = 5;
        else if (hasExit == 0 && hasGoal == 0) condition = 4;
        else if (hasGoal == 0) condition = 2;
        else if (hasExit == 0) condition = 3;
        else condition = 6;
        switch (condition) {
            case 1:
                System.out.println("Hey... Something is up with your maze, and not all paths are reachable... Better go fix it.");
                System.out.println("Found first unreachable path at X: " + getErrorX() + " and Y: " + getErrorY());
                System.exit(1);
            case 2:
                System.out.println("Wait... I don't think there a Goal in your maze... But at least you have an Exit... Better go fix it.");
                System.exit(1);
            case 3:
                System.out.println("Hold up... You have a Goal but not an Exit... Better go fix it.");
                System.exit(1);
            case 4:
                System.out.println("What do you think would happen if you don't have a Goal or an Exit??");
            case 5:
                System.out.println("It seems like your maze has more than 1 Goal or Exit. \nYou can continue and the program will try to navigate to the shortest destination.");
                if (checkAnswer()) {
                    System.out.println("I will now proceed with path finding");
                    break;
                } else {
                    System.out.println("Ok, ending program...");
                    System.exit(1);
                }
            case 6:
                System.out.println("Maze check is clean, off you go!");
                break;
            case 7:
                System.out.println("Wait, either there is an unreachable path or a wall that's sticking out, And this program is only for standard mazes without diagonal paths.");
                System.out.println("Found first questionable wall at X: " + getErrorX() + " and Y: " + getErrorY());
                System.out.println("\nIf you are certain that the maze is passable, you can continue the program");
                if (checkAnswer()) {
                    System.out.println("I will now proceed with path finding");
                    break;
                } else {
                    System.out.println("Ok, ending program...");
                    System.exit(1);
                }
        }
    } //end of checkMaze

    /**getErrorY method:
     * This accessor returns the y position of the error
     *
     * @return the y position of the error <type>int</type>
     */
    public int getErrorY() {
        return errorY;
    } //end of getErrorY

    /**setErrorY method:
     * This setter sets the the y position of the error given the y value
     *
     * @param errorY the y position of the error <type>int</type>
     */
    public void setErrorY(int errorY) {
        this.errorY = errorY;
    } //end of setErrorY

    /**getErrorX method:
     * This accessor returns the x position of the error
     *
     * @return the x position of the error <type>int</type>
     */
    public int getErrorX() {
        return errorX;
    } //end of getErrorX

    /**setErrorX method:
     * This setter sets the the x position of the error given the x value
     *
     * @param errorX the x position of the error <type>int</type>
     */
    public void setErrorX(int errorX) {
        this.errorX = errorX;
    } //end of setErrorX
}
