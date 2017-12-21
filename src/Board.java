import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Board Class.
 */
public class Board {
    //---------- LOCAL CLASS VARIABLES ----------
    private int size;
    private Cell[][] board;
    private int cellsInUse;
    private List<Position> occupiedCells;

    //---------- INITIALIZER ----------

    /***
     * Board(Cell[][] board).
     *
     * @param board Cell[][] -- a Cells matrix.
     */
    public Board(Cell[][] board) {
        this.board = board;
        this.size = board.length;
        this.cellsInUse = 0;
        occupiedCells = initializeOccupiedCells();
    }

    //---------- GETTERS ----------

    /**
     * getSize().
     *
     * @return the size of the board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getBoard().
     *
     * @return a copy of this game board.
     */
    public Board getBoard() {
        // Local Variables
        Cell[][] cellMatrix = new Cell[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cellMatrix[i][j] = this.board[i][j];
            }
        }

        return new Board(cellMatrix);
    }

    /**
     * getSpaceLeft().
     *
     * @return the number of empty cells on board.
     */
    public int getSpaceLeft() {
        return this.size - this.cellsInUse;
    }

    /**
     * getCellPotentialAsList(Position p, Cell c).
     *
     * @param p Position -- a position on board.
     * @param c Cell -- player's color.
     *
     * @return a list of all cells that are potentially effected in the next move.
     */
    public List<Position> getCellPotentialAsList(Position p, Cell c) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                // get potential in path
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, c);

                // found potential in path
                if(!potentialInPath.isEmpty()) {
                    // add it to potential list
                    potentialList.addAll(potentialInPath);
                }
            }
        }

        return potentialList;
    }

    //---------- PUBLIC FUNCTIONS ----------
    public void moveMade(Position p, Cell value) {
        // Local Variables
        int row = p.getRow();
        int col = p.getCol();

        List<Position> cellsEffected = getCellsPotentialAsList(p, value);

        // clear duplicates
        Set<Position> hs = new HashSet<>();
        hs.addAll(cellsEffected);
        cellsEffected.clear();
        cellsEffected.addAll(hs);

        // change cell's value
        this.board[row][col] = value;

        // update occupied cells
        this.occupiedCells.add(p);

        // change effected cells colors
        while (cellsEffected.isEmpty()) {
            // inner loop local variables
            int nItem = cellsEffected.size() - 1;

            // change the cell color
            changeCellColor(cellsEffected.get(nItem));
            cellsEffected.remove(nItem); // remove this item
        }
    }

    /**
     * getCellColor(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return the color of the board at (row,col).
     */
    public Cell getCellColor(int row, int col) {
        if (this.board[row][col] == Cell.WHITE) {
            return Cell.WHITE;
        }

        if (this.board[row][col] == Cell.BLACK) {
            return Cell.BLACK;
        }

        return Cell.EMPTY;
    }

    /**
     * getOccupiedCells().
     *
     * @return a copied list of the cells occupied on this board.
     */
    public List<Position> getOccupiedCells() {
        List<Position> cellsInUse = new ArrayList<>();
        cellsInUse.addAll(this.occupiedCells);

        return cellsInUse;
    }

    /**
     * getEmptyNeighbors(Position p).
     *
     * @param p Position -- a position.
     * @return a list of empty neighbors (as positions) around a given position.
     */
    public List<Position> getEmptyNeighbors(Position p) {
        // Local Variables
        List<Position> emptyNeighborsList = new ArrayList<>();
        int row = p.getRow();
        int col = p.getCol();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                // inner loop variables
                int dRow = row + i;
                int dCol = col + j;

                if (cellOnBoard(dRow, dCol)) {
                    // cell on board -> get it's color
                    Cell c = this.board[dRow][dCol];

                    if (c == Cell.EMPTY) {
                        // empty val -> push cell's index to the list
                        emptyNeighborsList.add(new Position(dRow, dCol));
                    }
                }
            }
        }

        return emptyNeighborsList;
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * getCellsPotentialAsList(Position p, Cell value).
     *
     * @param p     Position -- a position on board.
     * @param color Cell -- player color.
     * @return a list of positions, representing the cell potential.
     */
    private List<Position> getCellsPotentialAsList(Position p, Cell color) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, color);

                if (!potentialInPath.isEmpty()) {
                    // add potential
                    potentialList.addAll(potentialInPath);
                }
            }
        }
        return potentialList;
    }

    /**
     * findPotentialInPath(Position p, int dRow, int dCol, Cell color).
     *
     * @param p     Position -- a position on board.
     * @param dRow  int -- row delta.
     * @param dCol  int -- column delta.
     * @param color Cell -- cell color.
     * @return a list of potential cells to effect in a given row/col delta.
     */
    private List<Position> findPotentialInPath(Position p, int dRow, int dCol, Cell color) {
        // Local Variables
        List<Position> potentialInPath = new ArrayList<>();
        int row = p.getRow() + dRow;
        int col = p.getCol() + dCol;
        int counter = 0;

        // delta is 0 - no need to check anything
        if (dRow == 0 && dCol == 0)
            return potentialInPath;

        for (; cellOnBoard(row, col); row += dRow, col += dCol) {
            // hit an empty cell
            if (board[row][col] == Cell.EMPTY) {
                // clear potential
                potentialInPath.clear();
                return potentialInPath;
            }

            // player type is found -> return
            if (board[row][col] == color) {
                if (counter == 0)
                    potentialInPath.clear();
                return potentialInPath;
            }

            // getting so far means that this cell is an opponent cell -> add it!
            Position opponentCell = new Position(row, col);
            potentialInPath.add(opponentCell);
            ++counter;
        }
        if (!cellOnBoard(row, col))
            potentialInPath.clear();

        return potentialInPath;
    }

    /**
     * changeCellColor(Position p).
     *
     * @param p Position -- a position on the board.
     */
    private void changeCellColor(Position p) {
        // Local Variables
        Cell color = this.board[p.getRow()][p.getCol()];

        // change color
        if (color == Cell.BLACK) {
            this.board[p.getRow()][p.getCol()] = Cell.WHITE;
        } else {
            this.board[p.getRow()][p.getCol()] = Cell.BLACK;
        }
    }

    /**
     * cellOnBoard(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return true if cell is on board, or false otherwise.
     */
    private boolean cellOnBoard(int row, int col) {
        return (0 <= row && row < this.size && 0 <= col && col < this.size);
    }

    /**
     * initializeOccupiedCells().
     *
     * @return a list of occupied cells.
     */
    private List<Position> initializeOccupiedCells() {
        // Local Variables
        List<Position> occupiedCells = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] != Cell.EMPTY) {
                    occupiedCells.add(new Position(i, j));
                }
            }
        }
        return occupiedCells;
    }
}