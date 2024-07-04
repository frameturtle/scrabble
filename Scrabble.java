import java.util.*;

public class Scrabble {
    private Square[][] board;
    private List<Tile> bag;
    private List<Tile> rack;
    private List<Square> emptySquares;
    private List<Square> filledSquares;
    private HashMap<Position, Square> map;
    public int uniqueTiles = 27;
    public int rackSize = 7;

    public static final List<List<Integer>> tripleWordCoordinates = List.of(
            List.of(0, 0), List.of(7, 0), List.of(14, 0), List.of(0, 7), List.of(0, 14), List.of(7, 14), List.of(14, 7), List.of(14, 14));
    public static final Set<List<Integer>> twc = new HashSet<>(tripleWordCoordinates);
    public static final List<List<Integer>> doubleWordCoordinates = List.of(
            List.of(1, 1), List.of(2, 2), List.of(3, 3), List.of(4, 4), List.of(13, 1), List.of(12, 2), List.of(11, 3), List.of(10, 4),
            List.of(4, 10), List.of(3, 11), List.of(2, 12), List.of(1, 13), List.of(10, 10), List.of(11, 11), List.of(12, 12), List.of(13, 13));
    public static final Set<List<Integer>> dwc = new HashSet<>(doubleWordCoordinates);
    public static final List<List<Integer>> tripleLetterCoordinates = List.of(
            List.of(5, 1), List.of(9, 1), List.of(1, 5), List.of(1, 9), List.of(5, 5), List.of(5, 9), List.of(9, 5), List.of(9, 9),
            List.of(13, 5), List.of(13, 9), List.of(5, 13), List.of(9, 13));
    public static final Set<List<Integer>> tlc = new HashSet<>(tripleLetterCoordinates);
    public static final List<List<Integer>> doubleLetterCoordinates = List.of(
            List.of(5, 1), List.of(0, 3), List.of(7, 3), List.of(6, 2), List.of(8, 2), List.of(11, 0), List.of(14, 3), List.of(2, 6),
            List.of(3, 7), List.of(2, 8), List.of(6, 6), List.of(8, 6), List.of(6, 8), List.of(8, 8), List.of(12, 6), List.of(11, 7),
            List.of(12, 8), List.of(0, 11), List.of(7, 11), List.of(14, 11), List.of(6, 12), List.of(8, 12), List.of(3, 14), List.of(11, 14));
    public static final Set<List<Integer>> dlc = new HashSet<>(doubleLetterCoordinates);

    public Scrabble() {
        emptySquares = new ArrayList<>();
        filledSquares = new ArrayList<>();
        rack = new ArrayList<>();
        map = new HashMap<>();
        board = makeBoard();
        bag = fillBag();
    }

    private Square[][] makeBoard() {
        Square[][] board = new Square[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Square square = makeSquare(i, j);
                board[i][j] = square;
                map.put(square.getPosition(), square);
                emptySquares.add(square);
            }
        }
        return board;
    }

    private Square makeSquare(int row, int col) {
        Square.Type type = Square.Type.NORMAL;
        if (twc.contains(List.of(row, col))) {
            type = Square.Type.TRIPLE_WORD;
        } else if(dwc.contains(List.of(row, col))) {
            type = Square.Type.DOUBLE_WORD;
        } else if(tlc.contains(List.of(row, col))) {
            type = Square.Type.TRIPLE_LETTER;
        } else if(dlc.contains(List.of(row, col))) {
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

    public void fillRack() {
        Random random = new Random();
        while(rack.size() < 7) {
            Tile tile = bag.remove(random.nextInt(0,bag.size()));
            rack.add(tile);
        }
    }

    public void takeTurn(List<Tile> tilesPlaced, List<Position> placements) {
        for (int i = 0; i < tilesPlaced.size(); i++) {
            Square square = map.get(placements.get(i));
            emptySquares.remove(square);
            filledSquares.add(square);
            square.setTile(tilesPlaced.get(i));
        }
    }

    public List<Square> getEmptySquares() {
        return emptySquares;
    }

    public List<Square> getFilledSquares() {
        return filledSquares;
    }

    public List<Tile> getBag() {
        return bag;
    }

    public List<Tile> getRack() {
        return rack;
    }

    public Square[][] getBoard() {
        return board;
    }

    public void printBoard() {
        int index = 0;
        System.out.println("\n\t\t 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14\n");
        for (Square[] squares : board) {
            System.out.print("\t" + String.valueOf(index) + "\t");
            index++;
            for (Square square : squares) {
                System.out.print(square + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.fillBag();
        scrabble.fillRack();
        List<Tile> rack = scrabble.getRack();
        scrabble.takeTurn(rack, List.of(new Position(7,7), new Position(7, 8), new Position(7, 9), new Position(7, 10), new Position(7, 11), new Position(7, 12), new Position(7, 13)));
        scrabble.printBoard();
    }
}


