package org.launchcode;

import java.io.FileReader;
import java.io.IOException;
import java.security.Policy;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Maze maze1 = new Maze();
        Player player1 = new Player();

        System.out.println("Player at X " + player1.getPosX() + "   Y " + player1.getPosY());
        player1.setPosY(2);
        player1.setPosX(2);
        System.out.println("Player at X " + player1.getPosX() + "   Y " + player1.getPosY());
        maze1.setGoal('C');
        System.out.println(maze1.getMinDistance());
        /*maze1.setSizeX(11);
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
        for (int y = 0; y < maze1.getSizeY(); y++) {
            for (int x = 0; x < maze1.getSizeX(); x++) {
                if (maze1.getCords(y, x) == maze1.getGoal()) {
                    System.out.println("Player at X " + player1.getPosX() + "   Y " + player1.getPosY());
                    System.out.println("Goal X " + x + " Goal Y " + y);
                    player1.setPosY(y);
                    player1.setPosX(x);
                }
            }
        }
        System.out.println("Player at X " + player1.getPosX() + "   Y " + player1.getPosY());
        maze1.navigate(maze1.getMinDistance());
/*        maze1.print(1);
        maze1.print(2);
        maze1.readFile("maze.txt");
        maze1.setGoal('X');
        maze1.navigate();*/
/*        long endTime = System.nanoTime();
        maze1.print(1);
        maze1.print(2);
        System.out.println("Found <" + maze1.getGoal() + "> in: " + maze1.getNumSteps() + " steps");
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);*/
    }
}
