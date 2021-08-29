package Body;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class DefineHours {
    private int randomizeMinutes(int max, int min){
        int random = new Random().nextInt(max+min)-min;
        if(random == 0){
            return 1;
        }
        return random;
    }
    protected Map<String, String> generateEntry(){
        int max = 5;
        int min = 5;
        int hour1 = 9 * 60;
        int hour2 = 12 * 60;
        int hour4 = 18 * 60;

        int firstCol = hour1 +randomizeMinutes(max,min);

        if(firstCol < hour1){
            max = -1;
            min = 4;
        }else{
            min = 1;
        }

        int secondCol = hour2 +randomizeMinutes(max,min);

        int correctionOut = secondCol-firstCol-180;

        int correctionLunch = 0;
        if(correctionOut > 5){
            correctionLunch = correctionOut-5;
            correctionOut = -5;
        }
        if(correctionOut < -5){
            correctionLunch = correctionOut+5;
            correctionOut = 5;
        }
        if(correctionOut ==0){
            correctionOut = 1;
        }

        int thirdCol = secondCol +60+correctionLunch;
        int fourthCol = hour4 +correctionOut;

        int workDay = secondCol-firstCol+fourthCol-thirdCol;
        fourthCol = fourthCol+(8*60-workDay);

        Map<String, String> entryMap = new TreeMap<>();
        entryMap.put("1",formatHours(firstCol));
        entryMap.put("2",formatHours(secondCol));
        entryMap.put("3",formatHours(thirdCol));
        entryMap.put("4",formatHours(fourthCol));
        return entryMap;
    }

    protected String printEntry(Calendar c, int column, String entry){
        String weekend = verifyWeekend(c);
        if(!weekend.isEmpty()){
            if(column == 1){
                return weekend;
            }else{
                return "";
            }
        }
        return entry;
    }

    private String verifyWeekend(Calendar c){
        int intWeekDay = c.get(Calendar.DAY_OF_WEEK);
        String weekDay="";
        if(intWeekDay == Calendar.SATURDAY){
            weekDay = "SÁBADO";
        }
        if(intWeekDay == Calendar.SUNDAY){
            weekDay = "DOMINGO";
        }
        return weekDay;
    }

    private String formatHours(int hours){
        String hour = String.valueOf(hours/60);
        if(hour.length()==1){
            hour="0"+hour;
        }
        String minute = String.valueOf(hours%60);
        if(minute.length()==1){
            minute="0"+minute;
        }
        return hour+":"+minute;
    }
}
