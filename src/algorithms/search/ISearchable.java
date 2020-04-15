package algorithms.search;

import java.util.List;

/*
This is an interface for searchable domains, it declares which methods domain should implements.
 */

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState s);
}
