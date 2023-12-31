package hw2_connectFour;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;
import java.awt.event.*;

//! Fatıma Tuğba Akdoğan
//! 210203002
public class GUI extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    //static variables
    static int WIDTH, HEIGHT, widthUnit, heightUnit, boardLength, boardHeight;
    static JFrame frame;
    static GUI instance; // for creating an object
    //why i made them all static?
    //bcs i want to access it from wherever i want(Board class) rather than with instances of that class


    //main method to run the application
    public static void main(String[] args) {
        instance = new GUI();
    }


    public GUI() {
        setBackground(Color.LIGHT_GRAY);

        frame = new JFrame("Connect 4");
        frame.setBounds(30, 30, WIDTH, HEIGHT);
        //The first parameter, 30, represents the x-coordinate of the frame's top-left corner on the screen.
        //The second parameter, 30, represents the y-coordinate of the frame's top-left corner on the screen.
        frame.add(this);//is adding the current instance of the class to the JFrame named frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//it automatically stop the program also
        frame.setVisible(true);//making it visible on the screen

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
        //timer.start(); initiates the timer, causing it to start generating action events after the specified delay of 10 milliseconds
        // and sending those events to the ActionListener for processing.

        frame.addMouseListener(this); // adds a mouse listener to the frame  to respond to various mouse events
        frame.addMouseMotionListener(this); //adds a mouse motion listener to the frame to handle mouse movement events like dragging
    }

    //Static blocks are executed first
    //these values are computed and set
    static {
        //represent the dimensions of the game's screen
        int screenWidth = 1200;
        int screenHeight = 750;

        //represent the game board
        boardLength = 7;
        boardHeight = 6;

        //widthUnit and heightUnit are calculated to determine the size of each cell on the board based on the screen dimensions and board dimensions.
        widthUnit = screenWidth / (boardLength + 2); //why plus two(+2) i want to leave space from the right and left of the screen
        WIDTH = widthUnit * (boardLength + 2);

        heightUnit = screenHeight / (boardHeight + 2); //to leave space from the top and bottom of the screen
        HEIGHT = heightUnit * (boardHeight + 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //This line invokes the paintComponent method of the superclass JPanel
        Board.draw(g); // invokes a static method draw(Graphics g) from a class Board
    }

    //I automatically implement these methods
    @Override
    public void actionPerformed(ActionEvent e) {repaint();}

    //override the MouseMotionListeners' methods
    @Override
    public void mouseMoved(MouseEvent e) {
        Board.hover(e.getX()); //i call the hover method which is static so i can call with the class name
    }

    @Override
    public void mouseDragged(MouseEvent e) {}


    //override the MouseListeners' methods
    @Override
    public void mousePressed(MouseEvent e) {
        Board.drop(); //i call the drop method
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


}
