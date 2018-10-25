package smithies.textadventure.character;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.rooms.partition.RoomPartition;

import java.util.Optional;

public interface GameCharacter {
    Optional<RoomName> goToRoomInDirection(Adverb direction);

    RoomName getRoomInDirection(Adverb direction);

    RoomPartition getPartitionInDirection(Adverb direction);

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
