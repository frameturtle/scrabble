import java.util.List;

public class Word {
    private List<Square> squares;

    public Word(List<Square> squares) {
        this.squares = squares;
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
}
