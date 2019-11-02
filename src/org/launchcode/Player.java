package org.launchcode;

import java.util.Scanner;

public class Player {
    private int posX;
    private int posY;

    public Player() {
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
