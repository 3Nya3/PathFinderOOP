package org.launchcode;

import java.util.Scanner;

public class Player {
    private int posX;
    private int posY;

    public Player(){
        posX = 1;
        posY = 1;
    }

    public void setPos() {
        Maze maze = new Maze();
        Scanner input = new Scanner(System.in);
        System.out.println("Player at X: " + getPosX() + " and Y: " + getPosY());
        try {
            System.out.println("Please Enter X Position of the Player");
            setPosX(input.nextInt());
            System.out.println("Please Enter Y Position of the Player");
            setPosY(input.nextInt());

            if (maze.isValid("center", 1, getPosY(), getPosX())) {
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
        System.out.println("Get Y");
        return posY;
    }

    public void setPosY(int posY) {
        System.out.println("Set Y");
        this.posY = posY;
    }

    public int getPosX() {
        System.out.println("Get X");
        return posX;
    }

    public void setPosX(int posX) {
        System.out.println("Set X");
        this.posX = posX;
    }
}
