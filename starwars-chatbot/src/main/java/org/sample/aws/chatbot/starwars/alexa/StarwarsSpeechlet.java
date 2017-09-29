package org.sample.aws.chatbot.starwars.alexa;

import org.sample.aws.chatbot.starwars.common.StarWarsIntent;
import org.sample.aws.chatbot.starwars.common.StarWarsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
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
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        String character = request.getIntent().getSlot("character").getValue();

        if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else if (StarWarsIntent.QUOTES_INTENT.equals(intentName)) {
            return getQuotesResponse(character);
        } else if (StarWarsIntent.PLANET_INTENT.equals(intentName)) {
            return getPlanetResponse(intent.getSlot("character").getValue());
        } else if (StarWarsIntent.LIGHTSABER_INTENT.equals(intentName)) {
            return getLightsaberResponse(character);
        } else if (StarWarsIntent.FORCE_SENSITIVE_INTENT.equals(intent)) {
            return getForceSensitiveResponse(character);
        } else if (StarWarsIntent.FORCE_SIDE_INTENT.equals(intent)) {
            return getForceSideResponse(character);
        } else {
            throw new SpeechletException("Invalid Intent: " + intentName);
        }
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        StarWarsResponse response = StarWarsResponse.getHelpResponse();
        return getSpeechletResponseWithReprompt(response.getSpeechText(), response.getTitle());
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        StarWarsResponse response = StarWarsResponse.getHelpResponse();
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SpeechletResponse getPlanetResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getPlanetResponse(slotValue);
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SpeechletResponse getLightsaberResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getLightsaberResponse(slotValue);
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SpeechletResponse getQuotesResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getQuotesResponse(slotValue);
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SpeechletResponse getForceSensitiveResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getForceSensitiveResponse(slotValue);
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SpeechletResponse getForceSideResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getForceSideResponse(slotValue);
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    private SimpleCard getCard(String title, String speechText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(speechText);
        return card;
    }

    private PlainTextOutputSpeech getSpeech(String speechText) {
        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return speech;
    }

    private SpeechletResponse getSpeechletResponse(String speechText, String title) {
        return SpeechletResponse.newTellResponse(getSpeech(speechText), getCard(speechText, title));
    }

    private SpeechletResponse getSpeechletResponseWithReprompt(String speechText, String title) {
        // Create the plain text output.
        PlainTextOutputSpeech speech = getSpeech(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, getCard(speechText, title));
    }
}
