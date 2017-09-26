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

import java.util.Map;

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
        } else if (StarWarsIntent.DIALOG_INTENT.equals(intent)) {
            System.out.println("Invocation source: " + request.getInvocationSource());
            if (request.getInvocationSource().equals(LexRequest.INVOCATION_SOURCE_DIALOG_CODE_HOOK)) {
                return getDialogQuestion();
            } else {
                request.getCurrentIntent().getSlots().forEach((k, v) -> System.out.println(k + ":" + v));
                return getDialogResponse(request.getSessionAttributes());
            }
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

    private LexResponse getDialogQuestion() {
        StarWarsResponse response = StarWarsResponse.getDialogQuestion();

        DialogAction dialogAction = new DialogAction();
        dialogAction.setIntentName(StarWarsIntent.DIALOG_INTENT);
        dialogAction.setSlotToElicit("character");
        dialogAction.setType(DialogAction.ELICIT_SLOT_TYPE);

        Message message = new Message(Message.CONTENT_TYPE_PLAIN_TEXT, response.getSpeechText());
        dialogAction.setMessage(message);

        dialogAction.addSlots("character", "");

        LexResponse lexResponse = new LexResponse(dialogAction);
        lexResponse.setSessionAttributes(response.getSessionAttributes());

        return lexResponse;
    }

    private LexResponse getDialogResponse(Map<String, String> sessionAttributes) {
        StarWarsResponse response = StarWarsResponse.getDialogResponse(sessionAttributes);
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getHelpResponse() {
        StarWarsResponse response = StarWarsResponse.getWelcomeResponse();
        return getLexResponse(response.getSpeechText(), response.getTitle());
    }

    private LexResponse getLexResponse(String speechText, String title) {
        Message message = new Message(Message.CONTENT_TYPE_PLAIN_TEXT, speechText);
        DialogAction dialogAction = new DialogAction(DialogAction.CLOSE_TYPE, DialogAction.FULFILLMENT_STATE_FULFILLED, message);

        return new LexResponse(dialogAction);
    }
}
