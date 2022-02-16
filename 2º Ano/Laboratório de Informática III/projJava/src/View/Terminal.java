package View;


public class Terminal {

    public static final int WINDOW_WIDTH = 208;
    public static final int TEXT_MAX_WIDTH = 90;

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void error(String error) {
        System.out.println(Colors.ANSI_RED + "\nErro: " + Colors.ANSI_RESET + error);
    }

    public static void info(String info) {
        System.out.println(Colors.ANSI_BLUE + "\nInfo: " + Colors.ANSI_RESET + info);
    }
}