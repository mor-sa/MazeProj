package algorithms.search;

import java.util.PriorityQueue;

/*
This class extends BreadthFirstSearch and uses a priority queue instead of a regular queue.
 */

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() {
        super();
        this.Name = "Best First Search";
        this.openList = new PriorityQueue<>(AState::compareTo);
        this.numOfNodesEval = 0;
    }
}
