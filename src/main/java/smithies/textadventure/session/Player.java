package smithies.textadventure.session;

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

public class Player {

    private Room currentRoom;
    private Inventory inventory = new Inventory(1);
    private ClimbInteraction climbInteraction = new ClimbInteraction();
    private DisplayOutput output = new DisplayConsoleOutput();

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void viewInventory() {
        inventory.view();
    }

    public boolean isInventoryFull() {
        return inventory.isFull();
    }

    public boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }

    public Optional<Noun> inventoryPeek() {
        return inventory.peek();
    }

    public void enterRoom() {
        this.currentRoom.enter();
    }

    public RoomName goNorth() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goNorth();
    }

    public RoomName goSouth() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goSouth();
    }

    public RoomName goEast() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goEast();
    }

    public RoomName goWest() {
        if (isOnAnyClimbable()) climbDown();
        return this.currentRoom.goWest();
    }

    public void look() {
        currentRoom.displayFullDescription();
        displayOnClimbableDescription();
    }

    public boolean canTakeItem(Noun itemName) {
        return currentRoom.hasItem(itemName);
    }

    public void takeItem(Noun itemName) {
        currentRoom.takeItem(this, itemName).ifPresent(item -> {
            if (inventory.isFull()) {
                currentRoom.addItem(item, "");
            } else {
                inventory.addItem(item);
            }
        });
    }

    public void dropItem(Noun itemName) {
        inventory.removeItem(itemName).ifPresent(item -> {
            currentRoom.addItem(item, onFloorDescription(item));
        });

    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void search(Noun name, Adverb adverb) {
        Optional<Item> optionalItem = getCurrentRoom().search(name, adverb);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (!isInventoryFull()) {
                inventory.addItem(item);
            } else {
                currentRoom.addItem(item, onFloorDescription(item));
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

    private String onFloorDescription(Item item) {
        return "On the floor is a " + item.getName();
    }
}
