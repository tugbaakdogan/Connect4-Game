package hw2_connectFour;

import java.awt.*;
import java.util.Arrays;

import static hw2_connectFour.GUI.*;
import static hw2_connectFour.PointPair.p1;
import static hw2_connectFour.PointPair.p2;

//! Fatıma Tuğba Akdoğan
//! 210203002

public class Board {

    //! The double square brackets [][] signify a two-dimensional structure
    //! first bracket pair represents rows, and second pair represents elements within each row.
    static Color[][] board;

    //declare a static array named players that stores objects of type Color
    //for this game different players are represented by different colors(yellow and red), and could hold those colors for easy access
    static Color[] players;

    //to indicate which player's turn it is=>0 1
    static int turn;

    //hoverX and hoverY represents the coordinates of the top of the piece. hoverY always be zero bcs Y coordinates will not change
    //(run and show it)
    static int hoverX, hoverY;

    //it can store true or false
    static boolean gameDone;




    //The static block runs first. Therefore, the initial state of the game is prepared
    //and can be returned to when necessary.
    static {
        board = new Color[boardLength][boardHeight];
        for (Color[] colors : board) { //for each loop
            Arrays.fill(colors, Color.WHITE); //It makes all elements of the board array to WHITE using Arrays.fill
        }
        players = new Color[]{Color.YELLOW, Color.RED}; //i created an array called players and data type is Color
        //It assigns two colors, Color.YELLOW and Color.RED, to the players array
        turn = 0;
    }


    public static void draw(Graphics g) {
        //It makes drawn objects look smoother and less pixelated.
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //This line determines the line thickness. The BasicStroke class is used to control the thickness of drawn lines.
        ((Graphics2D)(g)).setStroke(new BasicStroke(2.0f));


        for (int i = widthUnit; i <= WIDTH - widthUnit; i += widthUnit) {

            //it draws vertical lines
            g.setColor(Color.BLACK);
            g.drawLine(i, heightUnit, i, HEIGHT - heightUnit);
            //(i, heightUnit) specifies the starting point of the line. i refers to the horizontal position and heightUnit refers to the vertical position.
            //(i, HEIGHT - heightUnit) indicates the end point of the line. i refers to horizontal position and (HEIGHT - heightUnit) refers to vertical position.


            if (i == WIDTH - widthUnit) break; //This statement skips the last step after drawing the last vertical line before starting to place the game pieces again.


            for (int j = heightUnit; j < HEIGHT - heightUnit; j += heightUnit) {
                g.setColor(board[i/widthUnit - 1][j/heightUnit - 1]); //determines the color of the corresponding cell of the game board.
                g.fillOval(i + 5, j + 5, widthUnit - 10, heightUnit - 10); // minus ten is to arrange the shape properly between the lines
                g.setColor(Color.BLACK);//The border color of the circle is set to black
                g.drawOval(i + 5, j + 5, widthUnit - 10, heightUnit - 10);
            }//draws a circle in the shape of a game piece. While i and j values determine the position of the game piece, widthUnit and heightUnit can adjust the size of the game piece.
        }

        //This line draws the bottom edge of the game board.
        g.drawLine(widthUnit, HEIGHT - heightUnit, WIDTH - widthUnit, HEIGHT - heightUnit);

        //This line draws the top edge of the game board.
        g.drawLine(widthUnit, heightUnit, WIDTH - widthUnit, heightUnit);
        //(widthUnit, heightUnit) specifies the starting point of the line. widthUnit refers to the horizontal position and heightUnit refers to the vertical position.
        //(WIDTH - widthUnit, heightUnit) indicates the end point of the line. WIDTH - widthUnit refers to horizontal position and heightUnit refers to vertical position.

        //for the hover circle after the game is over
        g.setColor(gameDone ? Color.GRAY : players[turn]); //if the game is over circle becomes gray color, but if it is not over it changes depend on the player's turn
        g.fillOval(hoverX + 5, hoverY + 5, widthUnit - 10, heightUnit - 10);
        g.setColor(Color.BLACK);
        g.drawOval(hoverX + 5, hoverY + 5, widthUnit - 10, heightUnit - 10);


        //thanks to PaintPair class we can draw line to connect four
        //If points p1 and p2 are not null and both represent a particular point, draws a line between these two points
        g.setColor(Color.BLACK);
        if (p1 != null && p2 != null) {
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            //? why p1.x
            //? bcs type of the p1 is Point, but to draw a line we need integers so write a '.x' now we have int value
        }

    }


    //The purpose of this method is to properly align the mouse position to the boundaries of the cells on the game board,
    //so that it becomes easier to determine which cell on the game board is selected.
    public static void hover(int x) {
        x -= x%widthUnit; //corresponds to multiples of widthUnit
        if (x < widthUnit)
            x = widthUnit;//Trying to match the width of the board's cells to a position that fits
        if (x >= WIDTH - widthUnit) //WIDTH-widthUnit is beyond the right line of the board
            x = WIDTH - 2*widthUnit; //to prevent going beyond the right side of the board.
        hoverX = x;
        hoverY = 0;
        //hoverX represents the horizontal mouse position, while hoverY represents the vertical position. In this case, the vertical position is specified as 0. bcs vertical position doesn't change
    }

