package algorithms.search;

import java.util.Objects;

public abstract class AState implements Comparable {
    protected AState prev;
    protected String state_str;
    protected int cost;

    public AState() {
        this.state_str = "";
        this.cost = 0;
        this.prev = null;
    }

    public AState(AState prev, String state_str, int cost) {
        this.prev = prev;
        this.state_str = state_str;
        this.cost = cost;
    }

    public AState getPrev() {
        return prev;
    }

    public String getState_str() {
        return state_str;
    }

    public int getCost() {
        return cost;
    }

    public void setPrev(AState prev) {
        this.prev = prev;
    }

    public void setState_str(String state_str) {
        this.state_str = state_str;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return state_str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return Objects.equals(state_str, aState.state_str);
    }

    @Override
    public int compareTo(Object o) {
        if (this.getClass().isInstance(o)) {
            AState other = (AState) o;
            if (this.cost > other.cost) {
                return 1;
            }
            else if (this.cost == other.cost) {
                return 0;
            }
            else {
                return -1;
            }
        }
        return 1;
    }



}
