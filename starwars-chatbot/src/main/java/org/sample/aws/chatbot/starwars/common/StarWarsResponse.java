package org.sample.aws.chatbot.starwars.common;

import org.sample.aws.chatbot.starwars.db.DBUtil;
import org.sample.aws.chatbot.starwars.db.StarWarsCharacter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StarWarsResponse {
    String speechText;
    String title;
    Map<String, String> sessionAttributes;

    private static final String help = "You can ask quotes, lightsaber color, Jedi or Sith questions.";

    public StarWarsResponse(String speechText, String title) {
        this.speechText = speechText;
        this.title = title;
        sessionAttributes = new HashMap<>();
    }

    public String getSpeechText() {
        return speechText;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public static StarWarsResponse getWelcomeResponse() {
        return new StarWarsResponse("Welcome to Star Wars Chatbot!" + help, "Star Wars Welcome");
    }

    public static StarWarsResponse getHelpResponse() {
        return new StarWarsResponse(help, "Star Wars Help");
    }

    public static StarWarsResponse getPlanetResponse(String name) {
        StarWarsCharacter character = DBUtil.getCharacter(name);

        String speechText;

        if (character != null && character.getName() != null) {
            String planet = character.getPlanet();

            if (null == planet || planet.equals("")) {
                speechText = character.getName() + "'s planet is not known";
            } else {
                speechText = character.getName() + " is from the planet " + planet;
            }
        } else {
            speechText = "Are you sure " + name + " was in Star Wars?";
        }

        return new StarWarsResponse(speechText, "Star Wars Planet");
    }

    public static StarWarsResponse getLightsaberResponse(String name) {
        StarWarsCharacter character = DBUtil.getCharacter(name);

        String speechText;

        if (character != null && character.getName() != null) {
            if (null == character.getLightsaberColor()) {
                speechText = character.getName() + " does not have a lightsaber";
            } else {
                speechText = character.getName() +
                        "'s ligthsaber is " +
                        character.getLightsaberColor();
            }
        } else {
            speechText = "Are you sure " + name + " was in Star Wars?";
        }
        return new StarWarsResponse(speechText, "Star Wars Lightsaber");
    }


    public static StarWarsResponse getQuotesResponse(String name) {
        StarWarsCharacter character = DBUtil.getCharacter(name);

        String speechText;

        if (character != null && character.getName() != null) {
            List<String> list = character.getQuotes();
            Random random = new Random();
            speechText = "Here is a quote from " +
                    character.getName() +
                    ": \"" +
                    list.get(random.nextInt(list.size())) +
                            "\"";
        } else {
            speechText = "Are you sure " + name + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Quotes");
    }


    public static StarWarsResponse getForceSensitiveResponse(String name) {
        StarWarsCharacter character = DBUtil.getCharacter(name);

        String speechText;

        if (character != null && character.getName() != null) {
            speechText = (character.isForceSensitive() ? "Yes, " : "No, ") +
                    character.getName() +
                    " is " + (character.isForceSensitive() ? "" : " not ") +
                    " sensitive to the Force.";
        } else {
            speechText = "Are you sure " + name + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Force Sensitive");
    }

    public static StarWarsResponse getForceSideResponse(String name) {
        StarWarsCharacter character = DBUtil.getCharacter(name);

        String speechText;

        if (character != null && character.getName() != null) {
            if (character.isForceSensitive()) {
                speechText = character.getName() +
                        " is on the " +
                        character.getForceSide() +
                        " side, and so is a " +
                        (character.getForceSide().equals("light") ? "Jedi" : "Sith");
            } else {
                speechText = character.getName() + " is not sensitive to the Force";
            }
        } else {
            speechText = "Are you sure " + name + " was in Star Wars?";
        }

        // Create the Simple card content.
        return new StarWarsResponse(speechText, "Star Wars Force Side");
    }

    public static StarWarsResponse getDialogueQuestion() {
        StarWarsCharacter character = DBUtil.getRandomCharacter();
        List<String> list = character.getQuotes();

        Random random = new Random();
        String speechText = "Who said \"" + list.get(random.nextInt(list.size())) + "\"";

        StarWarsResponse response = new StarWarsResponse(speechText, "Star Wars Quote Question");
        response.sessionAttributes.put("character", character.getName());
        response.sessionAttributes.put("question", speechText);

        return response;
    }

    public static StarWarsResponse getDialogueResponse() {
        String speechText = "Yep, you're right!";

        return new StarWarsResponse(speechText, "Star Wars Quote Response");
    }
}
