package pt.ph;

import java.io.Serializable;
import java.util.Objects;

/**
 * Volunteer Class.
 */
public class Volunteer extends Transport implements Serializable {
    private static int volunteerCodeCounter;
    final private String volunteerCode;
    final private String volunteerName;


    /**
     * Volunteer constructor.
     *
     * @param volunteerName Volunteer name.
     * @param radius        Volunteer radius of action.
     * @param location      Volunteer location.
     * @param speed         Volunteer speed.
     * @param credentials   Volunteer credentials.
     */
    public Volunteer(String volunteerName, double radius, Coordinates location, double speed, Credentials credentials) {
        super(radius, 0, 0, location, speed, credentials);
        this.volunteerCode = "v" + volunteerCodeCounter;
        this.volunteerName = volunteerName;
        incrementCode();
    }

    public Volunteer(String code, String volunteerName, double radius, Coordinates location, double speed, Credentials credentials) {
        super(radius, 0, 0, location, speed, credentials);
        this.volunteerCode = code;
        this.volunteerName = volunteerName;
    }

    public String getVolunteerCode() {
        return volunteerCode;
    }

    @Override
    public String getName() {
        return volunteerName;
    }

    @Override
    public String getCode() {
        return this.getVolunteerCode();
    }

    public static void setVolunteerCodeCounter(int biggest) {
        volunteerCodeCounter = biggest;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(volunteerCode, volunteer.volunteerCode) &&
                Objects.equals(volunteerName, volunteer.volunteerName);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isAvailable() {
        return this.getNextAvailableTime() < System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerCode='" + volunteerCode + '\'' +
                ", volunteerName='" + volunteerName + '\'' +
                ", price=" + price +
                '}';
    }

    /**
     * Increments volunteer's code.
     */
    public void incrementCode() {
        volunteerCodeCounter++;
    }
}