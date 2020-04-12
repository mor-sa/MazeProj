package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
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
            String posStr = s.substring(1,s.length()-2);
            String[] posArr = posStr.split(",");
            pos.setX(Integer.parseInt(posArr[0]));
            pos.setY(Integer.parseInt(posArr[1]));
        }
        return pos;
    }

    public Position getPos() {
        return pos;
    }
}
