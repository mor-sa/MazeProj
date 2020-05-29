package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
This is a class that defines a maze, it's attributes and methods.
 */

public class Maze implements Serializable {
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

    public Maze(byte[] byteArr) {
        this.rowsNum = (byteArr[0]*255) + (byteArr[1] & 0xFF);
        this.colsNum = (byteArr[2]*255) + (byteArr[3] & 0xFF);
        int startPosRow = (byteArr[4]*255) + (byteArr[5] & 0xFF);
        int startPosCol = (byteArr[6]*255) + (byteArr[7] & 0xFF);
        int goalPosRow = (byteArr[8]*255) + (byteArr[9] & 0xFF);
        int goalPosCol = (byteArr[10]*255) + (byteArr[11] & 0xFF);
        this.startPos = new Position(startPosRow, startPosCol);
        this.goalPos = new Position(goalPosRow, goalPosCol);
        this.ArrMaze = new int[this.rowsNum][this.colsNum];
        int index = 12;
        for (int i=0; i<this.rowsNum; i++){
            for (int j=0; j<this.colsNum; j++){
                this.ArrMaze[i][j] = byteArr[index];
                index++;
            }
        }
    }

    public void setStartPosition(int row, int y){ this.startPos = new Position(row, y); }
    public void setGoalPosition(int row, int y){ this.goalPos = new Position(row, y); }

    public Position getStartPosition(){ return startPos; }
    public Position getGoalPosition(){ return goalPos; }

    public int getRowsNum() { return rowsNum; }
    public int getColsNum() { return colsNum; }

    public void setValue(Position pos, int val){ this.ArrMaze[pos.getRowIndex()][pos.getColumnIndex()] = val; }
    public int getValue(Position pos){ return this.ArrMaze[pos.getRowIndex()][pos.getColumnIndex()]; }

    // Method that returns a list of neighbors positions to a given position
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

    public void printColor() {
        for(int i = 0; i < this.ArrMaze.length; ++i) {
            for(int j = 0; j < this.ArrMaze[i].length; ++j) {
                if (i == this.startPos.getRowIndex() && j == this.startPos.getColumnIndex()) {
                    System.out.print(" \u001b[44m ");
                } else if (i == this.goalPos.getRowIndex() && j == this.goalPos.getColumnIndex()) {
                    System.out.print(" \u001b[42m ");
                } else if (this.ArrMaze[i][j] == 1) {
                    System.out.print(" \u001b[45m ");
                } else {
                    System.out.print(" \u001b[107m ");
                }
            }
            System.out.println(" \u001b[107m");
        }
    }

    public void printColorSolution(ArrayList<Position> path) {
        for(int i = 0; i < this.ArrMaze.length; ++i) {
            for(int j = 0; j < this.ArrMaze[i].length; ++j) {
                if (i == this.startPos.getRowIndex() && j == this.startPos.getColumnIndex()) {
                    System.out.print(" \u001b[44m ");
                } else if (i == this.goalPos.getRowIndex() && j == this.goalPos.getColumnIndex()) {
                    System.out.print(" \u001b[44m ");
                } else if (this.ArrMaze[i][j] == 1) {
                    System.out.print(" \u001b[45m ");
                }
                else if (path.contains(new Position(i, j))) {
                    System.out.print(" \u001b[41m ");
                }
                else {
                    System.out.print(" \u001b[107m ");
                }

            }
            System.out.println(" \u001b[107m");
        }
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

    // Method that sets a random goal position after setting a start position
    public void setRandGoalPos() {
        do {
            Position goalPos = this.randPosOnVertex();
            this.setGoalPosition(goalPos.getRowIndex(), goalPos.getColumnIndex());
            // if the goal position on same vertex as start goal or a wall, keeps setting new goal pos
        } while (this.sameVertex(this.getStartPosition(), this.getGoalPosition()) || this.getValue(this.getGoalPosition()) == 1);
    }

    // Gets the clock neighbors with value 0 of a position
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
        if (pos.getRowIndex() -1 >= 0 && pos.getColumnIndex() + 1 < this.getColsNum()){
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
        if ((pos.getColumnIndex() + 1) < (this.getColsNum())) {
            curPos.setRowIndex(pos.getRowIndex());
            curPos.setColumnIndex(pos.getColumnIndex() + 1);
            if (getValue(curPos) == 0){
                clockNeighbors.add(new Position(pos.getRowIndex(), pos.getColumnIndex() + 1));
            }
        }
        // Right down
        if (pos.getColumnIndex() +1 < this.getColsNum() && pos.getRowIndex() + 1 < this.getRowsNum()){
            curPos.setRowIndex(pos.getRowIndex() + 1);
            curPos.setColumnIndex(pos.getColumnIndex() + 1);
            if (getValue(curPos) ==0){
                if ((getValue(new Position(pos.getRowIndex(),pos.getColumnIndex()+1)) == 0) || (getValue(new Position(pos.getRowIndex()+1, pos.getColumnIndex())) == 0)){
                    clockNeighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex() + 1));
                }
            }
        }
        // Down
        if (pos.getRowIndex() + 1 < this.getRowsNum()) {
            curPos.setRowIndex(pos.getRowIndex() + 1);
            curPos.setColumnIndex(pos.getColumnIndex());
            if (getValue(curPos) == 0){
                clockNeighbors.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex()));
            }
        }
        // Down Left
        if (pos.getRowIndex() + 1 < this.getRowsNum() && pos.getColumnIndex() -1 >= 0) {
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

    public byte[] toByteArray(){
        byte[] byteArr = new byte[12 + (this.rowsNum * this.colsNum)];
        // represent row number in bytes
        byteArr[0] = (byte)(this.rowsNum / 255);
        byteArr[1] = (byte)(this.rowsNum % 255);
        // represent column number in bytes
        byteArr[2] = (byte)(this.colsNum / 255);
        byteArr[3] = (byte)(this.colsNum % 255);
        // represent start position row index in bytes
        byteArr[4] = (byte)(this.getStartPosition().getRowIndex() / 255);
        byteArr[5] = (byte)(this.getStartPosition().getRowIndex() % 255);
        // represent start position col index in bytes
        byteArr[6] = (byte)(this.getStartPosition().getColumnIndex() / 255);
        byteArr[7] = (byte)(this.getStartPosition().getColumnIndex() % 255);
        // represent goal position row index in bytes
        byteArr[8] = (byte)(this.getGoalPosition().getRowIndex() / 255);
        byteArr[9] = (byte)(this.getGoalPosition().getRowIndex() % 255);
        // represent goal position col index in bytes
        byteArr[10] = (byte)(this.getGoalPosition().getColumnIndex() / 255);
        byteArr[11] = (byte)(this.getGoalPosition().getColumnIndex() % 255);

        // represent the maze array in bytes
        for (int i=0; i<this.rowsNum; i++){
            for (int j=0; j<this.colsNum; j++){
                byteArr[12 + (this.colsNum * i) + j] = (byte)(this.ArrMaze[i][j]);
            }
        }
        return byteArr;
    }
}
