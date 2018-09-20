package smithies.textadventure.character;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.Optional;

public abstract class BaseCharacter implements GameCharacter {

    protected Room currentRoom;
    protected Inventory inventory;

    @Override
    public Optional<RoomName> goDirection(Adverb direction) {
        return this.currentRoom.goDirection(direction);
    }

    @Override
    public void viewInventory() {
        inventory.view();
    }

    @Override
    public boolean isInventoryFull() {
        return inventory.isFull();
    }

    @Override
    public boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public Optional<Noun> inventoryPeek() {
        return inventory.peek();
    }

    @Override
    public boolean canTakeItem(Noun itemName) {
        return currentRoom.hasItem(itemName);
    }

    @Override
    public void takeItem(Noun itemName) {
        currentRoom.takeItem(this, itemName).ifPresent(item -> {
            if (inventory.isFull()) {
                currentRoom.addItem(item, "");
            } else {
                inventory.addItem(item);
            }
        });
    }

    @Override
    public void dropItem(Noun itemName) {
        inventory.removeItem(itemName).ifPresent(item -> {
            currentRoom.addItemToFloor(item);
        });
    }

    @Override
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public Room getCurrentRoom() {
        return currentRoom;
    }
}
