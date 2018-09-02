package smithies.textadventure;

import smithies.textadventure.session.SessionManager;

public class Main {

    public static void main(String[] args) {
            SessionManager sessionManager = new SessionManager();
            sessionManager.startGame();
    }
}
