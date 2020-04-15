package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState s);
    void printColor();
    void printColorSolution(ArrayList<Position> path);
}
