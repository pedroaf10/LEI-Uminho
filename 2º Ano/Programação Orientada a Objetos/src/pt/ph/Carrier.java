package pt.ph;

import java.util.Objects;

/**
 * Carrier class
 */
public class Carrier extends Transport {
    private static int carrierCodeCounter;
    final private String companyCode;
    final private String companyName;
    final private double nif;
    double kilometers;
    private int unavailableCarriers;
    private int totalCarriers;

    /**
     * Carrier constructor.
     *
     * @param companyName Company name.
     * @param location    Company coordinates.
     * @param nif         Company NIF.
     * @param radius      Company radius of action.
     * @param price       Company price.
     * @param isAvailable Time in millis when it is going to be available.
     * @param speed       Carrier speed.
     * @param carriers    number of cars from this carrier.
     * @param credentials Carrier credentials.
     */
    public Carrier(String companyName, double nif, double radius, double price, long isAvailable, Coordinates location, double speed, int carriers, Credentials credentials) {
        super(radius, price, isAvailable, location, speed, credentials);
        this.companyCode = "t" + carrierCodeCounter;
        this.companyName = companyName;
        this.nif = nif;
        this.kilometers = 0;
        this.unavailableCarriers = 0;
        this.totalCarriers = carriers;
        incrementCode();
    }

    public Carrier(String code, String companyName, double nif, double radius, double price, long isAvailable, Coordinates location, double speed, int carriers, Credentials credentials) {
        super(radius, price, isAvailable, location, speed, credentials);
        this.companyCode = code;
        this.companyName = companyName;
        this.nif = nif;
        this.kilometers = 0;
        this.unavailableCarriers = 0;
        this.totalCarriers = carriers;
    }

    public Carrier(String code, String companyName, double nif, double radius, double price, long isAvailable, Coordinates location, double speed, int carriers, Credentials credentials, double kms) {
        super(radius, price, isAvailable, location, speed, credentials);
        this.companyCode = code;
        this.companyName = companyName;
        this.nif = nif;
        this.kilometers = kms;
        this.unavailableCarriers = 0;
        this.totalCarriers = carriers;
    }

    @Override
    public String getName() {
        return companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public double getPrice() {
        return price;
    }

    public double getKilometers() {
        return kilometers;
    }

    public static void setCarrierCodeCounter(int biggest) {
        carrierCodeCounter = biggest;
    }

    public void setTotalCarriers(int totalCarriers) {
        this.totalCarriers = totalCarriers;
    }

    /**
     * Increments code number.
     */
    public void incrementCode() {
        carrierCodeCounter++;
    }

    /**
     * Adds kilometers to carrier.
     *
     * @param kms Kms to add.
     */
    public void addKilometers(double kms) {
        this.kilometers += kms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrier carrier = (Carrier) o;
        return Double.compare(carrier.nif, nif) == 0 &&
                Objects.equals(companyCode, carrier.companyCode) &&
                Objects.equals(companyName, carrier.companyName);
    }

    /**
     * Gets Code.
     *
     * @return Code.
     */
    @Override
    public String getCode() {
        return this.getCompanyCode();
    }

    /**
     * Updates carrier.
     */
    @Override
    public void update() {
        this.unavailableCarriers = 0;
    }

    /**
     * Verifies if there is any available car.
     *
     * @return Boolean.
     */
    @Override
    public boolean isAvailable() {
        return (totalCarriers - unavailableCarriers > 0);
    }

    /**
     * Add one unavailable carrier.
     */
    public void addUnavailable() {
        this.unavailableCarriers++;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", nif=" + nif +
                ", kilometers=" + kilometers +
                ", unavailableCarriers=" + unavailableCarriers +
                ", totalCarriers=" + totalCarriers +
                '}';
    }

    /**
     * Compares a carrier in terms of total kilometers. Decreasing order.
     *
     * @param carrier Carrier to compare to this.
     * @return Compare return.
     */
    public int compareKms(Carrier carrier) {
        return Double.compare(this.getKilometers(), carrier.getKilometers());
    }

}
