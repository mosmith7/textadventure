package smithies.textadventure.session;

import smithies.textadventure.command.CommandInterpretter;
import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.item.TennisBall;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser = new UserTextInputParser();
    private CommandInterpretter commandInterpretter = new CommandInterpretter();
    private DisplayOutput output = new DisplayConsoleOutput();

    private AllRooms allRooms;
    private Player player;

    public void startGame() {
        displayStartingMessage();
        allRooms = new AllRooms();
        distributeItems();

        player = new Player(allRooms.get(RoomName.HALL_SOUTH));
        player.enterRoom();
        while(true) {
            String input = inputRetriever.getLine();
            UserInputCommand command = inputParser.parseString(input);
            commandInterpretter.processCommand(player, allRooms, command);
        }
    }

    private void displayStartingMessage() {
        output.displayTextLine("You wake up on the floor. It is Christmas Day.");
    }

    private void distributeItems() {
        Room kitchenNorth = allRooms.get(RoomName.KITCHEN_NORTH);
        TennisBall tennisBall = new TennisBall();
        kitchenNorth.addItem(tennisBall, String.format("Just sitting there on the middle of the floor, unguarded, there is a %s", tennisBall.getName()));
    }

}
