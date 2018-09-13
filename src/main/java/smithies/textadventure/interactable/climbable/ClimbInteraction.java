package smithies.textadventure.interactable.climbable;

import smithies.textadventure.command.Noun;

public class ClimbInteraction {

    private boolean onClimbable;
    private Climbable climbable;

    public void climbUp(Climbable climbable) {
        this.onClimbable = true;
        this.climbable = climbable;
    }

    public void climbDown() {
        this.onClimbable = false;
        this.climbable = null;
    }

    public boolean inOnAnyClimbable() {
        return onClimbable;
    }

    public boolean inOnClimbable(Noun noun) {
        return inOnAnyClimbable() && climbable.getName().equals(noun);
    }

    public Noun getClimbableName() {
        return this.climbable.getName();
    }
}
