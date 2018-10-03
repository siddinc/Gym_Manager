package gui;

import database.DataHandler;
import database.models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.MonthDay;
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
                    setEnabled (true);
                    setData ();
                }
            });
            this.setEnabled (false);
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
                    "ID",
                    "First Name",
                    "Last Name",
                    "Gender",
                    "Birthdate",
                    "Joining Date",
                    "Membership End Date"
            });
            // Add filtered data.
            customerList.stream ()
                    .filter (e ->
                            e.daysTillEnd () < THRESHOLD_OF_ESSENTIALS || e.daysTillEnd () > -THRESHOLD_OF_ESSENTIALS
                                    || MonthDay.from (e.getBirthDate ()).equals (MonthDay.from (LocalDate.now ()))
                                    || e.daysFromJoining () % 365 < THRESHOLD_OF_ESSENTIALS
                    )
                    .forEach (e -> {
//                        Vector
//                                v = e.toVector (),
//                                vEssentials = new Vector ();
//                        // Id.
//                        vEssentials.add (v.get (0));
//                        // Full name.
//                        vEssentials.add (v.get (1) + " " + v.get (2));
//                        // Event.
//                        vEssentials.add ();

                        essentialsModel.addRow (e.toVector ());
                    });
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

}
