package fr.cookiedev.jlangton.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LangtonPathToMap {
    final Supplier<CartesianLangtonMap> mapSupplier;

    public LangtonPathToMap(Supplier<CartesianLangtonMap> mapSupplier) {
        this.mapSupplier = mapSupplier;
    }

    public CartesianLangtonMap applyPath(String path, long fromPos) {
        return applyPath(path, fromPos, mapSupplier.get(), new HashMap<>());
    }

    public CartesianLangtonMap applyPath(String path, long fromPos, CartesianLangtonMap initialLangtonMap, Map<Long, Boolean> checkMap) {
        for (int index = 0; index < path.length(); index++) {
            fromPos = move(path.charAt(index) == '1', initialLangtonMap, fromPos, checkMap);
        }
        initialLangtonMap.resetOrientation();
        return initialLangtonMap;
    }

    public CartesianLangtonMap applyBackPath(String path, long fromPos) {
        return applyBackPath(path, fromPos, mapSupplier.get(), new HashMap<>());
    }

    public CartesianLangtonMap applyBackPath(String path, long fromPos, CartesianLangtonMap initialLangtonMap, Map<Long, Boolean> checkMap) {
        for (int index = path.length() - 1; index >= 0; index--) {
            fromPos = back(path.charAt(index) == '1', initialLangtonMap, fromPos, checkMap);
        }
        initialLangtonMap.resetOrientation();
        return initialLangtonMap;
    }

    public boolean isLangtonPath(String path, long fromPos) {
        try {
            applyPath(path, fromPos);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public CartesianLangtonMap checkApplyAndBack(String forwardPath, String backwardPath, long fromPos) {
        try {
            CartesianLangtonMap langtonMap = mapSupplier.get();
            Map<Long,Boolean> checkMap = new HashMap<>();
            applyPath(forwardPath, fromPos, langtonMap, checkMap);
            applyBackPath(backwardPath, fromPos, langtonMap, checkMap);
            return langtonMap;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public long move(boolean isRight, LangtonMap initialLangtonMap, long fromPos, Map<Long, Boolean> currentMap) {
        Boolean tile = currentMap.get(fromPos);
        if (tile == null) {
            initialLangtonMap.set(fromPos, isRight);
        } else if (isRight != tile) {
            throw new IllegalArgumentException("Incorrect Move");
        }

        currentMap.put(fromPos, !isRight);
        return isRight ? initialLangtonMap.toRight(fromPos) : initialLangtonMap.toLeft(fromPos);
    }

    public long back(boolean isRight, LangtonMap initialLangtonMap, long fromPos, Map<Long, Boolean> currentMap) {
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

}