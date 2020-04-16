package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator{
    public SimpleMazeGenerator() {
    }

    @Override
    public Maze generate(int rows, int cols) {
        Maze newMaze = new Maze(rows, cols);
        Position startPos = newMaze.randPosOnVertex();
        newMaze.setStartPosition(startPos.getRowIndex(), startPos.getColumnIndex());
        newMaze.setValue(newMaze.getStartPosition(), 0);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j< cols; j++){
                if ((i == 0) || (j == 0) || (i == rows-1) || (j == cols-1)){
                    newMaze.setValue(new Position(i,j), 0);
                }
                else{
                    newMaze.setValue(new Position(i,j),((int)(Math.random()*2)));
                }
            }
        }
        newMaze.setRandGoalPos();
        return newMaze;
    }
}
