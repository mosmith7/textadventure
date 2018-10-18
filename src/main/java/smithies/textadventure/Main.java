package smithies.textadventure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import smithies.textadventure.session.SessionManager;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
            SessionManager sessionManager = new SessionManager();
            sessionManager.startGame();
    }
}
