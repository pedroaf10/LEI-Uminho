package pt.ph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Transport class.
 */
public abstract class Transport implements Serializable {
    private double radius;
    final double price;
    private Credentials credentials;
    private long nextAvailableTime;
    private long acceptTime;
    private boolean acceptMedProducts;
    private int[] ratings;
    private List<Order> ordersMade;
    private double averageRating;
    private Coordinates location;
    private double speed;

    public Transport(double radius, double price, long nextAvailableTime, Coordinates location, double speed, Credentials credentials) {
        int[] array = {0, 0, 0, 0, 0};
        this.radius = radius;
        this.price = price;
        this.nextAvailableTime = nextAvailableTime;
        this.location = location;
        this.speed = speed;
        this.ratings = array;
        this.averageRating = 0;
        this.acceptMedProducts = true;
        this.acceptTime = -1;
        this.ordersMade = new ArrayList<>();
        this.credentials = credentials;

    }

    public Credentials getCredentials() {
        return credentials;
    }

    public long getNextAvailableTime() {
        return nextAvailableTime;
    }

    public double getPrice() {
        return price;
    }

    public double getRadius() {
        return radius;
    }

    public Coordinates getLocation() {
        return location;
    }

    public double getSpeed() {
        return speed;
    }

    public abstract String getName();

    public double getAverageRating() {
        return averageRating;
    }

    public int[] getRatings() {
        return ratings;
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public void setNextAvailableTime(long nextAvailableTime) {
        this.nextAvailableTime = nextAvailableTime;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Refreshes transport rating.
     *
     * @param rating Rating from 1 to 5.
     */
    public void addRating(int rating) {
        int totalRatings = 0;
        int numberRatings = 0;
        this.ratings[rating - 1]++;
        for (int i = 0; i < 5; i++) {
            numberRatings += ratings[i];
            totalRatings += (i + 1) * ratings[i];
        }
        this.averageRating = (double) totalRatings / numberRatings;

    }

    /**
     * Adds an order to orders made.
     * @param order Order.
     */
    public void addOrder(Order order) {
        this.ordersMade.add(order);
    }

    /**
     * Says if it accepts medical products or not.
     * @return True if it does, False if not.
     */
    public boolean acceptMedTransport() {
        return this.acceptMedProducts;
    }

    /**
     * Changes if it accepts or not medical products.
     * @param bool  True if it does, False if not.
     */
    public void setAcceptMedProducts(boolean bool) {
        acceptMedProducts = bool;
    }


    public abstract boolean equals(Object o);

    /**
     * Increments transport code.
     */
    public abstract void incrementCode();

    /**
     * Get transport code.
     * @return code.
     */
    public abstract String getCode();

    /**
     * Updates transport.
     */
    public abstract void update();

    /**
     * Verify is a transport is available.
     *
     * @return If it is available or not.
     */
    public abstract boolean isAvailable();

}
