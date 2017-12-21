import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * AI Class.
 */
public class AI {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private Cell color;

    //---------- INITIALIZER ----------

    /**
     * AI(Board b, Cell color).
     *
     * @param b Board -- a game board.
     * @param color Cell - BLACK or WHITE.
     */
    AI(Board b, Cell color) {
        this.board = b;
        this.color = color;
    }

    //---------- GETTERS ----------

    /**
     * getColor().
     *
     * @return the color of this AI.
     */
    public Cell getColor() {
        return this.color;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * move().
     *
     * @return a position chosen by this AI.
     */
    //TODO: finish move() - need to simulate 3 next moves on board.

    /*public Position move() {
        // Local Variables
        Board simBoard = this.board.getBoard();
        Position p = new Position(-1, -1);
        List<Position> availableMoves = calculateAvailableMoves();
        int minPotential = Integer.MAX_VALUE;

        for(int i = 0; i < )

        return p;
    }*/

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * calculateAvailableMoves().
     *
     * @return a list of available moves for this turn.
     */
    private List<Position> calculateAvailableMoves() {
        List<Position> cellsInUse = this.board.getOccupiedCells();
        List<Position> availableMoves = new ArrayList<>();

        while(cellsInUse.isEmpty()) {
            // inner loop variables
            Position p = cellsInUse.get(0);
            Cell currentCell = board.getCellColor(p.getRow(), p.getCol());

            if(this.color == Cell.WHITE && currentCell == Cell.BLACK
                    || this.color == Cell.BLACK && currentCell == Cell.WHITE) {
                // Get empty neighbors for current position
                List<Position> emptyNeighbors = board.getEmptyNeighbors(p);

                while(!emptyNeighbors.isEmpty()) {
                    // get cell potential for current neighbor (always take index: 0)
                    List<Position> cellPotential = board.getCellPotentialAsList(emptyNeighbors.get(0), this.color);

                    if(!cellPotential.isEmpty()) {
                        // cell potential is NOT empty, and therefor an available move
                        availableMoves.addAll(cellPotential);
                    }

                    // remove the item just checked
                    emptyNeighbors.remove(0);
                }
            }

            // remove the item just checked
            cellsInUse.remove(0);
        }

        return availableMoves;
    }
}
