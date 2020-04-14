package algorithms.search;

import java.util.List;

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState s);
}
