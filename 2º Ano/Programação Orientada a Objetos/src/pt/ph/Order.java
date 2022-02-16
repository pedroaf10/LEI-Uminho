package pt.ph;

import java.io.Serializable;
import java.util.List;

/**
 * Order class.
 */
public class Order implements Serializable {
    private static int orderCodeCounter;
    final private String orderCode;
    final private String userCode;
    final private String shopCode;
    private String transportCode;
    private long shopWait;
    final private double weight;
    final private List<Product> productsList;
    private int status;
    private long orderDate;
    private long deliveryDate;

    /**
     * Order constructor.
     *
     * @param orderCode    Order code.
     * @param userCode     User code.
     * @param shopCode     Shop code.
     * @param productsList List of products.
     */
    public Order(String orderCode, String userCode, String shopCode, List<Product> productsList) {
        this.orderCode = orderCode;
        this.userCode = userCode;
        this.shopCode = shopCode;
        this.weight = totalOrderWeight(productsList);
        this.productsList = productsList;
        this.status = -1;
        this.orderDate = System.currentTimeMillis();
        this.deliveryDate = 0;
        this.shopWait = 0;
        this.transportCode = "none";
    }

    public Order(String userCode, String shopCode, List<Product> productsList) {
        this.orderCode = "e" + orderCodeCounter;
        this.userCode = userCode;
        this.shopCode = shopCode;
        this.weight = totalOrderWeight(productsList);
        this.productsList = productsList;
        this.status = -1;
        this.orderDate = System.currentTimeMillis();
        this.deliveryDate = 0;
        this.shopWait = 0;
        this.transportCode = "none";
        incrementCode();
    }

    public Order(String userCode, String shopCode, List<Product> productsList, long orderDate) {
        this.orderCode = "e" + orderCodeCounter;
        this.userCode = userCode;
        this.shopCode = shopCode;
        this.weight = totalOrderWeight(productsList);
        this.productsList = productsList;
        this.status = -1;
        this.orderDate = orderDate;
        this.deliveryDate = 0;
        this.shopWait = 0;
        this.transportCode = "none";
        incrementCode();
    }


    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public long getShopWait() {
        return shopWait;
    }

    public void setShopWait(long shopWait) {
        this.shopWait = shopWait;
    }

    public String getShopCode() {
        return shopCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public int getStatus() {
        return status;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public double getWeight() {
        return weight;
    }

    public String getTransportCode() {
        return transportCode;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public static void setOrderCodeCounter(int orderCodeCounter) {
        Order.orderCodeCounter = orderCodeCounter;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    /**
     * Increments order's code.
     */
    public void incrementCode() {
        orderCodeCounter++;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderCode='" + orderCode + '\'' +
                ", userCode='" + userCode + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", transportCode='" + transportCode + '\'' +
                ", shopWait=" + shopWait +
                ", weight=" + weight +
                ", productsList=" + productsList +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    /**
     * Verifies if it contains medical products.
     *
     * @return True if there are, false if not.
     */
    public boolean containsMedicalProducts() {
        boolean found = false;
        for (Product p : productsList) {
            if (p.isMedical()) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Total weight of an order.
     *
     * @param products Products to wisa
     * @return Total weight.
     */
    public static double totalOrderWeight(List<Product> products) {
        double weight = 0;
        for (Product p : products) {
            weight += p.getWeight();
        }
        return weight;
    }

}
