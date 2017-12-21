import java.util.Scanner;

/**
 * java_ex2 Class -- Main.
 */
public class java_ex2 {
    public static void main(String[] args) throws Exception {
        // Local java_ex1 Variables
        Scanner fp = new Scanner(System.in);
        System.out.println("Please enter input file path:");
        String path = fp.next();
        ParseResult p;

        // parse file into a map
        try {
            p = ParseBoardFromFile.ParseBoard(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting...");
            return;
        }

        Board board;

        try {
            board = new Board(p.getMatrix());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // create a game object
        ReversiGame g = new ReversiGame(board);

        // start game
        g.start();
    }
}
