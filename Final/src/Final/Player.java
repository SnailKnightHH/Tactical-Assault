/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.awt.Dimension;

/**
 *
 * @author huyan
 */
public class Player {
    public static final String UP = "up";       // initialize four string constants to define the direction the player faces 
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    
    public int positionX;       // define player position, direction and initial score 
    public int positionY;
    public String direction;
    public int score;

    public static final int movementSpeed = 20; // define movementSpeed as a constant with a value of 20

    public Player (int x, int y, String d, int s) { // setup constructor 
        positionX = x;
        positionY = y;
        direction = d;
        score = s;
    }

    public String getScoreString() {  // create a method to convert integer type score to string type score  
        return new Integer(score).toString();
    }
    
    
    public void moveLeft(Wall[] arrWalls) {  // method for player moving left 
        int newX = positionX - movementSpeed; // new x position is the current position minus movementSpeed 
        
        //loop through walls and check for collision
        Wall curWall;
        for (int i = 0; i < arrWalls.length; i++) {
            curWall = arrWalls[i];
            if (newX - 30 >= curWall.leftX && newX - 30 <= curWall.rightX) {
               if (positionY + 30 >= curWall.topY && positionY - 12 <= curWall.bottomY) { // check if player is inside each wall
                   newX = curWall.rightX + 30; // since player is moving left, he shall not pass the right wall
                   break;
               }
            }
        }
        
        // prevent the player moving out of window 
        newX = Math.max(35, newX);  // if player is outside the frame, set player x position to be the left edge of the screen  
        
        positionX = newX; // pass temporary variable newX's value to positionX
        direction = LEFT; // set direction as left 
    }
    
    public void moveRight(Wall[] arrWalls, Dimension size) { // same process as moveLeft 
        int newX = positionX + movementSpeed;
        //loop through walls and check for collision
        Wall curWall;
        for (int i = 0; i < arrWalls.length; i++) {
            curWall = arrWalls[i];
            if (newX + 60 >= curWall.leftX && newX + 60 <= curWall.rightX) {
               if (positionY + 30 >= curWall.topY && positionY - 12 <= curWall.bottomY) {
                   newX = curWall.leftX - 60;
                   break;
               }
            }
        }
        // prevent the player from moving out of window 
        newX = Math.min(newX, size.width - 65);
        positionX = newX;
        direction = RIGHT;
    }
    public void moveUp(Wall[] arrWalls) {// same process as moveLeft 
        int newY = positionY - movementSpeed;
        //loop through walls and check for collision
        Wall curWall;
        for (int i = 0; i < arrWalls.length; i++) {
            curWall = arrWalls[i];
            if (positionX + 30 >= curWall.leftX && positionX - 10 <= curWall.rightX) {
               if (newY - 30 >= curWall.topY && newY - 30 <= curWall.bottomY) {
                   newY = curWall.bottomY + 30;
                   break;
               }
            }
        }
        // prevent the player from moving out of window 
        newY = Math.max(35, newY);
        positionY = newY;
        direction = UP;
    }
    public void moveDown(Wall[] arrWalls, Dimension size) {// same process as moveLeft 
        int newY = positionY + movementSpeed;
        //loop through walls and check for collision
        Wall curWall;
        for (int i = 0; i < arrWalls.length; i++) {
            curWall = arrWalls[i];
            if (positionX + 30 >= curWall.leftX && positionX - 10 <= curWall.rightX) {
               if (newY + 54 >= curWall.topY && newY + 54 <= curWall.bottomY) {
                   newY = curWall.topY - 54;
                   break;
               }
            }
        }
        // prevent the player from moving out of window 
        newY = Math.min(newY, size.height - 65);
        positionY = newY;
        direction = DOWN;
    }
}
