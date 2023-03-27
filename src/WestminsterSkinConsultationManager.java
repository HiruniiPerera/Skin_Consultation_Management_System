import javax.swing.*;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager, Serializable {
    private final Scanner scan ;     // Scanner object for reading input from the console
    private ArrayList<Doctor> doctors;    // List of doctors available for consultations
    private List<Consultation> consultations;    // List of consultations that have been scheduled

//    Constructs a WestminsterSkinConsultationManager object.
    public WestminsterSkinConsultationManager() {

       // Initializes the scan field with a new Scanner object, and the doctors and consultations fields with new ArrayList objects.
        this.scan = new Scanner(System.in);
        this.doctors = new ArrayList<Doctor>();
        this.consultations = new ArrayList<Consultation>();
    }

    @Override
    // This method adds a doctor to the system
    public void addDoctor() {
        try {
            // Check if the centre already has 10 doctors
            if (this.doctors.size() >= 10) {
                System.out.println("The centre can only have 10 doctors at a time. Please remove a doctor and try again.");
                return;
            }

            // Prompt the user for the doctor's first name
            System.out.println("Enter the doctor's first name: ");
            String fname = this.scan.next();
            // Prompt the user for the doctor's surname
            System.out.println("Enter the doctor's surname: ");
            String surname = this.scan.next();
            LocalDate dob = date();
            // Prompt the user for the doctor's mobile number
            System.out.println("Enter doctor's mobile number: ");
            int num = Integer.parseInt(this.scan.next());
            // Prompt the user for the doctor's licence number
            System.out.println("Enter the doctor's licence number: ");
            int lic_num = Integer.parseInt(this.scan.next());
            // Prompt the user for the doctor's specialisation
            System.out.println("Enter the doctor's specialisation: ");
            String spec = scan.next();
            // Create a new Doctor object with the user-provided details and adds it to the list of doctors
            Doctor doc = new Doctor(fname, surname, dob, num, lic_num, spec);
            this.doctors.add(doc);
            System.out.println("Doctor " + fname + " " + surname + " with licence number " + lic_num + " has been added to the system");
        }catch (Exception ex){
            System.out.println("Unable to add doctor. Please try again with proper inputs");
        }
    }

    public LocalDate date(){
        // Prompt the user for the doctor's DOB

            System.out.println("Enter the doctor's year of birth: ");
            int year = this.scan.nextInt();
            System.out.println("Enter the doctor's month of birth: ");
            int month = this.scan.nextInt();
            System.out.println("Enter the doctor's date of birth: ");
            int date = this.scan.nextInt();
            LocalDate dob = LocalDate.of(year, month, date);
            return dob;

    }


    @Override
    //This method deletes a doctor from the list
    public void deleteDoctor() {

        // Prompt the user for the medical licence number of the doctor to be deleted
        System.out.println("Enter the medical licence number of the doctor to be deleted: ");
        int lic_num = Integer.parseInt(this.scan.next());
        // Search the list of doctors for a doctor with a matching licence number
        Doctor removedoc = null;
        for (Doctor doc : this.doctors) {
            if (doc.getLicenceNumber() == lic_num) {
                // Save the matching doctor in a variable
                removedoc = doc;
                break;
            }
        }
        // If a matching doctor was found, remove the doctor from the list and print a confirmation message
        if (removedoc == null) {
            System.out.println("There is no doctor with the licence number " + lic_num);
        } else {
            this.doctors.remove(removedoc);
            System.out.println("Doctor " + removedoc.getName() + " " + removedoc.getSurname() + " with licence number " + lic_num + " has been removed from the system.");
            System.out.println("There are "+doctors.size()+" doctors in the centre.");
        }
    }

    @Override
    //This method prints the list of doctors sorted alphabetically by the doctor's surname.
    public void printDoctors() {
        // Create a new list containing all the doctors in the doctors field
        List<Doctor> list = new ArrayList<>();
        list.addAll(this.doctors);

        // Sort the list by surname using the Collections.sort() method
        Collections.sort(list, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2) {
                return d1.getSurname().compareTo(d2.getSurname());
            }
        });
        // Print the table header
        System.out.printf("%-30s | %-30s | %-15s | %-20s | %-15s | %-15s", "Medical licence number", "Specialisation", "First name", "Surname", "Date of Birth", "Mobile number");
        System.out.println();
        System.out.printf("%-30s | %-30s | %-15s | %-20s | %-15s | %-15s", " ", " ", " ", " ", " ", " ");
        System.out.println();
        // Print a row for each doctor in the sorted list
        for (Doctor doctor : list) {
            System.out.printf("%-30s | %-30s | %-15s | %-20s | %-15s | %-15s", doctor.getLicenceNumber(), doctor.getSpecialisation(), doctor.getName(), doctor.getSurname(), doctor.getDateOfBirth(), doctor.getMobile());
            System.out.println();
        }
    }

    @Override
    // This method stores the doctors object to a file called "System_data.txt" using object serialization
    public void store() {
        try {
            // Create a FileOutputStream to write to the file "System_data.txt"
            FileOutputStream file = new FileOutputStream("System_data.txt");
            // Create an ObjectOutputStream to write objects to the file
            ObjectOutputStream object = new ObjectOutputStream(file);

            // Write the doctors object to the file
            object.writeObject(this.doctors);

            // Close the ObjectOutputStream and the FileOutputStream
            object.close();
            file.close();

            System.out.println("Data was stored successfully");
        } catch (FileNotFoundException f) {
            // If the file is not found, print an error message
            System.out.println("File not found.");
        } catch (IOException e) {
            // If there is any other input/output error, print an error message
            System.out.println("error");
        }
    }

    @Override
    // This method reads the doctors object from the file "System_data.txt" using object deserialization
    public void read() {
        try {
            // Create a FileInputStream to read from the file "System_data.txt"
            FileInputStream file = new FileInputStream("System_data.txt");
            // Create an ObjectInputStream to read objects from the file
            ObjectInputStream object = new ObjectInputStream(file);

            // Read the doctors object from the file
            this.doctors = (ArrayList<Doctor>) object.readObject();

            // Close the ObjectInputStream and the FileInputStream
            object.close();
            file.close();

            System.out.println("Data loaded successfully");
        } catch (FileNotFoundException f) {
            // If the file is not found, print an error message
            System.out.println("File not found.");
        } catch (IOException e) {
            // If there is any other input/output error, print an error message
            System.out.println("error1");
        } catch (ClassNotFoundException c) {
            // If the class of the object being read cannot be found, print an error message
            System.out.println("error2");
        }
    }

    @Override
    // This method creates and displays a graphical user interface (GUI) for the system
    public void GUI() {
        // Create a new Main_GUI window, passing in the doctors and consultations lists as arguments
        Main_GUI window = new Main_GUI(this.doctors, (ArrayList<Consultation>) this.consultations);
        // Make the window visible on the screen
        window.setVisible(true);
        // Maximize the window
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // This method allows the user to interact with the system through the command line
    public void console() {
        try {
            while (true) {
                System.out.println("Please enter your option from below:\n" +
                        "1: Add new doctor\n" +
                        "2: Delete doctor\n" +
                        "3: Print a list of doctors\n" +
                        "4: Save data to a file\n" +
                        "5: Read data from a file\n" +
                        "6: Open GUI\n" +
                        "7: Exit\n");
                System.out.println("Option: ");
                String option =this.scan.next();
                switch (option) {
                    case "1":
                        this.addDoctor();
                        break;
                    case "2":
                        this.deleteDoctor();
                        break;
                    case "3":
                        this.printDoctors();
                        break;
                    case "4":
                        this.store();
                        break;
                    case "5":
                        this.read();
                        break;
                    case "6":
                        this.GUI();
                        System.out.println("GUI loaded successfully");
                        break;
                    case "7":
                        return;
                    default:
                        //validates invalid options
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        }catch(Exception e){
            System.out.println("Error");
            //console();

        }
    }

    public static void main(String[] args) {
        //creates an object and then calls the console method
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.console();


    }
}

