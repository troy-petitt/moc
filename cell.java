import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// Represents a maze cell
class Cell {
    ArrayList<CellEdge> outEdges;
    boolean traversed;
    boolean correctPath;
    int x;
    int y;
    
    // Creates an unconnected cell with the given coordinates
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.traversed = false;
        this.correctPath = false;
        this.outEdges = new ArrayList<CellEdge>();
    }
    // Add the given edge to this node's list of edges
    // Note: assumes that the given edge's from field is this
    // EFFECT: modifies this.outEdges
    void addEdge(CellEdge edge) {
        outEdges.add(edge);
    }
    // Add the given edge to this cell and add the reverse edge
    // to the given edge's 'to' cell
    // Note: assumes that the given edge's from field is this
    // EFFECT: modifies this.outEdges and edge.to
    void addUndirectedEdge(CellEdge edge) {
        this.addEdge(edge);
        edge.to.addEdge(new CellEdge(edge.to, edge.from, edge.weight)); 
    }
    // Draw this cell, with walls where this cell is not
    // connected to an adjacent cell
    WorldImage drawCell() {
        int xPx = (2 * this.x + 1) * MazeWorld.CELL_SIZE / 2;
        int yPx = (2 * this.y + 1) * MazeWorld.CELL_SIZE / 2;
        int halfWidth = MazeWorld.CELL_SIZE / 2;
        int wallWidth = MazeWorld.CELL_SIZE / 10;
        Color wallColor = new Color(105, 105, 105);
        
        // Determine the color of the cell
        Color color;
        if (this.correctPath) {
            color = new Color(65, 105, 225);
        }
        else if (this.x == 0 && this.y == 0) {
            color = new Color(0, 102, 0);
        }
        else if (this.x == MazeWorld.MAZE_WIDTH - 1 &&
                 this.y == MazeWorld.MAZE_HEIGHT - 1) {
            color = new Color(51, 0, 102);
        }
        else if (this.traversed) {
            color = new Color(100, 149, 237);
        }
        else {
            color = new Color(190, 190, 190);
        }
        // Draw the cell
        WorldImage img = new RectangleImage(new Posn(xPx, yPx),
                                           MazeWorld.CELL_SIZE,
                                           MazeWorld.CELL_SIZE,
                                           color);
        // Determine where the  walls are
        boolean left = true;
        boolean right = true;
        boolean top = true;
        boolean bottom = true;
        for (CellEdge edge : this.outEdges) {
            Cell c = edge.to;
            if (c.x < this.x) {
                left = false;
            }
            else if (c.x > this.x) {
                right = false;
            }
            else if (c.y < this.y) {
                top = false;
            }
            else if (c.y > this.y) {
                bottom = false;
            }
        }
        // Draw the walls
        if (left) {
            img = img.overlayImages(new RectangleImage(
                    new Posn(xPx - halfWidth, yPx),
                    wallWidth,
                    MazeWorld.CELL_SIZE,
                    wallColor));
        }
        if (right) {
            img = img.overlayImages(new RectangleImage(
                    new Posn(xPx + halfWidth, yPx),
                    wallWidth,
                    MazeWorld.CELL_SIZE,
                    wallColor));
        }
        if (top) {
            img = img.overlayImages(new RectangleImage(
                    new Posn(xPx, yPx - halfWidth),
                    MazeWorld.CELL_SIZE,
                    wallWidth,
                    wallColor));
        }
        if (bottom) {
            img = img.overlayImages(new RectangleImage(
                    new Posn(xPx, yPx + halfWidth),
                    MazeWorld.CELL_SIZE,
                    wallWidth,
                    wallColor));
        }
        return img;
    }
    public int hashCode() {
        return this.x * 37 + this.y * 47;
    }
    public boolean equals(Object other) {
        if (!(other instanceof Cell)) {
            return false;
        }
        Cell that = (Cell) other;
        return this.traversed == that.traversed &&
               this.correctPath == that.correctPath &&
               this.x == that.x && this.y == that.y;
    }
}

// Represents the one-way weighted connection between two cells
class CellEdge {
    Cell from;
    Cell to;
    int weight;
    
