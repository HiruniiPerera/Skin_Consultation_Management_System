import java.io.Serializable;
import java.time.LocalDate;

//This class contains the constructor for the person object
public abstract class Person implements Serializable {
    // Instance variables for a Person object
    private String name;  // name of the person
    private String surname;  // surname of the person
    private LocalDate dateOfBirth;  // date of birth of the person
    private int mobile;  // mobile number of the person

    public Person(String name, String surname, LocalDate dateofbirth, int mobile) {
        // Takes in a name, surname, date of birth, and mobile number as arguments.
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateofbirth;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getMobile() {
        return mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
}
