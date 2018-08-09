package com.company.extractor.utils;

public class StringUtils {

    private static final int COUNT_TO_NAME_POSITION = 11;

    public static String removeBrackets(String string) {
        return string.replaceAll("\\[|\\]", "");
    }

    public static String getAnnotationName(String fullPathToAnnotation) {
        return fullPathToAnnotation.substring(fullPathToAnnotation.indexOf("annotation.") + COUNT_TO_NAME_POSITION, fullPathToAnnotation.lastIndexOf("(path"));
    }
}
