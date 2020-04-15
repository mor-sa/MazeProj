package algorithms.mazeGenerators;

/*
This is a class that defines a maze, it's attributes and methods.
 */

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int[][] ArrMaze;
    private Position startPos;
    private Position goalPos;
    private int rowsNum;
    private int colsNum;

    public Maze(int rowsNum, int colsNum) {
        this.ArrMaze = new int[rowsNum][colsNum];
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        this.startPos = new Position(0,0);
        this.goalPos = new Position(rowsNum-1, colsNum-1);
    }

    public void setStartPosition(int row, int y){ this.startPos = new Position(row, y); }
    public void setGoalPosition(int row, int y){ this.goalPos = new Position(row, y); }

    public Position getStartPosition(){ return startPos; }
    public Position getGoalPosition(){ return goalPos; }

    public int getRowsNum() { return rowsNum; }
    public int getColsNum() { return colsNum; }

    public void setValue(Position pos, int val){ this.ArrMaze[pos.getRowIndex()][pos.getColumnIndex()] = val; }
    public int getValue(Position pos){ return this.ArrMaze[pos.getRowIndex()][pos.getColumnIndex()]; }

    // Method that returns a list of all neighbors positions of a given position
    public List<Position> getNeighbors(Position pos) {
        List<Position> neighbors = new ArrayList<>();
        if (pos.getRowIndex() - 1 >= 0) {
            neighbors.add(new Position(pos.getRowIndex() - 1, pos.getColumnIndex()));
        }
        if (pos.getRowIndex() + 1 < this.getRowsNum()) {
            neighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex()));
        }
        if (pos.getColumnIndex() - 1 >= 0) {
            neighbors.add(new Position(pos.getRowIndex(), pos.getColumnIndex() - 1));
        }
        if (pos.getColumnIndex() + 1 < this.getColsNum()) {
            neighbors.add(new Position(pos.getRowIndex(), pos.getColumnIndex() + 1));
        }
        return neighbors;
    }

    // Method that prints a maze with 0 as path, 1 as walls, s as start and e as goal point
    public void print(){
        for (int i=0; i<rowsNum; i++){
            for (int j=0; j<colsNum; j++){
                if((i==startPos.getRowIndex()) && (j==startPos.getColumnIndex())){
                    System.out.print("S");
                }
                else if((i==goalPos.getRowIndex()) && (j==goalPos.getColumnIndex())){
                    System.out.print("E");
                }
                else
                {
                    System.out.print(ArrMaze[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Method that returns a random position on vertices
    public Position randPosOnVertex(){
        int PosX, PosY;
        PosX = (int)(Math.random()*this.getRowsNum());
        if (PosX == 0 || PosX == this.getRowsNum()-1){
            PosY = (int)(Math.random()*this.getColsNum());
        }
        else{
            PosY = ((int)(Math.random()*2)) * (this.getColsNum()-1);
        }
        return new Position(PosX, PosY);
    }

    // Method that returns a boolean if 2 positions are on the same vertex
    public boolean sameVertex(Position start, Position goal){
        boolean sameVertex = false;
        if (start.getRowIndex()== 0 && goal.getRowIndex() == 0){
            sameVertex = true;
        }
        if (start.getRowIndex() == this.getRowsNum()-1 && goal.getRowIndex() == this.getRowsNum()-1){
            sameVertex = true;
        }
        if (start.getColumnIndex() == 0 && goal.getColumnIndex() == 0){
            sameVertex = true;
        }
        if (start.getColumnIndex() == this.getColsNum()-1 && goal.getColumnIndex() == this.getColsNum()-1){
            sameVertex = true;
        }
        return sameVertex;
    }

    // Method that returns the clock neighbors of a given position if the neighbor position is a path cell
    public List<Position> getClockNeighbors(Position pos){
        List<Position> clockNeighbors = new ArrayList<>();
        Position curPos = new Position(0,0);
        // Up
        if (pos.getRowIndex() - 1 >= 0) {
            curPos.setRowIndex(pos.getRowIndex() - 1);
            curPos.setColumnIndex(pos.getColumnIndex());
            if (getValue(curPos)==0){
                clockNeighbors.add(new Position(pos.getRowIndex() - 1, pos.getColumnIndex()));
            }
        }
        // Up right
        if (pos.getRowIndex() -1 >= 0 && pos.getColumnIndex() + 1 <= this.getColsNum() -1){
            // Check if up and right are 0 or other path 0
            curPos.setRowIndex(pos.getRowIndex() - 1);
            curPos.setColumnIndex(pos.getColumnIndex() + 1);
            if (getValue(curPos) == 0) {
                if ((getValue(new Position(pos.getRowIndex(), pos.getColumnIndex() + 1)) == 0) || (getValue(new Position(pos.getRowIndex()-1, pos.getColumnIndex())) == 0)) {
                    clockNeighbors.add(new Position(pos.getRowIndex() - 1, pos.getColumnIndex() + 1));
                }
            }
        }
        // Right
        if ((pos.getColumnIndex() + 1) <= (this.getColsNum() - 1)) {
            curPos.setRowIndex(pos.getRowIndex());
            curPos.setColumnIndex(pos.getColumnIndex() + 1);
            if (getValue(curPos) == 0){
                clockNeighbors.add(new Position(pos.getRowIndex(), pos.getColumnIndex() + 1));
            }
        }
        // Right down
        if (pos.getColumnIndex() +1 <= this.getColsNum() -1 && pos.getRowIndex() + 1 <= this.getRowsNum() - 1){
            curPos.setRowIndex(pos.getRowIndex() + 1);
            curPos.setColumnIndex(pos.getColumnIndex() + 1);
            if (getValue(curPos) ==0){
                if ((getValue(new Position(pos.getRowIndex(),pos.getColumnIndex()+1)) == 0) || (getValue(new Position(pos.getRowIndex()+1, pos.getColumnIndex())) == 0)){
                    clockNeighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex() + 1));
                }
            }
        }
        // Down
        if (pos.getRowIndex() + 1 <= this.getRowsNum() - 1) {
            curPos.setRowIndex(pos.getRowIndex() + 1);
            curPos.setColumnIndex(pos.getColumnIndex());
            if (getValue(curPos) == 0){
                clockNeighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex()));
            }
        }
        // Down Left
        if (pos.getRowIndex() + 1 <= this.getRowsNum() - 1 && pos.getColumnIndex() -1 >= 0) {
            curPos.setRowIndex(pos.getRowIndex() + 1);
            curPos.setColumnIndex(pos.getColumnIndex()-1);
            if (getValue(curPos) == 0){
                if ((getValue(new Position(pos.getRowIndex()+1, pos.getColumnIndex())) == 0) || (getValue(new Position(pos.getRowIndex(), pos.getColumnIndex()-1)) == 0)){
                    clockNeighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex()-1));
                }
            }
        }
        // Left
        if (pos.getColumnIndex() - 1 >= 0) {
            curPos.setRowIndex(pos.getRowIndex());
            curPos.setColumnIndex(pos.getColumnIndex() - 1);
            if (getValue(curPos) == 0){
                clockNeighbors.add(new Position(pos.getRowIndex(), pos.getColumnIndex() - 1));
            }
        }
        // Left Up
        if (pos.getColumnIndex() - 1 >= 0 && pos.getRowIndex() - 1 >= 0){
            curPos.setRowIndex(pos.getRowIndex() - 1);
            curPos.setColumnIndex(pos.getColumnIndex() - 1);
            if (getValue(curPos) == 0){
                if ((getValue(new Position(pos.getRowIndex()-1, pos.getColumnIndex())) == 0) || (getValue(new Position(pos.getRowIndex(), pos.getColumnIndex()-1)) == 0)){
                    clockNeighbors.add(new Position(pos.getRowIndex() - 1, pos.getColumnIndex() - 1));
                }
            }
        }
        return clockNeighbors;
    }
}
