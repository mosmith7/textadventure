package smithies.textadventure.character.npc;

import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.character.GameCharacter;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.RoomName;

import java.util.List;

public interface Npc extends GameCharacter {

    String getName();

    String getNameForSasha();

    String[] getDescriptionWhenInSameRoom();

    void takeTurn();

    default boolean canOpenDoors() {
        return true;
    }

    default boolean isAllowedEverywhere() {
        return true;
    }

    void setOtherNpcs(List<Npc> otherNpcs);

    void setPlayer(BaseCharacter player);

    void openDoorAndMoveThrough(Adverb direction);

    void openDoor(Adverb direction);

    void setCurrentMoveState(MoveState moveState, int numberOfTurns);

}
