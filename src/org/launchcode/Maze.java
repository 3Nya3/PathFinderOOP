package org.launchcode;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Maze {
    private char[][] cords = new char[15][15];
    private int[][] cordsRefined = new int[15][15];
    private final Random RANDOM = new Random();
    private int sizeY;
    private int sizeX;
    private char goal;
    private int numSteps;

    public Maze(){
        sizeY = 15;
        sizeX = 15;
        numSteps = 0;
    }

    private void navigate(int minDistance) {
        Player player = new Player();
        boolean found = false;
        System.out.println("Minimum distance = " + minDistance);
        System.out.println("Player at X " + player.getPosX() + "   Y " + player.getPosY());
        int marked = 0;
        if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == minDistance) {
            System.out.println("Marked Up Compared " + minDistance + " with " + getCordsRefined((player.getPosY() - 1), player.getPosX()));
            setCords((player.getPosY() - 1), player.getPosX(), '●');
            player.setPosY(player.getPosY() - 1);
            marked++;
        } /*8else if (getCords((player.getPosY() - 1), player.getPosX()) != '●' && getCordsRefined((player.getPosY() - 1), player.getPosX()) != -1) {
                    System.out.println("Un-Marked Up Compared " + minDistance + " with " + getCordsRefined((player.getPosY() - 1), player.getPosX()));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosY() + 1);
                }*/
        if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() + 1)) == minDistance) {
            System.out.println("Marked Right, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() + 1)));
            setCords(player.getPosY(), (player.getPosX() + 1), '●');
            player.setPosX(player.getPosX() + 1);
            marked++;
        } /*else if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() + 1)) != '●' && getCordsRefined(player.getPosY(), (player.getPosX() + 1)) != -1) {
                    System.out.println("Un-Marked Right, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() + 1)));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosX() - 1);
                    marked++;
                }*/
        if (marked == 0 && getCordsRefined((player.getPosY() + 1), player.getPosX()) == minDistance) {
            System.out.println("Marked Down, Compared " + minDistance + " with " + getCordsRefined((player.getPosY() + 1), player.getPosX()));
            setCords((player.getPosY() + 1), player.getPosX(), '●');
            player.setPosY(player.getPosY() + 1);
            marked++;
        } /*else if (marked == 0 && getCordsRefined((player.getPosY() + 1), player.getPosX()) != '●' && getCordsRefined((player.getPosY() + 1), player.getPosX()) != -1) {
                    System.out.println("Un-Marked Down, Compared " + minDistance + " with " + getCordsRefined((player.getPosY() + 1), player.getPosX()));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosY() - 1);
                    marked++;
                }*/
        if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == minDistance) {
            System.out.println("Marked Left, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() - 1)));
            setCords(player.getPosY(), (player.getPosX() - 1), '●');
            player.setPosX(player.getPosX() - 1);
        } /*else if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() - 1)) != '●' && getCordsRefined(player.getPosY(), (player.getPosX() - 1)) != -1) {
                    System.out.println("Un-Marked Left, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() - 1)));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosX() + 1);
                }*/
        print(1);
        if (getCordsRefined(player.getPosY(), player.getPosX()) == 1) found = true;

        if (minDistance < -1) {
            System.out.println("Something bad happened");
            found = true;
        }

        if (found) return;
        navigate(--minDistance);
    }

    public int random(int min, int max) {
        return RANDOM.nextInt(max) + min;
    }

    public void readFile(String location) throws IOException {
        FileReader inputMaze = new FileReader(location);

        int i, x = 0, y = 0;
        while ((i = inputMaze.read()) != -1) {
            if (i != ' ' && x < 12) {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if ((char) i == '.') setCordsRefined(y, x, 0);
                x += 1;
            } else if (i != ' ') {
                setCords(y, x, (char) i);
                if ((char) i == 'B') setCordsRefined(y, x, -1);
                if ((char) i == '.') setCordsRefined(y, x, 0);
                x = 0;
                y += 1;
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
                break;
            case 2:
                for (int y = 0; y < getSizeY(); y++) {
                    for (int x = 0; x < getSizeX(); x++) {
                        System.out.print(getCordsRefined(y, x) + "\t");
                    }
                    System.out.println();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    public void flood() {
        System.out.println("Flooding");
        Player player = new Player();
        int initialX = player.getPosX();
        int initialY = player.getPosY();
        //System.out.println("Initial position at: " + initialX + "   " + initialY);
        int distance = 1;
        setCordsRefined(player.getPosY(), player.getPosX(), 1);
        boolean filled = false;
        while (!filled) {
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == distance) {
                        //System.out.println("Found value to be changed at X " + x + "   Y " + y);
                        distance++;
                        if (isValid("up", 2, y, x)) setCordsRefined(y - 1, x, distance);
                        if (isValid("right", 2, y, x)) setCordsRefined(y, x + 1, distance);
                        if (isValid("down", 2, y, x)) setCordsRefined(y + 1, x, distance);
                        if (isValid("left", 2, y, x)) setCordsRefined(y, x - 1, distance);
                    }
                }
            }
                /*
                switch (random(1, 4)) {
                    case 1:
                        System.out.println("Check Up");
                        if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == 0) {
                            System.out.println("Is valid moving to " + player.getPosX() + "   Y " + (player.getPosY() - 1));
                            setCordsRefined((player.getPosY() - 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() - 1);
                        } else if (getCordsRefined((player.getPosY() - 1), player.getPosX()) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            System.out.println("Overriding X " + player.getPosX() + "   Y " + (player.getPosY() - 1));
                            setCordsRefined((player.getPosY()), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() - 1);
                        } else if (getCordsRefined((player.getPosY() + 1), player.getPosX()) == distance) {
                            System.out.println("Backtracking...");
                            player.setPosY(player.getPosY() + 1);
                            distance--;
                            System.out.println("Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        } else {
                            System.out.println("Cannot move there, Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        }
                        break;
                    case 2:
                        System.out.println("Check Right");
                        if (getCordsRefined(player.getPosY(), (player.getPosX() + 1)) == 0) {
                            System.out.println("Is valid moving to " + (player.getPosX() + 1) + "   Y " + player.getPosY());
                            setCordsRefined(player.getPosY(), (player.getPosX() + 1), distance++);
                            player.setPosX(player.getPosX() + 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() + 1)) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            System.out.println("Overriding X " + player.getPosX() + "   Y " + player.getPosY());
                            setCordsRefined(player.getPosY(), (player.getPosX() + 1), distance++);
                            player.setPosX(player.getPosX() + 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == distance) {
                            System.out.println("Backtracking...");
                            player.setPosX(player.getPosX() - 1);
                            distance--;
                            System.out.println("Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        } else {
                            System.out.println("Cannot move there, Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        }
                        break;
                    case 3:
                        System.out.println("Check Down");
                        if (getCordsRefined((player.getPosY() + 1), player.getPosX()) == 0) {
                            System.out.println("Is valid moving to " + player.getPosX() + "   Y " + (player.getPosY()+ 1));
                            setCordsRefined((player.getPosY() + 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() + 1);
                        } else if (getCordsRefined((player.getPosY() + 1), player.getPosX()) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            System.out.println("Overriding X " + player.getPosX() + "   Y " + player.getPosY());
                            setCordsRefined((player.getPosY() + 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() + 1);
                        } else if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == distance) {
                            System.out.println("Backtracking...");
                            player.setPosY(player.getPosY() - 1);
                            distance--;
                            System.out.println("Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        } else {
                            System.out.println("Cannot move there, Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        }
                        break;
                    case 4:
                        System.out.println("Check Left");
                        if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == 0 ) {
                            System.out.println("Is valid moving to " + (player.getPosX() - 1) + "   Y " + player.getPosY());
                            setCordsRefined(player.getPosY(), (player.getPosX() - 1), distance++);
                            player.setPosX(player.getPosX() - 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            System.out.println("Overriding X " + player.getPosX() + "   Y " + player.getPosY());
                            setCordsRefined(player.getPosY(), (player.getPosX() - 1), distance++);
                            player.setPosX(player.getPosX() - 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == distance) {
                            System.out.println("Backtracking...");
                            player.setPosX(player.getPosX() + 1);
                            distance--;
                            System.out.println("Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        } else {
                            System.out.println("Cannot move there, Player is now at X " + player.getPosX() + "   Y " + player.getPosY());
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }
                */
            int temp = 0;
            //System.out.println("Distance of " + distance);
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCordsRefined(y, x) == 0) {
                        temp++;
                        System.out.println("Maze is NOT Filled");
                        y = 90;
                        x = 90;
                    }
                }
            }
            if (temp == 0) filled = true;

            if (distance > 100) {
                System.out.println("IT took too many steps, is this wrong? If not change range.");
                filled = true;
            }
        }
        setCords(initialY, initialX, 'S');
        System.out.println("Hi initial values are " + initialX + "  " + initialY);
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
    }

    public char getGoal() {
        return goal;
    }

    public boolean isValid(String direction, int value, int y, int x) {
        Player player = new Player();
        //System.out.println("Checking " + direction);
        if (value == 1) {
            switch (direction) {
                case "up":
                    //System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(y - 1, x) != 'B';
                case "right":
                    //System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(y, x + 1) != 'B';
                case "down":
                    //System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(y + 1, x) != 'B';
                case "left":
                    //System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(y, x - 1) != 'B';
                case "center":
                    //System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(y, x) != 'B';
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
        } else if (value == 2) {
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
        Player player = new Player();
        System.out.println("Player at X " + player.getPosX() + "   Y " + player.getPosY());
        for (int y = 0; y < getSizeY(); y++) {
            for (int x = 0; x < getSizeX(); x++) {
                if (getCords(y, x) == getGoal()) {
                    System.out.println("Player at X " + player.getPosX() + "   Y " + player.getPosY());
                    setNumSteps(getCordsRefined(y, x) - 1);
                    return getCordsRefined(y, x) - 1;
                }
            }
        }
        return 0;
    }
}
