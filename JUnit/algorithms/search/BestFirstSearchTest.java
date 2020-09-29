package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void NoSolutionMaze(){
        System.out.println("This test checks that the size of a solution a Best First Search finds for a no solution maze is 0");
        MyMazeGenerator mg = new MyMazeGenerator();
        int rows = 10 ; int cols = 10;
        Maze noSolMaze = mg.generate(rows,cols);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (i == noSolMaze.getStartPosition().getRowIndex() || i == noSolMaze.getGoalPosition().getRowIndex() || j == noSolMaze.getStartPosition().getColumnIndex() || j == noSolMaze.getGoalPosition().getColumnIndex()){
                    noSolMaze.setValue(new Position(i,j), 1);
                }
            }
        }
        BestFirstSearch bestfs = new BestFirstSearch();
        SearchableMaze noSolDomain = new SearchableMaze(noSolMaze);
        Solution sol = bestfs.solve(noSolDomain);
        assertEquals(0,sol.getSolutionPath().size());
    }
}