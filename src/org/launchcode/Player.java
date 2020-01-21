package org.launchcode;

//****************************************************************************************
//<Class>
//This class store the player y position and x position
//<List of Identifiers>
//let sizeY = an integer variable use to store the y position of the player <type int>
//let sizeX = an integer variable use to store the x position of the player <type int>
//****************************************************************************************
public class Player {
    private int posX;
    private int posY;

    /**Default constructor method which instantiates a new Player.
     */
    public Player() {

    }

    /**getPosY method:
     * This accessor returns the y position of the player
     *
     * @return the y position of the player <type>int</type>
     */
    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**getPosX method:
     * This accessor returns the x position of the player
     *
     * @return the x position of the player <type>int</type>
     */
    public int getPosX() {
        return posX;
    }

    /**setPosX method:
     * This setter sets the the x position of the player given the x value
     *
     * @param posX the x position of the player <type>int</type>
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }
}
