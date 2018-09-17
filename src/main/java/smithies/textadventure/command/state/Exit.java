package smithies.textadventure.command.state;

public class Exit implements GameCommandState {

    @Override
    public void run() {
        System.exit(0);
    }
}
