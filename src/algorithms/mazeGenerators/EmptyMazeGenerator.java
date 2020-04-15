package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {
    }

    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        Position startPos = newMaze.randPosOnVertex();
        newMaze.setStartPosition(startPos.getRowIndex(), startPos.getColumnIndex());
        newMaze.setValue(newMaze.getStartPosition(), 0);
        do{
            Position goalPos = newMaze.randPosOnVertex();
            newMaze.setGoalPosition(goalPos.getRowIndex(), goalPos.getColumnIndex());
        }while(newMaze.sameVertex(newMaze.getStartPosition(),newMaze.getGoalPosition()) || newMaze.getValue(newMaze.getGoalPosition()) == 1);
        for (int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                newMaze.setValue(new Position(i,j),0);
            }
        }
        return newMaze;
    }
}
