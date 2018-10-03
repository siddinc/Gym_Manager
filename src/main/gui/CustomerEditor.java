package gui;

import database.models.Customer;

import database.DataHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

public class CustomerEditor extends JDialog {
    private JPanel contentPane;

    private JPanel biodataPanel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel dobLabel;
    private JLabel genderLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JComboBox genderBox;

    private JPanel packagePanel;
    private JLabel packageLabel;
    private JList packageList;

    private JPanel paymentPanel;
    private JLabel paymentLabel;
    private JTextField textField1;
    private JButton submitButton;

    // -----
    private final static Pattern daysPattern = Pattern.compile ("(\\d+)");

    public CustomerEditor () {
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (submitButton);

        submitButton.addActionListener (e -> {
            if (!isEmpty ()) onOK ();
            else JOptionPane.showMessageDialog (
                    this,
                    "Please fill in all the details,",
                    "Invalid form",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
                onCancel ();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction (
                e -> {
                    onCancel ();
                },
                KeyStroke.getKeyStroke (KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    // -----

    private void onOK () {
        String
                firstName           = firstNameField.getText (),
                lastName            = lastNameField.getText (),
                genderString        = (String) genderBox.getSelectedItem (),
                dateOfBirthString   = dobField.getText ();

        // * Data validation.
        // Gender.
        Customer.Gender gender = Customer.Gender.valueOf (genderString);
        // Date of Birth.
        LocalDate dateOfBirth = null;
        try {
            dateOfBirth = LocalDate.parse (dateOfBirthString);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog (
                    this,
                    "Enter valid Date Of Birth in yyyy-mm-dd format.",
                    "Date of Birth incorrect",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Customer creation.
        Customer c = new Customer ();
        c.setFirstName (firstName);
        c.setLastName (lastName);
        c.setGender (gender);
        c.setBirthDate (dateOfBirth);

        // Package.
        String pkgSelected = (String) packageList.getSelectedValue ();
        if (pkgSelected != null) {
            Matcher m = daysPattern.matcher (pkgSelected);
            if (m.find ()) {
                c.addDaysToMembership (Integer.parseInt (m.group (0)));
            }
        }

        // Save to Database.
        try (DataHandler dh = new DataHandler ()) {
            dh.addCustomer (c);
        } catch (SQLException e) {
            e.printStackTrace ();
            JOptionPane.showMessageDialog (
                    this,
                    "ERROR: CHECK CONSOLE",
                    "SQLException",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose ();
        } catch (IOException e) {
            e.printStackTrace ();
            JOptionPane.showMessageDialog (
                    this,
                    "ERROR: CHECK CONSOLE",
                    "IOException",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        dispose ();
    }

    private void onCancel () {
        // add your code here if necessary
        dispose ();
    }

    // -----

    private boolean isEmpty () {
        return
                firstNameField.getText ().isEmpty ()
                        &&
                        lastNameField.getText ().isEmpty ()
                        &&
                        dobField.getText ().isEmpty ();
    }

}
