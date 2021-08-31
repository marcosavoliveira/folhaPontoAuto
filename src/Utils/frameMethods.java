package Utils;

import javax.swing.*;
import java.awt.*;

public class frameMethods {
    public void defineFrame(JFrame frame, int frameWidth, int frameHeight) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
