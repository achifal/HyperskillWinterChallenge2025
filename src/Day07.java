private static final Path BRIDGES_FILE_PATH = Path.of("Day07.txt");
private static final String BRIDGES_SEPARATOR = ",";

void main() throws IOException {
    List<String> lines = Files.readAllLines(BRIDGES_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .toList();
    long oddDegreeLandmasses = buildBridgesMap(lines)
            .values()
            .stream()
            .filter(connections -> connections.size() % 2 == 1)
            .count();
    System.out.println(Math.max(0, oddDegreeLandmasses / 2 - 1));
}

private static Map<String, List<String>> buildBridgesMap(List<String> lines) {
    Map<String, List<String>> bridgesMap = new HashMap<>();
    for (String line : lines) {
        String[] bridge = line.split(BRIDGES_SEPARATOR);
        bridgesMap.computeIfAbsent(bridge[0], _ -> new ArrayList<>()).add(bridge[1]);
        bridgesMap.computeIfAbsent(bridge[1], _ -> new ArrayList<>()).add(bridge[0]);
    }
    return bridgesMap;
}
