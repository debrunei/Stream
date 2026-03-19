void main() {

}

static List<Enemy> enemies = List.of(
        new Enemy("Goomba", 30, true, "ground"),
        new Enemy("Koopa", 50, true, "ground"),
        new Enemy("Boo", 40, false, "flying"),
        new Enemy("Piranha", 80, true, "flying"),
        new Enemy("Lakitu", 35, true, "flying"),
        new Enemy("Hammer bro", 90, true, "ground"),
        new Enemy("Buzzy Beetle", 120, false, "ceiling")
);

static List<Coin> coins = List.of(
        new Coin(100, 200, false, 10),
        new Coin(150, 200, true, 10),
        new Coin(200, 200, false, 10),
        new Coin(300, 15, false, 50),
        new Coin(400, 300, true, 10),
        new Coin(500, 100, false, 50),
        new Coin(600, 200, true, 10)
);

static List<Platform> platforms = List.of(
        new Platform("plat-C", 320f, 300f, 100f),
        new Platform("plat-A", 40f, 200f, 120f),
        new Platform("plat-E", 640f, 150f, 80f),
        new Platform("plat-B", 180f, 250f, 90f),
        new Platform("plat-D", 500f, 280f, 110f)
);

static List<Integer> highScores = List.of(
        8500, 4200, 9900, 3100, 7650, 5500, 9900, 2200, 6800
);

static List<Enemy> getAliveEnemies(){
    return enemies.stream().filter(Enemy::alive).toList();
}

static List<Enemy> getAliveFlyers(){
    return enemies.stream().filter(Enemy::alive).filter(e -> e.type().equals("flying")).toList();
}

static List<Coin> getUncollectedGoldCoins(){
    return coins.stream().filter(c -> !c.collected()).filter(c -> c.value() >= 50).toList();
}

static List<String> getAllEnemyNames(){
    return enemies.stream().map(Enemy::name).toList();
}

static long countUncollectedCoins(){
    return coins.stream().filter(c -> !c.collected()).count();
}

static String firstToughEnemy(){
    return enemies.stream().filter(e -> e.alive() && e.hp() > 70)
            .map(Enemy::name)
            .findFirst()
            .orElse("none");
}

static boolean hasCeilingEnemy(){
    return enemies.stream().anyMatch(e -> e.type().equals("ceiling"));
}


// ─────────────────────────────────────────────
//  PART 4 — Chained Pipelines (3 pts)
// ─────────────────────────────────────────────

/**
 * 4a. Return the top 3 unique high scores in DESCENDING order.
 * Hint: distinct() → sorted() with reverse comparator → limit() → collect()
 */
static List<Integer> topThreeScores() {
    return highScores.stream().distinct().sorted((s1, s2) -> -s1.compareTo(s2)).limit(3).toList();
}

/**
 * 4b. Return a comma-separated String of alive enemy names,
 *     in alphabetical order.
 *     Hint: filter → map to name → sorted → collect(Collectors.joining(", "))
 */
static String aliveEnemyNamesCsv() {
    return enemies.stream()
            .filter(Enemy::alive)
            .sorted((e1, e2) -> e1.name().compareTo(e2.name()))
            .map(Enemy::name)
            .collect(Collectors.joining(", "));
}

/**
 * 4c. Return a list of platform IDs for platforms with width >= 100f,
 *     sorted by x-position (ascending).
 */
static List<String> widePlatformIds() {
    return platforms.stream()
            .filter(p -> p.width() >= 100f)
            .sorted((p1, p2) -> Float.compare(p1.x(), p2.x()))
            .map(Platform::id)
            .toList();
}

// ─────────────────────────────────────────────
//  PART 5 — Platformer Integration (3 pts)
// ─────────────────────────────────────────────

/**
 * 5a. Calculate the total value of ALL uncollected coins.
 *     Hint: Use mapToInt() and sum(), OR map + reduce.
 *     mapToInt(Coin::value).sum() is the cleanest approach.
 */
