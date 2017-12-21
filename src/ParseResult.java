/**
 * ParseResult Class.
 */
public class ParseResult {
    //---------- LOCAL CLASS VARIABLES ----------
    private Cell[][] matrix;

    //---------- INITIALIZER ----------

    /**
     * ParseResult(Cell[][] matrix).
     *
     * @param matrix Cell[][] -- a Cells matrix.
     */
    public ParseResult(Cell[][] matrix) {
        this.matrix = matrix;
    }

    //---------- GETTERS ----------
    /**
     * getMatrix().
     *
     * @return the cells matrix.
     */
    public Cell[][] getMatrix() {
        return this.matrix;
    }
}