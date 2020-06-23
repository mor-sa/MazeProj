package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    public void generateMaze(int row, int col);
    public Maze getMaze();
    public void updateCharacterLocation(int direction);
    public int getRowChar();
    public int getColChar();
    public void assignObserver(Observer o);
    public void solveMaze();
    public ArrayList<Position> getSolutionPath();
    public void exit();
    public void Save(File file);
}
