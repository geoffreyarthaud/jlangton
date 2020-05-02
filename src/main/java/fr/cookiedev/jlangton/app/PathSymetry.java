package fr.cookiedev.jlangton.app;

import java.text.MessageFormat;

import fr.cookiedev.jlangton.core.AbstractRectLangtonMap;
import fr.cookiedev.jlangton.core.CartesianLangtonMap;
import fr.cookiedev.jlangton.core.LangtonPath;
import fr.cookiedev.jlangton.core.LangtonPathToMap;
import fr.cookiedev.jlangton.core.ListLangtonMapImpl;

public class PathSymetry {
    public static final int X_SIZE = 1024;
    public static final int Y_SIZE = 1024;
    public static final int INIT_POS_X = X_SIZE / 2;
    public static final int INIT_POS_Y = Y_SIZE / 2;

    public static void main(String[] args) {
        // Get forward good config
        LangtonPathToMap pathToMap = new LangtonPathToMap(ListLangtonMapImpl::new);
        final long fromPos = AbstractRectLangtonMap.encode(INIT_POS_X, INIT_POS_Y);
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String curPath = rotate(LangtonPath.CYCLE_PATH, i);
            CartesianLangtonMap langtonMap = pathToMap.applyPath(curPath, fromPos);
            if (relativeX(langtonMap.getMinX()) >= 0 && relativeY(langtonMap.getMinY()) >= 0) {
                System.out.println(MessageFormat.format("i = {0} - Frame : ({1}, {2}) ({3}, {4})",
                i, relativeX(langtonMap.getMinX()),
                   relativeY(langtonMap.getMinY()), relativeX(langtonMap.getMaxX()), relativeY(langtonMap.getMaxY())));   
            }
        }

        // Get backward good config
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String curPath = rotate(LangtonPath.CYCLE_PATH, i);
            CartesianLangtonMap langtonMap = pathToMap.applyBackPath(curPath, fromPos);
            if (relativeX(langtonMap.getMaxX()) <= 0 && relativeY(langtonMap.getMaxY()) <= 0) {
                System.out.println(MessageFormat.format("i = {0} - Frame : ({1}, {2}) ({3}, {4})",
                i, relativeX(langtonMap.getMinX()),
                   relativeY(langtonMap.getMinY()), relativeX(langtonMap.getMaxX()), relativeY(langtonMap.getMaxY())));   
            }
        }

        int nbCorrectConfigs = 0;
        int nbDiagNonCorrectConfigs = 0;
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String prefixPath = rotate(LangtonPath.CYCLE_PATH, i);
            for (int j = 0; j < LangtonPath.CYCLE_PATH.length(); j++) {
                String suffixPath = rotate(LangtonPath.CYCLE_PATH, j);
                CartesianLangtonMap langtonMap = pathToMap.checkApplyAndBack(duplicate(suffixPath, 10), duplicate(LangtonPath.backRevert(prefixPath),10), fromPos);
                if (langtonMap != null) {
                    System.out.println(MessageFormat.format("Found correct config : ({0},{1}) - Size : {2}", i, j, langtonMap.getAll().size()));
                    nbCorrectConfigs++;
                } else if (i == j) {
                    nbDiagNonCorrectConfigs++;
                }
            }
        }

        System.out.println("Found " + nbCorrectConfigs + " configs.");
        System.out.println("Found " + nbDiagNonCorrectConfigs + " diag non correct configs.");

        // Check Langton Map size
        System.out.println("Size for CYCLE : " + pathToMap.applyBackPath(duplicate(LangtonPath.CYCLE_PATH, 15), fromPos).getAll().size());
    }

    public static String rotate(String original, int offset) {
        return original.substring(offset) + original.substring(0, offset);
    }

    public static String duplicate(String original, int count) {
        if (count <= 0) {
            return "";
        } else {
            return original + duplicate(original, count - 1);
        }
    }

    public static int relativeX(int absoluteX) {
        return absoluteX - INIT_POS_X;
    }

    public static int relativeY(int absoluteY) {
        return absoluteY - INIT_POS_Y;
    }

    public static int absoluteX(int relativeX) {
        return relativeX + INIT_POS_X;
    }

    public static int absoluteY(int relativeY) {
        return relativeY + INIT_POS_Y;
    }

}