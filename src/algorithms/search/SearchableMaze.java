package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable{
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position startpos = this.maze.getStartPosition();
        return convertPosToState(startpos);
    }

    @Override
    public AState getGoalState() {
        Position goalpos = this.maze.getGoalPosition();
        return convertPosToState(goalpos);
    }

    public MazeState convertPosToState(Position pos){
        return new MazeState(null, pos.toString(), 0);
    }

    @Override
    public List<AState> getAllPossibleStates(AState s) {
        List<AState> AllSuccessors = new ArrayList<>();
        MazeState mState = new MazeState(s.getPrev(), s.getState_str(), s.getCost());
        if (mState.getPos().getRowIndex() <= this.maze.getRowsNum()-1 && mState.getPos().getColumnIndex() <= this.maze.getColsNum()-1){
            List<Position> NeighPositions = this.maze.getClockNeighbors(mState.getPos());
            for (Position tempPos : NeighPositions) {
                MazeState tempState = new MazeState(mState, tempPos.toString(), 0);
                if (tempPos.getRowIndex() != mState.getPos().getRowIndex() && tempPos.getColumnIndex() != mState.getPos().getColumnIndex()) {
                    tempState.setCost(15);
                } else {
                    tempState.setCost(10);
                }
                AllSuccessors.add(tempState);
            }
        }
        return AllSuccessors;
    }

    @Override
    public void printColor() {
        this.maze.printColor();
    }

    @Override
    public void printColorSolution(ArrayList<Position> path) {
        this.maze.printColorSolution(path);
    }
}
