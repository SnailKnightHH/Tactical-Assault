/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

/**
 *
 * @author huyan
 */
public class Wall {     // define four variables to set the x, y boundaries of walls 
    public int topY;
    public int bottomY;
    public int leftX;
    public int rightX;
    
    public Wall (int t, int b, int l, int r){   // setup constructor 
        topY = t;
        bottomY = b;
        rightX = r;
        leftX = l;
    }
}
