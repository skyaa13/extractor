package com.company.extractor.utils;

public class StringUtils {

    public static String removeBrackets(String string) {
        return string.replaceAll("\\[|\\]", "");
    }

    public static String getAnnotationName(String name) {
        return name.substring(name.indexOf("annotation.") + 11, name.lastIndexOf("(path"));
    }
}
