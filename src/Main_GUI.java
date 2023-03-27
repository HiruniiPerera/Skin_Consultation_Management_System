import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

//Responsible for the viewing of the GUI
public class Main_GUI extends JFrame implements ActionListener {
    // Declare variables for the menu bar and its components
    private JMenuBar menuBar;
    private JMenu menu1;
    private JMenuItem item1, item2, item3, item4;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    //Constructor for Main_GUI, sets up the menu bar and its items, and initializes the main panel and card layout.
    public Main_GUI(ArrayList<Doctor> list, ArrayList<Consultation> consultationlist) {
        super("Westminster Skin Consultation Management system");

        // Set up the menu bar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create the menu and its items
        menu1 = new JMenu("Menu");
        menuBar.add(menu1);
        item1 = new JMenuItem("Home");
        item1.setActionCommand("button1");
        item1.addActionListener(this);
        menu1.add(item1);
        item2 = new JMenuItem("List of Doctors");
        item2.setActionCommand("button2");
        item2.addActionListener(this);
        menu1.add(item2);
        item3 = new JMenuItem("Add consultation");
        item3.setActionCommand("button3");
        item3.addActionListener(this);
        menu1.add(item3);
        item4 = new JMenuItem("View consultations");
        item4.setActionCommand("button4");
        item4.addActionListener(this);
        menu1.add(item4);

        // Create the main panel and add it to the window
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        // Create the panels that will be displayed in the main panel
        // and add them to the CardLayout
        JPanel panel1 = new JPanel();
        Color col1 = new Color(167, 180, 227, 255);
        panel1.setBackground(col1);

        // Add components to panel1
        panel1.add(new JLabel("Welcome to Westminster Skin consultation management system!"), BorderLayout.CENTER);


        JPanel panel2 = new JPanel();
        // Add components to panel2
        Table_GUI table = new Table_GUI(list);

        panel2.add(table.getPane());
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));


        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        // Add components to panel3
        Form form = new Form(list, consultationlist);
        panel3.add(form.getFormPanel(), BorderLayout.CENTER);
        panel3.add(form.getButtonPanel(), BorderLayout.SOUTH);

        JPanel panel4 = new JPanel();
        try {

            panel4 = new JPanel();
            // Add components to panel 4
            View_consultations viewConsultations = new View_consultations();
            panel4.add(viewConsultations.getPane());
            panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        } catch (Exception e) {
            System.out.println("smn");
        }

        // Add the panels to the main panel
        mainPanel.add(panel1, "panel1");
        mainPanel.add(panel2, "panel2");
        mainPanel.add(panel3, "panel3");
        mainPanel.add(panel4, "panel4");

        // Set up the main window

        setLocationRelativeTo(null);

    }

    @Override
    //Handles the actions performed when a menu item is clicked.
    public void actionPerformed(ActionEvent e) {

        // Get the action command of the clicked menu item
        String command = e.getActionCommand();
        if (command.equals("button1")) {
            // Show the first panel in the mainPanel
            cardLayout.show(mainPanel, "panel1");
        } else if (command.equals("button2")) {
            // Show the second panel in the mainPanel
            cardLayout.show(mainPanel, "panel2");
        } else if (command.equals("button3")) {
            // Show the third panel in the mainPanel
            cardLayout.show(mainPanel, "panel3");
        } else if (command.equals("button4")) {
            // Show the fourth panel in the mainPanel
            cardLayout.show(mainPanel, "panel4");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
