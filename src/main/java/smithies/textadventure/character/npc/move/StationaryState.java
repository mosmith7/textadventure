package smithies.textadventure.character.npc.move;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.Npc;

public class StationaryState implements MoveState {

    private static final Logger LOG = LoggerFactory.getLogger(MoveRandomDirection.class);

    private Npc npc;

    public StationaryState(Npc npc) {
        this.npc = npc;
    }

    @Override
    public void move() {
        LOG.debug("{} is in: {}", npc.getName(), npc.getCurrentRoom().getName());
    }
}
