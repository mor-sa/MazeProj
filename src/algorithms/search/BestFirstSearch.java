package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() {
        super();
        this.Name = "Best First Search";
        this.openList = new PriorityQueue<>(AState::compareTo);
        this.numOfNodesEval = 0;
    }
}
