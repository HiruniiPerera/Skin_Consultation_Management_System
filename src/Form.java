import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.crypto.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
//This class gets inputs from the user via a GUI form and creates consultations
public class Form extends JFrame implements Serializable {

    private final JLabel imageLabel;
    private JTextField nameField;
    private JTextField surnameField;

    private JTextField mobileField;
    private JTextField idField;
    private JTextArea notesField;

    private JSpinner spinner;
    private JLabel costlabel;
    private JDatePickerImpl datePicker1;
    private JDatePickerImpl datePicker2;
    private final JComboBox<String> Doctorlist;
    private final JComboBox<String> timelist;
    private HashMap<LocalTime, Integer> timemap;
    private JPanel formPanel;
    private JPanel buttonPanel;

    private JButton addButton;
    private JButton cancelButton;
    private BufferedImage bi;
    private byte[] imageBytes;
    private ArrayList<Consultation> consultations;


    public Form(ArrayList<Doctor> list, ArrayList<Consultation> consultations) {
        Color col1 = new Color(167, 180, 227, 255);

        // loads the data at the start of the program
        load();

        formPanel = new JPanel();
        formPanel.setBackground(col1);
        //sets a gridbaglayout for the form to have a good alignment
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(11,11,11,11);

        // Add the name field
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(new JLabel("Name:"), constraints);
        nameField = new JTextField(20);
        nameField.setMinimumSize(nameField.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(nameField, constraints);

        // Add the surname field
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(new JLabel("Surname:"), constraints);
        surnameField = new JTextField(20);
        surnameField.setMinimumSize(surnameField.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(surnameField, constraints);

        //Add the datepicker for dob
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(new JLabel("Date of Birth:"), constraints);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel datemodel1 = new UtilDateModel();
        JDatePanelImpl datePanel1 = new JDatePanelImpl(datemodel1, p);
        datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        datePicker1.setMinimumSize(datePicker1.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(datePicker1, constraints);

        //Add the mobile field
        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(new JLabel("Mobile:"), constraints);
        mobileField = new JTextField(20);
        mobileField.setMinimumSize(mobileField.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(mobileField, constraints);

        // Add the ID field
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(new JLabel("ID:"), constraints);
        idField = new JTextField(20);
        idField.setMinimumSize(idField.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(idField, constraints);

        // Add the notes field
        constraints.gridx = 0;
        constraints.gridy = 5;
        formPanel.add(new JLabel("Notes:"), constraints);
        notesField = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(notesField);
        notesField.setMinimumSize(notesField.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(scrollPane, constraints);

        // Adds the label to choose and view an image
        constraints.gridx = 0;
        constraints.gridy = 6;
        formPanel.add(new JLabel("Attach image:"), constraints);
        constraints.gridx = 1;
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("default.jpg"));


        formPanel.add(imageLabel, constraints);
        // Choosing the file option
        JButton chooseButton = new JButton("Choose Image");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                int result = fileChooser.showOpenDialog(Form.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        bi = ImageIO.read(selectedFile);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(bi, "jpg", baos);
                            baos.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        imageBytes = baos.toByteArray();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    final int w = 100;
                    final int h = 100;
                    Image resizedImg = bi.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(resizedImg));
                }

            }
        });
        constraints.gridx = 2;
        formPanel.add(chooseButton, constraints);

        // Adds a dropdown menu to select the doctor
        constraints.gridx = 0;
        constraints.gridy = 7;
        formPanel.add(new JLabel("Doctor:"), constraints);
        String[] docs_available = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            docs_available[i] = "Dr. " + list.get(i).getName() + " " + list.get(i).getSurname();
        }
        Doctorlist = new JComboBox<String>(docs_available);
        Doctorlist.setMinimumSize(Doctorlist.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(Doctorlist, constraints);

        //Adds a datepicker to select the date of consultation
        constraints.gridx = 0;
        constraints.gridy = 8;
        formPanel.add(new JLabel("Date:"), constraints);
        constraints.gridx = 1;
        UtilDateModel datemodel2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(datemodel2, p);
        datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        datePicker2.setMinimumSize(datePicker2.getPreferredSize());
        formPanel.add(datePicker2, constraints);

        //Adds a drop down menu to select the time of consultation
        constraints.gridx = 0;
        constraints.gridy = 9;
        formPanel.add(new JLabel("Time:"), constraints);
        constraints.gridx = 1;
        String[] time = {"9 am", "10 am", "11 am", "2 pm", "3 pm", "4 pm"};
        timemap = new HashMap<>(Map.of(LocalTime.of(9, 0), 0, LocalTime.of(10, 0), 1, LocalTime.of(11, 0), 2, LocalTime.of(2, 0), 3, LocalTime.of(3, 0), 4, LocalTime.of(4, 0), 5));
        timelist = new JComboBox<>(time);
        timelist.setMinimumSize(timelist.getPreferredSize());
        constraints.gridx = 1;
        formPanel.add(timelist, constraints);

        //Adds a number incrementer to select no. of hrs
        constraints.gridx = 0;
        constraints.gridy = 10;
        formPanel.add(new JLabel("Hours:"), constraints);
        SpinnerModel model = new SpinnerNumberModel(1, 1, 3, 1);
        spinner = new JSpinner(model);
        constraints.gridx = 1;
        formPanel.add(spinner, constraints);
        costlabel = new JLabel();
        spinner.addChangeListener(new ChangeListener() {
                                      @Override
                                      public void stateChanged(ChangeEvent hourevent) {
                                          // responsible for the displaying of cost
                                          int hours = (int) spinner.getValue();
                                          int cost = 0;
                                          int patient_id = Integer.parseInt(idField.getText());
                                          if (consultations.size() == 0) {
                                              cost = 15 * hours;
                                              costlabel.setText("\u00A3 " + cost);
                                          } else {
                                              for (Consultation consultation : consultations) {
                                                  if (patient_id == consultation.getPatient().getPatient_id()) {
                                                      cost = 25 * hours;
                                                      costlabel.setText("\u00A3 " + cost);
                                                  } else {
                                                      cost = 15 * hours;
                                                      costlabel.setText("\u00A3 " + cost);
                                                  }
                                              }
                                          }

                                      }
                                  }
        );

        constraints.gridx = 0;
        constraints.gridy = 11;
        formPanel.add(new JLabel("Cost:"), constraints);
        constraints.gridx = 1;
        constraints.gridy = 11;
        formPanel.add(costlabel, constraints);


        buttonPanel = new JPanel();
        buttonPanel.setBackground(col1);
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // calls the add consultation method when the add button is pressed
                addConsultation(list, timemap, timelist, imageBytes);
            }
        });
        buttonPanel.add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((new ActionListener() {
            @Override
            //clears all the fields
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                surnameField.setText("");
                mobileField.setText("");
                idField.setText("");
                notesField.setText("");
                costlabel.setText("");
            }
        }));

        buttonPanel.add(cancelButton);

    }

    //loading consultation data
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

    // storing consultation data
    public void store() {
        try {
            FileOutputStream file = new FileOutputStream("Consultations.txt");
            ObjectOutputStream object = new ObjectOutputStream(file);

            object.writeObject(consultations);

            object.close();
            file.close();

            System.out.println("Data was stored successfully");
        } catch (FileNotFoundException f) {
            System.out.println("File not found.");
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public JPanel getFormPanel() {
        return formPanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }


    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    //adds consultation to the list if all required fields are filled
    private void addConsultation(ArrayList<Doctor> list, Map<LocalTime, Integer> timemap, JComboBox<String> timelist, byte[] image_array) {
        try {
            // Get the consultation data from the form fields
            String name = nameField.getText();
            String surname = surnameField.getText();

            Date dobinput = (Date) datePicker1.getModel().getValue();
            LocalDate dob = dobinput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Integer mobile = Integer.parseInt(mobileField.getText(), 10);
            Integer patient_id = Integer.parseInt(idField.getText());

            //Encrypts the notes and image data
            String Encryptednotes = AES.encryption(notesField.getText(), "skin123");
            byte[] Encryotedimage = AES.encryption(image_array, "skin123");
            int hours = (int) spinner.getValue();
            int finalcost = 0; // Initialize finalcost outside of the loop
            //calculate the cost of a session
            if (consultations.size() == 0) {
                finalcost = 15 * hours;
            } else {
                if (consultations.size() != 0) {
                    for (Consultation consultation : consultations) {
                        if (patient_id == consultation.getPatient().getPatient_id()) {
                            finalcost = 25 * hours; // Update finalcost
                            costlabel.setText("\u00A3 "+finalcost);
                            break;
                        } else {
                            finalcost = 15 * hours; // Update finalcost
                            costlabel.setText("\u00A3 "+finalcost);
                            break;
                        }
                    }
                }
            }

            Date app_dateinput = (Date) this.datePicker2.getModel().getValue();
            LocalDate app_date = app_dateinput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int timeIndex = timelist.getSelectedIndex();
            LocalTime app_time = LocalTime.of(0, 0);

            for (Map.Entry<LocalTime, Integer> entry : timemap.entrySet()) {
                if (entry.getValue().equals(timeIndex)) {
                    app_time = entry.getKey();
                }
            }


            int docIndex = Doctorlist.getSelectedIndex();
            Doctor doc = list.get(docIndex);
            boolean doctorAvailable = true;

            //checks if the paticular doctor is free atthe selected date and time
            for (Consultation c : consultations) {
                if (c.getDoctor().equals(doc) && c.getDate().equals(app_date) && c.getTime().equals(app_time) && c.getDoctor().equals(doc)) {
                    JOptionPane.showMessageDialog(this, "The doctor is not available at the selected time.", "Error", JOptionPane.ERROR_MESSAGE);
                    doctorAvailable = false;
                    break;
                }
            }

            //checks for empty fields
            if (name == null || surname == null || dob == null || mobile == null || patient_id == null || app_date == null || app_time == null || Encryptednotes == null) {
                JOptionPane.showMessageDialog(this, "Please fill the required fields", "Incomplete fields", JOptionPane.ERROR_MESSAGE);
            } else {
                if (doctorAvailable) {
                    //load data
                    load();
                    //add consultation to list
                    Consultation consultation = new Consultation(doc, new Patient(name, surname, dob, mobile, patient_id), app_date, app_time, finalcost, Encryptednotes, hours, Encryotedimage);
                    consultations.add(consultation);
                    //stores data
                    store();
                    JOptionPane.showMessageDialog(this, "Consultation has been added", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //validation
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please fill the required fields", "Incomplete fields", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(this, "Please fill the fields with valid inputs", "Invalid inputs", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JTextField getSurnameField() {
        return surnameField;
    }

    public void setSurnameField(JTextField surnameField) {
        this.surnameField = surnameField;
    }

    public JTextField getMobileField() {
        return mobileField;
    }

    public void setMobileField(JTextField mobileField) {
        this.mobileField = mobileField;
    }

    public JTextField getIdField() {
        return idField;
    }

    public void setIdField(JTextField idField) {
        this.idField = idField;
    }

    public JTextArea getNotesField() {
        return notesField;
    }

    public void setNotesField(JTextArea notesField) {
        this.notesField = notesField;
    }

}
