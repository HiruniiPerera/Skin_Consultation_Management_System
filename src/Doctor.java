import java.io.Serializable;
import java.time.LocalDate;

//This class contains the constructor for the doctor object.
public class Doctor extends Person  {
    private int licenceNumber; // licence number of the doctor
    private String specialisation; // specialisation of the doctor

    //Takes in a name, surname, date of birth, mobile number,licence number, and specialisation as arguments.
    public Doctor(String name, String surname, LocalDate dateofbirth, int mobile, int licenceNumber, String specialisation) {

        // Initialize instance variables with values of the parameters passed to the constructor.

        // Calls the superclass's constructor to initialize the name, surname, date of birth, and mobile number.
        super(name, surname, dateofbirth, mobile);
        this.licenceNumber = licenceNumber;
        this.specialisation = specialisation;
    }

    public int getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(int licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

}
