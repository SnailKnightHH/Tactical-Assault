package Final;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkgfinal;
//
///**
// *
// * @author huyan
// */
public class Bullet {
    public static final String UP = "up"; // initialize four string constants to define the direction of the bullet 
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    
    public int positionX; // define bullet position, direction and speed
    public int positionY;
    public String direction;
    public static final int MOVEMENT_DISTANCE = 10;
    
    public Bullet(int x, int y, String dir) { // setup constructor 
        positionX = x;
        positionY = y;
        direction = dir;
    }
    

    public void moveBullet(){  // method for moving the bullet 
        switch(direction){ // different cases for directions
            case Bullet.UP: // if bullet is facing up
                positionY -= Bullet.MOVEMENT_DISTANCE; // bullet moves up
                break;
            case Bullet.DOWN:// if bullet is facing down
                positionY += Bullet.MOVEMENT_DISTANCE;// bullet moves down
                break;
            case Bullet.LEFT:// if bullet is facing left
                positionX -= Bullet.MOVEMENT_DISTANCE;// bullet moves left
                break;
            case Bullet.RIGHT:// if bullet is facing right
                positionX += Bullet.MOVEMENT_DISTANCE;// bullet moves right
                break;
        }
    }
    
    // method for checking wall collisions
    public boolean hasBulletHitWall (Wall[] arrWalls) {
        //loop through walls and check for collision
        boolean isWallHit = false; // create boolean and set its initial status as false 
        Wall curWall;
        for (int i = 0; i < arrWalls.length; i++) { // check to see if bullet is inside walls 
            curWall = arrWalls[i];
            if(positionX + 5 > curWall.leftX  && positionX < curWall.rightX && positionY > curWall.topY && positionY < curWall.bottomY){
                isWallHit = true;
            }
        }
        return isWallHit; // return true if a wall was hit
    }
    
    // method for checking if the opponent is hit
    public boolean hasBulletHitOpponent (Player opp) { // same process for wall collision method 
        boolean isOppHit = false;
        int hitBoxRange = 20;
        if(positionX > opp.positionX - hitBoxRange && positionX < opp.positionX + hitBoxRange + 10 && positionY > opp.positionY - hitBoxRange && positionY < opp.positionY + hitBoxRange +10){
            isOppHit = true;
        }
        return isOppHit; // return true if opponent is hit
    }
}

