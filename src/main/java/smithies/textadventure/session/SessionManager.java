package smithies.textadventure.session;

import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.character.Player;
import smithies.textadventure.character.npc.HumanCharacter;
import smithies.textadventure.character.npc.Misty;
import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.CommandHandler;
import smithies.textadventure.command.Noun;
import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.command.state.ViewInventory;
import smithies.textadventure.interactable.searchable.*;
import smithies.textadventure.item.*;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;
import smithies.textadventure.ui.UserTextInputParser;
import smithies.textadventure.ui.UserTextInputRetriever;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionManager {

    private UserTextInputRetriever inputRetriever = new UserTextInputRetriever();
    private UserTextInputParser inputParser;
    private CommandHandler commandHandler = new CommandHandler();
    private DisplayOutput output = new DisplayConsoleOutput();

    private DungeonMap map;
    private Player player;
    private List<Npc> npcs = new ArrayList<>();

    private List<DogToy> collectables;
    private Interactable victoryLocation;
    private boolean victoryReached = false;

    public SessionManager() {
        map = new DungeonMap();
        inputParser = new UserTextInputParser(map);
    }

    public void startGame() {
        collectables = new ArrayList<>();
        displayStartingMessage();
        distributeItems();
        initialiseSearchables();

        player = new Player(map.get(RoomName.HALL_SOUTH));
        initialiseNpcs(player);

        player.enterRoom();
        while(!victoryReached) {
            displayVictoryLocationItems();
            checkVictoryCondition();
            String input = inputRetriever.getLine();
            UserInputCommand userCommand = inputParser.parseString(input);
            userCommand.toGameCommand(player, map).ifPresent(gameCommand -> {
                player.setCurrentState(gameCommand);
                commandHandler.setCurrentState(gameCommand);
                commandHandler.processCommand();
            });
            doNpcTurns();
            if (!(commandHandler.getCurrentState() instanceof ViewInventory)) {
                // TODO: should other commands prevent this being displayed?
                displayNpcsInSameRoom();
                npcsCheckPlayerIsInForbiddenRoom();
            }
        }

        displayVictoryMessage();
    }

    private void displayStartingMessage() {
        output.displayTextLine("You wake up on the floor. It is Christmas Day.");
    }

    private void distributeItems() {
        Room kitchenNorth = map.get(RoomName.KITCHEN_NORTH);
        TennisBall tennisBall = new TennisBall();
        kitchenNorth.addItem(tennisBall, String.format("Just sitting there on the middle of the floor, unguarded, there is a %s", tennisBall.getName()));
        collectables.add(tennisBall);
    }

    private void initialiseSearchables() {
        Sideboard shelf = new Sideboard("Against the wall is a " + Noun.SIDEBOARD);
        Kong kong = new Kong();
        hideItemInSearchable(RoomName.HALL_SOUTH, shelf, kong, Adverb.UNDER);
        collectables.add(kong);

        DogBed dogBed = new DogBed("Against the wall is a nice soft bed. Your bed.");
        BallAndRope ballAndRope = new BallAndRope();
        hideItemInSearchable(RoomName.KITCHEN_SOUTH, dogBed, ballAndRope, Adverb.UNDER);
        collectables.add(ballAndRope);

        Piano piano = new Piano("Against the north wall is a " + Noun.PIANO);
        addSearchableToRoom(RoomName.STUDY, piano);

        Desk desk = new Desk("Against the south wall is a " + Noun.DESK);
        addSearchableToRoom(RoomName.STUDY, desk);

        victoryLocation = dogBed;
    }

    private void addSearchableToRoom(RoomName roomName, Interactable searchable) {
        Room room = map.get(roomName);
        room.addSearchable(searchable);
    }

    private void hideItemInSearchable(RoomName roomName, Interactable searchable, Item item, Adverb adverb) {
        searchable.addItem(item, adverb);
        Room room = map.get(roomName);
        room.addSearchable(searchable);
    }

    private void initialiseNpcs(BaseCharacter player) {
        npcs.add(new Misty(map, map.get(RoomName.LIVING_ROOM)));
        HumanCharacter mark = new HumanCharacter("Mark", "Your favourite biped", map, map.get(RoomName.BEDROOM_ONE), 7, 6);
        npcs.add(mark);
        HumanCharacter maria = new HumanCharacter("Maria", "Your favourite female biped", map, map.get(RoomName.BEDROOM_TWO), 8, 5);
        npcs.add(maria);
        HumanCharacter alan = new HumanCharacter("Alan", "The one you're unsure of", map, map.get(RoomName.BEDROOM_THREE), 2, 1);
        npcs.add(alan);
        HumanCharacter susan = new HumanCharacter("Susan", "The one that feeds you", map, map.get(RoomName.BEDROOM_FOUR), 6, 3);
        npcs.add(susan);
        HumanCharacter steve = new HumanCharacter("Steve", "The one that stays up all night", map, map.get(RoomName.BEDROOM_THREE), 6, 2);
        npcs.add(steve);

        npcs.forEach(npc -> {
            npc.setOtherNpcs(npcs);
            npc.setPlayer(player);
        });
    }

    private void doNpcTurns() {
        npcs.forEach(Npc::takeTurn);
    }

    private void npcsCheckPlayerIsInForbiddenRoom() {
        if (player.getCurrentRoom().isForbiddenRoom()) {
            getNpcsInSameRoom()
                    .stream()
                    .filter(npc -> !(npc instanceof Misty))
                    .forEach(npc -> {
                        output.displayTextLines(npc.getNameForSasha() + " is glaring at you and says something in a stern voice.",
                                "You feel the urge to go back downstairs very quickly");
                        player.setCurrentRoom(map.get(RoomName.HALL_SOUTH));
                        player.enterRoom();
                    });
        }
    }

    private void displayNpcsInSameRoom() {
        getNpcsInSameRoom().forEach(npc -> {
            output.displayTextLines(npc.getDescriptionWhenInSameRoom());
        });
    }

    private List<Npc> getNpcsInSameRoom() {
        return this.npcs.stream().filter(npc -> player.getCurrentRoom().equals(npc.getCurrentRoom()))
                .collect(Collectors.toList());
    }

    private void checkVictoryCondition() {
        this.victoryReached = collectablesMatchItems(victoryLocation.peek());
    }

    private boolean collectablesMatchItems(List<Noun> items) {
        if (collectables.size() == items.size()) {
            for (DogToy collectable :  collectables) {
                if (!items.contains(collectable.getName())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void displayVictoryLocationItems() {
        if (player.getCurrentRoom().getSearchable(victoryLocation.getName()).isPresent()) {
            victoryLocation.displayItemDescription(Adverb.ON);
        }
    }

    private void displayVictoryMessage() {
        output.displayTextLine("Congratulations! You win! You collected all of your toys!");
    }

}
