import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Scrabble {
    private final Square[][] board;
    private final List<Tile> bag;
    private final List<Tile> rack;
    private final List<Square> emptySquares;
    private final List<Square> filledSquares;
    private final HashMap<Position, Square> map;
    private WordFinder wordFinder;
    public int uniqueTiles = 27;
    public int rackSize = 7;
    private final List<List<Word>> allWordsByTurn;
    private final List<Word> allWords;
    private Set<String> wordList;
    private static final String wordListPath = "C:\\Users\\Katie George\\Documents\\python\\scrabble\\scrabble\\words.txt";
    private int turnNumber;
    private int activeTurnNumber;
    private int score;

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
        allWordsByTurn = new ArrayList<>();
        allWords = new ArrayList<>();
        board = makeBoard();
        bag = fillBag();
        wordList = createWordList();
        wordFinder = new WordFinder(wordList);
        turnNumber = 0;
        activeTurnNumber = 0;
        score = 0;
        fillRack();
    }

    /* Setup Methods */

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

    public Set<String> createWordList() {
        wordList = new HashSet<>();
        try {
            Scanner scanner = new Scanner(new File(wordListPath));
            while (scanner.hasNext()) {
                wordList.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            // this shouldn't happen as the path is a set variable
            throw new RuntimeException(e);
        }
        return wordList;
    }

    /* In Game Methods */

    public void swap(List<Tile> tilesToSwap) {
        for (Tile tile : tilesToSwap) {
            rack.remove(tile);
        }
        fillRack();
        turnNumber++;
    }

    public void takeTurn(List<Tile> tilesPlaced, List<Position> placements) {
        try {
            for (int i = 0; i < tilesPlaced.size(); i++) {
                if (rack.contains(tilesPlaced.get(i))) {
                    rack.remove(tilesPlaced.get(i));
                } else {
                    throw new ScrabbleException("Tile(s) not in rack.");
                }
                Square square = map.get(placements.get(i));
                square.setTile(tilesPlaced.get(i));
            }
            checkWords();
            if (turnNumber > 0) {
                checkConnections();
            } else {
                checkStartingStar();
            }
        } catch (ScrabbleException se) {
            System.out.println(se.getMessage() + " Please try again");
            takeBackTurn(placements);
            return;
        }
        cullDuplicates();
        score += calculateScore();
        activeTurnNumber++;
        turnNumber++;
        fillRack();
    }

    private void takeBackTurn(List<Position> placements) {
        for (Position placement : placements) {
            Square square = map.get(placement);
            square.resetTile();
        }
        allWordsByTurn.removeLast();
    }

    private void checkWords() throws ScrabbleException {
        allWordsByTurn.add(new ArrayList<>());
        for (Square[] row : board) { // checking 'across' words
            List<Square> toWord = new ArrayList<>();
            for (Square square : row) {
                checkWordsHelper(square, toWord);
            }
            if (checkConditions(toWord) == 3) {
                allWordsByTurn.get(activeTurnNumber).add(new Word(new ArrayList<>(toWord)));
            }
            toWord.clear();
        }
        for (int i = 0; i < board.length; i++) { // checking 'down' words
            List<Square> toWord = new ArrayList<>();
            for (Square[] squares : board) {
                checkWordsHelper(squares[i], toWord);
            }
            if (checkConditions(toWord) == 3) {
                allWordsByTurn.get(activeTurnNumber).add(new Word(new ArrayList<>(toWord)));
            }
            toWord.clear();
        }
    }

    private void checkWordsHelper(Square square, List<Square> toWord) throws ScrabbleException {
        int value = checkCondition(square, toWord);
        if (value != 0) {
            if (value == 1) {        // found a letter from a word in a different orientation
                toWord.clear();
            } else if (value == 3) {        // end of the word was reached
                allWordsByTurn.get(activeTurnNumber).add(new Word(new ArrayList<>(toWord)));
                toWord.clear();
            } else {                        // end of the word has not been reached
                toWord.add(square);
            }
        }
    }

    private int checkCondition(Square square, List<Square> toWord) throws ScrabbleException {
        if (square.isEmpty()) {
            return checkConditions(toWord);
        } else {
            return 4;
        }
    }

    private int checkConditions(List<Square> toWord) throws ScrabbleException {
        if (toWord.isEmpty()) {
            return 0;
        } else if (toWord.size() == 1 && toWord.getFirst().tileHasNeighbors(map) >= 1) {
            return 1;
        } else if (toWord.size() == 1 && toWord.getFirst().tileHasNeighbors(map) == 0) {
            throw new ScrabbleException("Tiles must be connected."); // tile not connected
        } else if (!isWordValid(toWord)) {
            throw new ScrabbleException("Invalid Word(s)."); // end of the word was reached, but it was not found in the word list
        } else {
            return 3;
        }
    }

    private void checkStartingStar() throws ScrabbleException {
        boolean touchesStar = false;
        Word word = allWordsByTurn.get(activeTurnNumber).getFirst();
        for (Square square : word.getSquares()) {
            if (square.getType() == Square.Type.START) {
                touchesStar = true;
            }
        }
        if (!touchesStar) {
            throw new ScrabbleException("First word must touch the starting star.");
        }
    }

    private boolean isWordValid(List<Square> toWord) {
        Word word = new Word(toWord);
        return wordList.contains(word.toString().toLowerCase()) && word.toString().length() > 1;
    }

    private void checkConnections() throws ScrabbleException {
        for (Word word : allWordsByTurn.getLast()) {
            if (!word.isConnected(map)) {
                throw new ScrabbleException("Words must be connected.");
            }
        }
    }

    private void cullDuplicates() {
        List<Word> words = allWordsByTurn.get(activeTurnNumber);
        for (int i = 0; i < words.size(); i++) {
            for (Word lastWord : allWords) {
                if (words.get(i).equals(lastWord)) {
                    words.remove(i);
                    i--;
                }
            }
        }
        allWords.addAll(words);
    }

    private int calculateScore() {
        List<Word> words = allWordsByTurn.get(activeTurnNumber);
        int turnScore = 0;
        for (Word word : words) {
            turnScore += word.getValue();
        }
        return turnScore;
    }

    /* Word Finding */

    public void findWords() {

    }

    /* Getters and Setters */

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

    public List<List<Word>> getAllWordsByTurn() {
        return allWordsByTurn;
    }

    public List<Word> getAllWords() {
        return allWords;
    }

    public Set<String> getWordList() {
        return wordList;
    }

    public WordFinder getWordFinder() {
        return wordFinder;
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getScore() {
        return score;
    }

    /* Display */

    public void printBoard() {
        int index = 0;
        System.out.println("\n\t\t 0  1  2  3  4  5  6  7  8  9  10 11 12 13 14\n");
        for (Square[] squares : board) {
            System.out.print("\t" + index + "\t");
            index++;
            for (Square square : squares) {
                System.out.print(square);
            }
            System.out.print("\n");
        }
    }

    public void printAllWords() {
        System.out.println("Words Played:");
        int i = 0;
        for (List<Word> words : allWordsByTurn) {
            System.out.println("Turn " + String.valueOf(i));
            for (Word wo : words) {
                System.out.println("\t" + wo.getString());
            }
            i++;
        }
        System.out.println("Score: " + score);
    }

    public String rackString() {
        String rackString = "";
        for (Tile tile : rack) {
            rackString += tile.toString();
        }
        return rackString.toLowerCase();
    }

    /* Main */

    public static void main(String[] args) {
//        Scrabble scrabble = new Scrabble();
//        scrabble.fillBag();
//        scrabble.fillRack();
//        List<Tile> rack = new ArrayList<>();
//        rack.add(Tile.H);
//        rack.add(Tile.A);
//        rack.add(Tile.B);
//        rack.add(Tile.I);
//        rack.add(Tile.T);
//        rack.add(Tile.A);
//        rack.add(Tile.T);
//        scrabble.takeTurn(rack, List.of(new Position(1,7), new Position(2, 7), new Position(3, 7), new Position(4, 7), new Position(5, 7), new Position(6, 7), new Position(7, 7)));
//        scrabble.printBoard();
//        rack.remove(Tile.H);
//        scrabble.takeTurn(rack, List.of(new Position(1, 8), new Position(1, 9), new Position(1, 10), new Position(1, 11), new Position(1, 12), new Position(1, 13)));;
//        scrabble.printBoard();
//        rack.clear();
//        rack.add(Tile.P);
//        rack.add(Tile.A);
//        rack.add(Tile.Y);
//        scrabble.takeTurn(rack, List.of(new Position(0,12), new Position(0, 13), new Position(0,14)));
//        scrabble.printBoard();
//        scrabble.printAllWords();

//        System.out.println(scrabble.rackString());
//        System.out.println(scrabble.getWordFinder().findAnagram(scrabble.rackString()));

    }
}


