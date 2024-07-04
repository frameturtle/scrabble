import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Square {
    public static enum Type {
        DOUBLE_LETTER(" 2 ", Colors.BACKGROUND_LIGHT_BLUE, Colors.BLUE),
        TRIPLE_LETTER(" 3 ", Colors.BACKGROUND_BLUE, Colors.LIGHT_BLUE),
        DOUBLE_WORD(" 2 ", Colors.BACKGROUND_LIGHT_RED, Colors.RED),
        TRIPLE_WORD(" 3 ", Colors.BACKGROUND_RED, Colors.LIGHT_RED),
        START(" * ", Colors.BACKGROUND_GREEN, Colors.BLACK),
        NORMAL(" - ", Colors.BACKGROUND_GRAY, Colors.GRAY);
        private final String abbreviation;
        private final String backgroundColor;
        private final String textColor;

        Type(String abbreviation, String backgroundColor, String textColor) {
            this.abbreviation = abbreviation;
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
        }

        @Override
        public String toString() {
            return textColor + backgroundColor + abbreviation + Colors.RESET;
        }
    }

    private final Type type;
    private boolean isEmpty;
    private final int row;
    private final int col;
    private final Position position;
    private final List<Position> neighbors;
    private Tile tile;
    private int timesUsed;

    public Square(Type type, int row, int col) {
        this.type = type;
        isEmpty = true;
        this.row = row;
        this.col = col;
        this.position = new Position(row, col);
        this.neighbors = calculateNeighbors();
        tile = null;
        timesUsed = 0;
    }

    private List<Position> calculateNeighbors() {
        List<Position> neighbors = new ArrayList<>();
        if (row != 0) {
            Position above = new Position(row - 1, col);
            neighbors.add(above);
        }
        if (row != 14) {
            Position below = new Position(row + 1, col);
            neighbors.add(below);
        }
        if (col != 0) {
            Position left = new Position(row, col - 1);
            neighbors.add(left);
        }
        if (col != 14) {
            Position right = new Position(row, col + 1);
            neighbors.add(right);
        }
        return neighbors;
    }

    public Type getType() {
        return type;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getNeighbors() {
        return neighbors;
    }

    public Tile getTile() {
        return tile;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTile(Tile tile) throws ScrabbleException {
        if(isEmpty) {
            this.tile = tile;
            isEmpty = false;
            timesUsed++;
        } else {
            throw new ScrabbleException("Square is already occupied.");
        }
    }

    public void resetTile() {
        if (!isEmpty) {
            isEmpty = true;
            tile = null;
            timesUsed--;
        }
    }

    @Override
    public String toString() {
        if(getTile() == null) {
            return type.toString();
        } else {
            return Colors.YELLOW + Colors.BLACK + " " + getTile().toString() + " " + Colors.RESET;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Square other) {
            return (position.equals(other.position) && (tile.equals(other.tile)));
        } else {
            return false;
        }
    }
}
