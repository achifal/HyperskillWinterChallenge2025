private static final Path PASSWORDS_FILE_PATH = Path.of("Day03.txt");
private static final IntPredicate[] CATEGORY_CHECKS = {
        Character::isLowerCase,
        Character::isUpperCase,
        Character::isDigit,
        ch -> "!@#$%^&*".indexOf(ch) >= 0
};
private static final double CATEGORY_PENALTY = 0.75;
private static final double REPETITION_THRESHOLD = 0.3;

void main() throws IOException {
    String mostSecurePassword = Files.readAllLines(PASSWORDS_FILE_PATH)
            .stream()
            .filter(line -> !line.isBlank())
            .max(Comparator.comparingDouble(password -> calculatePasswordScore(password)))
            .orElseThrow();

    System.out.println(mostSecurePassword);
}

private static double calculatePasswordScore(String password) {
    double score = password.length();

    score *= Arrays.stream(CATEGORY_CHECKS)
            .filter(check -> password.chars().noneMatch(check))
            .mapToDouble(c -> CATEGORY_PENALTY)
            .reduce(1.0, (a, b) -> a * b);

    Map<Character, Long> frequencyMap = password.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    long maxOccurrences = Collections.max(frequencyMap.values());
    if (maxOccurrences >= password.length() * REPETITION_THRESHOLD) {
        score -= maxOccurrences;
    }

    return score;
}
