package gui;

import database.DataHandler;
import database.models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class Dashboard extends JFrame {

    private JPanel contentPanel;
    private JPanel menuPanel;
    private JTable essentialsTable;
    private JTable customersTable;
    private JButton addButton;
    private JButton removeButton;
    private JButton paymentButton;
    private JScrollPane essentialsScrollPane;
    private JScrollPane customersScrollPane;
    private JSplitPane tablesPane;
    private JTextField searchField;

    private static final int THRESHOLD_OF_ESSENTIALS = 14;

    public Dashboard () {
        addButton.addActionListener (e -> {
            CustomerEditor addCustomerDialog = new CustomerEditor ();
            addCustomerDialog.setSize (400, 600);
            addCustomerDialog.setVisible (true);
            addCustomerDialog.addWindowListener (new WindowAdapter () {
                @Override
                public void windowClosed (WindowEvent ent) {
                    setData ();
                }
            });
        });

        paymentButton.addActionListener (event -> {
            // Get the customer selected in table.
            int selectedRow = customersTable.getSelectedRow ();
            if (selectedRow == -1) return; // no selections.

            // Get ID.
            String id = (String) customersTable.getModel ().getValueAt (selectedRow, 0);
            Customer customer = null;
            try (DataHandler dh = new DataHandler ()) {
                customer = dh.getById (id);
            } catch (IOException | SQLException e) {
                e.printStackTrace ();
            }

            CustomerUpdater updateCustomerDialog = new CustomerUpdater (customer);
            updateCustomerDialog.setSize (600, 300);
            updateCustomerDialog.setVisible (true);
            updateCustomerDialog.addWindowListener (new WindowAdapter () {
                @Override
                public void windowClosed (WindowEvent ent) {
                    setData ();
                }
            });
        });
    }

    public static void main (String[] args) {
        JFrame frame = new JFrame ("Dashboard");
        Dashboard d = new Dashboard ();
        d.setData ();
        frame.setContentPane (d.contentPanel);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setVisible (true);
    }


    public void setData () {
        try (DataHandler dh = new DataHandler ()) {
            // Get list from database.
            List<Customer> customerList = dh.getList ();

            // Get customers table model.
            DefaultTableModel customersModel = (DefaultTableModel) customersTable.getModel ();
            // Clear table.
            customersModel.setRowCount (0);
            // Set column headers.
            customersModel.setColumnIdentifiers (new String [] {
                    "ID",
                    "First Name",
                    "Last Name",
                    "Gender",
                    "Birthdate",
                    "Joining Date",
                    "Membership End Date"
            });
            // Add data.
            customerList.stream ().forEach (e -> customersModel.addRow (e.toVector ()));

            // Get essentials table model.
            DefaultTableModel essentialsModel = (DefaultTableModel) essentialsTable.getModel ();
            // Clear table.
            essentialsModel.setRowCount (0);
            // Set column headers.
            essentialsModel.setColumnIdentifiers (new String [] {
                    "Name",
                    "Event"
            });
            // Add filtered data.
            customerList.stream ()
                    .forEach (e -> {
                        Vector v = e.toVector ();

                        long toBirthday = e.daysTillBirthday ();
                        long daysJoining = e.daysFromJoining ();
                        long daysMembershipEnd = e.daysTillEnd ();

                        if (0 <= toBirthday && toBirthday < THRESHOLD_OF_ESSENTIALS) {
                            Vector vEssentials = new Vector (3);
                            // Full name.
                            vEssentials.add (v.get (1) + " " + v.get (2));
                            // Event.
                            if (toBirthday == 0)
                                vEssentials.add ("Birthday!");
                            else
                                vEssentials.add (toBirthday + " days to birthday!");
                            // Add to table.
                            essentialsModel.addRow (vEssentials);
                        }

                        if (inDelta (daysJoining, THRESHOLD_OF_ESSENTIALS)) {
                            Vector vEssentials = new Vector (3);
                            // Full name.
                            vEssentials.add (v.get (1) + " " + v.get (2));
                            // Event.
                            vEssentials.add ("Days from joining: " + daysJoining);
                            // Add to table.
                            essentialsModel.addRow (vEssentials);
                        }

                        if (inDelta (daysMembershipEnd, THRESHOLD_OF_ESSENTIALS)) {
                            Vector vEssentials = new Vector (3);
                            // Full name.
                            vEssentials.add (v.get (1) + " " + v.get (2));
                            // Event.
                            vEssentials.add ("Membership end: " + daysMembershipEnd + " days");
                            // Add to table.
                            essentialsModel.addRow (vEssentials);
                        }

                    });
        } catch (SQLException e) { // TODO: Handle differently.
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    // -----

    // Utility function only.
    private static boolean inDelta (long ref, long delta) {
        return -delta < ref && ref < delta;
    }

    // -----

}
