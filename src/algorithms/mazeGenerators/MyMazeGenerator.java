package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a maze generator that creates a maze by prim's randomized algorithm.
 */

public class MyMazeGenerator extends AMazeGenerator{

    public MyMazeGenerator() {
    }

    /**
     * Generates a new maze created by prim's randomized algorithm
     * @param rows - number of rows wanted in the maze
     * @param columns - number of cols wanted in the maze
     * @return maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze newMaze = new Maze(rows, columns);
        // Setting all the maze as walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMaze.setValue(new Position(i, j), 1);
            }
        }
        // Pick a starting cell, mark it as part of the maze
        Position startPos = newMaze.randPosOnVertex();
        newMaze.setStartPosition(startPos.getRowIndex(), startPos.getColumnIndex());
        newMaze.setValue(startPos, 0);

        // Edge scenario where the maze is 2x2
        if ((rows == 2) && (columns == 2)) {
            List<Position> neighbors = newMaze.getNeighbors(startPos);
            int randInd = (int) (Math.random() * (neighbors.size()));
            newMaze.setValue(neighbors.get(randInd), 0);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if ((!(neighbors.contains(new Position (i,j)))) && (!(startPos.equals(new Position(i,j))))) {
                        newMaze.setValue(new Position(i, j), 0);
                        newMaze.setGoalPosition(i,j);
                    }
                }
            }
            return newMaze;
        }
        // Other cases:
        // add the walls of the cells
        List<Position> walls = newMaze.getNeighbors(newMaze.getStartPosition());

        // while there are walls in the list
        while (walls.size() > 0) {
            // Pick a random wall from the list
            int wallIndex = (int) (Math.random() * (walls.size()));
            Position wall = walls.get(wallIndex);
            // if only 1 of the 2 cells that the wall divides is visited
            ArrayList<Position> uc = addOppositeCell(newMaze, wall);
            if (uc.size() == 1) {
                // Make the wall a passage and mark the unvisited cell as part of the maze
                newMaze.setValue(new Position(wall.getRowIndex(), wall.getColumnIndex()), 0);
                if ((uc.get(0).getRowIndex() >= 0) && (uc.get(0).getRowIndex() < rows) && (uc.get(0).getColumnIndex() >= 0) && (uc.get(0).getColumnIndex() < columns)) {
                    newMaze.setValue(new Position(uc.get(0).getRowIndex(), uc.get(0).getColumnIndex()), 0);
                    // Add the neighboring walls of the cell to the wall list
                    walls.addAll(newMaze.getNeighbors(uc.get(0)));
                }
            }
            // remove the wall from the list
            walls.remove(wallIndex);
        }
        // Setting the goal position randomized and checking they are not on the same vertex
        newMaze.setRandGoalPos();
        return newMaze;
    }

    /**
     * Function to add the opposite cell of a cell in a given maze
     * @param m - a given maze to work on
     * @param wall - a given position to find it's opposite
     * @return list of opposite cells
     */
    public ArrayList<Position> addOppositeCell (Maze m, Position wall){
        ArrayList<Position> unvisitedCell = new ArrayList<>();
        if ((wall.getColumnIndex() + 1 < m.getColsNum()) && (m.getValue(new Position(wall.getRowIndex(), wall.getColumnIndex() + 1)) == 0)) {
            unvisitedCell.add(new Position(wall.getRowIndex(), wall.getColumnIndex() - 1));
        }
        if ((wall.getColumnIndex() - 1 >= 0) && (m.getValue(new Position(wall.getRowIndex(), wall.getColumnIndex() - 1)) == 0)) {
            unvisitedCell.add(new Position(wall.getRowIndex(), wall.getColumnIndex() + 1));
        }
        if ((wall.getRowIndex() + 1 < m.getRowsNum()) && (m.getValue(new Position(wall.getRowIndex() + 1, wall.getColumnIndex())) == 0)) {
            unvisitedCell.add(new Position(wall.getRowIndex() - 1, wall.getColumnIndex()));
        }
        if ((wall.getRowIndex() - 1 >= 0) && (m.getValue(new Position(wall.getRowIndex() - 1, wall.getColumnIndex())) == 0)) {
            unvisitedCell.add(new Position(wall.getRowIndex() + 1, wall.getColumnIndex()));
        }
        return unvisitedCell;
    }
}
