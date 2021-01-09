///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Final;
//import java.awt.*; //Import java abstract window kit package 
//import java.awt.Dimension;
///**
// *
// * @author huyan
// */
//public class Menu {
//     public Rectangle playButton = new Rectangle (470, 250, 200, 80); // button to start playing the game
//     public Rectangle ruleButton = new Rectangle (470, 350, 200, 80); // button to go to the rules page
//     public Rectangle quitButton = new Rectangle (470, 450, 200, 80); // button to quit the game
//     public Rectangle menuButton = new Rectangle (435, 540, 320, 80); // button for returning to main menu from rules screen
//
//     public Menu(Graphics g, Dimension size){
//         Graphics2D g2d = (Graphics2D)g;
//         
//         Font font1 = new Font ("arial", Font.BOLD, 50);
//         g.setFont(font1);
//         g.setColor(Color.blue);
//         g.drawString("Shooting Game", 400, 200); 
//         
//         Font font2 = new Font("arial", Font.BOLD, 30);
//         g.setFont(font2);
//         g.drawString("Play", playButton.x + 19, playButton.y + 30);
//         g2d.draw(playButton);
////         g.drawString("Instructions", ruleButton.x + 19, ruleButton.y + 30);
////         g2d.draw(ruleButton);
//         g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
//         g2d.draw(quitButton);
//     }
//}
