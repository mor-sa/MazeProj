package algorithms.mazeGenerators;
/*
This is an abstract class that implements the IMazeGenerator interface.
It implemnts the measureAlgorithmTimeMillis that every MazeGenerator will have.
 */

public abstract class AMazeGenerator implements IMazeGenerator{

    // Calculate time of generating maze
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long before = System.currentTimeMillis();
        generate(rows, columns);
        long after = System.currentTimeMillis();
        return after-before;
    }
}
