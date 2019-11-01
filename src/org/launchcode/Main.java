package org.launchcode;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static class Maze {
        private char[][] cords = new char[15][15];
        private int[][] cordsRefined = new int[15][15];
        private final Random RANDOM = new Random();
        private int sizeY = 15;
        private int sizeX = 15;
        private char goal = 'C';
        private int numSteps = 0;

        private void navigate() {
            Player player = new Player();
            int minDistance = 0;
            boolean found = false;
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if (getCords(y, x) == getGoal()) {
                        minDistance = getCordsRefined(y, x);
                        setNumSteps(minDistance);
                        System.out.println("Set distance to " + getNumSteps());
                        System.out.println("Goal at X " + x + "   Y " + y);
                        player.setPosY(y);
                        player.setPosX(x);
                        y = 90;
                        x = 90;
                    }
                }
            }
            while (!found) {
                System.out.println("Minimum distance = " + minDistance);
                minDistance--;
                int  marked = 0;

                if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == minDistance) {
                    //System.out.println("Marked Up Compared " + minDistance + " with " + getCordsRefined((player.getPosY() - 1), player.getPosX()));
                    setCords((player.getPosY() - 1), player.getPosX(), '●');
                    player.setPosY(player.getPosY() - 1);
                    marked++;
                } /*8else if (getCords((player.getPosY() - 1), player.getPosX()) != '●' && getCordsRefined((player.getPosY() - 1), player.getPosX()) != -1) {
                    System.out.println("Un-Marked Up Compared " + minDistance + " with " + getCordsRefined((player.getPosY() - 1), player.getPosX()));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosY() + 1);
                }*/
                if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() + 1)) == minDistance) {
                    //System.out.println("Marked Right, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() + 1)));
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
                    //System.out.println("Marked Down, Compared " + minDistance + " with " + getCordsRefined((player.getPosY() + 1), player.getPosX()));
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
                    //System.out.println("Marked Left, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() - 1)));
                    setCords(player.getPosY(), (player.getPosX() - 1), '●');
                    player.setPosX(player.getPosX() - 1);
                } /*else if (marked == 0 && getCordsRefined(player.getPosY(), (player.getPosX() - 1)) != '●' && getCordsRefined(player.getPosY(), (player.getPosX() - 1)) != -1) {
                    System.out.println("Un-Marked Left, Compared " + minDistance + " with " + getCordsRefined(player.getPosY(), (player.getPosX() - 1)));
                    setCords((player.getPosY()), player.getPosX(), '.');
                    player.setPosY(player.getPosX() + 1);
                }*/
                if (getCordsRefined(player.getPosY(), player.getPosX()) == 0) found = true;
                if (minDistance < -1) {
                    System.out.println("Something bad happened");
                    break;
                }
            }
            //setCords((player.getPosY()), player.getPosX(), '●');
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
            System.out.println("Initial position at: " + initialX + "   " + initialY);
            int distance = 1;
            setCordsRefined(player.getPosY(), player.getPosX(), 1);
            boolean filled = false;
            while (!filled) {
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
                int temp = 0;
                System.out.println("Distance of " + distance);
                for (int y = 0; y < getSizeY(); y++) {
                    for (int x = 0; x < getSizeX(); x++) {
                        if (getCordsRefined(y, x) == 0) {
                            temp++;
                            System.out.println("Maze is NOT Filled");
                            print(2);
                            y = 90;
                            x = 90;
                        }
                    }
                }
                if (temp == 0) filled = true;
            }
            setCordsRefined(initialY, initialX, 0);
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

        public boolean isValid(String direction) {
            Player player = new Player();
            System.out.println("Checking " + direction);
            switch (direction) {
                case "up":
                    System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords((player.getPosY() - 1), player.getPosX()) != 'B';
                case "right":
                    System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(player.getPosY(), (player.getPosX() + 1)) != 'B';
                case "down":
                    System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords((player.getPosY() + 1), player.getPosX()) != 'B';
                case "left":
                    System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(player.getPosY(), (player.getPosX() - 1)) != 'B';
                case "center":
                    System.out.println("Checking position at " + player.getPosY() + "   " + player.getPosX());
                    return getCords(player.getPosY(), player.getPosX()) != 'B';
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
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
    }

    static class Player {
        private int posX = 1;
        private int posY = 1;

        public void setPos() {
            Maze maze = new Maze();
            Scanner input = new Scanner(System.in);
            System.out.println("Player at X: " + getPosX() + " and Y: " + getPosY());
            try {
                System.out.println("Please Enter X Position of the Player");
                setPosX(input.nextInt());
                System.out.println("Please Enter Y Position of the Player");
                setPosY(input.nextInt());

                if (maze.isValid("center")) {
                    System.out.println("Checked isValid");
                    maze.setCords(getPosY(), getPosX(), 'S');
                } else {
                    System.out.println("Invalid Location, please try again");
                    setPos();
                }
            } catch (Exception e) {
                System.out.println("Invalid Location, please try a number");
                setPos();
            }
        }

        public int getPosY() {
            //System.out.println("Get Y");
            return posY;
        }

        public void setPosY(int posY) {
            //System.out.println("Set Y");
            this.posY = posY;
        }

        public int getPosX() {
            //System.out.println("Get X");
            return posX;
        }

        public void setPosX(int posX) {
            //System.out.println("Set X");
            this.posX = posX;
        }
    }

    public static void main(String[] args) throws IOException {
        Maze maze1 = new Maze();
        Player player1 = new Player();

        maze1.setSizeX(11);
        maze1.setSizeY(10);
        maze1.readFile("maze.txt");
        maze1.print(1);
        maze1.print(2);
        player1.setPos();
        long startTime = System.nanoTime();
        maze1.flood();
        maze1.print(1);
        maze1.print(2);
        maze1.setGoal('C');
        maze1.navigate();
/*        maze1.print(1);
        maze1.print(2);
        maze1.readFile("maze.txt");
        maze1.setGoal('X');
        maze1.navigate();*/
        long endTime = System.nanoTime();
        maze1.print(1);
        maze1.print(2);
        System.out.println("Found <" + maze1.getGoal() + "> in: " + maze1.getNumSteps() + " steps");
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);
    }
}
