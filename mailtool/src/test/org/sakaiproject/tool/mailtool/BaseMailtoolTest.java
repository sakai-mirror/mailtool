package org.sakaiproject.tool.mailtool;


import javax.mail.internet.InternetAddress;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;


/**
 * import junit.framework.TestCase;


public class BaseMailtoolTest extends TestCase {

}

 * @author dmccallum@unicon.net
 *
 */
public abstract class BaseMailtoolTest extends MockObjectTestCase {
	
	protected static final String DEFAULT_SMTP_HOST = "localhost";
	protected Mailtool mailtool;
	protected Mock mockUserDirectoryService;
	protected UserDirectoryService userDirectoryService;
	protected Mock mockCurrentUser;
	protected User currentUser;
	protected Mock mockMimeMessageSender;
	protected MimeMessageSender mimeMessageSender;
	protected Mock mockRecipientSelector;
	protected RecipientSelector recipientSelector;
	
	protected void setUp() throws Exception {
		
		mockUserDirectoryService = new Mock(UserDirectoryService.class);
		userDirectoryService = (UserDirectoryService)mockUserDirectoryService.proxy();
		
		mockCurrentUser = new Mock(User.class);
		currentUser = (User)mockCurrentUser.proxy();
		
		mockMimeMessageSender = new Mock(MimeMessageSender.class);
		mimeMessageSender = (MimeMessageSender)mockMimeMessageSender.proxy();
		
		mockRecipientSelector = new Mock(RecipientSelector.class);
		recipientSelector = (RecipientSelector)mockRecipientSelector.proxy();
		
		mailtool = createMailtool();
		
		mailtool.setUserDirectoryService(userDirectoryService);
		mailtool.m_recipientSelector = recipientSelector;
		mailtool.init();
		
		// override the effect of init().
		mailtool.m_selectByTree = false;
		
		super.setUp();
	}
	
	/**
	 * Instantiate a {@link Mailtool} for caching as the object
	 * under test. Useful for subclasses which may need to stub out
	 * additional behaviors.
	 * 
	 * @return a new {@link Mailtool}, typically a {@link StubMailtool}
	 */
	protected Mailtool createMailtool() {
		return new StubMailtoolWithMessageSink(mimeMessageSender);
	}
	
	/**
	 * Factory for {@link MimeMessageFromConstraint} instances.
	 * 
	 * @param expectedFrom passed through to {@link MimeMessageFromConstraint#MimeMessageFrom(InternetAddress)}
	 * @return a new {@link MimeMessageFromConstraint}
	 */
	protected Constraint messageFrom(InternetAddress expectedFrom) {
		return new MimeMessageFromConstraint(expectedFrom);
	}
	

}