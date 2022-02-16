package pt.ph;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;

/**
 * Class AppState
 */
public class AppState implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<User> usersList = new ArrayList<>();
    private List<Shop> shopsList = new ArrayList<>();
    private List<Transport> transportsList = new ArrayList<>();
    private List<Order> ordersList = new ArrayList<>();
    private List<Product> allProducts = new ArrayList<>();

    /**
     * AppState Constructor.
     */
    public AppState() {
    }

    /**
     * Add a shop in AppState.
     *
     * @param shop Shop to add.
     */
    public void addShop(Shop shop) {
        shopsList.add(shop);
    }

    /**
     * Add a user to AppState.
     *
     * @param user User to add.
     */
    public void addUser(User user) {
        usersList.add(user);
    }

    /**
     * Add a transport to AppState.
     *
     * @param transport Transport to add.
     */
    public void addTransport(Transport transport) {
        transportsList.add(transport);
    }

    /**
     * Add an order to AppState.
     *
     * @param order Order to add.
     */
    public void addOrder(Order order) {
        ordersList.add(order);
    }

    /**
     * Add a product to AppState.
     *
     * @param product Product to add.
     */
    public void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Prints an AppState.
     */
    public void printAppState() {
        System.out.println(shopsList);
        System.out.println(transportsList);
        System.out.println(usersList);
        System.out.println(ordersList);
        System.out.println(allProducts);
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public List<Shop> getShopsList() {
        return shopsList;
    }

    public List<Transport> getTransportsList() {
        return transportsList;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Write an object to file.
     *
     * @param fileName file name.
     * @throws IOException
     */
    public void writeObject(String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
        oos.writeObject(this);
    }

    /**
     * Read an object in a file.
     *
     * @param fileName File name.
     * @return AppState read.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AppState readObject(String fileName) throws ClassNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        return (AppState) ois.readObject();
    }

    /**
     * Get a User given its user code.
     *
     * @param userCode User code.
     * @return The user if exists, null if not.
     */
    public User getUser(String userCode) {
        boolean found = false;
        User user = null;
        for (User u : usersList) {
            if (found) {
                break;
            }
            if (u.getUserCode().equals(userCode)) {
                found = true;
                user = u;
            }
        }
        return user;
    }

    /**
     * Get a Product given its product name.
     *
     * @param productName Product description.
     * @return the product if it exists, null if not.
     */
    public Product getProduct(String productName) {
        boolean found = false;
        Product product = null;
        for (Product p : allProducts) {
            if (found) {
                break;
            }
            if (p.getDescription().equals(productName)) {
                found = true;
                product = p;
            }
        }
        return product;
    }

    /**
     * Get a Shop given its shop code.
     *
     * @param shopCode Shop code.
     * @return The shop if exists, null if not.
     */
    public Shop getShop(String shopCode) {
        boolean found = false;
        Shop shop = null;
        for (Shop s : shopsList) {
            if (found) {
                break;
            }
            if (s.getShopCode().equals(shopCode)) {
                found = true;
                shop = s;
            }
        }
        return shop;
    }

    /**
     * Get a Transport given its shop code.
     *
     * @param transportCode Transport Code.
     * @return The transport if exists, null if not.
     */
    public Transport getTransport(String transportCode) {
        boolean found = false;
        Transport transport = null;
        for (Transport t : transportsList) {
            if (found) {
                break;
            }
            if (t.getCode().equals(transportCode)) {
                found = true;
                transport = t;
            }
        }
        return transport;
    }

    /**
     * Get a Order given its order code.
     *
     * @param orderCode Order Code.
     * @return The order if exists, null if not.
     */
    public Order getOrder(String orderCode) {
        boolean found = false;
        Order order = null;
        for (Order o : ordersList) {
            if (found) {
                break;
            }
            if (o.getOrderCode().equals(orderCode)) {
                found = true;
                order = o;
            }
        }
        return order;
    }

    /**
     * Inserts a new order in Appstate.
     *
     * @param order    Order to add.
     * @param appState Appstate.
     */
    public void insertNewOrder(Order order, AppState appState) {
        int userIndex = appState.getUsersList().indexOf(getUser(order.getUserCode()));
        int shopIndex = appState.getShopsList().indexOf(getShop(order.getShopCode()));
        appState.getUsersList().get(userIndex).incrementUsersOrders();
        appState.getShopsList().get(shopIndex).addOrderToShop(order);
        appState.addOrder(order);
    }

    /**
     * Gets the top 10 Users in terms of total orders.
     *
     * @return List of the top 10.
     */
    public List<User> top10Users() {
        int i;
        List<User> sortedList = this.usersList.stream().sorted((o1, o2) -> o2.compare(o1)).collect(Collectors.toList());
        List<User> res = new ArrayList<>();
        for (i = 0; i < 10; i++) {
            res.add(sortedList.get(i));
        }
        return res;
    }

    /**
     * Gets the top 10 Carriers in terms of kilometers.
     *
     * @return List of the top 10.
     */
    public List<Carrier> top10Carriers() {
        int i;
        List<Carrier> sortedList = new ArrayList<>();
        for (Transport transport : this.transportsList) {
            if (transport instanceof Carrier) {
                sortedList.add((Carrier) transport);
            }
        }
        sortedList.sort((o1, o2) -> o2.compareKms(o1));
        List<Carrier> res = new ArrayList<>();
        if (sortedList.size() >= 10) {
            for (i = 0; i < 10; i++) {
                res.add(sortedList.get(i));
            }
        } else for (Transport t : sortedList) {
            res.add((Carrier) t);
        }
        return res;
    }

    /**
     * Get list of available transports for the order.
     *
     * @param order Order.
     * @return List of available transports.
     */
    public List<Transport> availableTransports(Order order) {
        User user = getUser(order.getUserCode());
        Shop shop = getShop(order.getShopCode());
        Coordinates userLocation = user.getLocation();
        Coordinates shopLocation = shop.getLocation();
        boolean medical = order.containsMedicalProducts();
        List<Transport> availableTransports = new ArrayList<>();
        for (Transport transport : this.transportsList) {
            if (medical) {
                if (transport.acceptMedTransport() && transport.isAvailable() && transport.getRadius() >= Coordinates.distance(userLocation, transport.getLocation()) && transport.getRadius() >= Coordinates.distance(shopLocation, transport.getLocation())) {
                    availableTransports.add(transport);
                }
            } else {
                if (transport.isAvailable() && transport.getRadius() >= Coordinates.distance(userLocation, transport.getLocation()) && transport.getRadius() >= Coordinates.distance(shopLocation, transport.getLocation())) {
                    availableTransports.add(transport);
                }

            }
        }
        if (availableTransports.isEmpty()) return null;
        else {
            return availableTransports;
        }
    }

    /**
     * List of available volunteers.
     *
     * @param transports All available transports.
     * @return List of volunteers.
     */
    public List<Transport> availableVolunteers(List<Transport> transports) {
        if (transports == null || transports.isEmpty()) return null;
        //if (transports == null) return null;
        List<Transport> res = new ArrayList<>();
        for (Transport transport : transports) {
            if (transport instanceof Volunteer) {
                res.add(transport);
            }
        }
        return res;
    }

    /**
     * Get Carrier that will take less time.
     *
     * @param transports Available carriers.
     * @param order      The order.
     * @return The carrier.
     */
    public Transport fastestTransport(List<Transport> transports, Order order) {
        User user = getUser(order.getUserCode());
        Shop shop = getShop(order.getShopCode());
        double aux;
        Transport res = transports.get(0);
        Coordinates userLocation = user.getLocation();
        Coordinates shopLocation = shop.getLocation();
        double distance = (Coordinates.distance(userLocation, shopLocation) + Coordinates.distance(shopLocation, res.getLocation()));
        double min = distance / res.getSpeed();
        for (Transport transport : transports) {
            distance = (Coordinates.distance(userLocation, shopLocation) + Coordinates.distance(shopLocation, transport.getLocation()));
            if (min > (aux = (distance / transport.getSpeed()))) {
                min = aux;
                res = transport;
            }
        }
        return res;
    }

    /**
     * Get cheaper Carrier.
     *
     * @param transports Available carriers.
     * @param order      The order.
     * @return The carrier.
     */
    public Transport carrierWithLessPrice(List<Transport> transports, Order order) {
        Transport res = transports.get(0);
        double min = MAX_VALUE;
        double price;
        for (Transport transport : transports) {
            if (transport instanceof Carrier) {
                price = getTotalPrice(order, transport);
                if (min > price) {
                    min = price;
                    res = transport;
                }
            }
        }
        return res;
    }

    /**
     * Gets orders between date1 and date2.
     *
     * @param day1  Lower day limit.
     * @param time1 Lower time limit.
     * @param day2  Upper day limit.
     * @param time2 Upper time limit.
     * @return List of products which delivery date is between date 1 and date 2.
     */
    public List<Order> ordersInPeriod(String day1, String time1, String day2, String time2) {
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateString1 = day1 + " " + time1;
        String dateString2 = day2 + " " + time2;
        long timeInMillis1 = System.currentTimeMillis();
        long timeInMillis2 = System.currentTimeMillis();
        try {
            Date date1 = sdf.parse(dateString1);
            Date date2 = sdf.parse(dateString2);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            timeInMillis1 = calendar1.getTimeInMillis();
            timeInMillis2 = calendar2.getTimeInMillis();
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        for (Order o : ordersList) {
            if (o.getOrderDate() >= timeInMillis1 && o.getOrderDate() <= timeInMillis2) {
                orders.add(o);
            }
        }
        if (orders.isEmpty()) return null;
        return orders;
    }

    /**
     * Sets all next codes.
     */
    public void setAllCodes() {
        setUserCode();
        setTransportsCode();
        setProductCode();
        setOrderCode();
        setShopCode();
    }

    /**
     * Sets next order code.
     */
    public void setOrderCode() {
        int biggest = 0;
        int aux;

        for (Order o : ordersList) {
            if ((biggest < (aux = parseInt(o.getOrderCode().substring(1))))) {
                biggest = aux;
            }
        }
        biggest++;
        Order.setOrderCodeCounter(biggest);
    }

    /**
     * Sets next user code.
     */
    public void setUserCode() {
        int biggest = 0;
        int aux;

        for (User o : usersList) {
            if ((biggest < (aux = parseInt(o.getUserCode().substring(1))))) {
                biggest = aux;
            }
        }
        biggest++;
        User.setUserCodeCounter(biggest);
    }

    /**
     * Sets next transport code.
     */
    public void setTransportsCode() {
        int biggestCarrier = 0;
        int biggestVolunteer = 0;
        int aux;

        for (Transport o : transportsList) {
            if (o instanceof Carrier) {
                if ((biggestCarrier < (aux = parseInt(o.getCode().substring(1))))) {
                    biggestCarrier = aux;
                }
                biggestCarrier++;
            }
            if (o instanceof Volunteer) {
                if ((biggestVolunteer < (aux = parseInt(o.getCode().substring(1))))) {
                    biggestVolunteer = aux;
                }
                biggestVolunteer++;
            }
        }
        Carrier.setCarrierCodeCounter(biggestCarrier);
        Volunteer.setVolunteerCodeCounter(biggestVolunteer);
    }

    /**
     * Sets next product code.
     */
    public void setProductCode() {
        int biggest = 0;
        int aux;

        for (Product o : allProducts) {
            if ((biggest < (aux = parseInt(o.getProductCode().substring(1))))) {
                biggest = aux;
            }
        }
        biggest++;
        Product.setProductCodeCounter(biggest);
    }

    /**
     * Sets next product code.
     */
    public void setShopCode() {
        int biggest = 0;
        int aux;

        for (Shop o : shopsList) {
            if ((biggest < (aux = parseInt(o.getShopCode().substring(1))))) {
                biggest = aux;
            }
        }
        biggest++;
        Shop.setShopCodeCounter(biggest);
    }

    /**
     * Total distance during a order.
     *
     * @param order Order.
     * @return Distance.
     */
    public double getTotalDistance(Order order) {
        double distance;
        User user = getUser(order.getUserCode());
        Shop shop = getShop(order.getShopCode());
        Transport transport = getTransport(order.getTransportCode());
        Coordinates userCoordinates = user.getLocation();
        Coordinates shopCoordinates = shop.getLocation();
        Coordinates transportCoordinates = transport.getLocation();
        double distanceTransportShop = Coordinates.distance(transportCoordinates, shopCoordinates);
        double distanceShopUser = Coordinates.distance(shopCoordinates, userCoordinates);
        distance = distanceShopUser + distanceTransportShop;
        return distance;
    }

    /**
     * Calculates the total price of the transportation.
     *
     * @param order     Order info.
     * @param transport Transport to check price.
     * @return Transportation total price.
     */
    public double getTotalPrice(Order order, Transport transport) {
        double totalPrice;
        User user = getUser(order.getUserCode());
        Shop shop = getShop(order.getShopCode());
        Coordinates userCoordinates = user.getLocation();
        Coordinates shopCoordinates = shop.getLocation();
        Coordinates transportCoordinates = transport.getLocation();
        double weight = order.getWeight();
        double distanceTransportShop = Coordinates.distance(transportCoordinates, shopCoordinates);
        double distanceShopUser = Coordinates.distance(shopCoordinates, userCoordinates);
        double actionRadius = transport.getRadius();
        if (distanceTransportShop <= actionRadius && distanceShopUser <= actionRadius) {
            totalPrice = transport.getPrice() * (distanceShopUser + distanceTransportShop) * weight;
        } else {
            totalPrice = -1;
        }

        return totalPrice;
    }

    /**
     * Gets delivery time based on locations and a random factor.
     *
     * @param order Order to deliver.
     * @return Time in millis.
     */
    public long getDeliveryTime(Order order) {
        long time;
        User user = getUser(order.getUserCode());
        Shop shop = getShop(order.getShopCode());
        Transport transport = getTransport(order.getTransportCode());
        Coordinates userCoordinates = user.getLocation();
        Coordinates shopCoordinates = shop.getLocation();
        double randomizer = (Math.random() * (2 - 0.7 + 1)) + 0.7;
        double distanceShopUser = Coordinates.distance(shopCoordinates, userCoordinates);
        double speed = transport.getSpeed();
        time = (long) (distanceShopUser / speed * randomizer * 60 * 60 * 1000);
        return time;
    }

    /**
     * Updates carriers.
     */
    public void updateTransports() {
        List<Order> orders = ordersList.stream().filter(order -> order.getStatus() == 1).collect(Collectors.toList());
        transportsList.forEach(Transport::update);
        for (Order o : orders) {
            Transport transport = getTransport(o.getTransportCode());
            if (o.getDeliveryDate() > System.currentTimeMillis()) {
                if (transport instanceof Carrier) {
                    ((Carrier) transport).addUnavailable();
                }
            }
        }
    }

    /**
     * Updates order.
     *
     * @param transport Transport that will carry it.
     * @param order     Order  to update.
     */
    public void updateOrder(Transport transport, Order order) {

        order.setStatus(1);
        order.setTransportCode(transport.getCode());

        if (transport instanceof Carrier) {
            ((Carrier) transport).addKilometers(getTotalDistance(order));
            ((Carrier) transport).addUnavailable();
        }

        transport.setNextAvailableTime(System.currentTimeMillis() + getDeliveryTime(order));
        order.setDeliveryDate(order.getOrderDate() + getDeliveryTime(order));
        transport.setAcceptTime(System.currentTimeMillis());
        transport.addOrder(order);
    }

    /**
     * Updates Orders.
     */
    public void updateOrders() {
        List<Order> orders = ordersList.stream()
                .filter(order -> order.getStatus() == -1)
                .sorted(Comparator.comparingLong(Order::getOrderDate))
                .collect(Collectors.toList());
        for (Order o : orders) {
            User user = getUser(o.getUserCode());
            Shop shop = getShop(o.getShopCode());
            if (availableTransports(o) != null) {
                List<Transport> volunteers = availableVolunteers(availableTransports(o))
                        .stream()
                        .sorted(Comparator.comparingLong(Transport::getNextAvailableTime))
                        .collect(Collectors.toList());
                if (volunteers.size() > 0) {
                    Volunteer v = (Volunteer) volunteers.get(0);
                    o.setTransportCode(v.getCode());
                    o.setStatus(1);
                    o.setShopWait(Math.round((Math.random() * (10 - 1 + 1)) + 1) * 1000 * 60);
                    v.setNextAvailableTime(o.getOrderDate() + o.getShopWait() + getDeliveryTime(o));
                    o.setDeliveryDate(o.getOrderDate() + o.getShopWait() + getDeliveryTime(o));
                    v.setLocation(user.getLocation());
                    shop.removeOrderFromShop(o);
                }
            }
        }
    }

    /**
     * Updates users.
     */
    public void updateUsers() {
        List<Order> orders = ordersList.stream()
                .filter(order -> order.getStatus() == -1)
                .sorted(Comparator.comparingLong(Order::getOrderDate))
                .collect(Collectors.toList());
        for (Order o : orders) {
            getUser(o.getUserCode()).incrementUsersOrders();
        }
    }

    /**
     * Adds orders to shop.
     */
    public void addOrdersToShop() {
        for (Order o : ordersList) {
            if (o.getStatus() == -1) {
                Shop shop = getShop(o.getShopCode());
                shop.addOrderToShop(o);
            }
        }
    }

    /**
     * Updates system.
     */
    public void updateSystem() {
        updateUsers();
        addOrdersToShop();
        // updateTransports();
        updateOrders();
    }

    /**
     * Gets all orders from a user.
     *
     * @param user user code.
     * @return Orders.
     */
    public List<Order> userOrders(String user) {
        List<Order> orders = ordersList.stream().filter(o -> o.getUserCode().equals(user)).collect(Collectors.toList());
        if (orders.isEmpty()) return null;
        return orders;
    }

    public List<Order> shopOrders(String code, List<Order> orders) {
        orders.stream().filter(o -> o.getShopCode().equals(code)).collect(Collectors.toList());
        if (orders.isEmpty()) return null;
        return orders;
    }


    /**
     * Gets all orders from a user.
     *
     * @param user   User code.
     * @param orders Orders to search.
     * @return Orders.
     */
    public List<Order> userOrders(String user, List<Order> orders) {
        if (orders.isEmpty()) return null;
        List<Order> os = orders.stream().filter(o -> o.getUserCode().equals(user)).collect(Collectors.toList());
        if (os.isEmpty()) return null;
        return os;
    }

    /**
     * Get number of orders from a transport.
     *
     * @param code Code of transport.
     * @return Number of orders.
     */
    public int ordersFromTransport(String code) {
        return (int) getOrdersList().stream().filter(order -> order.getTransportCode().equals(code)).count();
    }

    /**
     * Trnasports used on all orders from an user.
     *
     * @param code Code of user.
     * @return All transports.
     */
    public Set<Transport> transportsFromUserOrders(String code) {
        Set<Transport> transports = new HashSet<>();
        List<Order> orders = getOrdersList().stream().filter(order -> order.getUserCode().equals(code)).collect(Collectors.toList());
        for (Order o : orders) {
            Transport t = getTransport(o.getTransportCode());
            transports.add(t);
        }
        if (transports.isEmpty()) return null;
        return transports;
    }

    /**
     * Get all orders from an user with no transport yet.
     *
     * @param code Code of user.
     * @return orders from an user with no transport yet.
     */
    public List<Order> ordersFromUserNoTransport(String code) {
        return getOrdersList().stream().filter(order -> order.getUserCode().equals(code)).filter(order -> order.getStatus() == -1).collect(Collectors.toList());
    }

    /**
     * Inicialize random ratings.
     */
    public void initRating() {
        for (Transport t : transportsList) {
            int[] rates = t.getRatings();
            rates[0] = (int) Math.round((Math.random() * (2 - 0 + 1)) + 0);
            rates[1] = (int) Math.round((Math.random() * (3 - 0 + 1)) + 0);
            rates[2] = (int) (int) Math.round((Math.random() * (5 - 2 + 1)) + 2);
            rates[3] = (int) Math.round((Math.random() * (15 - 12 + 1)) + 12);
            rates[4] = (int) Math.round((Math.random() * (22 - 19 + 1)) + 19);
            int totalRatings = 0;
            int numberRatings = 0;
            for (int i = 0; i < 5; i++) {
                numberRatings += rates[i];
                totalRatings += (i + 1) * rates[i];
            }
            t.setAverageRating((double) totalRatings / numberRatings);

        }
    }
}
