package org.sample.aws.chatbot.starwars.common;

import org.sample.aws.chatbot.starwars.db.DBUtil;
import org.sample.aws.chatbot.starwars.db.StarWarsCharacter;

import java.util.List;
import java.util.Random;

public class StarWarsResponse {
    String speechText;
    String title;

    public StarWarsResponse(String speechText, String title) {
        this.speechText = speechText;
        this.title = title;
    }

    public String getSpeechText() {
        return speechText;
    }

    public String getTitle() {
        return title;
    }

    public static StarWarsResponse getWelcomeResponse() {
        return new StarWarsResponse("Welcome to Star Wars Trivia, you can ask quotes", "Star Wars Welcome");
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    public static StarWarsResponse getIntroResponse(String intro) {
        return new StarWarsResponse("", "Star Wars Intro");
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    public static StarWarsResponse getHelpResponse() {
        return new StarWarsResponse("Star Wars", "Star Wars Help");
    }


    public static StarWarsResponse getPlanetResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            speechText = character.getName() + " is from " + character.getPlanet();
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        return new StarWarsResponse(speechText, "Star Wars Planet");
    }

    public static StarWarsResponse getLightsaberResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            speechText = character.getName() + "'s ligthsaber is " + character.getLightsaberColor();
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }
        return new StarWarsResponse(speechText, "Star Wars Lightsaber");
    }


    public static StarWarsResponse getQuotesResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            List<String> list = character.getQuotes();
            Random random = new Random();
            speechText = "Here is a quote from " + character.getName() + ": " + list.get(random.nextInt(list.size()));
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Quotes");
    }


    public static StarWarsResponse getForceSensitiveResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            speechText = character.getName() + " is " + (character.isForceSensitive() ? "" : " not ") + " sensitive to the Force";
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Force Sensitive");
    }

    public static StarWarsResponse getForceSideResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            speechText = character.getName() + " is on the " + character.getForceSide() + " side";
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Force Side");
    }
}
