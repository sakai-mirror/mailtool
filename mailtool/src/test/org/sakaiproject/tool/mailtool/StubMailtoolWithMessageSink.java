package org.sakaiproject.tool.mailtool;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/** 
 * A subclass of {@link StubMailtool} with more stubbed methods
 * to aid with testing {@link Mailtool#processSendEmail()}. The
 * main feature-add is the ability to inject a sink 
 * (@link MimeMessageSender} into which 
 * {@link Mailtool#sendMimeMessage(MimeMessage)} will pass
 * message objects. This allows tests to assert on the state
 * of {@link MimeMessage}s configured by 
 * {@link Mailtool#processSendEmail()}
 * 
 * @author dmccallum@unicon.net
 * 
 */
public class StubMailtoolWithMessageSink extends StubMailtool {
	
	private MimeMessageSender mimeMessageSender;
	
	public StubMailtoolWithMessageSink(MimeMessageSender mimeMessageSender) {
		this.mimeMessageSender = mimeMessageSender;
	}
	
	protected void sendMimeMessage(MimeMessage message)
	throws MessagingException {
		mimeMessageSender.send(message);
	} 
	
	// TODO a better solution would be to just stop using the 
	// ServerConfigurationService cover
	public String getMailHost() {
		return BaseMailtoolTest.DEFAULT_SMTP_HOST;
	}
	
	// TODO a better solution would be to just stop using the 
	// ServerConfigurationService cover
	public String getMailDebug() {
		return "false";
	}
	
	// TODO a better solution would be to just stop using the 
	// ServerConfigurationService cover
	public String getUploadDirectory() {
		return "/tmp";
	}
}