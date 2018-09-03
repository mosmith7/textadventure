package smithies.textadventure.session;

import smithies.textadventure.item.Inventory;
import smithies.textadventure.item.Item;
import smithies.textadventure.item.ItemName;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.Optional;

public class Player {

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

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
