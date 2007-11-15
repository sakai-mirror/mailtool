package org.sakaiproject.tool.mailtool;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * A message sink. (Useful since {@link Transport#send(javax.mail.Message)}
 * is static. and therefore difficult to mock.)
 */
interface MimeMessageSender {
	public void send(MimeMessage msg) throws MessagingException;
}