static int totalUncollectedCoinValue() {
    return coins.stream()
            .filter(c -> !c.collected())
            .mapToInt(Coin::value)
            .sum();
}

/**
 * 5b. Return true if ALL alive enemies have HP >= 30.
 *     Hint: filter alive, then allMatch()
 */
static boolean allAliveEnemiesHealthy() {
    return enemies.stream().filter(Enemy::alive).allMatch(e -> e.hp() >= 30);
}

/**
 * 5c. Return a list of enemy names whose HP is above the
 * average HP of all enemies (alive or not).
 * Step 1: Calculate average HP using mapToInt().average().orElse(0)
 * Step 2: Filter enemies where hp > average, map to name, collect.
 */
static List<String> aboveAverageHpNames() {
    double averageHp = enemies.stream().mapToInt(Enemy::hp).average().orElse(0);

    return enemies.stream()
            .filter(e -> e.hp() >= averageHp)
            .map(Enemy::name)
            .toList();
}

// ─────────────────────────────────────────────
//  Main — Run to check your answers
// ─────────────────────────────────────────────

public static void main(String[] args) {
    System.out.println("====== Java Streams Activity ======\n");

    System.out.println("--- PART 1: filter() ---");
    System.out.println("1a alive enemies:     " + getAliveEnemies());
    System.out.println("1b alive flyers:      " + getAliveFlyers());
    System.out.println("1c uncollected gold:  " + getUncollectedGoldCoins());

    System.out.println("\n--- PART 2: map() ---");
    System.out.println("2a enemy names:       " + getAllEnemyNames());

    System.out.println("\n--- PART 3: terminal ops ---");
    System.out.println("3a uncollected count: " + countUncollectedCoins());
    System.out.println("3b first tough:       " + firstToughEnemy());
    System.out.println("3c ceiling enemy?:    " + hasCeilingEnemy());

    System.out.println("\n--- PART 4: chained ---");
    System.out.println("4a top 3 scores:      " + topThreeScores());
    System.out.println("4b alive names csv:   " + aliveEnemyNamesCsv());
    System.out.println("4c wide platforms:    " + widePlatformIds());

    System.out.println("\n--- PART 5: integration ---");
    System.out.println("5a uncollected value: " + totalUncollectedCoinValue());
    System.out.println("5b all healthy?:      " + allAliveEnemiesHealthy());
    System.out.println("5c above avg HP:      " + aboveAverageHpNames());
}


/*
 * ═══════════════════════════════════════════════════
 *  EXPECTED OUTPUT (do not look until you're done!)
 * ═══════════════════════════════════════════════════
 *
 * --- PART 1: filter() ---
 * 1a alive enemies:     [Enemy[name=Goomba, ...], Enemy[name=Koopa, ...],
 *                         Enemy[name=Piranha, ...], Enemy[name=Lakitu, ...],
 *                         Enemy[name=Hammer Bro, ...]]
 * 1b alive flyers:      [Enemy[name=Lakitu, ...]]
 * 1c uncollected gold:  [Coin[x=300,...], Coin[x=500,...]]
 *
 * --- PART 2: map() ---
 * 2a enemy names:       [Goomba, Koopa, Boo, Piranha, Lakitu, Hammer Bro, Thwomp]
 * 2b doubled HP:        [60, 100, 80, 160, 70, 180, 240]
 * 2c platform ids:      [plat-A, plat-B, plat-C, plat-D, plat-E]
 *
 * --- PART 3: terminal ops ---
 * 3a uncollected count: 4
 * 3b first tough:       Piranha
 * 3c ceiling enemy?:    true
 *
 * --- PART 4: chained ---
 * 4a top 3 scores:      [9900, 8500, 7650]
 * 4b alive names csv:   Goomba, Hammer Bro, Koopa, Lakitu, Piranha
 * 4c wide platforms:    [plat-A, plat-C, plat-D]
 *
 * --- PART 5: integration ---
 * 5a uncollected value: 120
 * 5b all healthy?:      true
 * 5c above avg HP:      [Piranha, Hammer Bro, Thwomp]
 */