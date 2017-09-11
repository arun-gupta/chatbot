package org.sample.aws.alexa.chatbot.lex;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.sample.aws.alexa.chatbot.StarWarsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarWarsLexBot implements RequestHandler<LexRequest, LexResponse> {

    private static final Logger log = LoggerFactory.getLogger(StarWarsLexBot.class);

    @Override
    public LexResponse handleRequest(LexRequest request, Context context) {
        log.info("onIntent requestId={} intent={}", context.getAwsRequestId(), request.getCurrentIntent().getName());

        if ("MovieIntent".equals(request.getCurrentIntent().getName())) {
            return getIntroResponse("Star Wars is cool");
        } else if ("PlanetIntent".equals(request.getCurrentIntent().getName())) {
            return getPlanetResponse(request.getCurrentIntent().getSlots().get("character"));
        } else if ("LightsaberIntent".equals(request.getCurrentIntent().getName())) {
            return getLightsaberResponse(request.getCurrentIntent().getSlots().get("character"));
        } else if ("QuotesIntent".equals(request.getCurrentIntent().getName())) {
            return getQuotesResponse(request.getCurrentIntent().getSlots().get("character"));
        } else if ("AMAZON.HelpIntent".equals(request.getCurrentIntent().getName())) {
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
