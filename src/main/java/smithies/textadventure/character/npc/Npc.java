package smithies.textadventure.character.npc;

import smithies.textadventure.character.GameCharacter;

public interface Npc extends GameCharacter {

    String getName();

    String[] getDescriptionWhenInSameRoom();

    void takeTurn();
}
