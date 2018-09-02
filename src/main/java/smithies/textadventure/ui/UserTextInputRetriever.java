package smithies.textadventure.ui;

import java.util.Scanner;

public class UserTextInputRetriever {

    private Scanner input = new Scanner(System.in);

    public String getLine() {
        return input.nextLine();
    }
}
