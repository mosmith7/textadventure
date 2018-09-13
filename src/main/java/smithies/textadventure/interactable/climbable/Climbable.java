package smithies.textadventure.interactable.climbable;

import smithies.textadventure.command.Named;

public interface Climbable extends Named {

    default ClimbResult attemptClimb() {
        return new ClimbResult(false, "This cannot be climbed.");
    }

    void displayClimbUpSuccess();

    void displayClimbDownSuccess();
}