    // Create a new edge between the two given cells with the given weight
    CellEdge(Cell from, Cell to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class ExamplesCell {
    Cell c1;
    Cell c2;
    Cell c3;
    Cell c4;
    Cell c5;
    Cell c6;
    Cell c7;
    Cell c8;
    Cell c9;
    CellEdge ce1;
    CellEdge ce2;
    CellEdge ce3;
    CellEdge ce4;
    CellEdge ce5;
    WorldImage c1Img;
    WorldImage c2Img;
    WorldImage c3Img;
    WorldImage c4Img;
    WorldImage c5Img;
    WorldImage c6Img;
    WorldImage c7Img;
    WorldImage c8Img;
    WorldImage c9Img;
    
    void initialize() {
        /* +----+----+----+
         * | c1 | c2 - c3 |
         * + \/ + || + /\ +
         * | c4 - c5 > c6 |
         * + /\ +----+ \/ +
         * | c7 > c8 | c9 |
         * +----+----+----+
         */
        this.c1 = new Cell(0, 0);
        this.c2 = new Cell(1, 0);
        this.c3 = new Cell(2, 0);
        this.c4 = new Cell(0, 1);
        this.c5 = new Cell(1, 1);
        this.c6 = new Cell(2, 1);
        this.c7 = new Cell(0, 2);
        this.c8 = new Cell(1, 2);
        this.c9 = new Cell(2, 2);
        
        this.c1.outEdges.add(new CellEdge(this.c1, this.c4, 10));
        this.c2.outEdges.addAll(Arrays.asList(new CellEdge(this.c2, this.c3, 10),
                                              new CellEdge(this.c2, this.c5, 5)));
        this.c3.outEdges.add(new CellEdge(this.c3, this.c2, 10));
        this.c4.outEdges.add(new CellEdge(this.c4, this.c5, 3));
        this.c5.outEdges.addAll(Arrays.asList(new CellEdge(this.c5, this.c2, 1),
                                              new CellEdge(this.c5, this.c4, 12),
                                              new CellEdge(this.c5, this.c6, 7)));
        this.c6.outEdges.addAll(Arrays.asList(new CellEdge(this.c6, this.c3, 4),
                                              new CellEdge(this.c6, this.c9, 6)));
        this.c7.outEdges.addAll(Arrays.asList(new CellEdge(this.c7, this.c4, 5),
                                              new CellEdge(this.c7, this.c8, 15)));
        // For testing adding edges
        this.ce1 = new CellEdge(this.c5, this.c8, 13);
        this.ce2 = new CellEdge(this.c6, this.c5, 4);
        this.ce3 = new CellEdge(this.c8, this.c5, 13);
        this.ce4 = new CellEdge(this.c1, this.c2, 1);
        this.ce5 = new CellEdge(this.c2, this.c1, 1);
        
        // For testing drawing
        this.c1.traversed = true;
        this.c2.traversed = true;
        this.c3.correctPath = true;

        Color dBlue = new Color(65, 105, 225);
        Color green = new Color(0, 102, 0);
        Color blue = new Color(100, 149, 237);
        Color grey = new Color(190, 190, 190);
        Color dGrey = new Color(105, 105, 105);
        RectangleImage cellBaseGrey = new RectangleImage(new Posn(0, 0), 10, 10, grey);
        RectangleImage cellBaseGreen = new RectangleImage(new Posn(0, 0), 10, 10, green);
        RectangleImage cellBaseBlue = new RectangleImage(new Posn(0, 0), 10, 10, blue);
        RectangleImage cellBaseDBlue = new RectangleImage(new Posn(0, 0), 10, 10, dBlue);
        RectangleImage cellWallHorizontal = new RectangleImage(new Posn(0, 0), 10, 1, dGrey);
        RectangleImage cellWallVertical = new RectangleImage(new Posn(0, 0), 1, 10, dGrey);
        
        c1Img = cellBaseGreen.getMovedImage(5, 5).overlayImages(
                     cellWallVertical.getMovedImage(0, 5)).overlayImages(
                     cellWallVertical.getMovedImage(10, 5)).overlayImages(
                     cellWallHorizontal.getMovedImage(5, 0));
        c2Img = cellBaseBlue.getMovedImage(15, 5).overlayImages(
                     cellWallVertical.getMovedImage(10, 5)).overlayImages(
                     cellWallHorizontal.getMovedImage(15, 0));
        c3Img = cellBaseDBlue.getMovedImage(25, 5).overlayImages(
                     cellWallVertical.getMovedImage(30, 5)).overlayImages(
                     cellWallHorizontal.getMovedImage(25, 0)).overlayImages(
                     cellWallHorizontal.getMovedImage(25, 10));
        c4Img = cellBaseGrey.getMovedImage(5, 15).overlayImages(
                     cellWallVertical.getMovedImage(0, 15)).overlayImages(
                     cellWallHorizontal.getMovedImage(5, 10)).overlayImages(
                     cellWallHorizontal.getMovedImage(5, 20));
        c5Img = cellBaseGrey.getMovedImage(15, 15).overlayImages(
                     cellWallHorizontal.getMovedImage(15, 20));
        c6Img = cellBaseGrey.getMovedImage(25, 15).overlayImages(
                     cellWallVertical.getMovedImage(20, 15)).overlayImages(
                     cellWallVertical.getMovedImage(30, 15));
        c7Img = cellBaseGrey.getMovedImage(5, 25).overlayImages(
                     cellWallVertical.getMovedImage(0, 25)).overlayImages(
                     cellWallHorizontal.getMovedImage(5, 30));
        c8Img = cellBaseGrey.getMovedImage(15, 25).overlayImages(
                     cellWallVertical.getMovedImage(10, 25)).overlayImages(
                     cellWallVertical.getMovedImage(20, 25)).overlayImages(
                     cellWallHorizontal.getMovedImage(15, 20)).overlayImages(
                     cellWallHorizontal.getMovedImage(15, 30));
        c9Img = cellBaseGrey.getMovedImage(25, 25).overlayImages(
                     cellWallVertical.getMovedImage(20, 25)).overlayImages(
                     cellWallVertical.getMovedImage(30, 25)).overlayImages(
                     cellWallHorizontal.getMovedImage(25, 20)).overlayImages(
                     cellWallHorizontal.getMovedImage(25, 30));
    }
    
    void testAddEdge(Tester t) {
        this.initialize();
        c5.addEdge(ce1);
        t.checkExpect(c5.outEdges.get(3), ce1);
        c6.addEdge(ce2);
        t.checkExpect(c6.outEdges.get(2), ce2);
    }
    void testAddUndirectedEdge(Tester t) {
        this.initialize();
        c5.addUndirectedEdge(ce1);
        t.checkExpect(c5.outEdges.get(3), ce1);
        t.checkExpect(c8.outEdges.get(0), ce3);
        c1.addUndirectedEdge(ce4);
        t.checkExpect(c1.outEdges.get(1), ce4);
        t.checkExpect(c2.outEdges.get(2), ce5);
    }
    void testDrawCell(Tester t) {
        this.initialize();
        t.checkExpect(this.c1.drawCell(), c1Img);
        t.checkExpect(this.c2.drawCell(), c2Img);
        t.checkExpect(this.c3.drawCell(), c3Img);
        t.checkExpect(this.c4.drawCell(), c4Img);
        t.checkExpect(this.c5.drawCell(), c5Img);
        t.checkExpect(this.c6.drawCell(), c6Img);
        t.checkExpect(this.c7.drawCell(), c7Img);
        t.checkExpect(this.c8.drawCell(), c8Img);
        t.checkExpect(this.c9.drawCell(), c9Img);
    }
    void testHashCode(Tester t) {
        t.checkExpect(new Cell(0, 0).hashCode(), 0);
        t.checkExpect(new Cell(0, 1).hashCode(), 47);
        t.checkExpect(new Cell(1, 0).hashCode(), 37);
        t.checkExpect(new Cell(13, 52).hashCode(), 2925);
        t.checkExpect(new Cell(12, 8).hashCode(), 820);
    }
    void testEquals(Tester t) {
        Cell cell1 = new Cell(0, 1);
        cell1.correctPath = true;
        Cell cell2 = new Cell(1, 1);
        cell2.correctPath = true;
        Cell cell3 = new Cell(0, 2);
        cell3.correctPath = true;
        Cell cell4 = new Cell(0, 1);
        cell4.correctPath = true;
        cell4.traversed = true;
        Cell cell5 = new Cell(0, 1);
        Cell cell6 = new Cell(0, 1);
        cell6.correctPath = true;
        
        t.checkExpect(cell1.equals(new Posn(0, 1)), false);
        t.checkExpect(cell1.equals(cell2), false);
        t.checkExpect(cell1.equals(cell3), false);
        t.checkExpect(cell1.equals(cell4), false);
        t.checkExpect(cell1.equals(cell5), false);
        t.checkExpect(cell1.equals(cell6), true);
        t.checkExpect(cell6.equals(cell1), true);
    }
}