    public static void drop() {

        Color color = players[turn]; //It determines the color of the next player.
        int x = hoverX;
        int i;

        //This loop starts at the bottom of the selected column, finds an empty cell,
        //and places the game piece in the first empty cell. If the column is full or the limits are reached, the loop stops.
        for (i = 0; i < board[x/widthUnit - 1].length && board[x/widthUnit - 1][i] == Color.WHITE; i++) {
            board[x/widthUnit - 1][i] = color; //When an empty cell is found, it assigns the color of the game piece to that cell.
            board[x/widthUnit - 1][i] = Color.WHITE; //paint the cell's tops white

        }

        board[x/widthUnit - 1][i - 1] = color; //It places the game piece in the previous cell.
        // Since the last loop step is placed in the empty cell, this step puts the actual game piece in the previous cell.
        checkConnect(x/widthUnit - 1, i - 1);//we call checkConnect method

        // Passes to next player
        turn = (turn + 1) % players.length;
    }

    public static void checkConnect(int x, int y) {

        PointPair pair = search(board, x, y); //calls the search method

        if (pair != null) {//If pair is not empty (connection found), joins the specified points
            p1 = new Point((p1.x + 1) * widthUnit + widthUnit / 2, (p1.y + 1) * heightUnit + heightUnit / 2);//? why "/2" because it will connect the points from the center of the full circle
            p2 = new Point((p2.x + 1) * widthUnit + widthUnit / 2, (p2.y + 1) * heightUnit + heightUnit / 2);
            frame.removeMouseListener(instance); //after there is a connection it prevents user to add new pieces to board
            gameDone = true; //so game over
        }
    }

    public static PointPair search(Color[][] arr, int i, int j) {
        Color color = arr[i][j]; //It assigns the color at the starting point to the color variable
        int left, right, up, down;

        // check horizontally left to right
        left = right = i;
        while (left >= 0 && arr[left][j] == color) left--;//It searches for cells of the same color by decreasing the left index (moving in the left direction).
        // This cycle continues as long as the conditions are within left's board borders and the cell is the same color.
        // This loop counts cells of the same color from the left of the board to a given starting point.
        left++;//When the loop is exited, left is the one before the last cell of the same color. so we should increase by one

        while (right < arr.length && arr[right][j] == color) right++;
        right--;
        if (right - left >= 3) { //Checks if the range is at least 4 cells long. If 4 or more cells are the same color, this indicates a specific connection.
            return new PointPair(left, j, right, j);//and draws line
        }

        // check vertically top to bottom
        //Finds how many cells are of the same color by moving downwards from the starting point
        down = j;
        while (down < arr[i].length && arr[i][down] == color) down++;
        // It searches for cells of the same color in a downward direction
        // This cycle continues as long as the conditions are within the down's board boundaries and the cell is the same color
        // This loop counts cells of the same color from the bottom of the board to a specific starting point
        down--;//When the loop is exited, down is the previous cell of the same color. Therefore, in the next step,
        // it reduces the down value to the correct position in order to move from this cell to the previous cell.
        if (down - j >= 3) {
            return new PointPair(i, j, i, down);
        }

        // check diagonal top left to bottom right
        left = right = i;
        up = down = j;
        while (left >= 0 && up >= 0 && arr[left][up] == color) { left--; up--; }
        //Counts cells of the same color in the upper left diagonal direction. By decreasing the left and up indices,
        // the border of cells of the same color in the diagonal direction is found
        left++; up++;
        while (right < arr.length && down < arr[right].length && arr[right][down] == color) { right++; down++; }
        right--; down--;
        //The second while loop counts cells of the same color in the lower right diagonal direction. By increasing the right and down indices,
        // the border of cells of the same color in the diagonal direction is found
        if (right - left >= 3 && down - up >= 3) {
            return new PointPair(left, up, right, down);
        }

        // check diagonal top right to bottom left
        left = right = i;
        up = down = j;
        while (left >= 0 && down < arr[left].length && arr[left][down] == color) {left--; down++;}
        //The first while loop counts cells of the same color in the upper right diagonal direction. By decreasing left and increasing down,
        //the border of cells of the same color in the diagonal direction is found.
        left++; down--;
        while (right < arr.length && up >= 0 && arr[right][up] == color) {right++; up--;}
        right--; up++;
        //The second while loop counts cells of the same color in the lower left diagonal direction. By increasing right and decreasing up,
        // the border of cells of the same color in the diagonal direction is found.
        if (right - left >= 3 && down - up >= 3) {
            return new PointPair(left, down, right, up);
        }

        return null;
    }

}

