package pt.ph;

import java.io.IOException;

/**
 * Main class.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        String fileLocation = "init.txt";
        AppState appState;
        int option;

        //appState = appState.readObject("app_state.txt");

        appState = Parser.readInitFile(fileLocation);
        appState.updateSystem();
        appState.setAllCodes();
        appState.initRating();


        do {
            option = View.viewLoginScreen();
            switch (option) {
                case (1):
                    option = View.viewChoseLogin(appState);
                    break;

                case (2):
                    option = View.viewLoginRegister(appState);
                    break;

                case(3):
                    fileLocation = IOClasse.getPath();
                    if(!fileLocation.equals("abort"))
                    appState = Parser.readFile(fileLocation);
                    break;

                case (0):
                    break;

                default:
                    System.out.println("Invalide input");
                    break;
            }
        } while (option != 0);

        appState.writeObject("app_state.txt");


    }
}
