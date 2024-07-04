public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position other) {
            return ((row == other.row) && (col == other.col));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (row * 1000) + col;
    }
}
