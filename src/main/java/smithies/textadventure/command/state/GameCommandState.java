package smithies.textadventure.command.state;

import smithies.textadventure.character.CharacterState;

public interface GameCommandState extends CharacterState {

    void run();
}
