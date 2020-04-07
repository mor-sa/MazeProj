package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.List;

public class MyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        newMaze.setStartAndGoal();
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                newMaze.setValue(new Position(i,j),1);
            }
        }
        List<Position> walls = new ArrayList<>();
        // Starting with the start position of the maze and building a tree from there
        Position randPos = newMaze.getStartPosition();
        newMaze.setValue(randPos, 0);
        List<Position> randNeighbors = newMaze.getNeighbors(randPos);
        for (int i=0; i<randNeighbors.size(); i++){
            if(newMaze.getValue(randNeighbors.get(i)) == 1){
                walls.add(randNeighbors.get(i));
            }
        }

        while(!walls.isEmpty()){
            int rand = (int)(Math.random()*(walls.size()));
            int curWallRow = walls.get(rand).getX();
            int curWallCol = walls.get(rand).getY();
            if(curWallCol == randPos.getX()){
                if (curWallRow < randPos.getX()){
                    if(curWallRow > 0){
                        Position curPos = new Position(curWallRow-1, curWallCol);
                        if (newMaze.getValue(curPos) == 1){
                            newMaze.setValue(walls.get(rand),0);
                            newMaze.setValue(curPos, 0);
                            randNeighbors = newMaze.getNeighbors(curPos);
                            for (int i=0; i<randNeighbors.size(); i++){
                                if(newMaze.getValue(randNeighbors.get(i)) == 1){
                                    walls.add(randNeighbors.get(i));
                                }
                            }
                            randNeighbors.remove(rand);
                        }
                    }
                }
            }
            /* check if only one of the 2 cells the wall divides is visited
             if(...){
                wall is passage


              }

              change randPos;
             */

        }




        return null;
    }
}
