package gui;

import database.models.Customer;

import database.DataHandler;

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
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField dobTextField;
    private JComboBox genderBox;

    private JPanel packagePanel;
    private JLabel packageLabel;
    private JList packageList;

    private JPanel paymentPanel;
    private JLabel paymentLabel;
    private JTextField textField1;
    private JButton submitButton;

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
                    JOptionPane.ERROR_MESSAGE
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

    private final static Pattern daysPattern = Pattern.compile ("(\\d+)");

    private void onOK () {
        String firstName = firstNameTextField.getText ();
        String lastName = lastNameTextField.getText ();
        String genderString = (String) genderBox.getSelectedItem ();
        String dateOfBirthString = dobTextField.getText ();

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
        try {
            DataHandler dh = new DataHandler ();
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
        } finally {

        }

        JOptionPane.showMessageDialog (this, c.toString ());
        dispose ();
    }

    private void onCancel () {
        // add your code here if necessary
        dispose ();
    }

    // -----

    private boolean isEmpty () {
        return
                firstNameTextField.getText ().isEmpty ()
                &&
                lastNameTextField.getText ().isEmpty ()
                &&
                dobTextField.getText ().isEmpty ();
    }

    // For testing only. TODO: Remove
    public static void main (String[] args) {
        CustomerEditor dialog = new CustomerEditor ();
        dialog.setSize (600, 400);
        dialog.setVisible (true);
        System.exit (0);
    }
}
