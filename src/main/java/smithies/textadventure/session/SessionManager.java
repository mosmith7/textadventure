package smithies.textadventure.session;

import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser = new UserTextInputParser();

    private AllRooms allRooms = new AllRooms();
    private Player player;

    public void startGame() {
        displayStartingMessage();

        player = new Player(allRooms.get(RoomName.HALL));
        player.enterRoom();
        while(true) {
            String input = inputRetriever.getLine();
            UserInputCommand command = inputParser.parseString(input);
            processCommand(command);
        }
    }

    private void displayStartingMessage() {
        System.out.println("You wake up on the floor. It is Christmas Day.");
    }

    private void processCommand(UserInputCommand command) {
        if (UserInputCommand.EXIT.equals(command)) {
            System.exit(0);
        } else if (UserInputCommand.WAIT.equals(command)) {
            System.out.println("You scratch your ears with your leg");
        } else if (UserInputCommand.SOUTH.equals(command)) {
            RoomName roomName = player.goSouth();
            player.setCurrentRoom(allRooms.get(roomName));
            player.enterRoom();
        }
    }
}
