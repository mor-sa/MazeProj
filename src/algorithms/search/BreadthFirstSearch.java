package algorithms.search;

import java.util.*;

/**
This class extends ASearchingAlgorithm and implements a Breadth First Search.
It gets an ISearchable domain and returns a Solution.
 */

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> openList;
    protected HashSet<String> visited;

    public BreadthFirstSearch() {
        this.Name = "Breadth First Search";
        this.openList = new LinkedList<>();
        this.numOfNodesEval = 0;
    }

    /**
     * Solves a given domain by BFS algorithm
     * @param domain - an ISearchable object to search
     * @return solution for the maze
     */
    @Override
    public Solution solve(ISearchable domain) {
        int countEval = 0;
        this.openList.clear();
        this.setNumOfNodesEval(0);
        visited = new HashSet<>();
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        visited.add(start.toString());
        openList.add(start);
        AState curState;
        // While open list is not empty
        while (openList.size() != 0){
             curState = openList.poll();
            if (curState.equals(goal)){
                setNumOfNodesEval(countEval);
                return new Solution(curState);
            }
            List<AState> neighbors = domain.getAllPossibleStates(curState);
            countEval++;
            for (AState neighbor : neighbors) {
                neighbor.setCost(curState.getCost() + neighbor.getCost());
                if (!(visited.contains(neighbor.toString()))) {
                    visited.add(neighbor.toString());
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
