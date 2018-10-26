package gui;

import javax.swing.*;
import java.awt.*;

public class Util {

    public static ImageIcon resizeIcon (String img, int w, int h) {
        return new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
    }

}
