package algorithms.mazeGenerators;

/*
This is a class that extends AMazeGenerator and overrides the generate method of IMazeGenerator.
This class creates an empty maze - with no walls.
 */

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {
    }

    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        Position startPos = newMaze.randPosOnVertex();
        newMaze.setStartPosition(startPos.getRowIndex(), startPos.getColumnIndex());
        newMaze.setValue(newMaze.getStartPosition(), 0);
        for (int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                newMaze.setValue(new Position(i,j),0);
            }
        }
        newMaze.setRandGoalPos();
        return newMaze;
    }
}
