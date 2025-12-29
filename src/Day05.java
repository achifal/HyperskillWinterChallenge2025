private static final Path INPUT_FILE_PATH = Path.of("Day05.txt");
private static final String COORDINATES_SEPARATOR = ",";
private static final String AREA_OUTPUT_FORMAT = "%.2f%n";

void main() throws IOException {
    Point[] points = Files.readAllLines(INPUT_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .map(Point::new)
            .toArray(Point[]::new);
    double starArea = calculatePolygonArea(points);
    System.out.printf(AREA_OUTPUT_FORMAT, starArea);
}

private static double calculatePolygonArea(Point[] points) {
    int n = points.length;
    double area = 0.0;

    for (int curIndex = 0; curIndex < n; ++curIndex) {
        Point current = points[curIndex], next = points[(curIndex + 1) % n];
        area += current.x * next.y - current.y * next.x;
    }
    return Math.abs(area) / 2.0;
}

private record Point(double x, double y) {
    Point(String line) {
        String[] coordinates = line.split(COORDINATES_SEPARATOR);
        this(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
