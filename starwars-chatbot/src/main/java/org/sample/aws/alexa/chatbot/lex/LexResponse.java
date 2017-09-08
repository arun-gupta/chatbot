package org.sample.aws.alexa.chatbot.lex;

import java.util.Map;

public class LexResponse {
    Map<String, String> sessionAttributes;
    private DialogAction dialogAction;

    public LexResponse(DialogAction dialogAction) {

        this.dialogAction = dialogAction;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }
}
