package org.sample.aws.alexa.chatbot.lex;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class StarWarsLexBot implements RequestHandler<LexRequest, LexResponse> {

    @Override
    public LexResponse handleRequest(LexRequest request, Context context) {
        System.out.println("Intent: " + request.currentIntent);
        Message message = new Message("PlainText", "Some response message");
        DialogAction dialogAction = new DialogAction("Close", "Fulfilled", message);
        return new LexResponse(dialogAction);
    }
}
