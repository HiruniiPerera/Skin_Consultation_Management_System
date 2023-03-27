import java.io.Serializable;
import java.time.LocalDate;

//This class contains the constructor for the patient object.
public class Patient extends Person implements Serializable {

    private int patient_id;// ID of the patient

    //Takes in the patient's name, surname, date of birth, mobile number, and ID as arguments.
    public Patient(String name, String surname, LocalDate dateofbirth, int mobile, int patient_id) {
        // Initialize instance variables with values of the parameters passed to the constructor.

        // Calls the superclass (Person) constructor to initialize the name, surname, date of birth, and mobile number
        super(name, surname, dateofbirth, mobile);
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
