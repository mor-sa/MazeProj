package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
This class defines a solution. It saves the goalState and then from it retrieves a solution path.
 */

public class Solution implements Serializable {
    private AState goalState;
    public Solution() {
        goalState = null;
    }
    public Solution(AState goal) {
        this.goalState = goal;
    }

    /**
     * This method reconstructs the solution path by finding the previous of each state starting with goal state.
     * @return array list of states from start state to goal state
     */
    public ArrayList<AState> getSolutionPath() {
        ArrayList<AState> path = new ArrayList<>();
        AState curState = goalState;
        if (curState != null) {
            path.add(curState);
            while (curState.getPrev() != null) {
                curState = curState.getPrev();
                path.add(curState);
            }
            Collections.reverse(path);
        }
        return path;
    }

    @Override
    public String toString() {
        return String.valueOf(getSolutionPath().size());
    }
}
