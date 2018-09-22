package smithies.textadventure.rooms;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.rooms.door.Deadend;
import smithies.textadventure.rooms.door.RoomPartition;

public class RoomBuilder {

    private Room room;

    public RoomBuilder(RoomName roomName, boolean forbiddenRoom) {
        room = new Room(roomName, forbiddenRoom);
        Directions.ALL_DIRECTIONS.forEach(direction -> {
            room.addRoom(direction, RoomName.DEADEND, new Deadend());
        });
    }

    public RoomBuilder addNorthRoom(RoomName room, RoomPartition partition) {
        this.room.addRoom(Adverb.NORTH, room, partition);
        return this;
    }

    public RoomBuilder addEastRoom(RoomName room, RoomPartition partition) {
        this.room.addRoom(Adverb.EAST, room, partition);
        return this;
    }

    public RoomBuilder addSouthRoom(RoomName room, RoomPartition partition) {
        this.room.addRoom(Adverb.SOUTH, room, partition);
        return this;
    }

    public RoomBuilder addWestRoom(RoomName room, RoomPartition partition) {
        this.room.addRoom(Adverb.WEST, room, partition);
        return this;
    }

    public Room build() {
        return this.room;
    }
}
