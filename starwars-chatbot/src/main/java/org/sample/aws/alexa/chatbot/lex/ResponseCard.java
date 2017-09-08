package org.sample.aws.alexa.chatbot.lex;

public class ResponseCard {
    int version;
    String contentType;
    Attachments[] genericAttachments;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Attachments[] getGenericAttachments() {
        return genericAttachments;
    }

    public void setGenericAttachments(Attachments[] genericAttachments) {
        this.genericAttachments = genericAttachments;
    }
}
