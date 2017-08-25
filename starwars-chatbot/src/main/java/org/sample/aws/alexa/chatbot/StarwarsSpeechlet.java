package org.sample.aws.alexa.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * @author Arun Gupta
 */
public class StarwarsSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(StarwarsSpeechlet.class);

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("MovieIntent".equals(intentName)) {
            return getIntroResponse("Star Wars is cool");
        } else if ("PlanetIntent".equals(intentName)) {
            return getPlanetResponse(intent.getSlot("character").getValue());
        } else if ("LightsaberIntent".equals(intentName)) {
            return getLightsaberResponse(intent.getSlot("character").getValue());
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to Star Wars Trivia, you can ask about planets";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Star Wars");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getIntroResponse(String intro) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Star Wars Intro");
        card.setContent(intro);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(intro);

        return SpeechletResponse.newTellResponse(speech, card);
    }
    
    private SpeechletResponse getPlanetResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;
        
        if (character != null && character.getName()!= null) {
            speechText = character.getName() + " is from " + character.getPlanet();
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Star Wars");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    private SpeechletResponse getLightsaberResponse(String slotValue) {
        StarWarsCharacter character = DBUtil.getCharacter(slotValue);

        String speechText;

        if (character != null && character.getName()!= null) {
            speechText = character.getName() + "'s ligthsaber is " + character.getLightsaberColor();
        } else {
            speechText = "Are you sure " + slotValue + " was in Star Wars?";
        }

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Star Wars");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "You can say hello to me!";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
