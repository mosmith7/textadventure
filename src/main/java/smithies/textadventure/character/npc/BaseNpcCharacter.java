package smithies.textadventure.character.npc;

import org.springframework.beans.factory.annotation.Autowired;
import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.List;

public abstract class BaseNpcCharacter extends BaseCharacter implements Npc {

    protected final DungeonMap map;

    protected MoveState currentMoveState;
    protected int moveStateLockedForTurns = 0;
    protected List<Npc> otherNpcs;

    @Autowired
    public BaseNpcCharacter(DungeonMap map) {
        this.map = map;
    }

    @Override
    public void setOtherNpcs(List<Npc> otherNpcs) {
        this.otherNpcs = otherNpcs;
    }

    @Override
    public void openDoorAndMoveThrough(Adverb direction) {
        RoomName room = getRoomInDirection(direction);
        if (!RoomName.DEADEND.equals(room)) {
            Room startingRoom = this.currentRoom;
            moveToRoom(room);
            Room endingRoom = this.currentRoom;
            map.openDoor(startingRoom, endingRoom);
        }
    }

    @Override
    public void openDoor(Adverb direction) {
        // Move to room and back so door can be updated both sides.
        Room startingRoom = this.currentRoom;
        openDoorAndMoveThrough(direction);
        moveToRoom(startingRoom.getName());
    }

    private void moveToRoom(RoomName roomName) {
        this.currentRoom = this.map.get(roomName);
    }

    @Override
    public void setCurrentMoveState(MoveState moveState, int numberOfTurns) {
        this.currentMoveState = moveState;
        this.moveStateLockedForTurns = numberOfTurns;
    }
}
