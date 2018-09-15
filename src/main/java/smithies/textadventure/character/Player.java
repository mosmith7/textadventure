package smithies.textadventure.character;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.interactable.climbable.ClimbInteraction;
import smithies.textadventure.interactable.climbable.ClimbResult;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.item.Item;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.interactable.searchable.Interactable;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Optional;

public class Player extends BaseCharacter {

    private ClimbInteraction climbInteraction = new ClimbInteraction();
    private DisplayOutput output = new DisplayConsoleOutput();

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(1);
    }

    public void enterRoom() {
        this.currentRoom.enter();
    }

    @Override
    public RoomName goNorth() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goNorth();
    }

    @Override
    public RoomName goSouth() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goSouth();
    }

    @Override
    public RoomName goEast() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goEast();
    }

    @Override
    public RoomName goWest() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goWest();
    }

    public void look() {
        currentRoom.displayFullDescription();
        displayOnClimbableDescription();
    }

    public void search(Noun name, Adverb adverb) {
        Optional<Item> optionalItem = getCurrentRoom().search(name, adverb);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (!isInventoryFull()) {
                inventory.addItem(item);
            } else {
                currentRoom.addItemToFloor(item);
            }
        }
    }

    public void climbUp(Noun name) {
        if (this.climbInteraction.isOnAnyClimbable()) {
            output.displayTextLine("You can't climb again, you are already on a " + this.climbInteraction.getClimbableName());
            return;
        }

        Optional<Interactable> optionalSearchable = getCurrentRoom().getSearchable(name);
        if (optionalSearchable.isPresent()) {
            Interactable searchable = optionalSearchable.get();
            ClimbResult climbResult = searchable.attemptClimb();
            if (climbResult.isSuccess()) {
                this.climbInteraction.climbUp(searchable);
            } else {
                this.climbInteraction.climbDown();
            }
            output.displayTextLine(climbResult.getResultMessage());
        } else {
            output.displayTextLine("The " + name + " isn't in this room");
        }
    }

    public void climbDown(Noun name) {
        if (!this.climbInteraction.inOnClimbable(name)) {
            output.displayTextLine("You aren't on the " + name + ". So how could you climb down from it?");
            return;
        }

        climbDown();
    }

    public void climbDown() {
        if (isOnAnyClimbable()) {
            this.climbInteraction.climbDown();
            output.displayTextLine("You climb down");
        } else {
            output.displayTextLine("You aren't on anything to climb down from");
        }
    }

    public boolean isOnAnyClimbable() {
        return this.climbInteraction.isOnAnyClimbable();
    }

    private void displayOnClimbableDescription() {
        if (this.climbInteraction.isOnAnyClimbable()) {
            output.displayTextLine("You are on top of a " + this.climbInteraction.getClimbableName());
        }
    }
}
