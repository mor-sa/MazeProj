package algorithms.mazeGenerators;

/*
This is an ineterface for MazeGenerators that declares which methods needs to be provided by the MazeGenerators.
 */

public interface IMazeGenerator {

    Maze generate(int rows, int columns);
    long measureAlgorithmTimeMillis(int rows, int columns);
}
