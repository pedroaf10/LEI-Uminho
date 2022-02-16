package pt.ph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

/**
 * Parser class. Used to parse a file.
 */
public class Parser {

    /**
     * Reads and makes lists with User, Volunteer, Carrier, Shop, Order from a file.
     *
     * @param fileLocation File directory.
     * @return AppState.
     */
    public static AppState readFile(String fileLocation) {
        AppState appState = new AppState();
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
            stream.forEach(line -> {
                String[] splittedLine = line.split(":", 2);
                switch (splittedLine[0]) {
                    case "Utilizador":
                        appState.addUser((parseUser(splittedLine[1])));
                        break;
                    case "Voluntario":
                        appState.addTransport((parseVolunteer(splittedLine[1])));
                        break;
                    case "Transportadora":
                        appState.addTransport((parseCarrier(splittedLine[1])));
                        break;
                    case "Loja":
                        appState.addShop((parseShop(splittedLine[1])));
                        break;
                    case "Encomenda":
                        appState.addOrder((parseOrder(splittedLine[1], appState)));
                        break;
                    case "Aceite":
                        parseAccept(splittedLine[1], appState);
                        break;
                    case "Produto":
                        appState.addProduct((parseProduct(splittedLine[1])));
                    default:
                        break;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appState;
    }

    public static AppState readInitFile(String fileLocation) {
        AppState appState = new AppState();
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
            stream.forEach(line -> {
                String[] splittedLine = line.split(":", 2);
                switch (splittedLine[0]) {
                    case "Utilizador":
                        appState.addUser((parseUser(splittedLine[1])));
                        break;
                    case "Voluntario":
                        appState.addTransport((parseVolunteer(splittedLine[1])));
                        break;
                    case "Transportadora":
                        appState.addTransport((parseInitCarrier(splittedLine[1])));
                        break;
                    case "Loja":
                        appState.addShop((parseShop(splittedLine[1])));
                        break;
                    case "Encomenda":
                        appState.addOrder((parseOrder(splittedLine[1], appState)));
                        break;
                    case "Aceite":
                        parseAccept(splittedLine[1], appState);
                        break;
                    case "Produto":
                        appState.addProduct((parseProduct(splittedLine[1])));
                    default:
                        break;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appState;
    }

    /**
     * Parses accept.
     *
     * @param s        string to check.
     * @param appState AppState.
     */
    private static void parseAccept(String s, AppState appState) {
        long timeInMillis = 0;
        String day = "1-1-2020";
        String time = "12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateString = day + " " + time;
        try {
            Date date = sdf.parse(dateString);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            timeInMillis = calendar1.getTimeInMillis();
        } catch (
                ParseException e) {
            e.printStackTrace();
        }

        if (appState.getOrder(s) != null) {
            Order order = appState.getOrder(s);
            order.setOrderDate(timeInMillis);
        }
    }

    /**
     * Parses product.
     *
     * @param s String containing a product.
     * @return The parsed product.
     */
    private static Product parseProduct(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String description = elements[1];
        double quantity = Double.parseDouble(elements[2]);
        double price = Double.parseDouble(elements[3]);
        boolean medical = Boolean.parseBoolean(elements[4]);
        return new Product(code, description, quantity, price, medical);
    }

    /**
     * Parses products.
     *
     * @param s        String containing the products.
     * @param appState AppState.
     * @return The parsed products.
     */
    public static List<Product> parseProducts(String s, AppState appState) {
        List<Product> res = new ArrayList<>();
        while (!s.isBlank()) {
            String[] product = s.split(",", 5);
            res.add(parseProduct(product, appState));
            if (product.length == 4) {
                break;
            }
            s = product[4];
        }
        return res;
    }

    /**
     * Parses a product.
     *
     * @param elements Product parsed elements.
     * @param appState AppState.
     * @return Parsed product.
     */
    public static Product parseProduct(String[] elements, AppState appState) {
        String code = elements[0];
        String description = elements[1];
        double quantity = Double.parseDouble(elements[2]);
        double price = Double.parseDouble(elements[3]);
        Product product = new Product(code, description, quantity, price, false);
        if (appState.getProduct(description) == null) {
            appState.addProduct(product);
        }
        return product;
    }

    /**
     * Parses an order.
     *
     * @param s String containing an order.
     * @return Parsed order.
     */
    private static Order parseOrder(String s, AppState appState) {
        String[] elements = s.split(",", 5);
        String orderCode = elements[0];
        String userCode = elements[1];
        String shopCode = elements[2];
        List<Product> products = parseProducts(elements[4], appState);
        return new Order(orderCode, userCode, shopCode, products);
    }

    /**
     * Parses a shop.
     *
     * @param s String containing a shop.
     * @return Parsed shop
     */
    private static Shop parseShop(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String name = elements[1];
        Coordinates location = new Coordinates(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        Credentials credentials = new Credentials(code + "@trazaqui.pt", "1234");
        return new Shop(code, name, location, credentials);
    }

    /**
     * Parses a carrier.
     *
     * @param s String containing a carrier.
     * @return Parsed carrier.
     */
    private static Carrier parseCarrier(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String companyName = elements[1];
        Coordinates location = new Coordinates(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        double nif = Double.parseDouble(elements[4]);
        double radius = Double.parseDouble(elements[5]);
        double price = Double.parseDouble(elements[6]);
        long isAvailable = 0;
        double speed = Math.round((Math.random() * (60 - 35 + 1)) + 35);
        Credentials credentials = new Credentials(code + "@trazaqui.pt", "1234");
        return new Carrier(code, companyName, nif, radius, price, isAvailable, location, speed, (int) Math.round((Math.random() * (15 - 5 + 1)) + 5), credentials);
    }

    /**
     * Parses a carrier.
     *
     * @param s String containing a carrier.
     * @return Parsed carrier.
     */
    private static Carrier parseInitCarrier(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String companyName = elements[1];
        Coordinates location = new Coordinates(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        double nif = Double.parseDouble(elements[4]);
        double radius = Double.parseDouble(elements[5]);
        double price = Double.parseDouble(elements[6]);
        double kms = Double.parseDouble(elements[7]);
        long isAvailable = 0;
        double speed = Math.round((Math.random() * (60 - 35 + 1)) + 35);
        Credentials credentials = new Credentials(code + "@trazaqui.pt", "1234");
        return new Carrier(code, companyName, nif, radius, price, isAvailable, location, speed, (int) Math.round((Math.random() * (15 - 5 + 1)) + 5), credentials, kms);
    }

    /**
     * Parses a volunteer.
     *
     * @param s String containing a volunteer.
     * @return Parsed volunteer.
     */
    private static Volunteer parseVolunteer(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String volunteerName = elements[1];
        Coordinates location = new Coordinates(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        double radius = Double.parseDouble(elements[4]);
        double price = 0;
        long isAvailable = 0;
        double speed = Math.round((Math.random() * (25 - 5 + 1)) + 5);
        Credentials credentials = new Credentials(code + "@trazaqui.pt", "1234");
        return new Volunteer(code, volunteerName, radius, location, speed, credentials);
    }

    /**
     * Parses an user.
     *
     * @param s String containing a user.
     * @return Parsed user.
     */
    private static User parseUser(String s) {
        String[] elements = s.split(",");
        String code = elements[0];
        String name = elements[1];
        Coordinates location = new Coordinates(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        Credentials credentials = new Credentials(code + "@trazaqui.pt", "1234");
        return new User(code, name, location, credentials);
    }

    /**
     * Verifies if a string is a valid NIF.
     *
     * @param test The string to verify.
     * @return Nif number is valid, else -1.
     */
    public static double validateNif(String test) {
        double numberNif = -1;
        boolean flag = true;
        try {
            numberNif = Double.parseDouble(test);
        } catch (NumberFormatException e) {
            flag = false;
        }
        if (!flag || !(test.length() == 9) || numberNif <= 0) {
            System.out.println("Invalid input!!!");
            return -1;
        }
        return numberNif;
    }

    /**
     * Verifies is a string is a positive number.
     *
     * @param test The string to verify.
     * @return The number if valid, else -1.
     */
    public static double validatePositiveNumber(String test) {
        double number = -1;
        boolean flag = true;
        try {
            number = Double.parseDouble(test);
        } catch (NumberFormatException e) {
            flag = false;
        }
        if (!flag || number <= 0) {
            System.out.println("Invalid input!!!");
            return -1;
        }
        return number;
    }

    /**
     * Verifies if a string is a valid vehicle.
     *
     * @param test The string to verify.
     * @return The vehicle speed if valid, else -1.
     */
    public static double validateVehicle(String test) {
        double volunteerSpeed = -1;
        switch (test) {
            case "foot":
                volunteerSpeed = Math.round((Math.random() * (7 - 4 + 1)) + 4);
                break;
            case "bicycle":
                volunteerSpeed = Math.round((Math.random() * (29 - 15 + 1)) + 15);
                break;
            case "scooter":
                volunteerSpeed = Math.round((Math.random() * (25 - 15 + 1)) + 15);
                break;
            case "motorcycle":
                volunteerSpeed = Math.round((Math.random() * (55 - 38 + 1)) + 38);
                break;
            case "car":
                volunteerSpeed = Math.round((Math.random() * (60 - 40 + 1)) + 40);
                break;
            default:
                System.out.println("Invalid vehicle!!!");
        }
        return volunteerSpeed;
    }

    /**
     * Verifies if a string is a valid carrier mode.
     *
     * @param test The string to verify.
     * @return The mode speed if valid, else -1.
     */
    public static double validateCarrierMode(String test) {
        double carrierSpeed = -1;
        switch (test) {
            case "normal":
                carrierSpeed = Math.round((Math.random() * (65 - 45 + 1)) + 45);
                break;
            case "express":
                carrierSpeed = Math.round((Math.random() * (80 - 55 + 1)) + 45);
                break;
            default:
                System.out.println("Invalid transport method!!!");
        }
        return carrierSpeed;
    }

    /**
     * Verifies if a string is a valid Longitude or Latitude.
     *
     * @param test The string to verify.
     * @param bot  Lower number.
     * @param top  higher number.
     * @return The Longitude or Latitude if valid, else null.
     */
    public static Double validateDoubleBetween(String test, int bot, int top) {
        double lat;
        try {
            lat = Double.parseDouble(test);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid value!");
            return null;
        }
        if (lat >= bot && lat <= top) {
            return lat;
        } else {
            System.out.println("Invalid value!");
            return null;
        }
    }


    /**
     * Verifies if a string is a valid email.
     *
     * @param test The string to verify.
     * @return True if valid, false if not.
     */
    public static boolean isValidEmail(String test) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (test == null)
            return false;
        return pat.matcher(test).matches();
    }

    /**
     * Verifies if the given UserCredentials matches with any User.
     *
     * @param credentials Credentials to verify.
     * @param type        User/Shop/Transport.
     * @param appState    AppState.
     * @return The User if it matches, null if not.
     */
    public static String verifyCredentials(Credentials credentials, String type, AppState appState) {
        if (type.equals("user")) {
            for (User u : appState.getUsersList()) {
                if (u.getCredentials().equals(credentials)) {
                    return u.getUserCode();
                }
            }
            return null;
        }
        if (type.equals("carrier")) {
            for (Transport c : appState.getTransportsList()) {
                if (c instanceof Carrier) {
                    if (c.getCredentials().equals(credentials)) {
                        return c.getCode();
                    }
                }
            }
            return null;
        }
        if (type.equals("volunteer")) {
            for (Transport v : appState.getTransportsList()) {
                if (v instanceof Volunteer) {
                    if (v.getCredentials().equals(credentials)) {
                        return v.getCode();
                    }
                }
            }
            return null;
        }
        if (type.equals("shop")) {
            for (Shop s : appState.getShopsList()) {
                if (s.getCredentials().equals(credentials)) {
                    return s.getShopCode();
                }
            }
            return null;
        }
        return null;
    }

    /**
     * Verifies if a User exists.
     *
     * @param user     User to verify.
     * @param appState AppState.
     * @return True if exists, false if not.
     */
    public static boolean verifyUser(String user, AppState appState) {
        boolean found = false;
        for (User u : appState.getUsersList()) {
            if (found) {
                break;
            }
            if (u.getUserCode().equals(user)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Verifies if a Shop exists.
     *
     * @param shop     Shop code to verify.
     * @param appState AppState.
     * @return True if exists, false if not.
     */
    public static boolean verifyShop(String shop, AppState appState) {
        boolean found = false;
        for (Shop s : appState.getShopsList()) {
            if (found) {
                break;
            }
            if (s.getShopCode().equals(shop)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Verifies if a Product exists.
     *
     * @param productName Product name to verify.
     * @param appState    AppState.
     * @return True if exists, false if not.
     */
    public static boolean verifyProduct(String productName, AppState appState) {
        boolean found = false;
        for (Product p : appState.getAllProducts()) {
            if (p.getDescription().equals(productName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Verifies if a Rating is an int between 1 and 5.
     *
     * @param test String to test.
     * @return True if it is, false if not.
     */
    public static boolean verifyRating(String test) {
        int rating;
        try {
            rating = parseInt(test);
        } catch (NumberFormatException ex) {
            return false;
        }
        return rating > 0 && rating < 6;
    }

    /**
     * Verifies if a Number is an int between int bot and int top.
     *
     * @param test String to test.
     * @param bot  Lower number.
     * @param top  Higher number.
     * @return True if it is, false if not.
     */
    public static boolean verifyInt(String test, int bot, int top) {
        int number;
        try {
            number = parseInt(test);
        } catch (NumberFormatException ex) {
            return false;
        }
        return number >= bot && number <= top;
    }


    /**
     * Verifies if a Transport exists.
     *
     * @param test     Transport code to verify.
     * @param appState AppState.
     * @return True if exists, false if not.
     */
    public static boolean verifyTransport(String test, AppState appState) {
        boolean found = false;
        for (Transport t : appState.getTransportsList()) {
            if (found) {
                break;
            }
            if (t.getCode().equals(test)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Verifies if an order exists.
     *
     * @param test     Order code to verify.
     * @param appState AppState.
     * @return True if exists, false if not.
     */
    public static boolean verifyOrder(String test, AppState appState) {
        boolean found = false;
        for (Order o : appState.getOrdersList()) {
            if (o.getOrderCode().equals(test)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Verifies if it is a valid file name.
     *
     * @param file String to test.
     * @return True if it is, false if not.
     */
    public static boolean verifyFileName(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Verifies if it is a valid date.
     *
     * @param strDate String to test.
     * @return True if it is, false if not.
     */
    public static boolean verifyDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setLenient(false);
            try {
                Date javaDate = format.parse(strDate);
                System.out.println(strDate + " is valid date format");
            } catch (ParseException e) {
                System.out.println(strDate + " is Invalid Date format");
                return false;
            }
            return true;
        }
    }

    /**
     * Verifies if it is a valid time.
     *
     * @param strTime String to test.
     * @return True if it is, false if not.
     */
    public static boolean verifyTime(String strTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Date parsedDate = dateFormat.parse("12-12-2017 " + strTime);
            System.out.println("Valid test case : " + strTime);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid");
            return false;
        }
    }
}
