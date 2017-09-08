package org.sample.aws.alexa.chatbot.lex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class LexRequest {
    String messageVersion;
    String invocationSource;
    String userId;
    Map<String, String> sessionAttributes;
    Bot bot;
    String outputDialogMode;
    Intent currentIntent;
    String confirmationStatus;
    String inputTranscript;

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public String getInvocationSource() {
        return invocationSource;
    }

    public void setInvocationSource(String invocationSource) {
        this.invocationSource = invocationSource;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public String getOutputDialogMode() {
        return outputDialogMode;
    }

    public void setOutputDialogMode(String outputDialogMode) {
        this.outputDialogMode = outputDialogMode;
    }

    public Intent getCurrentIntent() {
        return currentIntent;
    }

    public void setCurrentIntent(Intent currentIntent) {
        this.currentIntent = currentIntent;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getInputTranscript() {
        return inputTranscript;
    }

    public void setInputTranscript(String inputTranscript) {
        this.inputTranscript = inputTranscript;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
