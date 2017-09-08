package org.sample.aws.alexa.chatbot.lex;

import java.util.Map;

public class DialogAction {
    private final String type;
    private final String fulfillmentState;
    private final Message message;
    private ResponseCard responseCard;

    public DialogAction(String type, String fulfillmentState, Message message) {

        this.type = type;
        this.fulfillmentState = fulfillmentState;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getFulfillmentState() {
        return fulfillmentState;
    }

    public Message getMessage() {
        return message;
    }

    public ResponseCard getResponseCard() {
        return responseCard;
    }
}
