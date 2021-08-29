package fileHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import Footer.FooterDTO;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.table.DefaultTableModel;

public class FileEditor {

public void openFile(FooterDTO footer, String date, String path, DefaultTableModel scheduleValues){
        try {
            FileInputStream inputStream = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Cell monthReference = sheet.getRow(3).getCell(0);
            monthReference.setCellValue(date);

            for(int row =0; row < scheduleValues.getRowCount(); row++){
                for(int col=0;col < scheduleValues.getColumnCount(); col++){
                    sheet.getRow(row+6).getCell(col).setCellValue(scheduleValues.getValueAt(row,col).toString());
                }
            }

            Cell normalEH = sheet.getRow(43).getCell(1);
            normalEH.setCellValue(footer.getNormalEH());
            Cell specialEH = sheet.getRow(44).getCell(1);
            specialEH.setCellValue(footer.getSpecialEH());
            Cell warningHours = sheet.getRow(45).getCell(1);
            warningHours.setCellValue(footer.getWarningHours());
            Cell nightlyHours = sheet.getRow(46).getCell(1);
            nightlyHours.setCellValue(footer.getNightlyHours());

            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

}
