package org.sample.aws.chatbot.starwars.lex;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.sample.aws.chatbot.starwars.common.StarWarsIntent;
import org.sample.aws.chatbot.starwars.common.StarWarsResponse;
import org.sample.aws.lex.request.LexRequest;
import org.sample.aws.lex.response.DialogAction;
import org.sample.aws.lex.response.LexResponse;
import org.sample.aws.lex.response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarWarsLexBot implements RequestHandler<LexRequest, LexResponse> {

    private static final Logger log = LoggerFactory.getLogger(StarWarsLexBot.class);

    @Override
    public LexResponse handleRequest(LexRequest request, Context context) {
        log.info("onIntent requestId={} intent={}", context.getAwsRequestId(), request.getCurrentIntent().getName());

        String intent = request.getCurrentIntent().getName();
        String character = request.getCurrentIntent().getSlots().get("character");
        if (StarWarsIntent.MOVIE_INTENT.equals(intent)) {
            return getIntroResponse("Star Wars is cool");
        } else if (StarWarsIntent.PLANET_INTENT.equals(intent)) {
            return getPlanetResponse(character);
        } else if (StarWarsIntent.LIGHTSABER_INTENT.equals(intent)) {
            return getLightsaberResponse(character);
        } else if (StarWarsIntent.QUOTES_INTENT.equals(intent)) {
            return getQuotesResponse(character);
        } else if (StarWarsIntent.FORCE_SENSITIVE_INTENT.equals(intent)) {
            return getForceSensitiveResponse(character);
        } else if (StarWarsIntent.FORCE_SIDE_INTENT.equals(intent)) {
            return getForceSideResponse(character);
        } else if ("AMAZON.HelpIntent".equals(intent)) {
            return getHelpResponse();
        } else {
            throw new RuntimeException("Invalid Intent: " + request.getCurrentIntent().getName());
        }
    }

    private LexResponse getIntroResponse(String intro) {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getPlanetResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getPlanetResponse(slotValue);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getLightsaberResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getLightsaberResponse(slotValue);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getQuotesResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getQuotesResponse(slotValue);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getForceSensitiveResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getForceSensitiveResponse(slotValue);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getForceSideResponse(String slotValue) {
        StarWarsResponse response = StarWarsResponse.getForceSideResponse(slotValue);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getHelpResponse() {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getLexResponse(String speechText, String title) {
        Message message = new Message("PlainText", speechText);
        DialogAction dialogAction = new DialogAction("Close", "Fulfilled", message);
        return new LexResponse(dialogAction);
    }

}
