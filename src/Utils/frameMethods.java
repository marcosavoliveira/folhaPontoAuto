package Utils;

import javax.swing.*;
import java.awt.*;

public class frameMethods {
    static final int halfMeasure = 2;
    public void setterParamsFrame(JFrame frame, int frameWidth, int frameHeight) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / halfMeasure,
                          (Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / halfMeasure);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
