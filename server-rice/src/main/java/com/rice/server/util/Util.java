package com.rice.server.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int getSeconds() {
        DateFormat dateFormat = new SimpleDateFormat("ss");
        return Integer.parseInt(dateFormat.format(new Date()));
    }

    public static String getThreadInfo() {
        return Thread.currentThread().getName() + ":" + Thread.currentThread().getId();
    }

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
