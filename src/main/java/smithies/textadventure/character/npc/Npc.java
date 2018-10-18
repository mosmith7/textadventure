package smithies.textadventure.character.npc;

import smithies.textadventure.character.GameCharacter;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.RoomName;

public interface Npc extends GameCharacter {

    String getName();

    String[] getDescriptionWhenInSameRoom();

    void takeTurn();

    default boolean canOpenDoors() {
        return true;
    }

    default boolean isAllowedEverywhere() {
        return true;
    }

    void openDoorAndMoveThrough(Adverb direction);

    void openDoor(Adverb direction);

}
