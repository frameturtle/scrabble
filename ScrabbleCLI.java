import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrabbleCLI {
    private final static String HELP = "help";
    private final static String SWAP = "swap";
    private final static String PLAY = "play";
    private final static String QUIT = "quit";

    public static void help() {
        System.out.println("instructions");
    }

    public static void swap(String[] tiles, Scrabble game) {
        game.swap(makeTiles(tiles));
    }

    public static void play(String[] tiles, String[] coordinates, Scrabble game) {
        game.takeTurn(makeTiles(tiles), makePositions(coordinates));
        game.printBoard();
    }

    private static List<Tile> makeTiles(String[] tiles) {
        List<Tile> tileList = new ArrayList<>();
        for (String tile : tiles) {
            tileList.add(Tile.valueOf(tile));
        }
        return tileList;
    }

    private static List<Position> makePositions(String[] coordinates) {
        List<Position> positionList = new ArrayList<>();
        for (String coordinate : coordinates) {
            String[] values = coordinate.split(",");
            Position position = new Position(java.lang.Integer.parseInt(values[0]), java.lang.Integer.parseInt(values[1]));
            positionList.add(position);
        }
        return positionList;
    }

    public static void main(String[] args) {
        boolean noQuit = true;
        Scrabble game = new Scrabble();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome to Scrabble\n\nGet a score greater than 200 in 10 or fewer turns to win!");
        game.printBoard();
        while(noQuit) {
            if(game.getBag().isEmpty()) {
                System.out.println("Bag has no more tiles");
                noQuit = false;
                break;
            } else if (game.getScore() >= 60) {
                System.out.println("You won!");
                game.printAllWords();
                noQuit = false;
                break;
            } else if (game.getTurnNumber() >= 10) {
                System.out.println("You lost!");
            }
            System.out.println("\nRack: " + game.getRack() + "\nScore: " + game.getScore() + "\nTurns: " + game.getTurnNumber() + "\n");
            System.out.print(">> ");
            String command = sc.nextLine();
            switch (command) {
                case HELP -> help();
                case SWAP -> {
                    System.out.print("Enter tiles to be swapped: ");
                    String[] tiles = sc.nextLine().split(" ");
                    swap(tiles, game);
                }
                case PLAY -> {
                    System.out.print("Enter tiles to be played: ");
                    String[] tiles = sc.nextLine().split(" ");
                    System.out.print("Enter the desired coordinates for each tile: ");
                    String[] coordinates = sc.nextLine().split(" ");
                    play(tiles, coordinates, game);
                }
                case QUIT -> noQuit = false;
            }
        }
        System.out.println("Goodbye!");
    }
}
