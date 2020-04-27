package fr.cookiedev.jlangton.core;

import java.util.HashMap;
import java.util.Map;

public class LangtonPathToMap {
    final LangtonMap initialLangtonMap;

    final Map<Long, Boolean> currentMap;

    public LangtonPathToMap(LangtonMap initialLangtonMap) {
        this.initialLangtonMap = initialLangtonMap;
        this.currentMap = new HashMap<>();
    }

    public LangtonMap applyPath(String path, long fromPos) {
        for (int index = 0; index < path.length(); index++) {
            fromPos = move(path.charAt(index) == '1', fromPos);
        }
        return initialLangtonMap;
    }

    public long move(boolean isRight, long fromPos) {
        Boolean tile = currentMap.get(fromPos);
        if (tile == null) {
            initialLangtonMap.set(fromPos, isRight);
        } else if (isRight != tile) {
            throw new IllegalArgumentException("Incorrect Move");
        }

        currentMap.put(fromPos, !isRight);
        return isRight ? initialLangtonMap.toRight(fromPos) : initialLangtonMap.toLeft(fromPos);
    }

}