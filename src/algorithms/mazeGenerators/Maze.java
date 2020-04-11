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

    public void setStartPosition(int x, int y){ this.startPos = new Position(x, y); }
    public void setGoalPosition(int x, int y){ this.goalPos = new Position(x, y); }

    public Position getStartPosition(){ return startPos; }
    public Position getGoalPosition(){ return goalPos; }

    public int getRowsNum() { return rowsNum; }
    public int getColsNum() { return colsNum; }

    public void setValue(Position pos, int val){ this.theMaze[pos.getX()][pos.getY()] = val; }
    public int getValue(Position pos){ return this.theMaze[pos.getX()][pos.getY()]; }

    // Method that returns a list of neighbors positions to a given position
    public List<Position> getNeighbors(Position pos) {
        List<Position> neighbors = new ArrayList<>();
        if (pos.getX() - 1 >= 0) {
            neighbors.add(new Position(pos.getX() - 1, pos.getY()));
        }
        if (pos.getX() + 1 <= this.getRowsNum() - 1) {
            neighbors.add(new Position(pos.getX() + 1, pos.getY()));
        }
        if (pos.getY() - 1 >= 0) {
            neighbors.add(new Position(pos.getX(), pos.getY() - 1));
        }
        if (pos.getY() + 1 <= this.getColsNum() - 1) {
            neighbors.add(new Position(pos.getX(), pos.getY() + 1));
        }
        return neighbors;
    }

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

    // Function to randomly set the start and goal positions of the maze
//    public void setStartAndGoal(){
//        int startPosX, startPosY, goalPosX, goalPosY, sameVertex;
//        do{
//            sameVertex = 0;
//            startPosX = (int)(Math.random()*this.getRowsNum());
//            goalPosX = (int)(Math.random()*this.getRowsNum());
//            if (startPosX == 0 || startPosX == this.getRowsNum()-1){
//                startPosY = (int)(Math.random()*this.getColsNum());
//            }
//            else{
//                startPosY = ((int)(Math.random()*2)) * (this.getColsNum()-1);
//            }
//            if (goalPosX == 0 || goalPosX == this.getRowsNum() -1){
//                goalPosY = (int)(Math.random()*this.getColsNum());
//            }
//            else {
//                goalPosY = ((int) (Math.random() * 2)) * (this.getColsNum()-1);
//            }
//            if (startPosX == 0 && goalPosX == 0){
//                sameVertex = 1;
//            }
//            if (startPosX == this.getRowsNum()-1 && goalPosX == this.getRowsNum()-1){
//                sameVertex = 1;
//            }
//            if (startPosY == 0 && goalPosY == 0){
//                sameVertex = 1;
//            }
//            if (startPosY == this.getColsNum()-1 && goalPosY == this.getColsNum()-1){
//                sameVertex = 1;
//            }
//        }while(sameVertex == 1);
//        this.setStartPosition(startPosX, startPosY);
//        this.setGoalPosition(goalPosX, goalPosY);
//    }


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

    public boolean sameVertex(Position start, Position goal){
        boolean sameVertex = false;
        if (start.getX()== 0 && goal.getX() == 0){
            sameVertex = true;
        }
        if (start.getX() == this.getRowsNum()-1 && goal.getX() == this.getRowsNum()-1){
            sameVertex = true;
        }
        if (start.getY() == 0 && goal.getY() == 0){
            sameVertex = true;
        }
        if (start.getY() == this.getColsNum()-1 && goal.getY() == this.getColsNum()-1){
            sameVertex = true;
        }
        return sameVertex;
    }

}
