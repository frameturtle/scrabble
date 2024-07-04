public class Square {
    public static enum Type {
        DOUBLE_LETTER("DL", Colors.BACKGROUND_LIGHT_BLUE),
        TRIPLE_LETTER("TL", Colors.BACKGROUND_BLUE),
        DOUBLE_WORD("DW", Colors.BACKGROUND_LIGHT_RED),
        TRIPLE_WORD("TW", Colors.BACKGROUND_RED),
        START("**", Colors.BACKGROUND_GREEN),
        NORMAL("--", Colors.RESET);
        private final String abbreviation;
        private final String backgroundColor;

        Type(String abbreviation, String backgroundColor) {
            this.abbreviation = abbreviation;
            this.backgroundColor = backgroundColor;
        }

        @Override
        public String toString() {
            return Colors.BLACK + backgroundColor + abbreviation + Colors.RESET;
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
            return Colors.YELLOW + getTile().toString() + Colors.RESET;
        }
    }
}
