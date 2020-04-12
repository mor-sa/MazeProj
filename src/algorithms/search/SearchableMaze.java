package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {

        return null;
    }

    @Override
    public AState getGoalState() {
        return null;
    }

    public MazeState convertPosToState(Position pos){
        return new MazeState(null, pos.toString(), 0);
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        return null;
    }
}
