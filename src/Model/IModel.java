package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    void generateMaze(int row, int col);
    Maze getMaze();
    void updateCharacterLocation(int direction);
    int getRowChar();
    int getColChar();
    void assignObserver(Observer o);
    void solveMaze(int rowStartPos, int colStartPos);
    ArrayList<Position> getSolutionPath();
    void clearSolPath();
    void exit();
    void Save(File file);
    void Load(File file) throws IOException, ClassNotFoundException;
    String getMazeGenAlg();
    String getMazeSolveAlg();
    String getNumOfThreads();
    }
