package fr.farmeurimmo.purpurhub;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getCurrentTimeAndDate() {
        return new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
