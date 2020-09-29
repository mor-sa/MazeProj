package algorithms.mazeGenerators;
/**
This is an abstract class that implements the IMazeGenerator interface.
It implemnts the measureAlgorithmTimeMillis that every MazeGenerator will have.
 */

public abstract class AMazeGenerator implements IMazeGenerator{

    /**
     * Calculates the time in milliseconds to create a maze
     * @param rows - number of rows wanted in maze
     * @param columns - number of cols wanted in maze
     * @return the time in milliseconds to create a maze
     */
    public long measureAlgorithmTimeMillis(int rows, int columns){

        long before = System.currentTimeMillis();
        generate(rows, columns);
        long after = System.currentTimeMillis();
        return after-before;
    }
}
