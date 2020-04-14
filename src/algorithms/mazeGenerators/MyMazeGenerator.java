package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.List;

public class MyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        // Setting all the maze as walls
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                newMaze.setValue(new Position(i,j),1);
            }
        }
        Position startPos = newMaze.randPosOnVertex();
        newMaze.setStartPosition(startPos.getX(), startPos.getY());
        newMaze.setValue(newMaze.getStartPosition(), 0);
        //creating lists
        List<Position> framePassages = new ArrayList<>();
        // Starting with the start position of the maze and building a tree from there
        newMaze.setValue(newMaze.getStartPosition(), 0);
        List<Position> walls = newMaze.getNeighbors(newMaze.getStartPosition());
        //List<Position> walls = new ArrayList<>(startPosNeighbors);
        // Breaking walls to create maze
        while(!walls.isEmpty()){
            int wallIndex = (int)(Math.random()*(walls.size()));
            Position wall = walls.get(wallIndex);
            List<Position> wallNeighbors = newMaze.getNeighbors(wall);
            if (checkNeighbors(newMaze, wall)){
                newMaze.setValue(wall,0);
                walls.addAll(wallNeighbors);
                if (wallNeighbors.size() < 4){
                    framePassages.add(wall);
                }
            }
            walls.remove(wall);
        }
        // Set the goal position
        do{
            Position goalPos = newMaze.randPosOnVertex();
            newMaze.setGoalPosition(goalPos.getX(), goalPos.getY());
        }while(newMaze.sameVertex(newMaze.getStartPosition(),newMaze.getGoalPosition()) || newMaze.getValue(newMaze.getGoalPosition()) == 1);
        return newMaze;
    }


    // Checks if a cell has more than 1 neighbor with value 0 (more than 1 neighbors that are passages) returns false
    public boolean checkNeighbors(Maze maze, Position pos){
        List<Position> cellNeighbors = maze.getNeighbors(pos);
        int counter = 0;
        for (Position cellNeighbor : cellNeighbors) {
            if ((cellNeighbor != null) && (maze.getValue(cellNeighbor) == 0)) {
                counter++;
            }
        }
        return counter < 2;
    }
}
