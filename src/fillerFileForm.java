import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import Body.ScheduleHoursBuilder;
import Header.HeaderDTO;
import Utils.frameMethods;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Date;

public class fillerFileForm extends JFrame{
    final String[] columnNames = {"Data", "Horário de Entrada", "Entrada no Intervalo", "Saída do Intervalo","Horário de Saída", "Stand By"};
    private JPanel panel1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTable table1;
    private JSpinner spinner3;
    private JButton gravarButton;
    private JButton gerarButton;
    MaskFormatter hourMask = new MaskFormatter("##:##");
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
        SpinnerModel monthDayModel = new SpinnerNumberModel(20, 1, 31, 1);
        SpinnerModel howManyDays = new SpinnerNumberModel(20, 1, 31, 1);
        spinner4.setModel(yearModel);
        spinner3.setModel(monthModel);
        spinner2.setModel(monthDayModel);
        spinner1.setModel(howManyDays);

        TableModel model = new DefaultTableModel(columnNames, 0);

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        hourMask.install(formattedTextField1);
        hourMask.install(formattedTextField2);
        hourMask.install(formattedTextField3);
        hourMask.install(formattedTextField4);

        gerarButton.addActionListener(e -> {
            HeaderDTO header = new HeaderDTO();
            header.setFirstDay((Integer) spinner2.getValue());
            header.setNumOfDays((Integer) spinner1.getValue());
            header.setMonth((Integer) spinner3.getValue());
            header.setYear((Integer) spinner4.getValue());
            ScheduleHoursBuilder buildSchedule = new ScheduleHoursBuilder();
            table1.setModel(buildSchedule.buildTableModel(header,table1.getModel()));
        });

        gravarButton.addActionListener(e -> System.out.println("Até agora nada"));
    }

    public static void main(String[] Args) throws ParseException {
        JFrame login = new JFrame("Preenchedor Automático de Horas");
        login.setContentPane(new fillerFileForm(login,login.getWidth(),login.getHeight()).panel1);
        login.pack();
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.defineFrame(login,800,600);
    }
}
