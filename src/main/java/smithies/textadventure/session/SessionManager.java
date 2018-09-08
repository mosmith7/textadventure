package smithies.textadventure.session;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.CommandHandler;
import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.item.Item;
import smithies.textadventure.item.TennisBall;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.searchable.DogBed;
import smithies.textadventure.searchable.Searchable;
import smithies.textadventure.searchable.WoodenShelf;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser = new UserTextInputParser();
    private CommandHandler commandHandler = new CommandHandler();
    private DisplayOutput output = new DisplayConsoleOutput();

    private AllRooms allRooms;
    private Player player;

    public void startGame() {
        displayStartingMessage();
        allRooms = new AllRooms();
        distributeItems();
        initialiseSearchables();

        player = new Player(allRooms.get(RoomName.HALL_SOUTH));
        player.enterRoom();
        while(true) {
            String input = inputRetriever.getLine();
            UserInputCommand command = inputParser.parseString(input);
            commandHandler.processCommand(player, allRooms, command.toGameCommand());
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

    private void initialiseSearchables() {
        WoodenShelf shelf = new WoodenShelf("Against the wall is a wooden shelf.");
        hideItemInSearchable(RoomName.HALL_SOUTH, shelf, new TennisBall(), Adverb.UNDER);

        DogBed dogBed = new DogBed("Against the wall is a nice soft bed. Your bed.");
        hideItemInSearchable(RoomName.KITCHEN_SOUTH, dogBed, new TennisBall(), Adverb.UNDER);
    }

    private void hideItemInSearchable(RoomName roomName, Searchable searchable, Item item, Adverb adverb) {
        searchable.addItem(item, adverb);
        Room room = allRooms.get(roomName);
        room.addSearchable(searchable);
    }

}
