package algorithms.search;

public class BestFirstSearch extends ASearchingAlgorithm{

    public BestFirstSearch() {
        this.Name = "Best First Search";
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
