package fr.cookiedev.jlangton.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LangtonPathToMap {
    final Supplier<CartesianLangtonMap> mapSupplier;

    final Map<Long, Boolean> currentMap;

    public LangtonPathToMap(Supplier<CartesianLangtonMap> mapSupplier) {
        this.mapSupplier = mapSupplier;
        this.currentMap = new HashMap<>();
    }

    public CartesianLangtonMap applyPath(String path, long fromPos) {
        CartesianLangtonMap initialLangtonMap = mapSupplier.get();
        for (int index = 0; index < path.length(); index++) {
            fromPos = move(path.charAt(index) == '1', initialLangtonMap, fromPos);
        }
        return initialLangtonMap;
    }

    public CartesianLangtonMap applyBackPath(String path, long fromPos) {
        CartesianLangtonMap initialLangtonMap = mapSupplier.get();
        for (int index = path.length() - 1; index >= 0; index--) {
            fromPos = back(path.charAt(index) == '1', initialLangtonMap, fromPos);
        }
        return initialLangtonMap;
    }

    public long move(boolean isRight, LangtonMap initialLangtonMap, long fromPos) {
        Boolean tile = currentMap.get(fromPos);
        if (tile == null) {
            initialLangtonMap.set(fromPos, isRight);
        } else if (isRight != tile) {
            throw new IllegalArgumentException("Incorrect Move");
        }

        currentMap.put(fromPos, !isRight);
        return isRight ? initialLangtonMap.toRight(fromPos) : initialLangtonMap.toLeft(fromPos);
    }

    public long back(boolean isRight, LangtonMap initialLangtonMap, long fromPos) {
        boolean backRight = !isRight;
        long prevPos = initialLangtonMap.prev(fromPos);
        Boolean tile = currentMap.get(prevPos);
        if (tile == null) {
            initialLangtonMap.set(prevPos, backRight);
        } else if (backRight != tile) {
            throw new IllegalArgumentException("Incorrect Move");
        }

        currentMap.put(prevPos, isRight);
        return isRight ? initialLangtonMap.backRight(fromPos) : initialLangtonMap.backLeft(fromPos);
    }

    public CartesianLangtonMap symetricPath(String path, long fromPos) {
        CartesianLangtonMap initialLangtonMap = applyPath(path, fromPos);
        initialLangtonMap.resetOrientation();
        for (int index = path.length() - 1; index >= 0; index--) {
            fromPos = back(path.charAt(index) == '1', initialLangtonMap, fromPos);
        }

        return initialLangtonMap;
    }

}