package algorithms.search;

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
