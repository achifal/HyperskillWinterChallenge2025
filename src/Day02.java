private static final Path INPUT_FILE_PATH = Path.of("Day02.txt");
private static final Pattern SWEETNESS_VALUE_SEPARATOR_PATTERN = Pattern.compile(",");

void main() throws IOException {
    List<String> lines = Files.readAllLines(INPUT_FILE_PATH);
    int desiredSweetness = Integer.parseInt(lines.get(0));
    int[] sweetnessValues = SWEETNESS_VALUE_SEPARATOR_PATTERN
            .splitAsStream(lines.get(1))
            .mapToInt(Integer::parseInt)
            .toArray();

    int closestSweetness = findClosestSweetness(sweetnessValues, desiredSweetness);
    System.out.println(closestSweetness);
}

private static int findClosestSweetness(int[] sweetnessValues, int desiredSweetness) {
    int closestSweetness = 0;
    int left = 0, right = sweetnessValues.length - 1;

    while (left < right) {
        int currentSweetness = (sweetnessValues[left] + sweetnessValues[right]) / 2;

        if (Math.abs(closestSweetness - desiredSweetness) > Math.abs(currentSweetness - desiredSweetness)) {
            closestSweetness = currentSweetness;
        }

        if (currentSweetness < desiredSweetness) {
            ++left;
        } else {
            --right;
        }
    }

    return closestSweetness;
}
