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
        DefineHours defineHours = new DefineHours();
        scheduleModel.setRowCount(0);
        for(int i=0;i<numRows;i++){
            Calendar c = Calendar.getInstance();
            c.setTime(firstScheduleDay);
            c.add(Calendar.DATE, i);
            Date day = c.getTime();
            Map<String,String> entryMap = defineHours.generateEntry();
            scheduleModel.addRow(new Object[]{new SimpleDateFormat("dd/MM/yyyy").format(day),defineHours.printEntry(c,1,entryMap.get("1")),
                                                                                                    defineHours.printEntry(c,2,entryMap.get("2")),
                                                                                                    defineHours.printEntry(c,3,entryMap.get("3")),
                                                                                                    defineHours.printEntry(c,4,entryMap.get("4")),
                                                                                                    ""});
        }
        return scheduleModel;
    }
}
