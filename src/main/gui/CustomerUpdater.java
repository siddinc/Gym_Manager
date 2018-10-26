package gui;

import database.DataHandler;
import database.models.Customer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
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

        firstNameText.setText (customer.getFirstName ());
        lastNameText.setText (customer.getLastName ());
        dobText.setText (customer.getBirthDate ().toString ());
        joiningText.setText (customer.getJoiningDate ().toString ());
        membershipText.setText (customer.getMembershipEndDate ().toString ());
        genderBox.setSelectedItem (customer.getGender ().toString ());

        okButton.addActionListener (e -> onOK ());
        cancelButton.addActionListener (e -> onCancel ());

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
                dateOfBirth = null,
                joiningDate = null,
                membershipEndDate = null;
        try {
            dateOfBirth = LocalDate.parse (dobString);
            joiningDate = LocalDate.parse (joiningString);
            membershipEndDate = LocalDate.parse (membershipEndString);
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$ ();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$ () {
        contentPane = new JPanel ();
        contentPane.setLayout (new GridBagLayout ());
        final JPanel spacer1 = new JPanel ();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints ();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add (spacer1, gbc);
        customerPanel = new JPanel ();
        customerPanel.setLayout (new GridBagLayout ());
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 8.0;
        gbc.weighty = 16.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add (customerPanel, gbc);
        firstNameLabel = new JLabel ();
        firstNameLabel.setText ("First Name");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (firstNameLabel, gbc);
        final JPanel spacer2 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (spacer2, gbc);
        final JPanel spacer3 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer3, gbc);
        firstNameText = new JTextField ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (firstNameText, gbc);
        lastNameLabel = new JLabel ();
        lastNameLabel.setText ("Last Name");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (lastNameLabel, gbc);
        lastNameText = new JTextField ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (lastNameText, gbc);
        genderLabel = new JLabel ();
        genderLabel.setHorizontalAlignment (2);
        genderLabel.setText ("Gender");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (genderLabel, gbc);
        genderBox = new JComboBox ();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel ();
        defaultComboBoxModel1.addElement ("MALE");
        defaultComboBoxModel1.addElement ("FEMALE");
        defaultComboBoxModel1.addElement ("OTHER");
        genderBox.setModel (defaultComboBoxModel1);
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (genderBox, gbc);
        final JPanel spacer4 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer4, gbc);
        dobLabel = new JLabel ();
        dobLabel.setHorizontalAlignment (2);
        dobLabel.setText ("Date Of Birth");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (dobLabel, gbc);
        final JPanel spacer5 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer5, gbc);
        dobText = new JTextField ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (dobText, gbc);
        joiningLabel = new JLabel ();
        joiningLabel.setHorizontalAlignment (2);
        joiningLabel.setText ("Date Of Joining");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (joiningLabel, gbc);
        final JPanel spacer6 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer6, gbc);
        joiningText = new JTextField ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (joiningText, gbc);
        final JPanel spacer7 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer7, gbc);
        membershipLabel = new JLabel ();
        membershipLabel.setHorizontalAlignment (2);
        membershipLabel.setText ("Membership ends on");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        customerPanel.add (membershipLabel, gbc);
        membershipText = new JTextField ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.weightx = 4.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPanel.add (membershipText, gbc);
        final JPanel spacer8 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer8, gbc);
        final JPanel spacer9 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.weighty = 32.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        customerPanel.add (spacer9, gbc);
        packageScrollList = new JScrollPane ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 12.0;
        gbc.weighty = 16.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add (packageScrollList, gbc);
        packageScrollList.setBorder (BorderFactory.createTitledBorder (BorderFactory.createLoweredBevelBorder (), "Add a package:", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        packageList = new JList ();
        final DefaultListModel defaultListModel1 = new DefaultListModel ();
        defaultListModel1.addElement ("7 Days");
        defaultListModel1.addElement ("14 Days");
        defaultListModel1.addElement ("30 Days");
        defaultListModel1.addElement ("60 Days");
        defaultListModel1.addElement ("90 Days");
        defaultListModel1.addElement ("120 Days");
        defaultListModel1.addElement ("180 Days");
        defaultListModel1.addElement ("365 Days");
        packageList.setModel (defaultListModel1);
        packageScrollList.setViewportView (packageList);
        buttonsPanel = new JPanel ();
        buttonsPanel.setLayout (new GridBagLayout ());
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add (buttonsPanel, gbc);
        final JPanel spacer10 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 16.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (spacer10, gbc);
        okButton = new JButton ();
        okButton.setText ("Ok");
        gbc = new GridBagConstraints ();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (okButton, gbc);
        final JPanel spacer11 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (spacer11, gbc);
        cancelButton = new JButton ();
        cancelButton.setText ("Cancel");
        gbc = new GridBagConstraints ();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (cancelButton, gbc);
        final JPanel spacer12 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (spacer12, gbc);
        deleteButton = new JButton ();
        deleteButton.setText ("Delete");
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonsPanel.add (deleteButton, gbc);
        final JPanel spacer13 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add (spacer13, gbc);
        final JPanel spacer14 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add (spacer14, gbc);
        final JPanel spacer15 = new JPanel ();
        gbc = new GridBagConstraints ();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        contentPane.add (spacer15, gbc);
        firstNameLabel.setLabelFor (firstNameText);
        lastNameLabel.setLabelFor (lastNameText);
        genderLabel.setLabelFor (genderBox);
        dobLabel.setLabelFor (dobText);
        joiningLabel.setLabelFor (joiningText);
        membershipLabel.setLabelFor (membershipText);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$ () {
        return contentPane;
    }
}
