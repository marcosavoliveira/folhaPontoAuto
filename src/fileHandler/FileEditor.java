package fileHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Footer.FooterDTO;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FileEditor {

    public void manipulateFile(FooterDTO footer, String date, String path, DefaultTableModel scheduleValues) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(inputStream);
            inputDataIntoFile(workbook, date, scheduleValues, footer);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            JOptionPane.showMessageDialog(null, "Arquivo Gravado com Sucesso", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Falha ao tentar escrever no arquivo destino",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (EncryptedDocumentException ex) {
            JOptionPane.showMessageDialog(null, "Já em uso, " +
                    "certifique - se de que esteja fechado ou caminho correto", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void inputDataIntoFile(Workbook workbook, String date, DefaultTableModel scheduleValues, FooterDTO footer) {
        Sheet sheet = workbook.getSheetAt(0);
        Cell monthReference = sheet.getRow(3).getCell(0);
        try {
            monthReference.setCellValue(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar a data de referência",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

        for (int row = 0; row < scheduleValues.getRowCount(); row++) {
            for (int col = 0; col < scheduleValues.getColumnCount(); col++) {
                sheet.getRow(row + 6).getCell(col).setCellValue(scheduleValues.getValueAt(row, col).toString());
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
    }
}
