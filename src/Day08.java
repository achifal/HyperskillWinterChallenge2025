private static final Path POSITIONS_FILE_PATH = Path.of("Day08.txt");
private static final String ROW_COL_SEPARATOR = ",";
private static final int BOARD_SIZE = 8;
private static final int DIAGONALS_COUNT = 2 * BOARD_SIZE - 1;

void main() throws IOException {
    int[] rows = new int[BOARD_SIZE], cols = new int[BOARD_SIZE],
            diags = new int[DIAGONALS_COUNT], antiDiags = new int[DIAGONALS_COUNT];
    Files.readAllLines(POSITIONS_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .map(Position::new)
            .forEach(position -> {
                ++rows[position.row];
                ++cols[position.col];
                ++diags[diagIndex(position.row, position.col)];
                ++antiDiags[antiDiagIndex(position.row, position.col)];
            });
    int conflictCount = Stream.of(rows, cols, diags, antiDiags)
            .flatMapToInt(Arrays::stream)
            .map(item -> Math.max(0, item - 1))
            .sum();
    System.out.println(conflictCount);
}

private static int diagIndex(int row, int col) {
    return BOARD_SIZE - 1 + row - col;
}

private static int antiDiagIndex(int row, int col) {
    return row + col;
}

private record Position(int row, int col) {
    Position(String line) {
        String[] items = line.split(ROW_COL_SEPARATOR);
        this(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
    }
}
