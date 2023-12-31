package hw2_connectFour;

import java.awt.*;

//! Fatıma Tuğba Akdoğan
//! 210203002
public class PointPair {

    public static Point p1, p2;
    //why i made them static?
    //bcs i want to access with the class itself rather than with instances of that class

    PointPair(int x1, int y1, int x2, int y2) {
        p1 = new Point(x1, y1); //p1's coordinates
        p2 = new Point(x2, y2); //p2's coordinates
    }
}
