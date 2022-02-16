package pt.ph;

import java.util.*;

/**
 * Class that shows menus.
 */
public class View {

    /**
     * View constructor.
     */
    public View() {
    }

    /**
     * Admin interface.
     *
     * @param appState The appState.
     */
    public void viewAdmin(AppState appState) {
        boolean quit = false;
        String menuItem;
        String userCode;
        Order order;
        String shopCode;
        int rating;
        boolean valid;
        Scanner in = new Scanner(System.in);
        do {
            menuAdmin();
            menuItem = in.nextLine();

            switch (menuItem) {

                case "1":

                    appState.updateSystem();
                    IOClasse.addUser(appState);
                    break;

                case "2":

                    appState.updateSystem();
                    IOClasse.addVolunteer(appState);
                    break;


                case "3":

                    appState.updateSystem();
                    IOClasse.addCarrier(appState);
                    break;


                case "4":

                    appState.updateSystem();
                    IOClasse.addShop(appState);
                    break;

                case "5":

                    appState.updateSystem();
                    IOClasse.matchCredentials(appState);
                    break;

                case "6":

                    appState.updateSystem();
                    List<Product> products;
                    userCode = IOClasse.readUser(appState);
                    if (!userCode.equals("abort")) {
                        shopCode = IOClasse.readShop(appState);
                        if (!shopCode.equals("abort")) {
                            products = insertListOfProducts(appState);
                            order = new Order(userCode, shopCode, products);
                            appState.insertNewOrder(order, appState);
                            appState.printAppState();
                        }
                    }
                    break;

                case "7":

                    appState.updateSystem();
                    Transport transport = IOClasse.readTransport(appState);
                    if (transport != null) {
                        rating = IOClasse.readRating();
                        if (rating != -1) {
                            transport.addRating(rating);
                        }
                    }
                    break;

                case "8":
                    String test;
                    do {
                        appState.updateSystem();
                        List<User> top10Users;
                        top10Users = appState.top10Users();
                        top10User(top10Users);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");

                    } while (!test.equals("0"));
                    break;

                case "9":
                    do {
                        appState.updateSystem();
                        List<Carrier> top10Carrier;
                        top10Carrier = appState.top10Carriers();
                        top10Carrier(top10Carrier);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");
                    } while (!test.equals("0"));
                    break;

                case "10":

                    appState.updateSystem();
                    do {
                        System.out.println("Insert an Order Code!");
                        String orderCode = in.nextLine();
                        if (orderCode.equals("abort")) break;
                        valid = Parser.verifyOrder(orderCode, appState);
                        if (!valid) {
                            System.out.println("Invalid input!!!");
                        } else {
                            order = appState.getOrder(orderCode);
                            List<Transport> available;
                            available = appState.availableTransports(order);
                            System.out.println(available);
                            List<Transport> volunteer;
                            volunteer = appState.availableVolunteers(available);
                            if (order.getStatus() != -1) {
                                System.out.println("Order already has been taken.");
                            } else {

                                if (available == null) {
                                    System.out.println("There is no available transports right now! Please try again Later!!!");
                                    break;
                                }
                                System.out.println(available);
                                if (!volunteer.isEmpty()) {
                                    System.out.println("A Volunteer Will Make the Delivery!");
                                    appState.updateOrder(appState.fastestTransport(volunteer, appState.getOrder(orderCode)), order);

                                } else {
                                    System.out.println("Please choose : \n1) Fastest Delivery\n2) Cheapest Delivery");
                                    userCode = in.nextLine();
                                    if (!userCode.equals("abort")) {
                                        switch (userCode) {

                                            case "1":
                                                Carrier carrier = (Carrier) appState.fastestTransport(available, appState.getOrder(orderCode));
                                                appState.updateOrder(carrier, order);
                                                break;

                                            case "2":
                                                carrier = (Carrier) appState.carrierWithLessPrice(available, appState.getOrder(orderCode));
                                                appState.updateOrder(carrier, order);
                                                break;

                                            default:
                                                System.out.println("Invalid input!!!");
                                                break;
                                        }
                                    }
                                }


                            }
                        }
                    }
                    while (!valid);


                    break;


                case "11":

                    appState.updateSystem();
                    String date1;
                    String time1;
                    String date2;
                    String time2;

                    date1 = IOClasse.readDate();
                    if (date1.equals("abort")) break;
                    time1 = IOClasse.readDateTime();
                    if (time1.equals("abort")) break;
                    date2 = IOClasse.readDate();
                    if (date2.equals("abort")) break;
                    time2 = IOClasse.readDateTime();
                    if (time2.equals("abort")) break;
                    List<Order> orders = appState.ordersInPeriod(date1, time1, date2, time2);
                    do {
                        orders(orders);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");

                    } while (!test.equals("0"));
                    break;

                case "12":

                    appState.updateSystem();
                    String user = IOClasse.readUser(appState);
                    if (user.equals("abort")) break;
                    IOClasse.setCredentials(user, "user", appState);
                    break;

                case "13":
                    appState.updateSystem();
                    transport = IOClasse.readTransport(appState);
                    if (transport == null) break;
                    IOClasse.setCredentials(transport.getCode(), "transport", appState);
                    break;

                case "14":
                    appState.updateSystem();
                    String shop = IOClasse.readShop(appState);
                    IOClasse.setCredentials(shop, "shop", appState);
                    break;

                case "save":

                    appState.updateSystem();
                    IOClasse.saveFile(appState);
                    break;

                case "read":
                    appState = IOClasse.loadFile(appState);
                    appState.updateSystem();
                    break;

                case "remove":
                    IOClasse.removeFile();
                    break;

                case "print":

                    appState.updateSystem();
                    appState.printAppState();
                    break;

                case "0":

                    appState.updateSystem();
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
                    System.out.println();
            }

        } while (!quit);
        System.out.println();
        System.out.println("Bye-bye!");
    }

