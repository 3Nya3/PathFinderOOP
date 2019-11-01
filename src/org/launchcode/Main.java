package org.launchcode;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static class Maze {
        private Player player;
        private char[][] cords = new char[15][15];
        private int[][] cordsRefined = new int[15][15];
        private final Random RANDOM = new Random();
        private int sizeY = 15;
        private int sizeX = 15;
        private char goal = 'C';

        private void navigate() {
            int minDistance = 0;
            boolean found = false;
            for (int y = 0; y <= getSizeY(); y++) {
                for (int x = 0; x <= getSizeX(); x++) {
                    if (getCords(y, x) == getGoal()) {
                        minDistance = getCordsRefined(y, x);
                        player.setPosY(y);
                        player.setPosX(x);
                        break;
                    }
                }
            }
            while (!found) {
                switch (random(1, 4)) {
                    case 1:
                        if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == minDistance--) {
                            minDistance--;
                            setCords((player.getPosY() - 1), player.getPosX(), '●');
                            player.setPosY(player.getPosY() - 1);
                        } else {
                            setCords((player.getPosY()), player.getPosX(), '.');
                            player.setPosY(player.getPosY() + 1);
                            minDistance--;
                        }
                        break;
                    case 2:
                        if (getCordsRefined(player.getPosY(), (player.getPosX() + 1)) == minDistance--) {
                            minDistance--;
                            setCords((player.getPosX() + 1), player.getPosX(), '●');
                            player.setPosX(player.getPosX() + 1);
                        } else {
                            setCords((player.getPosY()), player.getPosX(), '.');
                            player.setPosY(player.getPosX() - 1);
                            minDistance--;
                        }
                        break;
                    case 3:
                        if (getCordsRefined((player.getPosY() + 1), player.getPosX()) == minDistance--) {
                            minDistance--;
                            setCords((player.getPosY() + 1), player.getPosX(), '●');
                            player.setPosY(player.getPosY() + 1);
                        } else {
                            setCords((player.getPosY()), player.getPosX(), '.');
                            player.setPosY(player.getPosY() - 1);
                            minDistance--;
                        }
                        break;
                    case 4:
                        if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == minDistance--) {
                            minDistance--;
                            setCords((player.getPosX() - 1), player.getPosX(), '●');
                            player.setPosX(player.getPosX() - 1);
                        } else {
                            setCords((player.getPosY()), player.getPosX(), '.');
                            player.setPosY(player.getPosX() + 1);
                            minDistance--;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }
                if (getCordsRefined(player.getPosY(), player.getPosX()) == 0) found = true;
            }
            setCords((player.getPosY()), player.getPosX(), '●');
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
                    for (int y = 0; y <= getSizeY(); y++) {
                        for (int x = 0; x <= getSizeX(); x++) {
                            System.out.print(getCords(y, x));
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    for (int y = 0; y <= getSizeY(); y++) {
                        for (int x = 0; x <= getSizeX(); x++) {
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
            int initialX = player.getPosX();
            int initialY = player.getPosY();
            int distance = 0;
            setCordsRefined(player.getPosY(), player.getPosX(), 1);
            boolean filled = false;
            while (!filled) {
                switch (random(1, 4)) {
                    case 1:
                        if (getCordsRefined((player.getPosY() - 1), player.getPosX()) == 0) {
                            setCordsRefined((player.getPosY() - 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() - 1);
                        } else if (getCordsRefined((player.getPosY() - 1), player.getPosX()) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            setCordsRefined((player.getPosY() - 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() - 1);
                        } else {
                            player.setPosY(player.getPosY() + 1);
                            distance--;
                        }
                        break;
                    case 2:
                        if (getCordsRefined(player.getPosY(), (player.getPosX() + 1)) == 0) {
                            setCordsRefined(player.getPosY(), (player.getPosX() + 1), distance++);
                            player.setPosX(player.getPosX() + 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() + 1)) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            setCordsRefined(player.getPosY(), (player.getPosX() + 1), distance++);
                            player.setPosX(player.getPosX() + 1);
                        } else {
                            player.setPosX(player.getPosX() - 1);
                            distance--;
                        }
                        break;
                    case 3:
                        if (getCordsRefined((player.getPosY() + 1), player.getPosX()) == 0) {
                            setCordsRefined((player.getPosY() + 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() + 1);
                        } else if (getCordsRefined((player.getPosY() + 1), player.getPosX()) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            setCordsRefined((player.getPosY() + 1), player.getPosX(), distance++);
                            player.setPosY(player.getPosY() + 1);
                        } else {
                            player.setPosY(player.getPosY() - 1);
                            distance--;
                        }
                        break;
                    case 4:
                        if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) == 0) {
                            setCordsRefined(player.getPosY(), (player.getPosX() - 1), distance++);
                            player.setPosX(player.getPosX() - 1);
                        } else if (getCordsRefined(player.getPosY(), (player.getPosX() - 1)) > getCordsRefined(player.getPosY(), player.getPosX())) {
                            setCordsRefined(player.getPosY(), (player.getPosX() - 1), distance++);
                            player.setPosX(player.getPosX() - 1);
                        } else {
                            player.setPosX(player.getPosX() + 1);
                            distance--;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }
                for (int y = 0; y <= getSizeY(); y++) {
                    for (int x = 0; x <= getSizeX(); x++) {
                        if (getCordsRefined(y, x) == 0) break;
                        else filled = true;
                    }
                }
            }
            setCordsRefined(initialY, initialX, 0);
            player.setPosX(initialX);
            player.setPosY(initialY);
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
            System.out.println("Checking " + direction);
            switch (direction) {
                case "up":
                    return getCords((player.getPosY() - 1), player.getPosX()) == '.';
                case "right":
                    return getCords(player.getPosY(), (player.getPosX() + 1)) == '.';
                case "down":
                    return getCords((player.getPosY() + 1), player.getPosX()) == '.';
                case "left":
                    return getCords(player.getPosY(), (player.getPosX() - 1)) == '.';
                case "center":
                    return getCords(player.getPosY(), player.getPosX()) == '.';
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

    }

    static class Player {
        private Maze maze;
        private int posX = 1;
        private int posY = 1;

        public void setPos() {
            Scanner input = new Scanner(System.in);
            System.out.println("Player at X: " + getPosX() + " and Y: " + getPosY());
            try {
                System.out.println("Please Enter X Position of the Player");
                setPosX(input.nextInt());
                System.out.println("Please Enter Y Position of the Player");
                setPosY(input.nextInt());
/*
                if (!maze.isValid("center")) {
                    System.out.println("Invalid Location, please try again");
                    setPos();
                }*/
            } catch (Exception e) {
                System.out.println("Invalid Location, please try again");
                setPos();
            }
        }

        public int getPosY() {
            return posY;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public int getPosX() {
            return posX;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }
    }

    public static void main(String[] args) throws IOException {
        Maze maze = new Maze();
        Player player = new Player();

        maze.setSizeX(11);
        maze.setSizeY(10);
        maze.readFile("maze.txt");
        maze.print(1);
        maze.print(2);
        player.setPos();
        long startTime = System.nanoTime();
        maze.flood();
        maze.print(1);
        maze.print(2);
        maze.setGoal('C');
        maze.navigate();
        maze.print(1);
        maze.print(2);
        maze.readFile("maze.txt");
        maze.setGoal('X');
        maze.navigate();
        long endTime = System.nanoTime();
        maze.print(1);
        maze.print(2);
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);
    }
}
