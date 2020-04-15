package algorithms.search;

/*
This is an interface for searching algorithms which declares which methods searching algorithms should implement.
 */

public interface ISearchingAlgorithm {
    Solution solve(ISearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();
}
