package smithies.textadventure.session;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.item.Item;
import smithies.textadventure.item.ItemName;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Optional;

public class Player {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Room currentRoom;
    private Inventory inventory = new Inventory(1);

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void viewInventory() {
        inventory.view();
    }

    public boolean isInventoryFull() {
        return inventory.isFull();
    }

    public void enterRoom() {
        this.currentRoom.enter();
    }

    public RoomName goNorth() {
        return this.currentRoom.goNorth();
    }

    public RoomName goSouth() {
        return this.currentRoom.goSouth();
    }

    public RoomName goEast() {
        return this.currentRoom.goEast();
    }

    public RoomName goWest() {
        return this.currentRoom.goWest();
    }

    public void look() {
        currentRoom.displayFullDescription();
    }

    public boolean canTakeItem(ItemName itemName) {
        return currentRoom.hasItem(itemName);
    }

    public void takeItem(ItemName itemName) {
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
        } else {
            output.displayTextLine("You can't find anything of interest.");
        }
    }

    private String onFloorDescription(Item item) {
        return "On the floor is a " + item.getName();
    }
}
