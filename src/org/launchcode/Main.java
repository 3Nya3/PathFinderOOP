/*=================================================================
PathFinder2
Frank Ye
Date: 02, 11, 2019
java, IntelliJ IDEA 2019.2.4 (Community Edition)
=================================================================
Problem Definition – Required to find the shortest path within a maze given starting point
Input – A maze file and starting point coordinates
Output – The path to the Goal and Exit and amount of steps it took to reach it
Process – uses BFS to find shortest path and creates a route
=================================================================
*/

package org.launchcode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    /**main method:
     *This procedural method is called automatically and is used to organize the calling of other methods defined in the class
     *
     * @param args <type>String</type>
     * @throws IOException the io exception
     * @throws InterruptedException the interrupted exception
     */
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
        m2.setGoal('C');
        m2.navigate(m2.getNumSteps());
        m2.print(1);
        System.out.println("Found <" + m2.getGoal() + "> in: " + (m2.getNumSteps() - 1) + " steps");
        m2.setGoal('C');
        m2.readFile("maze.txt", 2);
        m2.flood();
        m2.setGoal('X');
        m2.navigate(m2.getNumSteps());
        long endTime = System.nanoTime();
        m2.print(1);
        System.out.println("Found <" + m2.getGoal() + "> in: " + (m2.getNumSteps() - 1) + " steps");
        System.out.printf("BFS maze finder in %d milliseconds.\n", (endTime - startTime) / 1_000_000L);
        System.out.println("Thank you for running this program, if you'd like, please try it with different types of mazes");
    } //end of main method
}