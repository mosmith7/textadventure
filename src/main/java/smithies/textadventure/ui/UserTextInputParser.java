package smithies.textadventure.ui;

import smithies.textadventure.command.*;

import java.util.Optional;

public class UserTextInputParser {

    public UserInputCommand parseString(String rawInput) {

        String input = convertRawInput(rawInput);

        Optional<Verb> optionalVerb = findVerb(input);
        Optional<Adverb> optionalAdverb = findAdverb(input);
        Optional<Noun> optionalNoun = findNoun(input);

        if (optionalVerb.isPresent() && optionalAdverb.isPresent() && optionalNoun.isPresent()) {
            return new UserInputCommand(optionalVerb.get(), optionalAdverb.get(), optionalNoun.get());
        } else if (optionalVerb.isPresent() && optionalAdverb.isPresent()) {
            return new UserInputCommand(optionalVerb.get(), optionalAdverb.get());
        } else if (optionalVerb.isPresent() && optionalNoun.isPresent()) {
            return new UserInputCommand(optionalVerb.get(), optionalNoun.get());
        } else if (optionalVerb.isPresent()) {
            return new UserInputCommand(optionalVerb.get());
        } else if (optionalAdverb.isPresent()) {
            return new UserInputCommand(optionalAdverb.get());
        }

        return new UserInputCommand(Verb.FAILED_TO_PARSE);
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
}
