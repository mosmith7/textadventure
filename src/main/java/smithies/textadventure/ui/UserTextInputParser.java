package smithies.textadventure.ui;

import org.apache.commons.lang3.StringUtils;
import smithies.textadventure.command.UserInputCommand;

public class UserTextInputParser {

    public UserInputCommand parseString(String input) {
        if (StringUtils.isNotBlank(input)) {
            System.out.println("You typed: " + input);
        }

        try {
            return UserInputCommand.valueOf(input.toUpperCase());
        } catch (Exception e) {
            System.out.println("Command not recognised");
        }

        return UserInputCommand.WAIT;
    }
}
