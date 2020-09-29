package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

/**
This is an interface for searchable domains, it declares which methods domain should implements.
 */

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState s);
    void printColorSolution(ArrayList<Position> path);
    int hashCode();
}
