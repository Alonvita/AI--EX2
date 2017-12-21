/**
 * ReversiGame Class.
 */
public class ReversiGame {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private TurnsManager manager;

    //---------- INITIALIZER ----------

    /**
     * ReversiGame(Board b).
     *
     * @param b Board -- a game board.
     */
    public ReversiGame(Board b) {
        this.board = b;
        this.manager = new TurnsManager(this.board);
    }

    //---------- GETTERS ----------

    /**
     * getBoard().
     *
     * @return the board played by this game.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * start().
     */
    public void start() {
        while(this.board.getSpaceLeft() > 0) {
            // play a move
            this.board.moveMade(this.manager.nextMove(), this.manager.getCurrentPlayerColor());
            // end turn
            manager.endTurn();
        }
        //TODO: remove this after finishing the function: endGame();
    }


    //TODO: endGame().

    /**
     * endGame().
     */
    public void endGame() {

    }
}
