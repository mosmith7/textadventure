package smithies.textadventure.session;

import smithies.textadventure.command.CommandInterpretter;
import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser = new UserTextInputParser();
    private CommandInterpretter commandInterpretter = new CommandInterpretter();

    private AllRooms allRooms = new AllRooms();
    private Player player;

    public void startGame() {
        displayStartingMessage();

        player = new Player(allRooms.get(RoomName.HALL_SOUTH));
        player.enterRoom();
        while(true) {
            String input = inputRetriever.getLine();
            UserInputCommand command = inputParser.parseString(input);
            commandInterpretter.processCommand(player, allRooms, command);
        }
    }

    private void displayStartingMessage() {
        System.out.println("You wake up on the floor. It is Christmas Day.");
    }

}
