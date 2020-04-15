package algorithms.search;

/*
This is an abstract class that implement ISearchingAlgorithm.
 */

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected String Name;
    protected int numOfNodesEval;

    public int getNumberOfNodesEvaluated(){
        return numOfNodesEval;
    }

    public void setNumOfNodesEval(int count){
        this.numOfNodesEval = count;
    }
}
