package algorithms.mazeGenerators;

import javafx.geometry.Pos;

public class Position {
    private int row;
    private int col;

    public Position(){
        this.row = 0;
        this.col = 0;
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return col;
    }

    public void setRowIndex(int row) {
        this.row = row;
    }

    public void setColumnIndex(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "{"  + row +
                "," + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col;
    }
}
