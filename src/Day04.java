private static final Path LOG_FILE_PATH = Path.of("Day04.txt");
private static final String LOG_ITEMS_SEPARATOR = ",";
private static final boolean[] FORK_IN_USE = new boolean[4];

void main() throws IOException {
    long contentions = Files.readAllLines(LOG_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .map(Log::new)
            .filter(log -> detectContention(log))
            .count();

    System.out.println(contentions);
}

private static boolean detectContention(Log log) {
    if (FORK_IN_USE[log.forkId] && log.action == Action.PICK) {
        return true;
    }
    FORK_IN_USE[log.forkId] = log.action == Action.PICK;
    return false;
}

private record Log(Action action, int forkId) {
    Log(String line) {
        String[] logItems = line.split(LOG_ITEMS_SEPARATOR);
        this(Action.parseAction(logItems[1]), Integer.parseInt(logItems[2]) - 1);
    }
}

private enum Action {
    PICK,
    RELEASE;

    private static Action parseAction(String logItem) {
        return valueOf(logItem.toUpperCase(Locale.ROOT));
    }
}
