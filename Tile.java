public enum Tile {
    A('A',1,9,true),
    B('B',3,2,false),
    C('C',3,2,false),
    D('D',2,4,false),
    E('E',1,12,true),
    F('F',4,2,false),
    G('G',2,3,false),
    H('H',4,2,false),
    I('I',1,9,true),
    J('J',8,1,false),
    K('K',5,1,false),
    L('L',1,4,false),
    M('M',3,2,false),
    N('N',1,6,false),
    O('O',1,8,true),
    P('P',3,2,false),
    Q('Q',10,1,false),
    R('R',1,6,false),
    S('S',1,4,false),
    T('T',1,6,false),
    U('U',1,4,true),
    V('V',4,2,false),
    W('W',4,2,false),
    X('X',8,1,false),
    Y('Y',4,2,false),
    Z('Z',10,1,false),
    Special('?',0,2,false);

    private final char letter;
    private final int value;
    private final int amount;
    private final boolean isVowel;

    private Tile(char letter, int value, int amount, boolean isVowel) {
        this.letter = letter;
        this.value = value;
        this.amount = amount;
        this.isVowel = isVowel;
    }

    @Override
    public String toString() {
        return String.valueOf(letter);
    }

    public int getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isVowel() {
        return isVowel;
    }
}
