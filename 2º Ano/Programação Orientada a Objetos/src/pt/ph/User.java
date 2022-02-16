package pt.ph;

import java.io.Serializable;
import java.util.Objects;


/**
 * User class.
 */
public class User implements Serializable {

    public static int userCodeCounter;
    final private String userCode;
    final private String name;
    final private Coordinates location;
    private Credentials credentials;
    private int numberOfOrders;


    /**
     * User constructor.
     *
     * @param name        User name.
     * @param location    User coordinates.
     * @param credentials User credentials.
     */
    public User(String name, Coordinates location, Credentials credentials) {
        this.userCode = "u" + userCodeCounter;
        this.name = name;
        this.location = location;
        this.credentials = credentials;
        this.numberOfOrders = 0;
        incrementCode();
    }

    public User(String code, String name, Coordinates location, Credentials credentials) {
        this.userCode = code;
        this.name = name;
        this.location = location;
        this.credentials = credentials;
        this.numberOfOrders = 0;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public Coordinates getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getUserCode() {
        return userCode;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public static void setUserCodeCounter(int userCodeCounter) {
        User.userCodeCounter = userCodeCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userCode, user.userCode) &&
                Objects.equals(name, user.name) &&
                Objects.equals(location, user.location);
    }

    /**
     * Increments user's orders.
     */
    public void incrementUsersOrders() {
        this.numberOfOrders++;
    }

    /**
     * Increments user's code.
     */
    public void incrementCode() {
        userCodeCounter++;
    }

    @Override
    public String toString() {
        return "User{" +
                "userCode='" + userCode + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", userCredentials=" + credentials +
                ", numberOfOrders=" + numberOfOrders +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Compares a user in terms of total orders. Decreasing order.
     *
     * @param user User to compare to this.
     * @return Compare returns.
     */
    public int compare(User user) {
        return Integer.compare(this.getNumberOfOrders(), user.getNumberOfOrders());
    }
}