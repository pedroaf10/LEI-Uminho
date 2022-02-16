package pt.ph;

import java.io.Serializable;

/**
 * Coordinates class.
 */
public class Coordinates implements Serializable {
    final private double longitude;
    final private double latitude;


    /**
     * Coordinates constructor.
     * @param longitude Longitude value from the coordinates.
     * @param latitude Latitude value from the coordinates.
     */
    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * Calculates the distance between two differents coordinates.
     * @param coordinates1 Coordinates from the first place.
     * @param coordinates2 Coordinates from the second place.
     * @return Distance between palce 1 and place 2.
     */
    public static double distance(Coordinates coordinates1, Coordinates coordinates2) {
        double latitude1  = coordinates1.getLatitude();
        double longitude1 = coordinates1.getLongitude();
        double latitude2  = coordinates2.getLatitude();
        double longitude2 = coordinates2.getLongitude();
        double theta = longitude1 - longitude2;
        double dist = Math.sin(degreesToRadians(latitude1)) * Math.sin(degreesToRadians(latitude2)) + Math.cos(degreesToRadians(latitude1)) * Math.cos(degreesToRadians(latitude2)) * Math.cos(degreesToRadians(theta));
        dist = Math.acos(dist);
        dist = radiansToDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /**
     * Transform degrees into radians.
     * @param deg Value in degrees.
     * @return Value in radians.
     */
    private static double degreesToRadians(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Transform radians into degrees.
     * @param rad Value in radians.
     * @return Value in degrees.
     */
    private static double radiansToDegrees(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "pt.ph.Coordinates{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0;
    }

}
