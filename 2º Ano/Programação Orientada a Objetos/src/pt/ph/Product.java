package pt.ph;

import java.io.Serializable;

/**
 * Product class.
 */
public class Product implements Serializable {
    private static int productCodeCounter;
    final private String productCode;
    final private String description;
    final private double weight;
    final private double price;
    final private boolean medical;


    /**
     * Product constructor.
     *
     * @param description Product description.
     * @param weight      Product quantity.
     * @param price       Product price.
     * @param medical     Medical product or not.
     */
    public Product(String description, double weight, double price, boolean medical) {
        this.productCode = "p" + productCodeCounter;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.medical = medical;
        incrementCode();
    }

    public Product(String code, String description, double weight, double price, boolean medical) {
        this.productCode = code;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.medical = medical;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getProductCode() {
        return productCode;
    }

    public static void setProductCodeCounter(int biggest) {
        productCodeCounter = biggest;
    }

    /**
     * Verifies if it is a medical product.
     *
     * @return Boolean medical
     */
    public boolean isMedical() {
        return medical;
    }

    /**
     * Increment product code.
     */
    public void incrementCode() {
        productCodeCounter++;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + weight +
                ", price=" + price +
                '}';
    }
}
