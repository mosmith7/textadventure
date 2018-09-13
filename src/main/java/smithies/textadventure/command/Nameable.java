package smithies.textadventure.command;

public abstract class Nameable implements Named {

    private Noun name;

    public Nameable(Noun name) {
        this.name = name;
    }

    public Noun getName() {
        return name;
    }
}
