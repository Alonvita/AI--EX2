import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * ParseBoardFromFile Class.
 */
public class ParseBoardFromFile {
    //---------- INITIALIZER ----------

    /**
     * ParseBoard(String filename) throws Exception.
     *
     * @param filename String -- a string.
     *
     * @return the parsed result for this solution.
     * @throws Exception -- illegal number of lines in file.
     */
    public static ParseResult ParseBoard(String filename) throws Exception {
        // Local variables
        List<String> lines = Files.readAllLines(Paths.get(filename));

        if (lines.size() < 1) {
            throw new Exception("Illegal file format received");
        }

        // initialize a new Cells matrix
        int n = lines.get(1).length();
        Cell[][] matrix = new Cell[n][n];

        for (int row = 0; row < n; ++row) {
            String line = lines.get(row);
            for (int col = 0; col < n; ++col) {
                switch (line.charAt(col)) {
                    case 'W':
                        matrix[row][col] = Cell.WHITE;
                        break;
                    case 'B':
                        matrix[row][col] = Cell.BLACK;
                        break;
                    case 'E':
                        matrix[row][col] = Cell.EMPTY;
                        break;
                    default:
                        throw new Exception("Unsupported Cell Type '" + line.charAt(col) + "'");
                }
            }
        }

        return new ParseResult(matrix);
    }
}
