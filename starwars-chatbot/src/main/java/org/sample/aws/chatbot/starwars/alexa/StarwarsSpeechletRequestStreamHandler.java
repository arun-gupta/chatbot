package org.sample.aws.chatbot.starwars.alexa;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import org.sample.aws.chatbot.starwars.alexa.StarwarsSpeechlet;

/**
 * This class is the handler for AWS Lambda function.
 *
 * @author Arun Gupta
 */
public class StarwarsSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> SUPPORTED_APPLICATION_IDS = new HashSet<>();
    static {
        SUPPORTED_APPLICATION_IDS.add("amzn1.ask.skill.81e128e4-dd7a-4585-aa16-e3d9b1459962");
    }

    public StarwarsSpeechletRequestStreamHandler() {
        super(new StarwarsSpeechlet(), SUPPORTED_APPLICATION_IDS);
    }    
}
