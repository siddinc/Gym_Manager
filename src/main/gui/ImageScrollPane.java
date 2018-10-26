package gui;

import javax.swing.*;
import java.awt.*;

class ImageScrollPane extends JScrollPane {

    private Image img;

    public ImageScrollPane(String img) {
        setImage (new ImageIcon(img).getImage());
    }

    public void setImage (Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
