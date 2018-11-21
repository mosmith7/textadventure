package smithies.textadventure.ui;

import smithies.textadventure.command.*;
import smithies.textadventure.map.DungeonMap;

import java.util.Optional;

public class UserTextInputParser {

    private CommandCache commandCache = new CommandCache();

    private DungeonMap map;

    public UserTextInputParser(DungeonMap map) {
        this.map = map;
    }

    public UserInputCommand parseString(String rawInput) {

        String input = convertRawInput(rawInput);

        Optional<Verb> optionalVerb = findVerb(input);
        Optional<Adverb> optionalAdverb = findAdverb(input);
        Optional<Noun> optionalNoun = findNoun(input);
        Optional<Noun> optionalSecondNoun = findSecondNoun(input);

        if (optionalVerb.isPresent() && optionalAdverb.isPresent() && optionalNoun.isPresent() && optionalSecondNoun.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalVerb.get(), optionalAdverb.get(), optionalNoun.get(), optionalSecondNoun.get());
        } else if (optionalVerb.isPresent() && optionalAdverb.isPresent() && optionalNoun.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalVerb.get(), optionalAdverb.get(), optionalNoun.get(), null);
        } else if (optionalVerb.isPresent() && optionalAdverb.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalVerb.get(), optionalAdverb.get());
        } else if (optionalVerb.isPresent() && optionalNoun.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalVerb.get(), optionalNoun.get());
        } else if (optionalVerb.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalVerb.get());
        } else if (optionalAdverb.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalAdverb.get());
        } else if (optionalNoun.isPresent()) {
            return new UserInputCommand(commandCache, map, optionalNoun.get());
        }

        return UserInputCommand.empty(commandCache);
    }

    private String convertRawInput(String rawInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        builder.append(rawInput.toUpperCase());
        builder.append(" ");
        return builder.toString();
    }

    private Optional<Verb> findVerb(String input) {
        Verb[] allVerbs = Verb.values();
        for (int i = 0; i < allVerbs.length; i++) {
            Verb verb = allVerbs[i];
            for (String verbAlias : verb.getAliases()) {
                if (input.contains(verbAlias)) {
                    return Optional.of(verb);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Adverb> findAdverb(String input) {
        Adverb[] allAdverbs = Adverb.values();
        for (int i = 0; i < allAdverbs.length; i++) {
            Adverb adverb = allAdverbs[i];
            for (String adVerbAlias : adverb.getAliases()) {
                if (input.contains(adVerbAlias)) {
                    return Optional.of(adverb);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Noun> findNoun(String input) {
        Noun[] allNouns = Noun.values();
        for (int i = 0; i < allNouns.length; i++) {
            Noun noun = allNouns[i];
            for (String nounAlias : noun.getAliases()) {
                if (input.contains(nounAlias)) {
                    return Optional.of(noun);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Noun> findSecondNoun(String input) {
        Noun[] allNouns = Noun.values();
        Integer firstNounEndIndex = 0;
        for (int i = 0; i < allNouns.length; i++) {
            Noun noun = allNouns[i];
            for (String nounAlias : noun.getAliases()) {
                int index = input.indexOf(nounAlias);
                if (index != -1) {
                    int endIndex = index + nounAlias.length();
                    if (firstNounEndIndex == 0 || (firstNounEndIndex > 0 && endIndex < firstNounEndIndex)) {
                        firstNounEndIndex = endIndex;
                    }
                }
            }
        }
        return findNoun(input.substring(firstNounEndIndex));
    }
}
