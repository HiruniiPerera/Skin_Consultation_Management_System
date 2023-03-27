import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

//This class contains the constructor for the consultation object.
public class Consultation implements Serializable {

    private Doctor doctor; // The doctor who will conduct the consultation
    private Patient patient;// The patient who booked the consultation
    private LocalDate date;// The date of the consultation
    private LocalTime time;// The time of the consultation
    private int hours;// The duration of the consultation in hours
    private int cost;// The cost of the consultation
    private String note;// A note about the consultation
    private byte[] image_array;// An image associated with the consultation

    // Takes in information about the doctor, patient, date and time of the consultation, cost,
    // a note about the consultation, the duration of the consultation in hours, and an image associated with the consultation.
    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime time, int cost, String note, int hours, byte[] image_array) {
        // Initialize instance variables with values of the parameters passed to the constructor.
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.note = note;
        this.hours = hours;
        this.image_array = image_array;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;

    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage_array() {
        return image_array;
    }

    public void setImage_array(byte[] image_array) {
        this.image_array = image_array;
    }


    @Override
    public String toString() {
        return "Consultation{" +
                "doctor=" + doctor +
                ", patient=" + patient +
                ", date=" + date +
                ", time=" + time +
                ", cost=" + cost +
                ", note='" + note + '\'' +
                '}';
    }
}
