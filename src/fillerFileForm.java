import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import Body.ScheduleHoursBuilder;
import Footer.FooterDTO;
import Header.HeaderDTO;
import Utils.frameMethods;
import fileHandler.FileEditor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

public class fillerFileForm extends JFrame{
    final String[] columnNames = {"Data", "Horário de Entrada", "Entrada no Intervalo", "Saída do Intervalo","Horário de Saída", "Stand By"};
    HeaderDTO header = new HeaderDTO();
    private JPanel panel1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTable table1;
    private JSpinner spinner3;
    private JButton gravarButton;
    private JButton gerarButton;
    MaskFormatter hourMask1 = new MaskFormatter("##:##");
    MaskFormatter hourMask2 = new MaskFormatter("##:##");
    MaskFormatter hourMask3 = new MaskFormatter("##:##");
    MaskFormatter hourMask4 = new MaskFormatter("##:##");
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JFormattedTextField formattedTextField3;
    private JFormattedTextField formattedTextField4;
    private JSpinner spinner4;

    public fillerFileForm(JFrame fillFile, int frameWidth, int frameHeight) throws ParseException {
        fillFile.setSize(frameWidth,frameHeight);
        fillFile.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(fillFile,"Deseja realmente sair ?", "Fechar Janela?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        SpinnerModel yearModel = new SpinnerNumberModel(2021, 2020, 2200, 1);
        SpinnerModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel monthDayModel = new SpinnerNumberModel(25, 1, 31, 1);
        SpinnerModel howManyDays = new SpinnerNumberModel(28, 1, 31, 1);
        spinner4.setModel(yearModel);
        spinner3.setModel(monthModel);
        spinner2.setModel(monthDayModel);
        spinner1.setModel(howManyDays);

        TableModel model = new DefaultTableModel(columnNames, 0);

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        hourMask1.install(formattedTextField1);
        hourMask2.install(formattedTextField2);
        hourMask3.install(formattedTextField3);
        hourMask4.install(formattedTextField4);

        gerarButton.addActionListener(e -> {
            header.setFirstDay((Integer) spinner2.getValue());
            header.setNumOfDays((Integer) spinner1.getValue());
            header.setMonth((Integer) spinner3.getValue());
            header.setYear((Integer) spinner4.getValue());
            ScheduleHoursBuilder buildSchedule = new ScheduleHoursBuilder();
            table1.setModel(buildSchedule.buildTableModel(header,table1.getModel()));
        });

        gravarButton.addActionListener(e -> {
            if(table1.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Valores não gerados, impossível efetuar gravação","Erro",JOptionPane.ERROR_MESSAGE);
            }
            FooterDTO footer = new FooterDTO();
            footer.setNormalEH(formattedTextField1.getText());
            footer.setSpecialEH(formattedTextField2.getText());
            footer.setWarningHours(formattedTextField3.getText());
            footer.setNightlyHours(formattedTextField4.getText());
            FileEditor file = new FileEditor();
            String date = "01/"+(header.getMonth()+1)+"/"+header.getYear();
            file.openFile(footer,date,"D:\\Dropbox\\Teste - Entregar.xlsx",(DefaultTableModel) table1.getModel());
        });
    }

    public static void main(String[] Args) throws ParseException {
        JFrame login = new JFrame("Preenchedor Automático de Horas");
        login.setContentPane(new fillerFileForm(login,login.getWidth(),login.getHeight()).panel1);
        login.pack();
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.defineFrame(login,800,600);
    }
}
