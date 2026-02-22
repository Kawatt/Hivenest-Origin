package io.github.kawatt.hivenestkwt.utils;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.util.Pair;
import java.util.List;

public enum RemoveStrategy {
    FIRST,
    LAST,
    RANDOM,
    INDEX;

    public static <T> Pair<T, Boolean> remove(RemoveStrategy strategy, List<T> list, int index) {

        if (list == null || list.isEmpty()) {
            new Pair<>(null, false);
        }

        return switch (strategy) {
            case FIRST -> new Pair<>(list.remove(0), true);
            case LAST -> new Pair<>(list.remove(list.size() - 1), true);
            case RANDOM -> new Pair<>(
                    list.remove(
                        ThreadLocalRandom.current().nextInt(list.size())
                    ),
                    true
            );
            case INDEX -> {
                if (index < 0 || index >= list.size()) {
                    yield new Pair<>(null, false);
                }
                yield  new Pair<>(list.remove(index), true);
            }
        };
    }
}
