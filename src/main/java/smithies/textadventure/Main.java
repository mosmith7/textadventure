package smithies.textadventure;

import java.util.Random;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import smithies.textadventure.session.Player;
import smithies.textadventure.session.SessionManager;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random random = new Random();

        boolean exitGame = false;

        GAME:
        while (!exitGame) {

            System.out.println("Welcome to the game");

            SessionManager sessionManager = new SessionManager();
            sessionManager.startGame();
        }

    }
}
