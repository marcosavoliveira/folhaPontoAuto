package Body;

import Header.HeaderDTO;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleHoursBuilder {
    public DefaultTableModel buildTableModel(HeaderDTO header, TableModel scheduleTableModel){
        int firsDateRow = header.getFirstDay();
        int numRows = header.getNumOfDays();
        int month = header.getMonth();
        int year = header.getYear();
        Date firstScheduleDate = null;
        try {
            firstScheduleDate = formatDate(firsDateRow, month, year);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return setScheduleTableModel(firstScheduleDate,numRows,(DefaultTableModel) scheduleTableModel);
    }

    private Date formatDate(int firsDateRow,int month, int year) throws ParseException {
        String stringDate =firsDateRow+"/"+month+"/"+year;
        return new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
    }

    private DefaultTableModel setScheduleTableModel(Date firstScheduleDay, int numRows, DefaultTableModel scheduleModel){
        scheduleModel.setRowCount(0);
        for(int i=0;i<numRows;i++){
            Calendar c = Calendar.getInstance();
            c.setTime(firstScheduleDay);
            c.add(Calendar.DATE, i);
            Date day = c.getTime();
            Map<String,String> entryMap = generateEntry();
            scheduleModel.addRow(new Object[]{new SimpleDateFormat("dd/MM/yyyy").format(day),printEntry(c,1,entryMap.get("1")),
                                                                                                    printEntry(c,2,entryMap.get("2")),
                                                                                                    printEntry(c,3,entryMap.get("3")),
                                                                                                    printEntry(c,4,entryMap.get("4")),
                                                                                                    ""});
        }
        return scheduleModel;
    }

    private int randomizeMinutes(int max, int min){
        int random = new Random().nextInt(max+min)-min;
        if(random == 0){
            return 1;
        }
        return random;
    }
    private Map<String, String> generateEntry(){
        int max = 5;
        int min = 5;
        Map<String, String> entryMap = new TreeMap<>();
        int hour1 = 9 * 60;
        int firstCol = hour1 +randomizeMinutes(max,min);
        int hour2 = 12 * 60;
        if(firstCol > hour1){
            max = -1;
            min = 4;
        }else{
            min = 1;
        }
        int secondCol = hour2 +randomizeMinutes(max,min);
        int diff = secondCol-firstCol-180;
        int correctionLunch = 0;
        if(diff > 5){
            correctionLunch = diff-5;
            diff = 5;
        }
        if(diff < -5){
            correctionLunch = diff+5;
            diff = -5;
        }
        if(diff ==0){
            diff = 1;
        }
        int thirdCol = secondCol +60+correctionLunch;
        int hour4 = 18 * 60;
        int fourthCol = hour4 +diff;
        //VALIDATE HOURS BOUNDS

        entryMap.put("1",formatHours(firstCol));
        entryMap.put("2",formatHours(secondCol));
        entryMap.put("3",formatHours(thirdCol));
        entryMap.put("4",formatHours(fourthCol));
        return entryMap;
    }

    private String printEntry(Calendar c, int column,String entry){
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
