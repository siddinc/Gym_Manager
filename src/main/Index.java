import gui.DBoard;

import javax.swing.*;

class Index {

    public static void main (String [] args) {
        // Main frame.
        JFrame frame = new JFrame ("");
        DBoard d = new DBoard ();
        frame.setContentPane (d.$$$getRootComponent$$$ ());
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setVisible (true);
    }

}