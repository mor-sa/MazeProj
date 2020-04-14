package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue openList = new LinkedList();
    protected HashSet<AState> visited;

    public BreadthFirstSearch() {
        this.Name = "Breadth First Search";
        this.numOfNodesEval = 0;
    }

    @Override
    public Solution solve(ISearchable domain) {
        int countEval = 0;
        this.openList.clear();
        this.setNumOfNodesEval(0);
        visited = new HashSet<>();
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        visited.add(start);
        openList.add(start);
        AState curState;
        while (openList.size() != 0){
             curState = (AState)openList.poll();
             countEval++;
            if (curState == goal){
                setNumOfNodesEval(countEval);
                return new Solution(curState);
            }
            List<AState> neighbors = domain.getAllPossibleStates(curState);
            for (AState neighbor : neighbors) {
                neighbor.setCost(curState.getCost() + neighbor.getCost());
                if (!(visited.contains(neighbor))) {
                    if (goal == neighbor){
                        setNumOfNodesEval(countEval);
                        return new Solution(neighbor);
                    }
                    visited.add((AState) neighbor);
                    openList.add(neighbor);
                }
            }
        }
        this.setNumOfNodesEval(countEval);
        return new Solution();
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
