/*
 * Created on Apr 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.sakaiproject.api.kernel.tool.cover.ToolManager;
import org.sakaiproject.service.framework.email.EmailService;
import org.sakaiproject.service.legacy.notification.NotificationService;
import org.sakaiproject.service.legacy.realm.Realm;
import org.sakaiproject.service.legacy.realm.RealmService;
import org.sakaiproject.service.legacy.site.ToolConfiguration;
import org.sakaiproject.service.legacy.time.cover.TimeService;
import org.sakaiproject.service.legacy.user.User;
import org.sakaiproject.service.legacy.user.UserDirectoryService;
import org.sakaiproject.service.legacy.email.MailArchiveChannel;
import org.sakaiproject.service.legacy.email.MailArchiveMessageEdit;
import org.sakaiproject.service.legacy.email.MailArchiveMessageHeaderEdit;
import org.sakaiproject.service.legacy.email.cover.MailArchiveService;
import javax.faces.context.FacesContext;

public class Mailtool
{
	protected boolean DEBUG_NO_EMAIL = true;
	
	protected static final int NUMBER_ROLES = 15;
	
	/** Config Parameters **/
	protected String m_realm = "";
	
	protected List /* EmailRole */ m_emailroles = new ArrayList();
	protected String m_recipview = "role";
	
	/** For Main.jsp **/
	protected String m_subject = "";
	protected String m_body = "";

	protected boolean m_selectByRole = false;
	protected boolean m_selectByUser = false;
	protected boolean m_selectByTree = false;
	protected boolean m_selectSideBySide = false;
	protected boolean m_selectByFoothill = false;
	
	/** For Results.jsp **/
	protected String m_results = "";
	
	
	/***********************/
	/** Set Sakai Services */
	protected ToolConfiguration m_toolConfig = null;
	protected EmailService m_emailService = null;
	protected UserDirectoryService m_userDirectoryService = null;
	protected RealmService m_realmService = null;
	
	protected String getConfigParam(String parameter)
	{
		return ToolManager.getCurrentPlacement().getPlacementConfig().getProperty(parameter);
	}
	
	public void setEmailService(EmailService service) { this.m_emailService = service; }
	public void setUserDirectoryService(UserDirectoryService service) { this.m_userDirectoryService = service; }
	public void setRealmService(RealmService service) { this.m_realmService = service; }
	
	/**  Done Setting Sakai Services **/
	
	
	public Mailtool()
	{

	}
	
	/* JavaScript should look like this.
	<script language="JavaScript" type="text/javascript">
	var groups = new Array();

	var teachers = new Array();
	teachers[teachers.length] = new User("user004", "User, Four", "u@four.org");
	teachers[teachers.length] = new User("user005", "User, Five", "u@five.org");
	groups[groups.length] = new Group("realm2", "Teachers", "Teacher", teachers);
	
	var users = new Array();
	users[users.length] = new User("user001", "User, kljkjl", "u@one.org");
	users[users.length] = new User("user002", "asdfsf, Two", "u@two.org");
	users[users.length] = new User("user003", "User, Three", "u@three.org");
	users[users.length] = new User("user006", "User, Six", "u@six.org");
	users[users.length] = new User("user007", "User, Seven", "u@seven.org");
	users[users.length] = new User("user008", "User, Eight", "u@eight.org");
	groups[groups.length] = new Group("realm1", "Students", "Student", users);
	
	populate(groups);
	*/
	public String getInitJavascript()
	{
		System.out.println("SWG: Checking out the Client IDs");
		for (Iterator i = FacesContext.getCurrentInstance().getClientIdsWithMessages(); i.hasNext();)
		{
			Object o = i.next();
			System.out.println(o.getClass().toString());
			System.out.println(o.toString());
		}
		System.out.println("SWG: getInitJavascript");
		String retval = "<script language=\"JavaScript\" type=\"text/javascript\">\n"; 
		retval += "function addusers()\n{";
		retval += "var groups = new Array();\n";
		
		List emailgroups = this.getEmailGroups();
		for (Iterator i = emailgroups.iterator(); i.hasNext();)
		{
			EmailGroup egroup = (EmailGroup) i.next();
			String roleid = egroup.getEmailrole().getRoleid();
			String rolearray = roleid + "_array";
			retval += "var " + rolearray + " = new Array();\n";
			
			for (Iterator j = egroup.getEmailusers().iterator(); j.hasNext();)
			{
				EmailUser u = (EmailUser) j.next();
				retval += rolearray + "[" + rolearray + ".length] = new User(\"" +
					u.getUserid() + "\", \"" + u.getDisplayname() + "\", \"" +
					u.getEmail() + "\");\n";
			}
			retval += "groups[groups.length] = new Group(\"" + roleid + "\", \"" +
				egroup.getEmailrole().getRoleplural() + "\", \"" +
				egroup.getEmailrole().getRolesingular() + "\", " + rolearray + ");\n";
		}
		
		retval += "populate(groups);\n";
		retval += "}</script>\n";
		return retval;
	}

	public String getMessageSubject()
	{
		return m_subject;
	}
	
	public void setMessageSubject(String subject)
	{
		m_subject = subject;
	}
	
	public String getMessageBody()
	{
		return m_body;
	}
	
	public void setMessageBody(String body)
	{
		m_body = body;
	}
	
	public String getResults()
	{
		//return "What's going on?";
		return m_results;
	}
	
	public String getRecipJsp()
	{
		return m_recipJSPfrag;
	}
	
	private String m_recipJSPfrag = "";
	protected void setSelectorType()
	{	
		String type = getRecipview();
		
		m_selectByRole = false;
		m_selectByUser = false;
		m_selectByTree = false;
		m_selectSideBySide = false;
		m_selectByFoothill = false;
		
		if (type.equals("role")) 
		{
			m_selectByRole = true;
			m_recipJSPfrag = "selectByRole.jsp";
		}
		else if (type.equals("user"))
		{
			m_selectByUser = true;
			m_recipJSPfrag = "selectByUser.jsp";
		}
		else if (type.equals("tree"))
		{
			m_selectByTree = true;
			m_recipJSPfrag = "selectByTree.jsp";
		}
		else if (type.equals("sidebyside"))
		{
			m_selectSideBySide = true;
			m_recipJSPfrag = "selectSideBySide.jsp";
		}
		else if (type.equals("foothill"))
		{
			m_selectByFoothill = true;
			m_recipJSPfrag = "selectByFoothill.jsp";
		}
		else /* default to role */
		{
			m_selectByRole = true;
			m_recipJSPfrag = "selectByRole.jsp";
		}
	}
	
	public boolean isSelectByRole()
	{
		setSelectorType();
		return m_selectByRole;
	}
	
	public boolean isSelectByUser()
	{
		setSelectorType();
		return m_selectByUser;
	}
	
	public boolean isSelectByTree()
	{
		setSelectorType();
		return m_selectByTree;
	}
	
	public boolean isSelectSideBySide()
	{
		setSelectorType();
		return m_selectSideBySide;
	}
	
	public boolean isSelectByFoothill()
	{
		setSelectorType();
		return m_selectByFoothill;
	}

	public String processCancelEmail()
	{
		this.m_recipientSelector = null;
		this.m_subject = "";
		this.m_body = "";
		return "main";
	}
	
	public String processSendEmail()
	{
		List /* EmailUser */ selected = m_recipientSelector.getSelectedUsers();
		
		/* Put everyone in a set so the same person doesn't get multiple 
		 * emails.
		 */
		Set emailusers = new HashSet();
		for (Iterator i = selected.iterator(); i.hasNext();)
		{
			EmailUser u = (EmailUser) i.next();
			emailusers.add(u);
		}
		
		String subjectprefix = getSubjectPrefix();
		
		EmailUser curUser = getCurrentUser();
		
		String fromEmail = "";
		String fromDisplay = "";
		if (curUser != null)
		{
			fromEmail = curUser.getEmail();
			fromDisplay = curUser.getDisplayname();
		}
		String fromString = fromDisplay + " <" + fromEmail + ">";
		
		m_results = "Message sent to: <br>";

		String subject = "";
		if (subjectprefix != null)
			subject = subjectprefix + m_subject;
		else
			subject = m_subject;
		
		//Should we append this to the archive?
		String emailarchive = this.getConfigParam("emailarchive");
		if ((emailarchive != "") && (m_archiveMessage))
		{
			this.appendToArchive(emailarchive, fromString, subject, m_body);
		}
		
		//Send the emails
		for (Iterator i = emailusers.iterator(); i.hasNext();)
		{
			EmailUser euser = (EmailUser) i.next();
			
			String toEmail = euser.getEmail(); // u.getEmail();
			String toDisplay = euser.getDisplayname(); // u.getDisplayName();
			String toString = toDisplay + " <" + toEmail + ">";
			
			try 
			{
				log("SWG Sending email with: fromString=" + fromString + 
			        "  toString=" + toString + 
					"  subject=" + subject +
					"  headerToStr=" + toString + 
					"  replyToStr=" + fromString );
				
				m_emailService.send(fromString,// fromString
						       		toEmail,  // toString
									subject,   // subject 
									m_body,	   // content
									null,   // headerToStr
									null, // replyToStr
									null);
			}
			catch (Exception e)
			{
				log("SWG Exception while trying to send the email: " + e.getMessage());
			}
			
			if (i.hasNext())
			{
				m_results += toDisplay + ", ";
			}
			else
			{
				m_results += toDisplay;
			}
		}
		
		//	Clear the Subject and Body of the Message
		m_subject = "";
		m_body = "";
		
		/* Display Users with Bad Emails if the option is
		 * turned on.
		 */
		Boolean showBadEmails = getDisplayInvalidEmailAddr();
		
		if (showBadEmails.booleanValue() == true)
		{
			log("SWG inside print bad if statement");
			m_results += "<br/><br/>";
				
			List /* String */ badnames = new ArrayList();
			
			for (Iterator i = selected.iterator(); i.hasNext();)
			{
				EmailUser user = (EmailUser) i.next();
				
				/* This check should maybe be some sort of regular expression */
				if (user.getEmail().equals(""))
				{
					badnames.add(user.getDisplayname());
				}
			}
			
			if (badnames.size() > 0)
			{
				m_results += "The following users do not have valid email addresses:<br/>";
				
				for (Iterator i = badnames.iterator(); i.hasNext();)
				{
					String name = (String) i.next();
					if (i.hasNext() == true)
						m_results += name + ", ";
					else 
						m_results += name;
				}
			}
		}
		
		return "results";
	}
	
	protected RecipientSelector m_recipientSelector = null;
	public RecipientSelector getRecipientSelector()
	{
	 if (m_recipientSelector == null)
	 {
		List emailGroups = getEmailGroups();
		
		if (m_selectByRole == true)
		{
			m_recipientSelector = new RoleSelector();
		}
		else if (m_selectByUser == true)
		{
			m_recipientSelector = new UserSelector();
		}
		else if (m_selectByTree == true)
		{
			m_recipientSelector = new TreeSelector();
		}
		else if (m_selectSideBySide == true)
		{
			m_recipientSelector = new SideBySideSelector();
		}
		else if (m_selectByFoothill == true)
		{
			m_recipientSelector = new FoothillSelector();
		}
		else
		{
			m_recipientSelector = new RoleSelector();
		}
		
		m_recipientSelector.populate(emailGroups);
	 }
		
		return m_recipientSelector;
	}

	protected void log(String message)
	{
		System.out.println(message);
	}
	
	/*
	 * Get Information from the Tool Config
	 */
	public String getSubjectPrefix()
	{
		String prefix = this.getConfigParam("subjectprefix");        //propsedit.getProperty("subjectprefix");
		if (prefix == null)
			return "";
		else
			return prefix;
	}
	
	/*
	 * Get Information from the Tool Config
	 */
	public String getRecipview()
	{
		//String recipview = m_toolConfig.getPlacementConfig().getProperty("recipview");
		String recipview = this.getConfigParam("recipview");
		if (recipview == null)
			return "";
		else 
			return recipview;
	}
	
	public boolean isAllowedToSend()
	{
		String siteid = this.getConfigParam("mail.newlock.siteid");
		//System.out.println("SWG: isAllowedToSend, " + siteid);
		if (siteid == null)
			return true;
		
		if (siteid.equals(""))
			return true;
		
		return m_realmService.unlock(this.getCurrentUser().getUserid(), "mail.new", siteid);
	}
	
	
	/*
	 * Get Information from the Tool Config
	 */
	public Boolean getDisplayInvalidEmailAddr()
	{
		//String invalid = m_toolConfig.getPlacementConfig().getProperty("displayinvalidemailaddrs");
		String invalid = this.getConfigParam("displayinvalidemailaddrs");
		if (invalid == null)
			return Boolean.FALSE;
		
		if (invalid.equals("yes"))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	/*
	 * Read the tool config and build the email roles that are specified
	 */
	public List /* EmailRole */ getEmailRoles()
	{
		
		List /* EmailRole */ theroles = new ArrayList();
		
		for (int i = 1; i < (NUMBER_ROLES+1); i++)
		{
			String rolerealm = this.getConfigParam("role" + i + "realmid");
			String rolename = this.getConfigParam("role" + i + "id");
			String rolesingular = this.getConfigParam("role" + i + "singular");
			String roleplural = this.getConfigParam("role" + i + "plural");
			
			if ((rolerealm != null && rolerealm != "")  &&
				(rolename != null && rolename != "") &&
				(rolesingular != null && rolesingular != "") &&
				(roleplural != null && roleplural != "") )
			{
				EmailRole emailrole = new EmailRole(rolerealm,rolename,rolesingular,roleplural);
				theroles.add(emailrole);
			}
		}	
		
		return theroles;
	}
	
	public boolean isEmailArchived()
	{
		String emailarchive = this.getConfigParam("emailarchive");
		if (emailarchive == null)
			return false;
		
		if (emailarchive.equals(""))
			return false;
		
		return true;
	}
	
	boolean m_archiveMessage = false;
	public boolean isArchiveMessage()
	{
		return m_archiveMessage;
	}
	
	public void setArchiveMessage(boolean value)
	{
		m_archiveMessage = value;
	}
	
	/*
	 * Build all groups that will be used for this
	 */
	public List /* EmailGroup */ getEmailGroups()
	{
		List /* EmailGroup */ thegroups = new ArrayList();
		
		List emailroles = this.getEmailRoles();
		
		for (Iterator i = emailroles.iterator(); i.hasNext();)
		{
			EmailRole emailrole = (EmailRole) i.next();
			
			String realmid = emailrole.getRealmid();
			
			Realm therealm = null;
			try {
				therealm = m_realmService.getRealm(realmid);
			} catch (Exception e) {}
			
			Set users = therealm.getUsersWithRole(emailrole.getRoleid());
			List /* EmailUser */ mailusers = new ArrayList();
			for (Iterator j = users.iterator(); j.hasNext();)
			{
				String userid = (String) j.next();
				try {
					User theuser = m_userDirectoryService.getUser(userid);
					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getDisplayName(), theuser.getEmail());
					mailusers.add(emailuser);
				} catch (Exception e) {}
			}
			
			EmailGroup thegroup = new EmailGroup(emailrole, mailusers);
			thegroups.add(thegroup);
		}
		
		return thegroups;
	}
	
	/*
	 * Get the current user
	 */
	public EmailUser getCurrentUser()
	{
		EmailUser euser = null;
		User curU = null;
		try
		{
			curU = m_userDirectoryService.getCurrentUser();
			euser = new EmailUser(curU.getId(), curU.getDisplayName(),
								  curU.getEmail());
		}
		catch (Exception e)
		{
			log("Exception: MailtoolBackend.getCurrentUser, " + e.getMessage());
		}
		
		return euser;
	}
	

	protected boolean appendToArchive(String channelRef, String sender, String subject, String body)
	{
		MailArchiveChannel channel = null;
		
		try
		{
			channel = MailArchiveService.getMailArchiveChannel(channelRef);
		}
		catch (Exception goOn)
		{
			return false;
		}
		
		if (channel == null)
		{	
			System.out.println("SWG: The channel: " + channelRef + " is null.");
			return false;
		}
		List mailHeaders = new Vector();
		mailHeaders.add(MailArchiveService.HEADER_OUTER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
		mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
		mailHeaders.add("Mime-Version: 1.0");
		mailHeaders.add("From: " + sender);
		mailHeaders.add("Reply-To: " + sender);
		
		try {
			// This way actually sends the email too
			//channel.addMailArchiveMessage(subject, sender, TimeService.newTime(),
			//	mailHeaders, null, body);
			
			MailArchiveMessageEdit edit = (MailArchiveMessageEdit) channel.addMessage();
			MailArchiveMessageHeaderEdit header = edit.getMailArchiveHeaderEdit();
			edit.setBody(body);
			header.replaceAttachments(null);
			header.setSubject(subject);
			header.setFromAddress(sender);
			header.setDateSent(TimeService.newTime());
			header.setMailHeaders(mailHeaders);
			
			channel.commitMessage(edit, NotificationService.NOTI_NONE);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
}

