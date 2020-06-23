package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    public void generateMaze(int row, int col);
    public Maze getMaze();
    public void updateCharacterLocation(int direction);
    public int getRowChar();
    public int getColChar();
    public void assignObserver(Observer o);
    public void solveMaze(int rowStartPos, int colStartPos);
    public ArrayList<Position> getSolutionPath();
    public void exit();
    public void Save(File file);
    public void Load(File file) throws IOException, ClassNotFoundException;
    public String getMazeGenAlg();
    public String getMazeSolveAlg();
    public String getNumOfThreads();
    }
