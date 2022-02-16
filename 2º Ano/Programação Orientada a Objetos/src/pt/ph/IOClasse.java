package pt.ph;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Class with many IO interactions.
 */
public class IOClasse {

    /**
     * Adds a new User.
     *
     * @param appState AppState to add user.
     */
    public static void addUser(AppState appState) {
        Scanner in = new Scanner(System.in);
        String test;
        String userEmail;
        boolean valid;

        System.out.println("Insert User's name!");
        String userName = in.nextLine();
        if (!userName.equals("abort")) {
            Double userLongitude = null;
            do {
                System.out.println("Insert User's location longitude!");
                test = in.nextLine();
                if (test.equals("abort")) break;
                userLongitude = Parser.validateDoubleBetween(test, -180, 180);
            } while (userLongitude == null);
            if (userLongitude != null) {

                Double userLatitude = null;
                do {
                    System.out.println("Insert User's location latitude!");
                    test = in.nextLine();
                    if (test.equals("abort")) break;
                    userLatitude = Parser.validateDoubleBetween(test, -90, 90);
                } while (userLatitude == null);
                if (userLatitude != null) {

                    Coordinates userCoordinates = new Coordinates(userLongitude, userLatitude);

                    do {
                        System.out.println("Insert User's email!");
                        userEmail = in.nextLine();
                        if (userEmail.equals("abort")) break;
                        valid = Parser.isValidEmail(userEmail);
                        if (!valid) {
                            System.out.println("Invalid input!!!");
                        }
                    } while (!valid);
                    if (!userEmail.equals("abort")) {

                        System.out.println("Insert User's password!");
                        String userPassword = in.nextLine();
                        if (!userPassword.equals("abort")) {

                            Credentials credentials = new Credentials(userEmail, userPassword);

                            User newUser = new User(userName, userCoordinates, credentials);
                            appState.addUser(newUser);
                        }
                    }
                }
            }
        }
    }

    /**
     * Changes transport method ( normal or express ).
     *
     * @param code     Code of Transport.
     * @param appState AppState.
     */
    public static void changeTransportMethod(String code, AppState appState) {
        String test;
        Transport transport = appState.getTransport(code);
        Scanner in = new Scanner(System.in);
        double carrierSpeed = 0;
        do {
            System.out.println("Insert Carrier Company's transport method (normal, express)!");
            test = in.nextLine();
            if (test.equals("abort")) break;
            carrierSpeed = Parser.validateCarrierMode(test);
        } while (carrierSpeed == -1);
        if (!test.equals("abort"))
            transport.setSpeed(carrierSpeed);
    }

    /**
     * Sets new credentials to someone.
     *
     * @param code     Code to change.
     * @param type     User/Transport/Shop.
     * @param appState AppState.
     */
    public static void setCredentials(String code, String type, AppState appState) {
        Scanner in = new Scanner(System.in);
        String userEmail;
        boolean valid;
        do {
            System.out.println("Insert new email!");
            userEmail = in.nextLine();
            if (userEmail.equals("abort")) break;
            valid = Parser.isValidEmail(userEmail);
            if (!valid) {
                System.out.println("Invalid input!!!");
            }
        } while (!valid);
        if (!userEmail.equals("abort")) {


            System.out.println("Insert new password!");
            String userPassword = in.nextLine();
            if (!userPassword.equals("abort")) {

                Credentials credentials = new Credentials(userEmail, userPassword);
                switch (type) {
                    case "user":
                        User user = appState.getUser(code);
                        user.setCredentials(credentials);
                        break;

                    case "transport":
                        Transport transport = appState.getTransport(code);
                        transport.setCredentials(credentials);
                        break;

                    case "shop":
                        Shop shop = appState.getShop(code);
                        shop.setCredentials(credentials);
                        break;
                }
            }
        }
    }

    /**
     * Change transport from Volunteer.
     *
     * @param code     Code of Volunteer.
     * @param appState AppState.
     */
    public static void changeVehicle(String code, AppState appState) {
        Transport transport = appState.getTransport(code);
        Scanner in = new Scanner(System.in);
        double volunteerSpeed = -1;
        do {
            System.out.println("Insert Volunteers's transport (foot, bicycle, scooter, motorcycle, car)!");
            String volunteerVehicle = in.nextLine();
            if (volunteerVehicle.equals("abort")) break;
            volunteerSpeed = Parser.validateVehicle(volunteerVehicle);
        } while (volunteerSpeed == -1);
        transport.setSpeed(volunteerSpeed);
    }

