package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
This class extends AState and defines a MazeState and its attributes and methods.
 */

public class MazeState extends AState implements Serializable {
    private Position pos;

    public MazeState(AState prev, String state_str, int cost) {
        this.prev = prev;
        this.state_str = state_str;
        this.cost = cost;
        this.pos = convertStrToPos(state_str);
    }

    public Position convertStrToPos(String s){
        Position pos = new Position();
        if (s != null){
            String posStr = s.substring(1,s.length()-1);
            String[] posArr = posStr.split(",");
            pos.setRowIndex(Integer.parseInt(posArr[0]));
            pos.setColumnIndex(Integer.parseInt(posArr[1]));
        }
        return pos;
    }

    public Position getPos() {
        return pos;
    }

    @Override
    public int hashCode() {
        return pos.hashCode();
    }
}
