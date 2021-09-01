package Body;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.DAY_OF_WEEK;

public class HoursGenerator {
    static final int FIRST_HOUR = 540;
    static final int LUNCH_HOUR = 720;
    static final int EXIT_HOUR = 1080;
    static final int HOUR_IN_MINUTES = 60;
    static final int MINUTES_OF_TOLERANCE = 5;
    static final int MINUTES_OF_WORKDAY = 480;
    static final int MINUTES_OF_HALF_WORKDAY = 180;


    private int randomizeMinutes(int controlRandom) {
        int random = new Random().nextInt(HoursGenerator.MINUTES_OF_TOLERANCE);
        if (controlRandom > 0) {
            random = random * -1;
        } else {
            random = defineIfIsPositive(random);
        }

        if (random == 0) {
            return 1;
        }

        return random;
    }

    private int defineIfIsPositive(int minutesGenerated) {
        if (new Random().nextInt(2) == 0) {
            minutesGenerated = minutesGenerated * -1;
        }
        return minutesGenerated;
    }

    public Map<String, String> generateEntry() {

        int controlRandom = randomizeMinutes(0);
        int firstCol = FIRST_HOUR + controlRandom;

        int secondCol = LUNCH_HOUR + randomizeMinutes(controlRandom);

        int correctionOut = secondCol - firstCol - MINUTES_OF_HALF_WORKDAY;
        int correctionLunch = 0;
        if (correctionOut > MINUTES_OF_TOLERANCE) {
            correctionLunch = correctionOut - MINUTES_OF_TOLERANCE;
            correctionOut = -MINUTES_OF_TOLERANCE;
        }
        if (correctionOut < -MINUTES_OF_TOLERANCE) {
            correctionLunch = correctionOut + MINUTES_OF_TOLERANCE;
            correctionOut = MINUTES_OF_TOLERANCE;
        }
        if (correctionOut == 0) {
            correctionOut = 1;
        }

        int thirdCol = secondCol + HOUR_IN_MINUTES + correctionLunch;
        int fourthCol = EXIT_HOUR + correctionOut;

        int workDay = secondCol - firstCol + fourthCol - thirdCol;
        fourthCol = fourthCol + (MINUTES_OF_WORKDAY - workDay);

        Map<String, String> entryMap = new TreeMap<>();
        entryMap.put("1", formatHours(firstCol));
        entryMap.put("2", formatHours(secondCol));
        entryMap.put("3", formatHours(thirdCol));
        entryMap.put("4", formatHours(fourthCol));
        return entryMap;
    }

    public String printEntry(Calendar c, int column, String entry) {
        String weekend = verifyWeekend(c);
        if (!weekend.isEmpty()) {
            if (column == 1) {
                return weekend;
            } else {
                return "";
            }
        }
        return entry;
    }

    private String verifyWeekend(Calendar c) {
        int intWeekDay = c.get(DAY_OF_WEEK);
        String weekDay = "";
        if (intWeekDay == SATURDAY) {
            weekDay = "SÁBADO";
        }
        if (intWeekDay == SUNDAY) {
            weekDay = "DOMINGO";
        }
        return weekDay;
    }

    private String formatHours(int hours) {
        String hour = String.valueOf(hours / 60);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        String minute = String.valueOf(hours % 60);
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }
}
