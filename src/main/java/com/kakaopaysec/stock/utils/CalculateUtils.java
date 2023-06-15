package com.kakaopaysec.stock.utils;

import java.util.Random;

public class CalculateUtils {



    public static int calculateRandomFluctuation(int value) {
        Random random = new Random();
        double percentageRange = 0.5; // 50% 범위
        double range = value * percentageRange;
        double randomOffset = random.nextDouble() * range * 2 - range;
        return (int) (value + randomOffset);
    }

    public static long calculateRandomFluctuation(long value) {
        Random random = new Random();

        double percentageRange = 0.5; // 50% 범위
        double range = value * percentageRange;
        double randomOffset = random.nextDouble() * range * 2 - range;
        return (int) (value + randomOffset);
    }

}
