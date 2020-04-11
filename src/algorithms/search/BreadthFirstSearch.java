package algorithms.search;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    public BreadthFirstSearch() {
        this.Name = "Breadth First Search";
        this.numOfNodesEval = 0;
    }

    @Override
    public Solution solve(ISearchable domain) {
        return null;
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
