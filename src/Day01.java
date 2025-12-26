import java.util.Map.Entry;

private static final Path LOG_FILE_PATH = Path.of("Day01.txt");
private static final LocalTime TIME_WINDOW_START = LocalTime.of(15, 0);
private static final LocalTime TIME_WINDOW_END = LocalTime.of(15, 30);

void main() throws IOException {
    List<Log> logs = Files.readAllLines(LOG_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .map(Log::new)
            .toList();
    String backgroundNoiseError = findMostFrequentError(logs);

    List<Log> alertWindowLogs = logs
            .stream()
            .filter(log -> log.timestamp.isAfter(TIME_WINDOW_START))
            .filter(log -> log.timestamp.isBefore(TIME_WINDOW_END))
            .filter(log -> !log.error().equals(backgroundNoiseError))
            .toList();
    String alertError = findMostFrequentError(alertWindowLogs);

    System.out.println(alertError);
}

private static String findMostFrequentError(List<Log> logs) {
    return logs
            .stream().map(Log::error)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .max(Entry.comparingByValue())
            .map(Entry::getKey)
            .orElseThrow();
}

private record Log(LocalTime timestamp, String error) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    Log(String line) {
        String[] parts = line.split(" ");
        this(LocalTime.parse(parts[0], TIME_FORMATTER), parts[1]);
    }
}
