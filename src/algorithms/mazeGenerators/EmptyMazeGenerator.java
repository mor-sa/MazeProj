package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        newMaze.setStartAndGoal();
        for (int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                newMaze.setValue(new Position(i,j),0);
            }
        }
        return newMaze;
    }
}
