package mx.edu.uaz.is.poo2.carger.services.simulator;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerSimulator {

    private static int getRandomInt(int lo, int hi, int factor) {
        return ThreadLocalRandom.current().nextInt(lo, hi) + ThreadLocalRandom.current().nextInt(0, 3) * factor;
    }

    private static int getFactor() {
        int factorDeterminant = getRandomInt(0, 10, 1);
        int factor;
        if (factorDeterminant < 2)
            factor = 0;
        else if (factorDeterminant < 7)
            factor = 1;
        else if (factorDeterminant < 10)
            factor = 2;
        else
            factor = 4;
        return factor;
    }

    private static int getRandom(int lolo, int lohi, int hilo, int hihi) {
        return getRandomInt(
            ThreadLocalRandom.current().nextInt(lolo, lohi),
            ThreadLocalRandom.current().nextInt(hilo, hihi),
            getFactor()
        );
    }

    public static int getRandomGoals() {
        return getRandom(0, 4, 10, 40);
    }

    public static int getRandomAssists() {
        int goals = getRandom(0, 7, 10, 40) - getRandomInt(0, 10, 1);
        return goals >= 0 ? goals : 0;
    }

    public static int getRandomGamesPlayed() {
        return getRandom(0, 20, 20, 38);
    }

    public static int getRandomYellowCards() {
        int cards = getRandom(0, 1, 1, 5) - getRandomInt(0, 3, 1);
        return cards >= 0 ? cards : 0;
    }

    public static int getRandomRedCards() {
        int cards = getRandom(-1, 0, 0, 5) - getRandomInt(0, 4, 1);
        return cards >= 0 ? cards : 0;
    }
}