public class Square {
    public static enum Type {
        DOUBLE_LETTER("DL"), TRIPLE_LETTER("TL"), DOUBLE_WORD("DW"), TRIPLE_WORD("TW"), START("* "), NORMAL("- ");
        private final String abbreviation;

        Type(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        @Override
        public String toString() {
            return abbreviation;
        }
    }

    private final Type type;
    private boolean isEmpty;
    private final int row;
    private final int col;
    private Tile tile;

    public Square(Type type, int row, int col) {
        this.type = type;
        isEmpty = true;
        this.row = row;
        this.col = col;
        tile = null;
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

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        if(isEmpty) {
            this.tile = tile;
            isEmpty = false;
        }
    }

    @Override
    public String toString() {
        if(getTile() == null) {
            return type.toString();
        } else {
            return getTile().toString();
        }
    }
}
