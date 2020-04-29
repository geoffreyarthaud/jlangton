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
        final long fromPos = AbstractRectLangtonMap.encode(INIT_POS_X, INIT_POS_Y);
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String curPath = LangtonPath.CYCLE_PATH.substring(i) + LangtonPath.CYCLE_PATH.substring(0, i);
            CartesianLangtonMap langtonMap = new LangtonPathToMap(ListLangtonMapImpl::new).applyPath(curPath, fromPos);
            if (relativeX(langtonMap.getMinX()) >= 0 && relativeY(langtonMap.getMinY()) >= 0) {
                System.out.println(MessageFormat.format("i = {0} - Frame : ({1}, {2}) ({3}, {4})",
                i, relativeX(langtonMap.getMinX()),
                   relativeY(langtonMap.getMinY()), relativeX(langtonMap.getMaxX()), relativeY(langtonMap.getMaxY())));   
            }
        }

        // Get backward good config
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String curPath = LangtonPath.CYCLE_PATH.substring(i) + LangtonPath.CYCLE_PATH.substring(0, i);
            CartesianLangtonMap langtonMap = new LangtonPathToMap(ListLangtonMapImpl::new).applyBackPath(curPath, fromPos);
            if (relativeX(langtonMap.getMaxX()) <= 0 && relativeY(langtonMap.getMaxY()) <= 0) {
                System.out.println(MessageFormat.format("i = {0} - Frame : ({1}, {2}) ({3}, {4})",
                i, relativeX(langtonMap.getMinX()),
                   relativeY(langtonMap.getMinY()), relativeX(langtonMap.getMaxX()), relativeY(langtonMap.getMaxY())));   
            }
        }

        // Get backward good config
        for (int i = 0; i < LangtonPath.CYCLE_PATH.length(); i++) {
            String curPath = LangtonPath.CYCLE_PATH.substring(i) + LangtonPath.CYCLE_PATH.substring(0, i);
            try {
                new LangtonPathToMap(ListLangtonMapImpl::new).symetricPath(curPath, fromPos);
                System.out.println("i = {0} - Symetric Path OK");
            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        String prefixPath = LangtonPath.CYCLE_PATH.substring(58) + LangtonPath.CYCLE_PATH.substring(0, 58);
        String suffixPath = LangtonPath.CYCLE_PATH;
        String testPath = prefixPath + suffixPath;

        try {
            CartesianLangtonMap langtonMap = new LangtonPathToMap(ListLangtonMapImpl::new).applyBackPath(testPath, fromPos);
            System.out.println("Correct config : " + testPath);
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect config !");
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