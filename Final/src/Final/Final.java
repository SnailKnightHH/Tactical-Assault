
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.awt.*; //Import java abstract window kit package 
import javax.swing.*; //Import java's toolkit Swing package 
import java.awt.event.*; //Allows java to listen for events
import java.io.*; // Import package to enable images in the program 
import java.util.Calendar;
import java.util.Date;
import javax.imageio.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Yangming Hu
 */
public class Final extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    Player player1;     // initialize an object called player 1 for Player class
    Player player2;     // initialize an object called player 2 for Player class 
    Bullet p1Bullet; // initialize object p1Bullet (player1 bullet) for Bullet class
    Bullet p2Bullet; // initialize object p2Bullet (player2 bullet) for Bullet class
    String winningPlayer = ""; // initialize winning player to be an empty string
//
//    int gameSeconds = 300;
//    Timer myTimer = new Timer();
//    TimerTask = timerTask new TimerTask(){
//        @Override 
//        public void run(){
//            gameSeconds--;
//            repaint();
//    }
//    };
//    
    
    String gameMessage; //message shown on the screen
    boolean showGameMessage; // boolean for determining if it is time to display the message 

    Wall[] arrWalls; // create an array for collision detection with walls 

    Dimension size; // set the deimension of the screen
    int menuStatus = 1; // create a variable called menuStatus to indicate with screen the player is on
    // menuStatus == 1: Menus screen     menuStatus == 3: Game Screen 
    
    int mouseX, mouseY; // create integer variables mouseX and mouseY to track the mouse's position
    boolean inPlay = false; // set two boolean variables to see if the mouse is inside certain boxes 
    boolean inQuit = false;

    public Rectangle playButton = new Rectangle(350, 250, 200, 80); // create a new rectangle to represent the button to start playing the game 
    public Rectangle quitButton = new Rectangle(350, 450, 200, 80); // create a new rectangle to represent the button to quit the game

    Final() {
        this.addKeyListener(this); //add the key listener
        addMouseListener(this); // add mouse listener 
        addMouseMotionListener(this);
        this.addKeyListener(this);
        setFocusable(true); //set the focus of the keyboard and mouse 
   
       // myTimer.schedule(timeTask, 1000, 1000);
    }

    public void initializeGame() {
        //initialize the players
        player1 = new Player(170, 70, Player.DOWN, 0); // initialize object player 1 with parameters defined by Player class
        player2 = new Player(650, 580, Player.UP, 0); // initialize object player 2 with parameters defined by Player class

        //initialize the walls
        arrWalls = new Wall[8];  // allocate 8 memories for the array of walls
        // bottom section
        arrWalls[0] = new Wall(500, 520, 100, 310);    // set the position for each wall
        arrWalls[1] = new Wall(500, 700, 450, 470);
        arrWalls[2] = new Wall(500, 520, 590, 800);
        // top section
        arrWalls[3] = new Wall(0, 190, 360, 380);
        arrWalls[4] = new Wall(190, 230, 180, 550);
        arrWalls[5] = new Wall(90, 110, 520, 740);
        arrWalls[6] = new Wall(90, 290, 740, 760);
        // middle wall
        arrWalls[7] = new Wall(350, 410, 440, 460);

        winningPlayer = "";  // every time player returns menu and press play, the winning player is reset 
        p1Bullet = null;   // initialize both players' bullets by setting them as null
        p2Bullet = null;
    }

    //generates only one event for each pressed-released combination
    public void mouseClicked(MouseEvent e) { // if mouse is clicked 
        if (menuStatus == 1) { // if the player is on the main menu of the game
            if (inQuit) // if the player clicks on the quit button
            {
                System.exit(0); // exit the game
            } else if (inPlay) { // if the player clicks on the play button
                menuStatus = 3; // start the game
                initializeGame(); // call initializeGame method to start a new round 
            }
        } 

        repaint(); // update the screen

    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) { // whenever mouse is moved 
        mouseX = e.getX(); // get the x, y coordinate of the mouse 
        mouseY = e.getY();
        if (menuStatus == 1) { // if the player is on the main menu
            if (playButton.contains(mouseX, mouseY)) { // if the mouse is inside the play button box 
                inPlay = true; // set inPlay boolean as true, ackowledging the mouse is inside the box 
            } else {
                inPlay = false; // else inPlay remains false 
            }
        }
        if (quitButton.contains(mouseX, mouseY)) { // if the mouse is inside the quit button box 
            inQuit = true;  // set inQuit boolean as true, ackowledging the mouse is inside the box 
        } else {
            inQuit = false; // else inQuit remains false 
        }
    }

    public void keyTyped(KeyEvent e) { //for the key typed

    }

    public void keyPressed(KeyEvent e) { //for the key pressed
        char key = (char) e.getKeyCode(); //acquire the ascii value for the key pressed
        boolean redrawScreen = true;    // create a boolean variable named redrawScreen to determine whether or not it is necessary to repaint the screen 
        
        // player 2
        if (key == e.VK_LEFT) { //if the left arrow key is pressed
            player2.moveLeft(arrWalls); // player 2 moves left 
        } else if (key == e.VK_RIGHT) { //if the right arrow key is pressed
            player2.moveRight(arrWalls, size); // player2 moves right 
        } else if (key == e.VK_UP) { //if the right arrow key is pressed
            player2.moveUp(arrWalls); // player2 moves up 
        } else if (key == e.VK_DOWN) { //if the right arrow key is pressed
            player2.moveDown(arrWalls, size); // player2 moves down
        } else if (key == 'M') { // if key "M" is pressed, player 2 shoots a bullet 
            if (p2Bullet == null) { // if p2Bullet does not exist on the screen 
                int x = player2.positionX; // define two temporary variables to store bullet position
                int y = player2.positionY;
                switch (player2.direction) {    // adjust position values so that no matter which direction
                    case Player.UP:             // the player is facing, the bullet always comes out of 
                        y -= 35;                // the tip of the gun 
                        x += 10;
                        break;
                    case Player.DOWN:
                        y += 35;
                        x += 10;
                        break;
                    case Player.LEFT:
                        y += 10;
                        x -= 35;
                        break;
                    case Player.RIGHT:
                        y += 10;
                        x += 35;
                        break;
                }
                p2Bullet = new Bullet(x, y, player2.direction); // initialize p2Bullet in Bullet class
            }

        } 

        // player 1
        else if (key == 'A') { // if the key "A" is pressed
            player1.moveLeft(arrWalls); // player 1 moves left
        } else if (key == 'D') {// if the key "A" is pressed
            player1.moveRight(arrWalls, size); // player 1 moves right 
        } else if (key == 'W') {// if the key "A" is pressed
            player1.moveUp(arrWalls);// player 1 moves up
        } else if (key == 'S') {// if the key "A" is pressed
            player1.moveDown(arrWalls, size);// player 1 moves down
        } else if (key == e.VK_SPACE) { // if space bar is pressed 
            if (p1Bullet == null) {
                int x = player1.positionX;
                int y = player1.positionY;
                switch (player1.direction) { // same process for player 1
                    case Player.UP:
                        y -= 35;
                        x += 10;
                        break;
                    case Player.DOWN:
                        y += 35;
                        x += 10;
                        break;
                    case Player.LEFT:
                        y += 10;
                        x -= 35;
                        break;
                    case Player.RIGHT:
                        y += 10;
                        x += 35;
                        break;
                }
                p1Bullet = new Bullet(x, y, player1.direction);
            }
        } else if (key == e.VK_ESCAPE || key == 'q') { // if esc is pressed 
            System.exit(0); //quit the program
        } else { // if no key is pressed, nothing changes, thus no need to repaint hte screen 
            redrawScreen = false;
        }
        if (redrawScreen) {
            repaint(); //refresh the frame
        }
    }

    public void keyReleased(KeyEvent e) { //for key released

    }


    // draw players
    // the following four methods is for drawing players in four different directions 
    public void drawPlayerDown(Graphics g, Player p) {
        g.setColor(Color.black);
        // head
        g.drawOval(p.positionX, p.positionY, 25, 25);
        // left arm 
        g.drawLine(p.positionX + 5, p.positionY + 1, p.positionX - 16, p.positionY + 12);
        g.drawLine(p.positionX + 5, p.positionY + 23, p.positionX - 16, p.positionY + 12);
        // right arm
        g.drawLine(p.positionX + 19, p.positionY + 1, p.positionX + 41, p.positionY + 12);
        g.drawLine(p.positionX + 19, p.positionY + 23, p.positionX + 41, p.positionY + 12);
        // backpack
        g.drawRect(p.positionX + 2, p.positionY + 25 - 33, 22, 8);
        // gun
        g.drawLine(p.positionX - 7, p.positionY + 6 + 12, p.positionX + 22, p.positionY - 17 + 55);
        g.drawLine(p.positionX + 30, p.positionY + 6 + 12, p.positionX + 14, p.positionY - 37 + 90);
    }

    public void drawPlayerUp(Graphics g, Player p) {
        g.setColor(Color.black);
        // head
        g.drawOval(p.positionX, p.positionY, 25, 25);
        // left arm 
        g.drawLine(p.positionX + 5, p.positionY + 1, p.positionX - 16, p.positionY + 12);
        g.drawLine(p.positionX + 5, p.positionY + 23, p.positionX - 16, p.positionY + 12);
        // right arm
        g.drawLine(p.positionX + 19, p.positionY + 1, p.positionX + 41, p.positionY + 12);
        g.drawLine(p.positionX + 19, p.positionY + 23, p.positionX + 41, p.positionY + 12);
        // backpack
        g.drawRect(p.positionX + 2, p.positionY + 25, 22, 8);
        // gun
        g.drawLine(p.positionX - 7, p.positionY + 6, p.positionX + 22, p.positionY - 17);
        g.drawLine(p.positionX + 30, p.positionY + 6, p.positionX + 14, p.positionY - 37);
    }

    public void drawPlayerRight(Graphics g, Player p) {
        g.setColor(Color.black);
        // head
        g.drawOval(p.positionX, p.positionY, 25, 25);
        // bottom arm 
        g.drawLine(p.positionX + 3, p.positionY + 20, p.positionX + 12, p.positionY + 38);
        g.drawLine(p.positionX + 5 + 17, p.positionY + 20, p.positionX + 12, p.positionY + 38);
        // top arm
        g.drawLine(p.positionX + 4, p.positionY + 2, p.positionX + 12, p.positionY - 18);
        g.drawLine(p.positionX + 20, p.positionY + 2, p.positionX + 12, p.positionY - 18);
        // backpack
        g.drawRect(p.positionX + 25 - 33, p.positionY, 8, 22);
        // gun
        g.drawLine(p.positionX + 19, p.positionY - 2, p.positionX + 67, p.positionY + 14);
        g.drawLine(p.positionX + 18, p.positionY + 27, p.positionX + 43, p.positionY + 6);
    }

    public void drawPlayerLeft(Graphics g, Player p) {
        g.setColor(Color.black);
        // head
        g.drawOval(p.positionX, p.positionY, 25, 25);
        // bottom arm 
        g.drawLine(p.positionX + 3, p.positionY + 20, p.positionX + 12, p.positionY + 38);
        g.drawLine(p.positionX + 5 + 17, p.positionY + 20, p.positionX + 12, p.positionY + 38);
        // top arm
        g.drawLine(p.positionX + 4, p.positionY + 2, p.positionX + 12, p.positionY - 18);
        g.drawLine(p.positionX + 20, p.positionY + 2, p.positionX + 12, p.positionY - 18);
        // backpack
        g.drawRect(p.positionX + 25, p.positionY, 8, 22);
        // gun
        g.drawLine(p.positionX + 5, p.positionY + 1, p.positionX - 37, p.positionY + 15);
        g.drawLine(p.positionX + 7, p.positionY + 28, p.positionX - 16, p.positionY + 8);
    }

    // draw walls
    public void drawWalls(Graphics g) {
        g.setColor(Color.black);
        Wall curWall;
        int curWidth;
        int curHeight;

        //loop through walls
        for (int i = 0; i < arrWalls.length; i++) {  // initialize each wall using a for loop
            curWall = (Wall) arrWalls[i];
            curWidth = curWall.rightX - curWall.leftX;
            curHeight = curWall.bottomY - curWall.topY;
            g.fillRect(curWall.leftX, curWall.topY, curWidth, curHeight);
        }

    }

    // draw bullets
    public void drawBullets(Graphics g, Bullet b) {
        g.fillOval(b.positionX, b.positionY, 10, 10);
    }

    // draw menu
    public void Menu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!winningPlayer.equals("")) { // if winning player is not set to nothing, meaning a game has been played
            Font font1 = new Font("arial", Font.BOLD, 20);
            g.setFont(font1);
            g.setColor(Color.red);
            g.drawString(winningPlayer + " wins", 380, 210); // then display the winning player message 
        }

        Font font1 = new Font("arial", Font.BOLD, 50);
        g.setFont(font1);
        g.setColor(Color.blue);
        g.drawString("Tactical Assault", 260, 150); // draw title 

        Font font2 = new Font("arial", Font.BOLD, 30);
        g.setFont(font2);
        g.drawString("Play", playButton.x + 70, playButton.y + 50); // draw string "play" inside play button 
        g2d.draw(playButton); // draw play button 
        g.drawString("Quit", quitButton.x + 70, quitButton.y + 50); // draw string "quit" inside quit button 
        g2d.draw(quitButton); // draw quit button 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (menuStatus == 1) { // if player is on menu screen 
            Menu(g); // call Menu method to draw the menu 
        } else if (menuStatus == 3) { // if player is on game screen 
            size = getSize(); // get the dimension of the frame 
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height); // paint background to be white 
            g.setColor(Color.black); // set the color back to black

            //draw walls in the screen 
            drawWalls(g); 

            //draw player1 in the screen 
            switch (player1.direction) {
                case Player.UP: // if player 1 is facing up
                    drawPlayerUp(g, player1); // called the method that draws player facing up
                    break;  // if condition is true, exit switch 
                case Player.DOWN:// if player 2 is facing down
                    drawPlayerDown(g, player1); // called the method that draws player facing down
                    break; // if condition is true, exit switch 
                case Player.LEFT:// if player 2 is facing left
                    drawPlayerLeft(g, player1);// called the method that draws player facing left
                    break; // if condition is true, exit switch 
                case Player.RIGHT:// if player 2 is facing right
                    drawPlayerRight(g, player1);// called the method that draws player facing right
                    break; // if condition is true, exit switch 
            }

            //draw player2
            switch (player2.direction) { // same process for player 2
                case Player.UP:
                    drawPlayerUp(g, player2);
                    break;
                case Player.DOWN:
                    drawPlayerDown(g, player2);
                    break;
                case Player.LEFT:
                    drawPlayerLeft(g, player2);
                    break;
                case Player.RIGHT:
                    drawPlayerRight(g, player2);
                    break;
            }

            // bullet control
            boolean animateBullets = false; // create a boolean variable to determine if it is necessary to repaint the screen 
            if (p2Bullet != null) { // if there is a bullet on the screen 
                animateBullets = true; // set animateBullet boolean to be true
                drawBullets(g, p2Bullet); // draw Player 2 bullet
                p2Bullet.moveBullet(); // call method for moving player 2 bullet 
                // bullet wall collision detection
                if (p2Bullet.hasBulletHitWall(arrWalls)) { 
                    p2Bullet = null; // destroy the bullet 
                }  
                // bullet hit opponent detection
                else if (p2Bullet.hasBulletHitOpponent(player1)) {
                    p2Bullet = null;// destroy the bullet 
                    player2.score++;// player score one point 

                    player1.positionX = 170; // reset player 1 position
                    player1.positionY = 70;
                } // screen collision detection 
                else if (p2Bullet.positionX < 0 || p2Bullet.positionX > size.width || p2Bullet.positionY < 0 || p2Bullet.positionY > size.height) {
                    p2Bullet = null; // if the bullet reaches out of the frame, destroy the bullet 
                }
            }
            if (p1Bullet != null) { // same process for player 1
                animateBullets = true;
                drawBullets(g, p1Bullet);
                p1Bullet.moveBullet();
                if (p1Bullet.hasBulletHitWall(arrWalls)) {
                    p1Bullet = null;
                } else if (p1Bullet.hasBulletHitOpponent(player2)) {
                    //whatever we do when there is a hit

                    //gameMessage = "Player 2 has been killed.";
                    p1Bullet = null;
                    player1.score++;
                    // respawn
                    player2.positionX = 650;
                    player2.positionY = 580;

                } else if (p1Bullet.positionX < 0 || p1Bullet.positionX > size.width || p1Bullet.positionY < 0 || p1Bullet.positionY > size.height) {
                    p1Bullet = null;
                }

            }

            if (player1.score == 5) { // if player1's score is equal to 5
                winningPlayer = "Player 1"; // set player 1 to be the message and store it in winningPlayer string variable 
                menuStatus = 1; // go back to menu screen 
            } else if (player2.score == 5) { // if player2's score is equal to 5
                winningPlayer = "Player 2";// set player 2 to be the message and store it in winningPlayer string variable 
                menuStatus = 1;// go back to menu screen 
            }

            if (animateBullets) { // if animateBullet is true
                delay(5); // delay for controlling bullet speed
                repaint(); // repaint the screen
            }


            //show scores
            g.setColor(Color.ORANGE);
            g.drawString("ScoreBoard", 405, 15);
            g.setColor(Color.red);
            g.drawString("player1: " + player1.getScoreString(), 380, 30);
            g.setColor(Color.blue);
            g.drawString("player2: " + player2.getScoreString(), 445, 30);
        
            // countdown
//            if(gameSeconds <= 0){
//                
//            }
//            String time = String.format("%2d:%02d", gameSeconds / 60, gameSeconds % 60);
//            g.drawString(time, 500, 100);
        }
    }

    public static void delay(int mili) { // delay method
        try {
            Thread.sleep(mili); //run one frame for every x milliseconds
        } catch (InterruptedException e) { //if the delay code is interrupted
            System.out.println("ERROR IN SLEEPING"); // output statement "ERROR IN SLEEPING"
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tactical Assuault"); //create a new JFrame called Test Frame
        frame.getContentPane().add(new Final()); //Creates a constructor of class called Final
        frame.setSize(900, 700); //Set the size of the frame to be 900 * 700
        frame.setVisible(true); //makes the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // clears the data of the frame upon exit
    }

}


