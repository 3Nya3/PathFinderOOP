package org.launchcode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public class Test{

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Maze m2 = new Maze();

        m2.setSizeX(11);
        m2.setSizeY(10);
        m2.readFile("maze.txt", 1);
        m2.checkMaze();
        System.out.println("Program will continue in 3 seconds...");
        TimeUnit.SECONDS.sleep(3);
        m2.print(1);
        m2.setPos();
        long startTime = System.nanoTime();
        m2.flood();
        m2.print(2);
        m2.setGoal('C');
        System.out.println("Player at X " + m2.getPosX() + "   Y " + m2.getPosY());
        m2.navigate(m2.getNumSteps());
        m2.print(1);
        //m2.print(2);
        System.out.println("Found <" + m2.getGoal() + "> in: " + (m2.getNumSteps() - 1) + " steps");
        m2.setGoal('C');
        m2.readFile("maze.txt", 2);
        m2.flood();
        m2.setGoal('X');
        System.out.println("Player at X " + m2.getPosX() + "   Y " + m2.getPosY());
        m2.navigate(m2.getNumSteps());
        long endTime = System.nanoTime();
        m2.print(1);
        //m2.print(2);
        System.out.println("Found <" + m2.getGoal() + "> in: " + (m2.getNumSteps() - 1) + " steps");
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);
        //System.out.println((endTime - startTime) + " nanoseconds.");
    }
}
