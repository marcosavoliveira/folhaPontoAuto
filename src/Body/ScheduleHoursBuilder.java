package Body;

import Header.HeaderDTO;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
            scheduleModel.addRow(new Object[]{new SimpleDateFormat("dd/MM/yyyy").format(day),generateEntry(c,1),
                                                                                                    generateEntry(c,2),
                                                                                                    generateEntry(c,3),
                                                                                                    generateEntry(c,4),
                                                                                                    ""});
        }
        return scheduleModel;
    }
    private String generateEntry(Calendar c, int column){
        String entry = verifyWeekend(c);
        if(!entry.isEmpty()){
            if(column == 1){
                return entry;
            }else{
                return "";
            }
        }else{
            entry = "00:01";
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
}
