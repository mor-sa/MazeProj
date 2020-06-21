package Model;

import algorithms.mazeGenerators.Maze;

import java.util.Observer;

public interface IModel {
    public void generateMaze(int row, int col);
    public Maze getMaze();
    public void updateCharacterLocation(int direction);
    public int getRowChar();
    public int getColChar();
    public void assignObserver(Observer o);
    public void solveMaze();
    public void getSolution();
}
