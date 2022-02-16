package pt.ph;

import java.io.Serializable;
import java.util.*;

/**
 * Shop class.
 */
public class Shop implements Serializable {
    private static int shopCodeCounter;
    final private String shopCode;
    final private String name;
    final private Coordinates location;
    private Credentials credentials;
    private Queue<Order> ordersToDelivery;


    /**
     * Shop constructor.
     *
     * @param name        Shop name.
     * @param location    Shop location.
     * @param credentials shop credentials.
     */
    public Shop(String name, Coordinates location, Credentials credentials) {
        this.shopCode = "l" + shopCodeCounter;
        this.name = name;
        this.location = location;
        this.ordersToDelivery = new LinkedList<>();
        this.credentials = credentials;
        incrementCode();

    }

    public Shop(String code, String name, Coordinates location, Credentials credentials) {
        this.shopCode = code;
        this.name = name;
        this.location = location;
        this.credentials = credentials;
        this.ordersToDelivery = new LinkedList<>();
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getShopCode() {
        return shopCode;
    }

    public String getName() {
        return name;
    }

    public Coordinates getLocation() {
        return location;
    }

    public static void setShopCodeCounter(int biggest) {
        shopCodeCounter = biggest;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Increments shop code.
     */
    public void incrementCode() {
        shopCodeCounter++;
    }

    /**
     * Adds an order to Shop list of orders.
     *
     * @param order Order to add.
     */
    public void addOrderToShop(Order order) {
        order.setShopWait(System.currentTimeMillis());
        order.setStatus(-1);
        this.ordersToDelivery.add(order);
    }

    /**
     *  Number of users waiting for a product in a shop.
     * @return Number of users waiting.
     */
    public int waitingUsers() {
        return ordersToDelivery.size();
    }

    public void removeOrderFromShop(Order order) {
        this.ordersToDelivery.remove(order);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopCode='" + shopCode + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", credentials=" + credentials +
                ", ordersToDelivery=" + ordersToDelivery +
                '}';
    }
}
