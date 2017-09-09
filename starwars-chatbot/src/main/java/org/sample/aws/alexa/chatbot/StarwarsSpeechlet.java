package org.sample.aws.alexa.chatbot;

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

import java.util.List;
import java.util.Random;

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

        if ("MovieIntent".equals(intentName)) {
            return getIntroResponse("Star Wars is cool");
        } else if ("PlanetIntent".equals(intentName)) {
            return getPlanetResponse(intent.getSlot("character").getValue());
        } else if ("LightsaberIntent".equals(intentName)) {
            return getLightsaberResponse(intent.getSlot("character").getValue());
        } else if ("QuotesIntent".equals(intentName)) {
            return getQuotesResponse(intent.getSlot("character").getValue());
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
        return getSpeechletResponseWithReprompt(response.speechText, response.title);
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getIntroResponse(String intro) {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
        return getSpeechletResponse(response.getSpeechText(), response.getTitle());
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
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