    public static int viewLoginScreen() {
        Scanner in = new Scanner(System.in);
        String test;
        boolean valid;
        int option = 0;
        do {
            log();
            test = in.nextLine();
            valid = Parser.verifyInt(test, 0, 3);
            if (valid) {
                option = Integer.parseInt(test);
            } else {
                System.out.println("Invalid input!!!");
                System.out.println();
            }
        } while (!valid);
        return option;
    }

    public static int viewLoginRegister(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String test;
        int option = 0;
        do {
            sign();
            test = in.nextLine();
            valid = Parser.verifyInt(test, 0, 4);
            if (valid) {
                option = Integer.parseInt(test);
            } else {
                System.out.println("Invalid input!!!");
                System.out.println();
            }
        } while (!valid);
        switch (option) {
            case 1:
                IOClasse.addUser(appState);
                break;
            case 2:
                IOClasse.addVolunteer(appState);
                break;
            case 3:
                IOClasse.addCarrier(appState);
                break;
            case 4:
                IOClasse.addShop(appState);
                break;

            case 0:
                break;

            default:


        }
        return -1;
    }


    public static int viewChoseLogin(AppState appState) {
        boolean valid;
        String user;
        View view = new View();
        int option = -1;
        Scanner in = new Scanner(System.in);
        do {
            firstMenu();
            String test = in.nextLine();
            valid = Parser.verifyInt(test, 0, 5);
            if (valid) {
                option = Integer.parseInt(test);
            } else {
                System.out.println("Invalid input!!!");
            }
        } while (!valid);

        switch (option) {
            case (1):
                user = (View.loginUser(appState));
                if (user != null)
                    view.viewUser(appState, user);
                break;


            case (2):
                user = (View.loginVolunteer(appState));
                if (user != null)
                    view.viewVolunteer(appState, user);
                break;


            case (3):
                user = (View.loginCarrier(appState));
                if (user != null)
                    view.viewCarrier(appState, user);
                break;


            case (4):
                user = (View.loginShop(appState));
                if (user != null)
                    view.viewShop(appState, user);
                break;

            case (5):
                loginAdmin();
                view.viewAdmin(appState);
                break;

            case (0):
                break;

        }
        return -1;
    }

