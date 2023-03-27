
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Table_GUI extends JFrame {
    private JLabel lbl;
    private JTable table;
    private final Container pane;
//displays the table of doctors

    public Table_GUI(ArrayList<Doctor> list) {

        pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        Color col1 = new Color(167, 180, 227, 255);
        pane.setBackground(col1);

        Font f = new Font("Arial", Font.BOLD, 20);

        lbl = new JLabel("List of doctors available");
        lbl.setFont(f);
        lbl.setBorder(new EmptyBorder(20, 0, 0, 0));
        pane.add(lbl);

        String[][] data = new String[list.size()][6];

        for (int i = 0; i < list.size(); i++) {

            String[] details = {Integer.toString(list.get(i).getLicenceNumber()), list.get(i).getSpecialisation(), list.get(i).getName(), list.get(i).getSurname(), list.get(i).getDateOfBirth().toString(), Integer.toString(list.get(i).getMobile())};

            data[i] = details;


        }

        String[] columnNames = {"Medical licence number", "Specialisation", "First name", "Surname", "Date of Birth", "Mobile number"};
        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);

        Color col2 = new Color(181, 145, 190, 255);
        table.setBackground(col2);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);

        JScrollPane sp = new JScrollPane(table);
        pane.add(sp, BorderLayout.AFTER_LINE_ENDS);
        table.setAutoCreateRowSorter(true);


    }


    // returns the pane with table
    public Container getPane() {
        return pane;
    }


}
