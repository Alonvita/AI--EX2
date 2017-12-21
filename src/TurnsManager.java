import java.util.ArrayList;
import java.util.List;

/**
 * TurnsManager Class.
 */
public class TurnsManager {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private AI[] ai;
    private int playerTurn;
    private List<Position> availableMoves;

    //---------- INITIALIZER ----------
    public TurnsManager(Board b) {
        this.board = b;
        this.playerTurn = 1;
        this.availableMoves = new ArrayList<>();
        initializePlayers();
    }

    //---------- GETTERS ----------
    public Cell getCurrentPlayerColor() {
        return playerTurnToCellType();
    }

    //---------- PUBLIC FUNCTIONS ----------
    public void endTurn() {
        this.playerTurn = this.playerTurn ^ 1;
    }

    /**
     * nextMove().
     *
     * @return the position of the next move.
     */
    public Position nextMove() {
        return (this.ai[playerTurn].move());
    }

    //---------- PRIVATE FUNCTIONS ----------
    private void initializePlayers() {
        // Local Variables
        this.ai = new AI[2];

        // initialize players
        this.ai[0] = new AI(this.board, Cell.WHITE);
        this.ai[1] = new AI(this.board, Cell.BLACK);
    }

    /**
     * playerTurnToCellType().
     *
     * @return the player color.
     */
    private Cell playerTurnToCellType() {
        if (this.playerTurn == 0)
            return Cell.WHITE;
        return Cell.BLACK;
    }
}
