package Body;

import Header.HeaderDTO;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static java.util.Calendar.DATE;

public class ScheduleHoursBuilder {
    public DefaultTableModel buildTableModel(HeaderDTO header, TableModel scheduleTableModel) {
        Date firstScheduleDate = null;
        try {
            firstScheduleDate = formatDate(header.getFirstDay(), header.getMonth(), header.getYear());
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return setScheduleTableModel(firstScheduleDate, header.getNumOfDays(), (DefaultTableModel) scheduleTableModel);
    }

    private Date formatDate(int firsDateRow, int month, int year) throws ParseException {
        String stringDate = firsDateRow + "/" + month + "/" + year;
        return new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
    }

    private DefaultTableModel setScheduleTableModel(Date firstScheduleDay, int numRows,
                                                    DefaultTableModel scheduleModel) {
        HoursGenerator hoursGenerator = new HoursGenerator();
        scheduleModel.setRowCount(0);
        for (int i = 0; i < numRows; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(firstScheduleDay);
            c.add(DATE, i);
            Date day = c.getTime();
            Map<String, String> entryMap = hoursGenerator.generateEntry();
            scheduleModel.addRow(new Object[]{new SimpleDateFormat("dd/MM/yyyy").format(day),
                    hoursGenerator.printEntry(c, 1, entryMap.get("1")),
                    hoursGenerator.printEntry(c, 2, entryMap.get("2")),
                    hoursGenerator.printEntry(c, 3, entryMap.get("3")),
                    hoursGenerator.printEntry(c, 4, entryMap.get("4")),
                    ""});
        }
        return scheduleModel;
    }
}
