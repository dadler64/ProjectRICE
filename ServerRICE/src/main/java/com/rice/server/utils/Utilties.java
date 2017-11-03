package com.rice.server.utils;

public class Utilties {
    public static String getSpaces(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            sb.append("             ");
            return sb.toString();
        }

        if (str.length() < 16 && str.length() > 0) {
            for (int i = 0; i < (16 - str.length()); i++) {
                sb.append(" ");
            }
        } else {
            for (int i = 0; i < 16; i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
