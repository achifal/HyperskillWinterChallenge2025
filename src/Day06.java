private static final Path INPUT_FILE_PATH = Path.of("Day06.txt");
private static final String RELATION_SEPARATOR = ",";

void main() throws IOException {
    List<String> lines = Files.readAllLines(INPUT_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .collect(Collectors.toCollection(ArrayList::new));
    String startBeing = lines.removeFirst();
    Map<String, List<String>> relations = buildRelations(lines);
    String furthestBeing = findFurthestBeing(startBeing, relations);
    System.out.println(furthestBeing);
}

private static Map<String, List<String>> buildRelations(List<String> lines) {
    Map<String, List<String>> relations = new HashMap<>();
    for (String line : lines) {
        String[] relation = line.split(RELATION_SEPARATOR);
        relations.computeIfAbsent(relation[0], _ -> new ArrayList<>()).add(relation[1]);
        relations.computeIfAbsent(relation[1], _ -> new ArrayList<>()).add(relation[0]);
    }
    return relations;
}

private static String findFurthestBeing(String startBeing, Map<String, List<String>> relations) {
    Set<String> visited = new HashSet<>(Set.of(startBeing));
    Queue<String> queue = new LinkedList<>(List.of(startBeing));
    String furthestBeing = null;

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        furthestBeing = queue.peek();

        for (int i = 0; i < levelSize; ++i) {
            String currentBeing = queue.poll();
            if (furthestBeing.compareTo(currentBeing) > 0) {
                furthestBeing = currentBeing;
            }
            for (String connection : relations.get(currentBeing)) {
                if (visited.add(connection)) {
                    queue.offer(connection);
                }
            }
        }
    }
    return furthestBeing;
}
