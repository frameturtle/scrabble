import java.util.*;

public class Scrabble {
    private Square[][] board;
    private List<Tile> bag;
    private List<Square> emptySquares;
    private List<Square> filledSquares;
    public int uniqueTiles = 27;

    public static final int[][] tripleWordCoordinates = new int[][]{
            {0, 0}, {7, 0}, {14, 0}, {0, 7}, {0, 14}, {7, 14}, {14, 7}, {14, 14}
    };
    public static final Set<int[]> twc = new HashSet<>(Arrays.asList(tripleWordCoordinates));
    public static final int[][] doubleWordCoordinates = new int[][]{
            {1, 1}, {2, 2}, {3, 3}, {4, 4}, {13, 1}, {12, 2}, {11, 3}, {10, 4}, {4, 10}, {3, 11},
            {2, 12}, {1, 13}, {10, 10}, {11, 11}, {12, 12}, {13, 13}
    };
    public static final Set<int[]> dwc = new HashSet<>(Arrays.asList(doubleWordCoordinates));
    public static final int[][] tripleLetterCoordinates = new int[][]{
            {5, 1}, {9, 1}, {1, 5}, {1, 9}, {5, 5}, {5, 9}, {9, 5}, {9, 9}, {13, 5}, {13, 9}, {5, 13}, {9, 13}
    };
    public static final Set<int[]> tlc = new HashSet<>(Arrays.asList(tripleLetterCoordinates));
    public static final int[][] doubleLetterCoordinates = new int[][]{
            {5, 1}, {0, 3}, {7, 3}, {6, 2}, {8, 2}, {11, 0}, {14, 3}, {2, 6}, {3, 7}, {2, 8}, {6, 6},
            {8, 6}, {6, 8}, {8, 8}, {12, 6}, {11, 7}, {12, 8}, {0, 11}, {7, 11}, {14, 11}, {6, 12},
            {8, 12}, {3, 14}, {11, 14}
    };
    public static final Set<int[]> dlc = new HashSet<>(Arrays.asList(doubleLetterCoordinates));

    public Scrabble() {
        emptySquares = new ArrayList<>();
        filledSquares = new ArrayList<>();
        board = makeBoard();
        bag = fillBag();
    }

    private Square[][] makeBoard() {
        Square[][] board = new Square[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Square square = makeSquare(i, j);
                board[i][j] = square;
                emptySquares.add(square);
            }
        }
        return board;
    }

    private Square makeSquare(int row, int col) {
        Square.Type type = Square.Type.NORMAL;
        if (twc.contains(new int[]{row, col})) {
            type = Square.Type.TRIPLE_WORD;
        } else if(dwc.contains(new int[]{row, col})) {
            type = Square.Type.DOUBLE_WORD;
        } else if(tlc.contains(new int[]{row, col})) {
            type = Square.Type.TRIPLE_LETTER;
        } else if(dlc.contains(new int[]{row, col})) {
            type = Square.Type.DOUBLE_LETTER;
        } else if(row == 7 && col == 7) {
            type = Square.Type.START;
        }
        return new Square(type, row, col);
    }

    private List<Tile> fillBag() {
        List<Tile> bag = new ArrayList<>();
        char firstLetter = 'A';
        for(int i = 0; i < uniqueTiles-1; i++) {
            Tile tile = Tile.valueOf(String.valueOf(firstLetter));
            for(int j = 0; j < tile.getAmount(); j++) {
                bag.add(tile);
            }
            firstLetter++;
        }
        bag.add(Tile.Special);
        bag.add(Tile.Special);
        return bag;
    }

    public List<Square> getEmptySquares() {
        return emptySquares;
    }

    public List<Square> getFilledSquares() {
        return filledSquares;
    }
}