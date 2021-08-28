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
            scheduleModel.addRow(new Object[]{new SimpleDateFormat("dd/MM/yyyy").format(day),verifyWeekend(c,1),
                                                                                                    verifyWeekend(c,2),
                                                                                                    verifyWeekend(c,3),
                                                                                                    verifyWeekend(c,4),
                                                                                                    ""});
        }
        return scheduleModel;
    }
    private String verifyWeekend(Calendar c,int collunm){
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        String gerarEntrada="";
        if(weekDay == c.SATURDAY){
            gerarEntrada = "SÁBADO";
        }
        if(weekDay == c.SUNDAY){
            gerarEntrada = "DOMINGO";
        }
        if(!gerarEntrada.isEmpty()){
            if(collunm == 1){
                return gerarEntrada;
            }else{
                return "";
            }
        }else{
            gerarEntrada = "00:01";
        }
        return gerarEntrada;
    }
}
