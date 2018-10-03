package gui;

import database.DataHandler;
import database.models.Customer;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerUpdater extends JDialog {
    private JPanel contentPane;
    private JList packageList;
    private JPanel customerPanel;
    private JScrollPane packageScrollList;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JTextField lastNameText;
    private JLabel genderLabel;
    private JComboBox genderBox;
    private JLabel dobLabel;
    private JTextField dobText;
    private JLabel joiningLabel;
    private JTextField joiningText;
    private JLabel membershipLabel;
    private JTextField membershipText;
    private JTextField firstNameText;
    private JPanel buttonsPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JButton deleteButton;

    // -----
    private final static Pattern daysPattern = Pattern.compile ("(\\d+)");

    private Customer customer;

    public CustomerUpdater (Customer customer) {
        this.customer = customer;

        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (okButton);

        firstNameText   .setText (customer.getFirstName ());
        lastNameText    .setText (customer.getLastName ());
        dobText         .setText (customer.getBirthDate ().toString ());
        joiningText     .setText (customer.getJoiningDate ().toString ());
        membershipText  .setText (customer.getMembershipEndDate ().toString ());
        genderBox       .setSelectedItem (customer.getGender ().toString ());

        okButton.addActionListener      (e -> onOK ());
        cancelButton.addActionListener  (e -> onCancel ());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
                onCancel ();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction (
                e -> onCancel (),
                KeyStroke.getKeyStroke (KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    // -----

    private void onOK () {
        String
                firstName = firstNameText.getText (),
                lastName = lastNameText.getText (),
                genderString = (String) genderBox.getSelectedItem (),
                dobString = dobText.getText (),
                joiningString = joiningText.getText (),
                membershipEndString = membershipText.getText ();

        // * Data validation.
        // Gender.
        Customer.Gender gender = Customer.Gender.valueOf (genderString);
        // Date of Birth.
        LocalDate
                dateOfBirth         = null,
                joiningDate         = null,
                membershipEndDate   = null;
        try {
            dateOfBirth         = LocalDate.parse (dobString);
            joiningDate         = LocalDate.parse (joiningString);
            membershipEndDate   = LocalDate.parse (membershipEndString);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog (
                    this,
                    "Enter valid dates in yyyy-mm-dd format.",
                    "Date incorrect format",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Customer creation.
        customer.setFirstName (firstName);
        customer.setLastName (lastName);
        customer.setGender (gender);
        customer.setBirthDate (dateOfBirth);
        customer.setJoiningDate (joiningDate);
        customer.setMembershipEndDate (membershipEndDate);

        // Package.
        String pkgSelected = (String) packageList.getSelectedValue ();
        if (pkgSelected != null) {
            Matcher m = daysPattern.matcher (pkgSelected);
            if (m.find ()) {
                customer.addDaysToMembership (Integer.parseInt (m.group (0)));
            }
        }

        // Save to Database.
        try (DataHandler dh = new DataHandler ()) {
            dh.updateCustomer (customer);
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
                firstNameText.getText ().isEmpty ()
                && lastNameText.getText ().isEmpty ()
                && dobText.getText ().isEmpty ()
                && joiningText.getText ().isEmpty ()
                && membershipText.getText ().isEmpty ()
                ;
    }

}
