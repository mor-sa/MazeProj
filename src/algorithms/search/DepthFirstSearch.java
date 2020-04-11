package algorithms.search;

public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        this.Name = "Depth First Search";
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
