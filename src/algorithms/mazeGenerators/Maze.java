package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int[][] theMaze;
    private Position startPos;
    private Position goalPos;
    private int rowsNum;
    private int colsNum;

    public Maze(int rowsNum, int colsNum) {
        this.theMaze = new int[rowsNum][colsNum];
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        this.startPos = new Position(0,0);
        this.goalPos = new Position(rowsNum-1, colsNum-1);
    }

    public void setStartPosition(int x, int y){
        this.startPos = new Position(x, y);
    }
    public void setGoalPosition(int x, int y){
        this.goalPos = new Position(x, y);
    }

    public Position getStartPosition(){
        return startPos;
    }
    public Position getGoalPosition(){
        return goalPos;
    }
    public int getRowsNum() { return rowsNum; }
    public int getColsNum() { return colsNum; }

    public void setValue(Position pos, int val){
        this.theMaze[pos.getX()][pos.getY()] = val;
    }

    public int getValue(Position pos){
        return this.theMaze[pos.getX()][pos.getY()];
    }

    // Method that returns a list of neighbors positions to a given position
    public List<Position> getNeighbors(Position pos){
        List<Position> neighbors = new ArrayList<>();
        Position up = new Position(pos.getX() - 1, pos.getY());
        Position under = new Position(pos.getX() + 1, pos.getY());
        Position left = new Position(pos.getX(), pos.getY() - 1);
        Position right = new Position(pos.getX() , pos.getY() + 1);
        if ((pos.getX() != 0) && (pos.getX() != this.getRowsNum() - 1)){
            neighbors.add(up);
            neighbors.add(under);
        }
        else if (pos.getX() == 0 ){
            neighbors.add(under);
        }
        else{
            neighbors.add(up);
        }
        if ((pos.getY() != 0) && (pos.getY() != this.getColsNum() - 1)){
            neighbors.add(left);
            neighbors.add(right);
        }
        else if (pos.getY() == 0 ){
            neighbors.add(right);
        }
        else{
            neighbors.add(left);
        }
        return neighbors;
    }

   // public Position getPos()

    public void print(){
        for (int i=0; i<rowsNum; i++){
            for (int j=0; j<colsNum; j++){
                if((i==startPos.getX()) && (j==startPos.getY())){
                    System.out.print("S");
                }
                else if((i==goalPos.getX()) && (j==goalPos.getY())){
                    System.out.print("E");
                }
                else
                {
                    System.out.print(theMaze[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Function to set the start and goal positions of the maze
    public void setStartAndGoal(){
        int startPosX, startPosY, goalPosX, goalPosY, sameVertex;
        do{
            sameVertex = 0;
            startPosX = (int)(Math.random()*this.getRowsNum());
            goalPosX = (int)(Math.random()*this.getRowsNum());
            if (startPosX == 0 || startPosX == this.getRowsNum()-1){
                startPosY = (int)(Math.random()*this.getColsNum());
            }
            else{
                startPosY = ((int)(Math.random()*2)) * (this.getColsNum()-1);
            }
            if (goalPosX == 0 || goalPosX == this.getRowsNum() -1){
                goalPosY = (int)(Math.random()*this.getColsNum());
            }
            else {
                goalPosY = ((int) (Math.random() * 2)) * (this.getColsNum()-1);
            }
            if (startPosX == 0 && goalPosX == 0){
                sameVertex = 1;
            }
            if (startPosX == this.getRowsNum()-1 && goalPosX == this.getRowsNum()-1){
                sameVertex = 1;
            }
            if (startPosY == 0 && goalPosY == 0){
                sameVertex = 1;
            }
            if (startPosY == this.getColsNum()-1 && goalPosY == this.getColsNum()-1){
                sameVertex = 1;
            }
        }while(sameVertex == 1);
        this.setStartPosition(startPosX, startPosY);
        this.setGoalPosition(goalPosX, goalPosY);
    }
}