    public void viewUser(AppState appState, String userCode) {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        String menuItem;
        Order order;
        String shopCode;
        int rating = 0;
        boolean valid;
        Set<Transport> transports;
        do {
            menuUser();
            menuItem = in.nextLine();
            switch (menuItem) {

                case "1":
                    valid = false;
                    appState.updateSystem();
                    List<Product> products;
                    shopCode = IOClasse.readShop(appState);
                    if (!shopCode.equals("abort")) {
                        products = insertListOfProducts(appState);
                        if (products != null) {
                            order = new Order(userCode, shopCode, products);
                            appState.insertNewOrder(order, appState);
                            List<Transport> available;
                            available = appState.availableTransports(order);
                            if (available == null) {
                                System.out.println("There is no available transports right now! Please try again later!!!");
                            } else {
                                List<Transport> volunteer;
                                volunteer = appState.availableVolunteers(available);
                                do {
                                    if (!volunteer.isEmpty()) {
                                        Transport transport = appState.fastestTransport(volunteer, appState.getOrder(order.getOrderCode()));
                                        appState.updateOrder(transport, order);
                                        order.setShopWait(Math.round((Math.random() * (10 - 1 + 1)) + 1) * 1000 * 60);
                                        System.out.println("Volunteer " + transport.getName() + " will transport your order!");
                                        System.out.println("Please rate your transport experience after delivery!");
                                        valid = true;
                                    } else {
                                        choosetransporter();
                                        String test = in.nextLine();
                                        switch (test) {
                                            case "1":
                                                Carrier transport = (Carrier) appState.fastestTransport(available, order);
                                                appState.updateOrder(transport, order);
                                                System.out.println("Carrier " + transport.getName() + " will transport your order!");
                                                System.out.println("Please rate your transport experience after delivery!");
                                                valid = true;
                                                break;
                                            case "2":
                                                transport = (Carrier) appState.carrierWithLessPrice(available, order);
                                                appState.updateOrder(transport, order);
                                                System.out.println("Carrier " + transport.getName() + " will transport your order!");
                                                System.out.println("Please rate your transport experience after delivery!");
                                                valid = true;
                                                break;
                                            case "0":
                                                valid = true;
                                                break;
                                            default:
                                                System.out.println("Invalid input!!!");
                                                break;
                                        }
                                    }
                                } while (!valid);
                            }

                        } else {
                            System.out.println("Your purchase was cancelled!");
                        }
                    }
                    break;

                case "2":
                    appState.updateSystem();
                    String date1;
                    String time1;
                    String date2;
                    String time2;
                    String test;

                    do {
                        date1 = IOClasse.readDate();
                        if (date1.equals("abort")) break;
                        time1 = IOClasse.readDateTime();
                        if (time1.equals("abort")) break;
                        date2 = IOClasse.readDate();
                        if (date2.equals("abort")) break;
                        time2 = IOClasse.readDateTime();
                        if (time2.equals("abort")) break;
                        List<Order> orders = appState.ordersInPeriod(date1, time1, date2, time2);
                        if (orders != null) {
                            orders = appState.userOrders(userCode, orders);
                            orders(orders);
                            test = in.nextLine();
                            if (!test.equals("0"))
                                System.out.println("Invalid Input");
                        } else {
                            orders(orders);
                            test = in.nextLine();
                        }
                    } while (!test.equals("0"));
                    break;


                case "3":
                    List<Order> orders = appState.userOrders(userCode);
                    do {
                        orders(orders);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");

                    } while (!test.equals("0"));
                    break;

                case "4":
                    List<Transport> available = null;
                    valid = false;
                    orders = appState.ordersFromUserNoTransport(userCode);
                    if (orders.size() == 0) {
                        System.out.println("All your orders have transports!");
                    } else {
                        do {
                            ordersfortransport(orders);
                            order = IOClasse.readOrder(appState);
                            if (order == null) break;
                            if (orders.contains(order)) {
                                available = appState.availableTransports(order);
                                valid = true;
                            } else valid = false;
                        } while (!valid);
                        if (valid && available != null) {
                            choosetransporter();
                            test = in.nextLine();
                            switch (test) {
                                case "1":
                                    Carrier transport = (Carrier) appState.fastestTransport(available, order);
                                    appState.updateOrder(transport, order);
                                    System.out.println("Carrier " + transport.getName() + " will transport your order!");
                                    System.out.println("Please rate your transport experience after delivery!");
                                    break;
                                case "2":
                                    transport = (Carrier) appState.carrierWithLessPrice(available, order);
                                    appState.updateOrder(transport, order);
                                    System.out.println("Carrier " + transport.getName() + " will transport your order!");
                                    System.out.println("Please rate your transport experience after delivery!");
                                    break;
                                case "0":
                                    break;
                                default:
                                    System.out.println("Invalid input!!!");
                                    break;
                            }
                        }
                    }

                    break;
                case "5":
                    IOClasse.setCredentials(userCode, "user", appState);
                    break;
                case "6":
                    appState.updateSystem();
                    transports = appState.transportsFromUserOrders(userCode);
                    if (transports == null) {
                        System.out.println("There are no transports to rate!");
                    } else {

                        List<Transport> list = new ArrayList<Transport>(transports);

                        transportToRate(list);
                        Transport transport = IOClasse.readTransport(appState);
                        if (transport != null) {
                            rating = IOClasse.readRating();
                            if (rating != -1) transport.addRating(rating);
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid input!!!");
                    break;

                case "0":
                    System.out.println("Bye!!!");
                    quit = true;
                    break;
            }
        } while (!quit);
    }

    public void viewVolunteer(AppState appState, String volunteerCode) {
        Scanner in = new Scanner(System.in);
        String menuItem;
        String option;
        boolean valid;
        boolean quit = false;
        do {
            menuVolunteer();
            menuItem = in.nextLine();
            switch (menuItem) {
                case "1":
                    valid = false;
                    do {
                        choosestatus();
                        option = in.nextLine();
                        switch (option) {
                            case "1":
                                appState.getTransport(volunteerCode).setNextAvailableTime(0);
                                valid = true;
                                break;
                            case "2":
                                appState.getTransport(volunteerCode).setNextAvailableTime(Long.MAX_VALUE);
                                valid = true;
                                break;
                            case "0":
                                valid = true;
                                break;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } while (!valid);
                    break;
                case "2":
                    valid = false;
                    do {
                        choosestatus();
                        menuItem = in.nextLine();
                        switch (menuItem) {
                            case "1":
                                appState.getTransport(volunteerCode).setAcceptMedProducts(true);
                                valid = true;
                                break;
                            case "2":
                                appState.getTransport(volunteerCode).setAcceptMedProducts(false);
                                valid = true;
                                break;
                            case "0":
                                valid = true;
                                break;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } while (!valid);
                    break;
                case "3":
                    String test;
                    int orders = appState.ordersFromTransport(volunteerCode);
                    do {
                        allorders(orders);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");
                    } while (!test.equals("0"));
                    break;
                case "4":
                    Double radius = null;
                    valid = false;
                    do {
                        System.out.println("Insert your new Radius");
                        option = in.nextLine();
                        if (option.equals("abort")) break;
                        radius = Parser.validateDoubleBetween(option, 0, (int) Double.MAX_VALUE);
                        if (radius != null) valid = true;
                    } while (!valid);
                    if (!option.equals("abort")) {
                        appState.getTransport(volunteerCode).setRadius(radius);
                        System.out.println("New radius set to(" + radius + ")");
                    }
                    break;
                case "5":
                    IOClasse.changeVehicle(volunteerCode, appState);
                    break;
                case "6":
                    IOClasse.setCredentials(volunteerCode, "transport", appState);
                    break;
                case "7":
                    double rating = appState.getTransport(volunteerCode).getAverageRating();
                    System.out.println("Your average rating is (" + rating + ") !");
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input!!!");
                    break;
            }
        }
        while (!quit);
    }

    public void viewCarrier(AppState appState, String carrierCode) {
        Scanner in = new Scanner(System.in);
        String menuItem;
        Carrier carrier;
        boolean valid, part;
        boolean quit = false;
        do {
            menuCarrier();
            menuItem = in.nextLine();
            switch (menuItem) {
                case "1":
                    valid = false;
                    do {
                        choosestatus();
                        String option = in.nextLine();
                        switch (option) {
                            case "1":
                                part = false;
                                carrier = (Carrier) appState.getTransport(carrierCode);
                                carrier.setNextAvailableTime(0);
                                do {
                                    System.out.println("Insert number of transporters");
                                    option = in.nextLine();
                                    if (option.equals("abort")) break;
                                    Double number = Parser.validateDoubleBetween(option, 0, (int) Double.MAX_VALUE);
                                    if (number != null) {
                                        int local = (int) Math.round(number);
                                        carrier.setTotalCarriers(local);
                                        part = true;
                                    }
                                } while (!part);
                                break;
                            case "2":
                                appState.getTransport(carrierCode).setNextAvailableTime(Long.MAX_VALUE);
                                carrier = (Carrier) appState.getTransport(carrierCode);
                                carrier.setTotalCarriers(0);
                                valid = true;
                                break;
                            case "0":
                                valid = true;
                                break;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } while (!valid);
                    break;
                case "2":
                    valid = false;
                    do {
                        choosestatus();
                        menuItem = in.nextLine();
                        switch (menuItem) {
                            case "1":
                                appState.getTransport(carrierCode).setAcceptMedProducts(true);
                                valid = true;
                                break;
                            case "2":
                                valid = true;
                                appState.getTransport(carrierCode).setAcceptMedProducts(false);
                                break;
                            case "0":
                                valid = true;
                                break;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } while (!valid);
                    break;
                case "3":
                    String test;
                    carrier = (Carrier) appState.getTransport(carrierCode);

                    double kms = carrier.getKilometers();
                    do {
                        allordersandkms(kms);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");
                    } while (!test.equals("0"));
                    break;
                case "4":
                    Double radius = null;
                    valid = false;
                    do {
                        System.out.println("Insert your new Radius");
                        menuItem = in.nextLine();
                        if (menuItem.equals("abort")) break;
                        radius = Parser.validateDoubleBetween(menuItem, 0, (int) Double.MAX_VALUE);
                        if (radius != null) valid = true;
                    } while (!valid);
                    if (!menuItem.equals("abort")) {
                        appState.getTransport(carrierCode).setRadius(radius);
                        System.out.println("New radius set to(" + radius + ")");
                    }
                    break;
                case "5":
                    IOClasse.changeTransportMethod(carrierCode, appState);
                    break;
                case "6":
                    IOClasse.setCredentials(carrierCode, "transport", appState);
                    break;
                case "7":
                    double rating = appState.getTransport(carrierCode).getAverageRating();
                    System.out.println("Your average rating is (" + rating + ") !");
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input!!!");
                    break;
            }
        }
        while (!quit);
    }

    public void viewShop(AppState appState, String shopCode) {
        Scanner in = new Scanner(System.in);
        String menuItem;
        boolean quit = false;
        do {
            shopCarrier();
            menuItem = in.nextLine();
            switch (menuItem) {
                case "1":
                    String test;
                    do {
                        ordersstuck(appState.getShop(shopCode).waitingUsers());
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");
                    } while (!test.equals("0"));
                    break;
                case "2":
                    appState.updateSystem();
                    String date1;
                    String time1;
                    String date2;
                    String time2;
                    do {
                        date1 = IOClasse.readDate();
                        if (date1.equals("abort")) break;
                        time1 = IOClasse.readDateTime();
                        if (time1.equals("abort")) break;
                        date2 = IOClasse.readDate();
                        if (date2.equals("abort")) break;
                        time2 = IOClasse.readDateTime();
                        if (time2.equals("abort")) break;
                        List<Order> orders = appState.ordersInPeriod(date1, time1, date2, time2);
                        if (orders != null) {
                            orders = appState.shopOrders(shopCode, orders);
                        }
                        orders(orders);
                        test = in.nextLine();
                        if (!test.equals("0"))
                            System.out.println("Invalid Input");
                    } while (!test.equals("0"));
                    break;
                case "3":
                    IOClasse.setCredentials(shopCode, "shop", appState);
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        while (!quit);
    }

    public static void top10User(List<User> top10Users) {
        int i, a = 0;
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.user).forEach(System.out::println);
        System.out.println();
        for (i = 0; i < top10Users.size(); i++) {
            if (i + 5 < top10Users.size()) {
                System.out.println("                                                              " + top10Users.get(i).getName() + "  |  " + top10Users.get(i + 1).getName() + "  |  " + top10Users.get(i + 2).getName() + "  |  " + top10Users.get(i + 3).getName() + "  |  " + top10Users.get(i + 4).getName());
                i += 4;
            } else {
                for (; i < top10Users.size(); i++) {
                    if (a == 0) {
                        System.out.print("                                                              " + top10Users.get(i).getName() + " | ");
                        a++;
                    } else {
                        System.out.print(top10Users.get(i).getName() + "  |  ");
                    }
                }
            }
        }
        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void top10Carrier(List<Carrier> top10Carriers) {
        int i, a = 0;
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrier).forEach(System.out::println); // substituir por carrier
        System.out.println();
        for (i = 0; i < top10Carriers.size(); i++) {
            if (i + 5 < top10Carriers.size()) {
                System.out.println("                                                                                    " + top10Carriers.get(i).getName() + "  |  " + top10Carriers.get(i + 1).getName() + "  |  " + top10Carriers.get(i + 2).getName() + "  |  " + top10Carriers.get(i + 3).getName() + "  |  " + top10Carriers.get(i + 4).getName());
                i += 4;
            } else {
                for (; i < top10Carriers.size(); i++) {
                    if (a == 0) {
                        System.out.print("                                                                                    " + top10Carriers.get(i).getName() + " | ");
                        a++;
                    } else {
                        System.out.print(top10Carriers.get(i).getName() + "  |  ");
                    }
                }
            }
        }

        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void orders(List<Order> orders) {
        int i, a = 0;
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.orderbaner).forEach(System.out::println); // substituir por carrier
        System.out.println();
        if (orders == null) {
            System.out.print(MenuOptions.noorders);
            System.out.println();
        } else {
            for (i = 0; i < orders.size(); i++) {
                if (i + 5 < orders.size()) {
                    System.out.println("                                                                                    " + orders.get(i).getOrderCode() + "  |  " + orders.get(i + 1).getOrderCode() + "  |  " + orders.get(i + 2).getOrderCode() + "  |  " + orders.get(i + 3).getOrderCode() + "  |  " + orders.get(i + 4).getOrderCode());
                    i += 4;
                } else {
                    for (; i < orders.size(); i++) {
                        if (a == 0) {
                            System.out.print("                                                                                    " + orders.get(i).getOrderCode() + "  |  ");
                            a++;
                        } else {
                            System.out.print(orders.get(i).getOrderCode() + "  |  ");
                        }
                    }
                }
            }
        }
        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void transportToRate(List<Transport> transports) {
        int i, a = 0;
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrier).forEach(System.out::println); // substituir por carrier
        System.out.println();
        if (transports == null) {
            System.out.print(MenuOptions.noorders);
            System.out.println();
        } else {
            for (i = 0; i < transports.size(); i++) {
                if (i + 5 < transports.size()) {
                    System.out.println("                                                                                    " + transports.get(i).getCode() + " " + transports.get(i).getName() + "  |  " + transports.get(i + 1).getCode() + " " + transports.get(i).getName() + "  |  " + transports.get(i + 2).getCode() + " " + transports.get(i).getName() + "  |  " + transports.get(i + 3).getCode() + " " + transports.get(i).getName() + "  |  " + transports.get(i + 4).getCode() + " " + transports.get(i).getName());
                    i += 4;
                } else {
                    for (; i < transports.size(); i++) {
                        if (a == 0) {
                            System.out.print("                                                                                    " + transports.get(i).getCode() + " " + transports.get(i).getName() + "  |  ");
                            a++;
                        } else {
                            System.out.print(transports.get(i).getCode() + " " + transports.get(i).getName() + "  |  ");
                        }
                    }
                }
            }
        }
        System.out.println();
        System.out.print(MenuOptions.ratetransport);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }


    public static void printproducts(AppState appState, double price) {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        IOClasse.printProducts(appState);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(MenuOptions.price + price);
        System.out.println(" ");
        System.out.print(MenuOptions.add);
        System.out.println(" ");
        System.out.print(MenuOptions.stop);
        System.out.println(" ");
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void menuCarrier() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrier).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrierOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void shopCarrier() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.shop).forEach(System.out::println);
        Arrays.stream(MenuOptions.shopOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void menuVolunteer() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.volunteer).forEach(System.out::println);
        Arrays.stream(MenuOptions.volunteerOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }


    public static void sign() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.welcome).forEach(System.out::println);
        Arrays.stream(MenuOptions.signOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void log() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.trazAqui).forEach(System.out::println);
        Arrays.stream(MenuOptions.LogMenuoptions).forEach(System.out::println);
        Arrays.stream(MenuOptions.car).forEach(System.out::println);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void firstMenu() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.menu).forEach(System.out::println);
        Arrays.stream(MenuOptions.firstMenuoptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void menuAdmin() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.admin).forEach(System.out::println);
        Arrays.stream(MenuOptions.adminOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void menuUser() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.user).forEach(System.out::println);
        Arrays.stream(MenuOptions.userOptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void choosetransporter() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrier).forEach(System.out::println); //change with carrier
        System.out.println();
        System.out.print(MenuOptions.noVolunteers);
        Arrays.stream(MenuOptions.deliveryoptions).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void choosestatus() {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.menu).forEach(System.out::println);
        System.out.println();
        Arrays.stream(MenuOptions.setstatus).forEach(System.out::println);
        System.out.print(MenuOptions.abort);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void allorders(int orders) {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.orderbaner).forEach(System.out::println); // substituir por order
        System.out.println();
        System.out.print(MenuOptions.deliveries + " : " + orders + " orders!");
        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void allordersandkms(double kms) {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.carrier).forEach(System.out::println); // substituir por order
        System.out.println();
        System.out.print(MenuOptions.kmsdone + " : " + kms + " KMs!");
        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void ordersstuck(int orders) {
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.orderbaner).forEach(System.out::println); // substituir por order
        System.out.println();
        System.out.print(MenuOptions.orderstuck + " : " + orders);
        System.out.println();
        System.out.print(MenuOptions.end);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }

    public static void ordersfortransport(List<Order> orders) {
        int i, a = 0;
        Terminal.clear();
        Arrays.stream(MenuOptions.top).forEach(System.out::println);
        Arrays.stream(MenuOptions.orderbaner).forEach(System.out::println);
        System.out.println();
        System.out.print(MenuOptions.newtransport);
        System.out.println();
        if (orders == null) {
            System.out.print(MenuOptions.noorders);
            System.out.println();
        } else {
            for (i = 0; i < orders.size(); i++) {
                if (i + 5 < orders.size()) {
                    System.out.println("                                                                                    " + orders.get(i).getOrderCode() + "  |  " + orders.get(i + 1).getOrderCode() + "  |  " + orders.get(i + 2).getOrderCode() + "  |  " + orders.get(i + 3).getOrderCode() + "  |  " + orders.get(i + 4).getOrderCode());
                    i += 4;
                } else {
                    for (; i < orders.size(); i++) {
                        if (a == 0) {
                            System.out.print("                                                                                    " + orders.get(i).getOrderCode() + "  |  ");
                            a++;
                        } else {
                            System.out.print(orders.get(i).getOrderCode() + "  |  ");
                        }
                    }
                }
            }
        }
        System.out.println();
        System.out.print(MenuOptions.orderstotransport);
        Arrays.stream(MenuOptions.bot).forEach(System.out::println);
        System.out.print(MenuOptions.option);
    }


    /**
     * User's login Interface.
     *
     * @param appState The appState.
     * @return User that logged on.
     */
    public static String loginUser(AppState appState) {
        Scanner in = new Scanner(System.in);
        String email;
        String password = null;
        boolean valid;
        Credentials credentialsToVerify;
        User userVerified = null;
        do {
            do {
                System.out.println("Insert User's email!");
                email = in.nextLine();
                if (email.equals("abort")) break;
                valid = Parser.isValidEmail(email);
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (email.equals("abort")) break;
            System.out.println("Insert User's password!");
            password = in.nextLine();
            if (password.equals("abort")) break;
            credentialsToVerify = new Credentials(email, password);
            userVerified = appState.getUser(Parser.verifyCredentials(credentialsToVerify, "user", appState));
            if (userVerified == null) {
                System.out.println("Invalid credentials!!!");
            }
        } while (userVerified == null);
        if (email.equals("abort") || password.equals("abort")) {
            return null;
        }
        System.out.println("Welcome back " + userVerified.getName() + "!!!");
        System.out.println();
        return userVerified.getUserCode();
    }

    /**
     * Volunteer's login Interface.
     *
     * @param appState The appState.
     * @return Volunteer that logged on.
     */
    public static String loginVolunteer(AppState appState) {
        Scanner in = new Scanner(System.in);
        String email;
        String password = null;
        boolean valid;
        Credentials credentialsToVerify;
        Transport transportVerified = null;
        do {
            do {
                System.out.println("Insert Volunteer's email!");
                email = in.nextLine();
                valid = Parser.isValidEmail(email);
                if (email.equals("abort")) break;
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (email.equals("abort")) break;
            System.out.println("Insert Volunteer's password!");
            password = in.nextLine();
            if (password.equals("abort")) break;
            credentialsToVerify = new Credentials(email, password);
            transportVerified = appState.getTransport(Parser.verifyCredentials(credentialsToVerify, "volunteer", appState));
            if (transportVerified == null) {
                System.out.println("Invalid credentials!!!");
            }
        } while (transportVerified == null);
        if (email.equals("abort") || password.equals("abort")) {
            return null;
        }
        System.out.println("Welcome back " + transportVerified.getName() + "!!!");
        System.out.println();
        return transportVerified.getCode();
    }

    /**
     * Carrier's login Interface.
     *
     * @param appState The appState.
     * @return Carrier that logged on.
     */
    public static String loginCarrier(AppState appState) {
        Scanner in = new Scanner(System.in);
        String email;
        String password = null;
        boolean valid;
        Credentials credentialsToVerify;
        Transport transportVerified = null;
        do {
            do {
                System.out.println("Insert Carrier's email!");
                email = in.nextLine();
                if (email.equals("abort")) break;
                valid = Parser.isValidEmail(email);
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (email.equals("abort")) break;
            System.out.println("Insert Carrier's password!");
            password = in.nextLine();
            if (password.equals("abort")) break;
            credentialsToVerify = new Credentials(email, password);
            transportVerified = appState.getTransport(Parser.verifyCredentials(credentialsToVerify, "carrier", appState));
            if (transportVerified == null) {
                System.out.println("Invalid credentials!!!");
            }
        } while (transportVerified == null);
        if (email.equals("abort") || password.equals("abort")) {
            return null;
        }
        System.out.println("Welcome back " + transportVerified.getName() + "!!!");
        return transportVerified.getCode();
    }

    public static String loginShop(AppState appState) {
        Scanner in = new Scanner(System.in);
        String email;
        String password = null;
        boolean valid;
        Credentials credentialsToVerify;
        Shop shopVerified = null;
        do {
            do {
                System.out.println("Insert Shop's email!");
                email = in.nextLine();
                if (email.equals("abort")) break;
                valid = Parser.isValidEmail(email);
                if (!valid) {
                    System.out.println("Invalid input!!!");
                }
            } while (!valid);
            if (email.equals("abort")) break;
            System.out.println("Insert Shop's password!");
            password = in.nextLine();
            if (password.equals("abort")) break;
            credentialsToVerify = new Credentials(email, password);
            shopVerified = appState.getShop(Parser.verifyCredentials(credentialsToVerify, "shop", appState));
            if (shopVerified == null) {
                System.out.println("Invalid credentials!!!");
            }
        } while (shopVerified == null);
        if (email.equals("abort") || password.equals("abort")) {
            return null;
        }
        System.out.println("Welcome back " + shopVerified.getName() + "!!!");
        System.out.println();
        return shopVerified.getShopCode();
    }

    /**
     * Admin's login Interface.
     */
    public static void loginAdmin() {
        Scanner in = new Scanner(System.in);
        String test;
        boolean exit, valid;
        do {
            exit = false;
            do {
                System.out.println("Insert Admin's username!");
                test = in.nextLine();
                valid = test.equals("admin");
                if (!valid) {
                    System.out.println("Wrong input!!!");
                }
            } while (!valid);
            System.out.println("Insert Admin's password!");
            test = in.nextLine();
            if (test.equals("admin")) exit = true;
            if (!exit) System.out.println("Wrong input!!!");
        } while (!exit);
    }


    /**
     * Inserts a new product.
     *
     * @param appState The appState.
     * @return The product to insert.
     */
    public Product insertProduct(AppState appState) {
        Scanner in = new Scanner(System.in);
        boolean valid;
        String productName;
        do {
            System.out.println("Insert a Product name!");
            productName = in.nextLine();
            if (productName.equals("abort")) break;
            valid = Parser.verifyProduct(productName, appState);
            if (!valid) System.out.println("Invalid input!!!");
        } while (!valid);
        if (productName.equals("abort")) return null;
        return appState.getProduct(productName);
    }

    /**
     * Inserts a list of products.
     *
     * @param appState The appState.
     * @return List of products to insert.
     */
    public List<Product> insertListOfProducts(AppState appState) {
        Scanner in = new Scanner(System.in);
        String menuItem;
        boolean aborted = false;
        boolean quit = false;
        double price = 0.0;
        Product product;
        List<Product> productList = new ArrayList<>();
        do {
            printproducts(appState, price);
            menuItem = in.nextLine();
            switch (menuItem) {
                case "add":
                    product = insertProduct(appState);
                    if (product != null) {
                        price += product.getPrice();
                        productList.add(product);
                    }
                    break;
                case "exit":
                    if (price == 0) aborted = true;
                    quit = true;
                    break;

                case "abort":
                    aborted = true;
                    quit = true;
                    break;
                default:
                    System.out.println("1-Invalid input!!!");
                    break;
            }
        } while (!quit);
        if (aborted) return null;
        return productList;
    }
}
