package smithies.textadventure.rooms;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.rooms.partition.Deadend;
import smithies.textadventure.rooms.partition.RoomPartition;

public class RoomBuilder {

    private Room room;

    public RoomBuilder(RoomName roomName, boolean forbiddenRoom) {
        room = new Room(roomName, forbiddenRoom);
        Directions.ALL_DIRECTIONS.forEach(direction -> {
            room.addRoom(direction, RoomName.DEADEND, new Deadend());
        });
    }

    public RoomBuilder addNorthRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.NORTH, room, partition);
    }

    public RoomBuilder addEastRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.EAST, room, partition);
    }

    public RoomBuilder addSouthRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.SOUTH, room, partition);
    }

    public RoomBuilder addWestRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.WEST, room, partition);
    }

    public RoomBuilder addNorthEastRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.NORTH_EAST, room, partition);
    }

    public RoomBuilder addNorthWestRoom(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.NORTH_WEST, room, partition);
    }

    public RoomBuilder addSouthEast(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.SOUTH_EAST, room, partition);
    }

    public RoomBuilder addSouthWest(RoomName room, RoomPartition partition) {
        return goDirection(Adverb.SOUTH_WEST, room, partition);
    }

    private RoomBuilder goDirection(Adverb adverb, RoomName room, RoomPartition partition) {
        this.room.addRoom(adverb, room, partition);
        return this;
    }

    public Room build() {
        return this.room;
    }
}
