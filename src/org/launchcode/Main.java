package org.launchcode;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Maze m2 = new Maze();

        m2.setSizeX(11);
        m2.setSizeY(10);
        m2.readFile("maze.txt", 1);
        switch (m2.checkMaze()) {
            case 1:
                System.out.println("Hey... Something is up with your maze, and not all paths are reachable... Better go fix it.");
                System.out.println("Found first unreachable path at X: " + m2.getErrorX() + " and Y: " + m2.getErrorY());
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
                System.out.println("Maze check is clean, off you go!");
                break;
        }
        m2.print(1);
        m2.print(2);
        m2.setPos();
        long startTime = System.nanoTime();
        m2.flood();
        m2.setGoal('C');
        System.out.println("Player at X " + m2.getPosX() + "   Y " + m2.getPosY());
        m2.navigate(m2.getMinDistance());
        m2.print(1);
        m2.print(2);
        System.out.println("Found <" + m2.getGoal() + "> in: " + m2.getNumSteps() + " steps");
        m2.readFile("maze.txt", 2);
        m2.print(1);
        m2.print(2);
        m2.setGoal('C');
        m2.flood();
        m2.setGoal('X');
        System.out.println("Player at X " + m2.getPosX() + "   Y " + m2.getPosY());
        m2.navigate(m2.getMinDistance());
        long endTime = System.nanoTime();
        m2.print(1);
        m2.print(2);
        System.out.println("Found <" + m2.getGoal() + "> in: " + m2.getNumSteps() + " steps");
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);
        //System.out.println((endTime - startTime) + " nanoseconds.");
    }
}