    /**
     * Adds a volunteer.
     *
     * @param appState Where is going to add.
     */
    public static void addVolunteer(AppState appState) {
        Scanner in = new Scanner(System.in);
        String test;
        System.out.println("Insert Volunteers's name!");
        String volunteerName = in.nextLine();
        boolean valid;
        if (!volunteerName.equals("abort")) {
            String userEmail;
            do {
                System.out.println("Insert User's email!");
                userEmail = in.nextLine();
                if (userEmail.equals("abort")) break;
                valid = Parser.isValidEmail(userEmail);
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (!userEmail.equals("abort")) {
                System.out.println("Insert User's password!");
                String userPassword = in.nextLine();
                if (!userPassword.equals("abort")) {
                    Credentials credentials = new Credentials(userEmail, userPassword);

                    double volunteerRadius = -1;
                    do {
                        System.out.println("Insert Volunteers's radius of action!");
                        test = in.nextLine();
                        if (test.equals("abort")) break;
                        volunteerRadius = Parser.validatePositiveNumber(test);
                    } while (volunteerRadius == -1);
                    if (volunteerRadius != -1) {

                        Double volunteerLongitude = null;
                        do {
                            System.out.println("Insert Volunteers's location longitude!");
                            test = in.nextLine();
                            if (test.equals("abort")) break;
                            volunteerLongitude = Parser.validateDoubleBetween(test, -180, 180);
                        } while (volunteerLongitude == null);
                        if (volunteerLongitude != null) {

                            Double volunteerLatitude = null;
                            do {
                                System.out.println("Insert Volunteers's location latitude!");
                                test = in.nextLine();
                                if (test.equals("abort")) break;
                                volunteerLatitude = Parser.validateDoubleBetween(test, -90, 90);
                            } while (volunteerLatitude == null);
                            if (volunteerLatitude != null) {

                                Coordinates volunteerLocation = new Coordinates(volunteerLongitude, volunteerLatitude);

                                double volunteerSpeed = -1;
                                do {
                                    System.out.println("Insert Volunteers's transport (foot, bicycle, scooter, motorcycle, car)!");
                                    String volunteerVehicle = in.nextLine();
                                    if (volunteerVehicle.equals("abort")) break;
                                    volunteerSpeed = Parser.validateVehicle(volunteerVehicle);
                                } while (volunteerSpeed == -1);
                                if (volunteerSpeed != -1) {
                                    Transport newVolunteer = new Volunteer(volunteerName, volunteerRadius, volunteerLocation, volunteerSpeed, credentials);
                                    appState.addTransport(newVolunteer);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a carrier.
     *
     * @param appState Where is going to add.
     */
    public static void addCarrier(AppState appState) {
        Scanner in = new Scanner(System.in);
        String test;
        System.out.println("Insert Carrier Company's name!");
        String carrierName = in.nextLine();
        if (!carrierName.equals("abort")) {
            boolean valid;
            String userEmail;
            do {
                System.out.println("Insert User's email!");
                userEmail = in.nextLine();
                if (userEmail.equals("abort")) break;
                valid = Parser.isValidEmail(userEmail);
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (!userEmail.equals("abort")) {
                System.out.println("Insert User's password!");
                String userPassword = in.nextLine();
                if (!userPassword.equals("abort")) {
                    Credentials credentials = new Credentials(userEmail, userPassword);

                    double carrierNif = -1;
                    do {
                        System.out.println("Insert Carrier Company's NIF!");
                        test = in.nextLine();
                        if (test.equals("abort")) break;
                        carrierNif = Parser.validateNif(test);
                    } while (carrierNif == -1);
                    if (carrierNif != -1) {
                        double carrierPrice = -1;
                        do {
                            System.out.println("Insert Carrier Company's price per Km!");
                            test = in.nextLine();
                            if (test.equals("abort")) break;
                            carrierPrice = Parser.validatePositiveNumber(test);
                        } while (carrierPrice == -1);
                        if (carrierPrice != -1) {

                            double carrierRadius = -1;
                            do {
                                System.out.println("Insert Carrier Company's radius of action!");
                                test = in.nextLine();
                                if (test.equals("abort")) break;
                                carrierRadius = Parser.validatePositiveNumber(test);
                            } while (carrierRadius == -1);
                            if (carrierRadius != -1) {
                                Double carrierLongitude = null;
                                do {
                                    System.out.println("Insert Carrier Company's location longitude!");
                                    test = in.nextLine();
                                    if (test.equals("abort")) break;
                                    carrierLongitude = Parser.validateDoubleBetween(test, -180, 180);
                                } while (carrierLongitude == null);
                                if (carrierLongitude != null) {
                                    Double carrierLatitude = null;
                                    do {
                                        System.out.println("Insert Carrier Company's location latitude!");
                                        test = in.nextLine();
                                        if (test.equals("abort")) break;
                                        carrierLatitude = Parser.validateDoubleBetween(test, -90, 90);
                                    } while (carrierLatitude == null);
                                    if (carrierLatitude != null) {

                                        Coordinates carrierLocation = new Coordinates(carrierLongitude, carrierLatitude);

                                        double carrierSpeed = -1;
                                        do {
                                            System.out.println("Insert Carrier Company's transport method (normal, express)!");
                                            test = in.nextLine();
                                            if (test.equals("abort")) break;
                                            carrierSpeed = Parser.validateCarrierMode(test);
                                        } while (carrierSpeed == -1);
                                        if (carrierSpeed != -1) {

                                            Transport newCarrier = new Carrier(carrierName, carrierNif, carrierRadius, carrierPrice, 0, carrierLocation, carrierSpeed, (int) Math.round((Math.random() * (15 - 5 + 1)) + 5), credentials);
                                            appState.addTransport(newCarrier);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a shop.
     *
     * @param appState Where is going to add.
     */
    public static void addShop(AppState appState) {
        Scanner in = new Scanner(System.in);
        String test;
        System.out.println("Insert Shop's name!");
        String shopName = in.nextLine();
        if (!shopName.equals("abort")) {
            boolean valid;
            String userEmail;

            do {
                System.out.println("Insert User's email!");
                userEmail = in.nextLine();
                valid = Parser.isValidEmail(userEmail);
                if (userEmail.equals("abort")) break;
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (!userEmail.equals("abort")) {

                System.out.println("Insert User's password!");
                String userPassword = in.nextLine();
                if (!userPassword.equals("abort")) {
                    Credentials credentials = new Credentials(userEmail, userPassword);

                    Double shopLongitude = null;
                    do {
                        System.out.println("Insert Shop's location longitude!");
                        test = in.nextLine();
                        if (test.equals("abort")) break;
                        shopLongitude = Parser.validateDoubleBetween(test, -180, 180);
                    } while (shopLongitude == null);
                    if (shopLongitude != null) {

                        Double shopLatitude = null;
                        do {
                            System.out.println("Insert Shop's location latitude!");
                            test = in.nextLine();
                            if (test.equals("abort")) break;
                            shopLatitude = Parser.validateDoubleBetween(test, -90, 90);
                        } while (shopLatitude == null);
                        if (shopLatitude != null) {

                            Coordinates shopLocation = new Coordinates(shopLongitude, shopLatitude);

                            Shop newShop = new Shop(shopName, shopLocation, credentials);
                            appState.addShop(newShop);
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifies if user credentials are right.
     *
     * @param appState AppState.
     */
    public static void matchCredentials(AppState appState) {
        Scanner in = new Scanner(System.in);

        System.out.println("Insert email:");
        String emailToVerify = in.nextLine();
        if (!emailToVerify.equals("abort")) {

            System.out.println("Insert password:");
            String passwordToVerify = in.nextLine();
            if (!passwordToVerify.equals("abort")) {

                Credentials credentialsToVerify;
                credentialsToVerify = new Credentials(emailToVerify, passwordToVerify);
                User userVerified = appState.getUser(Parser.verifyCredentials(credentialsToVerify, "user", appState));
                if (userVerified == null) System.out.println("Invalid credentials!!!");
                else {
                    System.out.println("Valid credentials!!!");
                }
                System.out.println();
            }
        }
    }

    /**
     * Read an user.
     *
     * @param appState AppState.
     * @return User code.
     */
    public static String readUser(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String userCode;
        do {
            System.out.println("Insert a User Code!");
            userCode = in.nextLine();
            if (userCode.equals("abort")) break;
            valid = Parser.verifyUser(userCode, appState);
            if (!valid) {
                System.out.println("Invalid input!!!");
            }
        } while (!valid);
        return userCode;
    }

    /**
     * Read a shop.
     *
     * @param appState AppState.
     * @return Shop code.
     */
    public static String readShop(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String shopCode;
        do {
            System.out.println("Insert a Shop Code!");
            shopCode = in.nextLine();
            if (shopCode.equals("abort")) break;
            valid = Parser.verifyShop(shopCode, appState);
            if (!valid) {
                System.out.println("Invalid input!!!");
            }
        } while (!valid);
        return shopCode;
    }

    /**
     * Read a rating.
     *
     * @return Rating.
     */
    public static int readRating() {
        Scanner in = new Scanner(System.in);
        boolean valid;
        int rating = -1;
        do {
            System.out.println("Please insert rating from 1 to 5!");
            String test = in.nextLine();
            if (test.equals("abort")) break;
            valid = Parser.verifyRating(test);
            if (!valid) {
                System.out.println("Invalid input!!!");
            } else {
                rating = Integer.parseInt(test);
            }
        } while (!valid);
        return rating;
    }

    /**
     * Read an order.
     *
     * @param appState AppState.
     * @return Order code.
     */
    public static Order readOrder(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        Order order = null;
        do {
            String code = in.nextLine();
            if (code.equals("abort")) break;
            valid = Parser.verifyOrder(code, appState);
            if (!valid) {
                System.out.println("Invalid input!!!");
            } else {
                order = appState.getOrder(code); //verifcar se contem o order
            }
        } while (!valid);
        return order;
    }

    /**
     * Read a transport.
     *
     * @param appState AppState.
     * @return Transport code.
     */
    public static Transport readTransport(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        Transport transport = null;
        do {
            String transportCode = in.nextLine();
            if (transportCode.equals("abort")) break;
            valid = Parser.verifyTransport(transportCode, appState);
            if (!valid) {
                System.out.println("Invalid input!!!");
            } else {
                transport = appState.getTransport(transportCode); //verifcar se contem o transport
            }
        } while (!valid);
        return transport;
    }

    /**
     * Removes a file.
     */
    public static void removeFile() {
        Scanner in = new Scanner(System.in);
        String fileName;
        System.out.println("Insert file's name!");
        fileName = in.nextLine();
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        System.out.println("Deletion successful.");
    }

    /**
     * Saves a file.
     *
     * @param appState AppState.
     */
    public static void saveFile(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        do {
            System.out.println("Enter a name for saving the file!\n Example : example.txt");
            String fileName = in.nextLine();
            valid = Parser.verifyFileName(fileName);
            try {
                appState.writeObject(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!valid);
    }

    /**
     * Loads a file.
     *
     * @param appState AppState.
     * @return AppState saved on file.
     */
    public static AppState loadFile(AppState appState) {
        Scanner in = new Scanner(System.in);
        System.out.println(("Insert file's name!"));
        String fileName = in.nextLine();
        AppState readAppState = new AppState();
        try {
            readAppState = appState.readObject(fileName);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return readAppState;
    }

    /**
     * Reads a date.
     *
     * @return Date in string.
     */
    public static String readDate() {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String date;
        do {
            System.out.println("Insert date in the format dd-MM-yyyy!");
            date = in.nextLine();
            if (date.equals("abort")) break;
            valid = Parser.verifyDate(date);
        } while (!valid);
        return date;
    }

    /**
     * Reads a time.
     *
     * @return Time in string.
     */
    public static String readDateTime() {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String time;
        do {
            System.out.println("Enter the first date time as HH:MM:SS");
            time = in.nextLine();
            if (time.equals("abort")) break;
            valid = Parser.verifyTime(time);
        } while (!valid);
        return time;
    }

    /**
     * Prints products.
     *
     * @param appState AppState.
     */
    public static void printProducts(AppState appState) {
        int a = 0;
        for (int i = 0; i < appState.getAllProducts().size(); i++) {
            if (i + 7 < appState.getAllProducts().size()) {
                System.out.println("                                           " + appState.getAllProducts().get(i).getDescription() + "  |  " + appState.getAllProducts().get(i + 1).getDescription() + "  |  " + appState.getAllProducts().get(i + 2).getDescription() + "  |  " + appState.getAllProducts().get(i + 3).getDescription() + "  |  " + appState.getAllProducts().get(i + 4).getDescription() + "  |  " + appState.getAllProducts().get(i + 5).getDescription() + "  |  " + appState.getAllProducts().get(i + 6).getDescription() + "  |  ");
                i = i + 6
                ;
                System.out.println("                                           ---------------------------------------------------------------------------------------------------------------");
            } else {
                for (; i < appState.getAllProducts().size(); i++) {
                    if (a == 0) {
                        System.out.print("                                           " + appState.getAllProducts().get(i).getDescription() + "  |  ");
                        a++;
                    } else {
                        System.out.print(appState.getAllProducts().get(i).getDescription() + "  |  ");
                    }
                }
            }
        }
    }

    public static String getPath() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        String test;
        do {
            System.out.println("Please insert file's path");
            test = in.nextLine();
            if(test.equals("abort")) break;
            Path path = Paths.get(test);
            valid = Files.exists(path);
        } while (!valid);
        return test;
    }
}
