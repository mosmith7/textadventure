package smithies.textadventure.character;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.Optional;

public interface GameCharacter {
    Optional<RoomName> getRoomInDirection(Adverb direction);

    void viewInventory();

    boolean isInventoryFull();

    boolean isInventoryEmpty();

    Optional<Noun> inventoryPeek();

    boolean canTakeItem(Noun itemName);

    void takeItem(Noun itemName);

    void dropItem(Noun itemName);

    void setCurrentRoom(Room currentRoom);

    Room getCurrentRoom();
}
