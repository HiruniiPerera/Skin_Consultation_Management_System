import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

// responsible for viewing consultations
public class View_consultations extends JFrame {

    private JLabel lbl;
    private JTable table;
    private JScrollPane scrollPane;
    private Container pane;
    private BufferedImage image;

    private ArrayList<Consultation> consultations;

    public View_consultations() {

        //loads the data
        load();

        pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        Color col1 = new Color(167, 180, 227, 255);
        pane.setBackground(col1);

        Font f = new Font("Arial", Font.BOLD, 20);

        lbl = new JLabel("List of Consultations");
        lbl.setFont(f);
        lbl.setBorder(new EmptyBorder(20, 0, 0, 0));
        pane.add(lbl);

        String[][] data = new String[consultations.size()][6];

        for (int i = 0; i < consultations.size(); i++) {

            String[] details = {String.valueOf(consultations.get(i).getPatient().getPatient_id()), consultations.get(i).getPatient().getName(), consultations.get(i).getPatient().getSurname(), "Dr. " + consultations.get(i).getDoctor().getName() +" "+ consultations.get(i).getDoctor().getSurname(), String.valueOf(consultations.get(i).getDate()), String.valueOf(consultations.get(i).getTime() + " - " + consultations.get(i).getTime().plusHours(consultations.get(i).getHours()))};

            data[i] = details;
        }

        String[] columnNames = {"Patient ID", "Patient Name", "Patient Surname", "Doctor", "Date", "Time"};

        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);
        // table.setFont(f);
        Color col2 = new Color(181, 145, 190, 255);
        table.setBackground(col2);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);


        table.setFocusable(false);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {

                // when a row is double-clicked the user is prompted for a passsword
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                //int column = table.columnAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    try {
                        JPanel panel = new JPanel();
                        JLabel label = new JLabel("Enter your password:");
                        JPasswordField pass = new JPasswordField(10);
                        panel.add(label);
                        panel.add(pass);
                        // String secret_key = "skin123";
                        String[] options = new String[]{"OK", "Cancel"};
                        int option = JOptionPane.showOptionDialog(null, panel, "The title",
                                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options[1]);
                        //if password is correct the details of the consultation will be shown in a new window
                        JFrame newFrame = new JFrame();
                        newFrame.setTitle("Consultation details");
                        newFrame.setSize(400, 300);
                        Color col2 = new Color(144, 145, 190, 255);
                        newFrame.setBackground(col2);
                        newFrame.setLayout(new GridBagLayout());

                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(8, 8, 8, 8);

                        constraints.gridx = 0;
                        constraints.gridy = 0;
                        newFrame.add(new JLabel("Patient ID:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel(String.valueOf(consultations.get(row).getPatient().getPatient_id())), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 1;
                        newFrame.add(new JLabel("Patient Name:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel(consultations.get(row).getPatient().getName()), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 2;
                        newFrame.add(new JLabel("Patient Surname:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel(consultations.get(row).getPatient().getSurname()), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 3;
                        newFrame.add(new JLabel("Doctor:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel("Dr. " + consultations.get(row).getDoctor().getName() + " " + consultations.get(row).getDoctor().getSurname()), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 4;
                        newFrame.add(new JLabel("Date:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel(String.valueOf(consultations.get(row).getDate())), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 5;
                        newFrame.add(new JLabel("Time"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel(consultations.get(row).getTime() + " - " + consultations.get(row).getTime().plusHours(consultations.get(row).getHours())), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 6;
                        newFrame.add(new JLabel("Cost:"), constraints);
                        constraints.gridx = 1;
                        newFrame.add(new JLabel("\u00A3 " + consultations.get(row).getCost()), constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 7;
                        newFrame.add(new JLabel("Notes:"), constraints);
                        constraints.gridx = 1;
                        String note = consultations.get(row).getNote();

                        String decryotednote = AES.decryption(note, String.valueOf(pass.getPassword()));
                        JLabel notelbl = new JLabel(decryotednote);

                        newFrame.add(notelbl, constraints);

                        constraints.gridx = 0;
                        constraints.gridy = 8;
                        newFrame.add(new JLabel("Images:"), constraints);


                        byte[] encryptedBytes = consultations.get(row).getImage_array();
                        byte[] decryptedBytes = AES.decryption(encryptedBytes, String.valueOf(pass.getPassword()));

                        InputStream in = new ByteArrayInputStream(decryptedBytes);
                        image = ImageIO.read(in);


                        final int w = 100;
                        final int h = 100;
                        Image resizedImg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                        //ImageIcon imageIcon = new ImageIcon(resizedImg);
                        JLabel imageLabel = new JLabel();
                        imageLabel.setIcon(new ImageIcon(resizedImg));
                        constraints.gridx = 1;
                        newFrame.add(imageLabel, constraints);


                        newFrame.setVisible(true);


                    } catch (NullPointerException err) {
                        System.out.println("err");
                    } catch (IOException e) {
                        System.out.println("err");
                    }
                }


            }
//            }
        });


        JScrollPane sp = new JScrollPane(table);
        pane.add(sp, BorderLayout.AFTER_LINE_ENDS);

    }
    public void load() {
        try {
            FileInputStream file = new FileInputStream("Consultations.txt");
            ObjectInputStream object = new ObjectInputStream(file);

            consultations = (ArrayList<Consultation>) object.readObject();

            object.close();
            file.close();

            System.out.println("Data loaded successfully");
        } catch (FileNotFoundException f) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("error1");
        } catch (ClassNotFoundException c) {
            System.out.println("error2");
        }
    }



    public Container getPane() {
        return pane;
    }


}
