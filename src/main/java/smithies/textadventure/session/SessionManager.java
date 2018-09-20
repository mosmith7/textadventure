package smithies.textadventure.session;

import smithies.textadventure.character.Player;
import smithies.textadventure.character.npc.Misty;
import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.*;
import smithies.textadventure.command.state.ViewInventory;
import smithies.textadventure.item.Item;
import smithies.textadventure.item.TennisBall;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.interactable.searchable.DogBed;
import smithies.textadventure.interactable.searchable.Interactable;
import smithies.textadventure.interactable.searchable.Sideboard;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser = new UserTextInputParser();
    private CommandHandler commandHandler = new CommandHandler();
    private DisplayOutput output = new DisplayConsoleOutput();

    private DungeonMap map;
    private Player player;
    private List<Npc> npcs = new ArrayList<>();

    public SessionManager() {
        map = new DungeonMap();
    }

    public void startGame() {
        displayStartingMessage();
        distributeItems();
        initialiseSearchables();
        initialiseNpcs();

        player = new Player(map.get(RoomName.HALL_SOUTH));
        player.enterRoom();
        while(true) {
            String input = inputRetriever.getLine();
            UserInputCommand userCommand = inputParser.parseString(input);
            userCommand.toGameCommand(player, map).ifPresent(gameCommand -> {
                commandHandler.setCurrentState(gameCommand);
                commandHandler.processCommand();
            });
            doNpcTurns();
            if (!(commandHandler.getCurrentState() instanceof ViewInventory)) {
                // TODO: should other commands prevent this being displayed?
                displayNpcsInSameRoom();
            }
        }
    }

    private void displayStartingMessage() {
        output.displayTextLine("You wake up on the floor. It is Christmas Day.");
    }

    private void distributeItems() {
        Room kitchenNorth = map.get(RoomName.KITCHEN_NORTH);
        TennisBall tennisBall = new TennisBall();
        kitchenNorth.addItem(tennisBall, String.format("Just sitting there on the middle of the floor, unguarded, there is a %s", tennisBall.getName()));
    }

    private void initialiseSearchables() {
        Sideboard shelf = new Sideboard("Against the wall is a " + Noun.SIDEBOARD);
        hideItemInSearchable(RoomName.HALL_SOUTH, shelf, new TennisBall(), Adverb.UNDER);

        DogBed dogBed = new DogBed("Against the wall is a nice soft bed. Your bed.");
        hideItemInSearchable(RoomName.KITCHEN_SOUTH, dogBed, new TennisBall(), Adverb.UNDER);
    }

    private void hideItemInSearchable(RoomName roomName, Interactable searchable, Item item, Adverb adverb) {
        searchable.addItem(item, adverb);
        Room room = map.get(roomName);
        room.addSearchable(searchable);
    }

    private void initialiseNpcs() {
        Misty misty = new Misty(map, map.get(RoomName.LIVING_ROOM));
        npcs.add(misty);
    }

    private void doNpcTurns() {
        npcs.forEach(Npc::takeTurn);
    }

    private void displayNpcsInSameRoom() {
        npcs.forEach(npc -> {
            if (player.getCurrentRoom().equals(npc.getCurrentRoom())) {
                output.displayTextLines(npc.getDescriptionWhenInSameRoom());
            }
        });
    }

}
