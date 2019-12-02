package com.wtbw.util;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/*
  @author: Naxanria
*/
public class RandomUtil {
    public static <T> T get(Random random, @Nonnull List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T get(Random random, @Nonnull T[] array) {
        return array[random.nextInt(array.length)];
    }

    public static boolean chance(Random random, int chance) {
        return random.nextInt(100) <= chance;
    }

    public static boolean chance(Random random, float chance) {
        return random.nextFloat() <= chance;
    }

    public static <T> List<T> shuffle(Random random, @Nonnull List<T> list) {
        return shuffle(random, list, list.size() / 2 + 3);
    }

    public static <T> List<T> shuffle(Random random, @Nonnull List<T> list, int tries) {
        for (int i = 0; i < tries; i++) {
            int j = random.nextInt(list.size());
            int k = random.nextInt(list.size());

            if (j != k) {
                T temp = list.get(j);
                list.set(j, list.get(k));
                list.set(k, temp);
            }
        }

        return list;
    }
}
