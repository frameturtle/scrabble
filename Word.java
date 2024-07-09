import java.util.HashMap;
import java.util.List;

public class Word {
    private final List<Square> squares;
    private final String string;
    private final int value;

    public Word(List<Square> squares) {
        this.squares = squares;
        this.string = String.valueOf(buildString());
        this.value = calculateValue();
    }

    private StringBuilder buildString() {
        StringBuilder string = new StringBuilder();
        for (Square square : squares) {
            string.append(square.getTile().toString());
        }
        return string;
    }

    public int calculateValue() {
        int value = 0;
        if (string.length() == 7) {
            value += 50;
        }
        int wordMultiplier = 1;
        for (Square square : squares) {
            int baseValue = square.getTile().getValue();
            if (square.getTimesUsed() <= 1) {
                if (square.getType() == Square.Type.DOUBLE_LETTER) {
                    baseValue *= 2;
                } else if (square.getType() == Square.Type.TRIPLE_LETTER) {
                    baseValue *= 3;
                } else if (square.getType() == Square.Type.DOUBLE_WORD) {
                    wordMultiplier += 1;
                } else if (square.getType() == Square.Type.TRIPLE_WORD) {
                    wordMultiplier += 2;
                }
            }
//            System.out.println(square.getTile() + ", " + baseValue);
            value += baseValue;
//            System.out.println("Value before multiplier: " + value);
        }
        value *= wordMultiplier;
//        System.out.println("Value after multiplier: " + value);
        return value;
    }

    public boolean isConnected(HashMap<Position, Square> map) {
        for (Square square : squares) {
            List<Position> neighbors = square.getNeighbors();
            for (Position neighborPos : neighbors) {
                Square neighbor = map.get(neighborPos);
                if (!squares.contains(neighbor) && !neighbor.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public String getString() {
        return string;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Square square : squares) {
            string.append(square.getTile().toString());
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Word other) {
            return squares.equals(other.squares);
        } else {
            return false;
        }
    }
//
//    @Override
//    public int hashCode() {
////        int result = 0;
////        for (Square square : squares) {
////            int hash = square.getPosition().hashCode();
////            result += hash;
////        }
////        return result;
//    }
}
