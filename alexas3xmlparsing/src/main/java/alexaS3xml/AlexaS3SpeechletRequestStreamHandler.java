package alexaS3xml;


import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.Speechlet;


public class AlexaS3SpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

	 private static final Set<String> supportedApplicationIds;

	    static {
	        supportedApplicationIds = new HashSet<String>();
	        supportedApplicationIds.add("amzn1.ask.skill.faade7d6-b2f2-4232-994f-5bf6c843ea23");
	    }

	    public AlexaS3SpeechletRequestStreamHandler() {
	        super(new alexas3xp(), supportedApplicationIds);
	    }

	    public AlexaS3SpeechletRequestStreamHandler(Speechlet speechlet,
	            Set<String> supportedApplicationIds) {
	        super(speechlet, supportedApplicationIds);
	    }

	
}
