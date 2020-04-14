package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue openList = new LinkedList();
    protected Set<AState> visited = new HashSet<>();

    public BreadthFirstSearch() {
        this.Name = "Breadth First Search";
        this.numOfNodesEval = 0;
    }

    @Override
    public Solution solve(ISearchable domain) {
        int countEval = 0;
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        visited.add(start);
        openList.add(start);
        AState curState = start;
        while (openList.size() != 0){
             curState = (AState)openList.poll();
            if (curState == goal){
                break;
            }
            List<AState> neighbors = domain.getAllPossibleStates(curState);
            for (Object neighbor : neighbors) {
                if (!(visited.contains(neighbor))) {
                    countEval++;
                    visited.add((AState) neighbor);
                    ((AState) neighbor).setPrev(curState);
                    openList.add(neighbor);
                }
            }
        }
        Solution path = new Solution();
        if (curState == goal) {
            do {
                path.Add(curState);
                curState = curState.prev;
            } while (curState.getPrev() != null);
        }
        this.setNumOfNodesEval(countEval);
        return path;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfNodesEval;
    }
}
