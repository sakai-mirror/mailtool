/*
 * Created on Apr 15, 2005 by Steven Githens
 *
 * Modified/expanded Aug 2006 by kimsooil@bu.edu
 *
 */
package org.sakaiproject.tool.mailtool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FilenameUtils;

import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.email.cover.EmailService;
import org.sakaiproject.event.cover.NotificationService;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.cover.AuthzGroupService;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.cover.ContentHostingService;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.time.cover.TimeService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.mailarchive.api.MailArchiveChannel;
import org.sakaiproject.mailarchive.api.MailArchiveMessageEdit;
import org.sakaiproject.mailarchive.api.MailArchiveMessageHeaderEdit;
import org.sakaiproject.mailarchive.cover.MailArchiveService;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.site.cover.SiteService;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.event.ActionEvent;
import javax.faces.event.AbortProcessingException;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.sakaiproject.tool.mailtool.Attachment;
public class Mailtool
{

	private final Log log = LogFactory.getLog(this.getClass());

	protected FacesContext facesContext = FacesContext.getCurrentInstance();

	protected boolean DEBUG_NO_EMAIL = true;

	protected static final int NUMBER_ROLES = 15;

	/** Config Parameters **/
	protected String m_realm = "";

	protected List /* EmailRole */ m_emailroles = new ArrayList();
	protected String m_recipview = "";
	protected String uploaddirectoryDefault="/tmp/";
	protected String recipviewDefault="user";

	/** For Main.jsp **/
	protected String m_subject = "";
	protected String m_body = "";
	protected String m_editortype="";

	protected boolean is_fckeditor=false;
	protected boolean is_htmlarea=false;

	protected boolean m_selectByRole = false;
	protected boolean m_selectByUser = false;
	protected boolean m_selectByTree = false;
	protected boolean m_selectSideBySide = false;
	protected boolean m_selectByFoothill = false;
	protected boolean m_archiveMessage = false;

	private String m_recipJSPfrag = "";
	private boolean m_buildNewView = false;
	private String m_changedViewChoice = "";

	/** For Results.jsp **/
	protected String m_results = "";

	/***********************/
	/** Set Sakai Services */
	protected ToolConfiguration m_toolConfig = null;


	//protected EmailService m_emailService = null;
	protected EmailService m_emailService = null;

	protected UserDirectoryService m_userDirectoryService = null;
	protected AuthzGroupService m_realmService = null;
	//protected Logger logger = null;  // by SK 6/30/2006

//	protected int MAXFILE=readMAXFILE();

	private List attachedFiles = new ArrayList();
	private int MaxNumAttachment=readMaxNumAttachment();
	private String filename="";
	private int num_files=0;
	private int num_id=0;
	private boolean attachClicked=false;
//	protected String uploaddirectory="";
	protected String eid="";

	public void setattachClicked(boolean a)
	{
		this.attachClicked=a;
	}
	public boolean getattachClicked()
	{
		return attachClicked;
	}
	protected String getConfigParam(String parameter)
	{
		String p=ToolManager.getCurrentPlacement().getPlacementConfig().getProperty(parameter);
		if (p==null) return "";
		return p;
	}
	protected String getSiteID()
	{
		String id=ToolManager.getCurrentPlacement().getContext();
		return id;
	}
	protected String getSiteType()
	{
		String sid=getSiteID();
		String type="";
		try{
		type=SiteService.getSite(sid).getType();
		}
		catch(Exception e)
		{
		}
		return type;
	}
	protected String getSiteTitle()
	{
		String sid=getSiteID();
		String title="";
		try{
			title=SiteService.getSite(sid).getTitle();
		}
		catch (Exception e)
		{
		}
		return title;

	}
	//public void setEmailService(EmailService service) { this.m_emailService = service; }
	public void setUserDirectoryService(UserDirectoryService service) { this.m_userDirectoryService = service; }
	public void setAuthzGroupService(AuthzGroupService service) { this.m_realmService = service; }
	//public void setLogger(Logger logger) { this.logger = logger; } // by SK 6/30/2006


	/**  Done Setting Sakai Services **/

	public Mailtool()
	{
		log.debug("Constructor");
		//System.out.println("site title="+getSiteTitle());
		//System.out.println("site type="+getSiteType());
		//System.out.println("site id="+getSiteID());
	}
	public String getfilename()
	{
		return filename;
	}
	public void setfilename(String filename)
	{
		this.filename=filename;
	}
	public int getnum_files()
	{
		return this.num_files;
	}
	public void setnum_files(int num_files)
	{
		this.num_files=num_files;
	}
	public String getEditorType()
	{
		String editortype = this.getConfigParam("wysiwygeditor");
		return editortype;
	}

	public int readMaxNumAttachment()
	{
		try{
			//int maxnumattachment = Integer.parseInt(this.getConfigParam("max.num.attachment"));
			int maxnumattachment = Integer.parseInt(ServerConfigurationService.getString("mailtool.max.num.attachment"));
			return maxnumattachment;
		}
		catch (NumberFormatException e)
		{
			return 10000; // Actually this means "unlimited if not set or invalid"
		}
	}
	public int getMaxNumAttachment()
	{
		return MaxNumAttachment;
	}

	public void setMaxNumAttachment(int m)
	{
		this.MaxNumAttachment=m;
	}

	public String getUploadDirectory()
	{
		String ud=ServerConfigurationService.getString("mailtool.upload.directory");
		if (ud!="" && ud!=null)
		{
			File dir = new File(ud);
			if (dir.isDirectory())
				return ud;
		}

		return uploaddirectoryDefault;
	}
	public void setEditorType(String editor)
	{
		m_editortype = editor;
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

	protected void setSelectorType()
	{
		String type = "";
		if (m_changedViewChoice.equals(""))
			type = getRecipview();
		else
			type = m_changedViewChoice;

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
		num_files=0;
		attachedFiles.clear();
		return "main";
	}
//	public String processSendEmail(){ return "results";}

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
		/////String emailarchive="/mailarchive/channel/"+getSiteID()+"/main";
		/////if (m_archiveMessage)
		if ((emailarchive != "") && (m_archiveMessage))
		{
			String attachment_info="<br/>";
			Attachment a=null;
	    	Iterator iter = attachedFiles.iterator();
	    	int i=0;
			while(iter.hasNext()) {
				a = (Attachment) iter.next();
				attachment_info+="<br/>";
				attachment_info+="Attachment #"+(i+1)+": "+a.getFilename()+"("+a.getSize()+" Bytes)";
				i++;
			}
			this.appendToArchive(emailarchive, fromString, subject, m_body+attachment_info);
		}

		//Send the emails
		for (Iterator i = emailusers.iterator(); i.hasNext();)
		{
			EmailUser euser = (EmailUser) i.next();

			String toEmail = euser.getEmail(); // u.getEmail();
			String toDisplay = euser.getDisplayname(); // u.getDisplayName();
			String toString = toDisplay + " <" + toEmail + ">";

			List headers = new ArrayList();
			if (isFCKeditor() || isHTMLArea())
				headers.add("content-type: text/html");
			else
				headers.add("content-type: text/plain");
			try
			{
/*
				m_emailService.send(fromString,// fromString
						       		toEmail,  // toString
									subject,   // subject
									m_body,	   // content
									null,   // headerToStr
									null, // replyToStr
									headers);
*/
	    		  Properties props = new Properties();

	    		  String smtp_server = ServerConfigurationService.getString("smtp@org.sakaiproject.email.api.EmailService");
	    		  String smtp_port = ServerConfigurationService.getString("smtp.port");
	    		  //System.out.println("smtp@org.sakaiproject.email.api.EmailService="+smtp_server);

	    		  //System.out.println("wysiwyg.editor="+ServerConfigurationService.getString("wysiwyg.editor"));
	    		  //System.out.println("content.upload.max="+ServerConfigurationService.getString("content.upload.max"));
	    		  //System.out.println("mailtool.max.num.attachment="+ServerConfigurationService.getString("mailtool.max.num.attachment"));
	    		  //System.out.println("mailtool.upload.directory="+ServerConfigurationService.getString("mailtool.upload.directory"));

	    		  props.put("mail.smtp.host", smtp_server);
	    		  props.put("mail.smtp.port", smtp_port);

	    		  Session s = Session.getInstance(props,null);

	    		  MimeMessage message = new MimeMessage(s);

	    		  InternetAddress from = new InternetAddress(fromString);
	    		  message.setFrom(from);
	    		  String toAddresses = toEmail;
	    		  //request.getParameter("to");
	    		  message.addRecipients(Message.RecipientType.TO, toAddresses);
	    		  //String ccAddresses = request.getParameter("cc");
	    		  //message.setRecipients(Message.RecipientType.CC, ccAddresses);
	    		  //String bccAddresses = request.getParameter("bcc");
	    		  //message.setRecipients(Message.RecipientType.BCC, bccAddresses);

	    		  //String subject = "Test Attachment";
	    			  //request.getParameter("subject");
	    		  message.setSubject(subject);
	    		  //String text = "test";
	    		  String text = m_body;
	    		  //request.getParameter("text");

	    		// String url1= "architecture_mailtool2_small.JPG";
	    		 //request.getParameter("url1");
	    		 //String url2= request.getParameter("url2");
	    		 //String url3= request.getParameter("url3");
	    		 //message.setText(text);
	    		 //String url1 = files[0];
	    		 //String url2 = files[1];
	    		 //String url3 = files[2];
	    		 //System.out.println(url1);

	    		 //String attachmentdirectory=ServerConfigurationService.getString("mailtool.upload.directory");
	    		 String attachmentdirectory=getUploadDirectory();
	    		 //System.out.println("attachmentdirectory="+attachmentdirectory);


	    		  		// Create the message part
	    		  			BodyPart messageBodyPart = new MimeBodyPart();

	    		  			// Fill the message
	    					String messagetype="";
	    					if (isFCKeditor() || isHTMLArea()){
	    						messagetype="text/html";
	    					}
	    					else{
	    						messagetype="text/plain";
	    					}
	    		  			//System.out.println("messagetype="+messagetype);
	    		  			//System.out.println("text="+text);
	    		  			//messageBodyPart.setText(text);
	    					messageBodyPart.setContent(text, messagetype);


	    					Multipart multipart = new MimeMultipart();
	    					multipart.addBodyPart(messageBodyPart);

	    					// Part two is attachment
	    					Attachment a=null;
	    			    	Iterator iter = attachedFiles.iterator();
	    					while(iter.hasNext()) {
	    						a = (Attachment) iter.next();
	    						messageBodyPart = new MimeBodyPart();
	    						DataSource source = new FileDataSource(attachmentdirectory + this.getCurrentUser().getUserid()+"-"+a.getFilename());
	    						messageBodyPart.setDataHandler(new DataHandler(source));
	    						messageBodyPart.setFileName(a.getFilename());
	    						multipart.addBodyPart(messageBodyPart);
	    					}

	    					message.setContent(multipart);

	    					Transport.send(message);
			}
			catch (Exception e)
			{
				//logger.debug("SWG Exception while trying to send the email: " + e.getMessage());
				// by SK 6/30/2006

				log.debug("SWG Exception while trying to send the email: " + e.getMessage());

			}

			if (i.hasNext())
			{
				m_results += toDisplay + "/ ";
			}
			else
			{
				m_results += toDisplay;
			}
		}

		//	Clear the Subject and Body of the Message
		m_subject = "";
		m_body = "";
//		files[0]="";sizes[0]="";
//		files[1]="";sizes[1]="";
//		files[2]="";sizes[2]="";
//		files[3]="";sizes[3]="";
//		files[4]="";sizes[4]="";
		num_files=0;


		attachedFiles.clear();

		/* Display Users with Bad Emails if the option is
		 * turned on.
		 */
		Boolean showBadEmails = getDisplayInvalidEmailAddr();

		if (showBadEmails.booleanValue() == true)
		{
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
						m_results += name + "/ ";
					else
						m_results += name;
				}
			}
		}

		return "results";
	}


	public void setViewChoice(String view)
	{
		if (m_changedViewChoice.equals(view))
		{
			m_buildNewView = false;
		}
		else
		{
			m_changedViewChoice = view;
			m_buildNewView = true;
		}
	}

	public String getViewChoice()
	{
		if (m_changedViewChoice.equals(""))
			return this.getRecipview();
		else
			return m_changedViewChoice;
	}

	public List /* SelectItemGroup */ getViewChoiceDropdown()
	{
		List selectItems = new ArrayList();

		SelectItem item = new SelectItem();
		item.setLabel("User");
		item.setValue("user");
		selectItems.add(item);

		item = new SelectItem();
		item.setLabel("Role");
		item.setValue("role");
		selectItems.add(item);

		item = new SelectItem();
		item.setLabel("Tree");
		item.setValue("tree");
		selectItems.add(item);

		item = new SelectItem();
		item.setLabel("Side By Side");
		item.setValue("sidebyside");
		selectItems.add(item);

		item = new SelectItem();
		item.setLabel("Foothill");
		item.setValue("foothill");
		selectItems.add(item);

		return selectItems;
	}

	protected RecipientSelector m_recipientSelector = null;
	public RecipientSelector getRecipientSelector()
	{
	 if ((m_recipientSelector == null) || (m_buildNewView == true))
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
		m_buildNewView = false;
	 }

		return m_recipientSelector;
	}

	/*
	 * Get Information from the Tool Config
	 */
	public String getSubjectPrefix()
	{
		String prefix = this.getConfigParam("subjectprefix");        //propsedit.getProperty("subjectprefix");
		if (prefix == null || prefix == "")
		{
			String titleDefault=getSiteTitle()+": ";
			return titleDefault;
			//return "";
		}
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
		if (recipview == null || recipview=="")
			return recipviewDefault;
		else
			return recipview;
	}

	public boolean isAllowedToSend()
	{
		String siteid = this.getConfigParam("mail.newlock.siteid");
		if (siteid == null)
			return true;

		if (siteid.equals(""))
			return true;

/////		String siteid="/site/"+getSiteID();
		//return m_realmService.unlock(this.getCurrentUser().getUserid(), "mail.new", siteid);
		return m_realmService.isAllowed(this.getCurrentUser().getUserid(), "mail.new", siteid);

	}

	public boolean isFCKeditor()
	{
		String editortype=this.getConfigParam("wysiwygeditor");
		if (editortype.equals("") || editortype==null)
		{
			editortype = ServerConfigurationService.getString("wysiwyg.editor");
			if (editortype == null)
				return false;

			if (editortype.equals(""))
				return false;

			if (editortype.equalsIgnoreCase("fckeditor"))
				return true;

			return false;
		}
		else if (editortype.equalsIgnoreCase("fckeditor"))
			return true;

		return false;
	}
	public boolean isHTMLArea()
	{
/*
		String editortype = this.getConfigParam("wysiwygeditor");
		if (editortype == null)
			return false;

		if (editortype.equals(""))
			return false;

		if (editortype.equalsIgnoreCase("htmlarea"))
			return true;

		return false;
*/
		String editortype=this.getConfigParam("wysiwygeditor");
		if (editortype.equals("") || editortype==null)
		{
			editortype = ServerConfigurationService.getString("wysiwyg.editor");
			if (editortype == null)
				return false;

			if (editortype.equals(""))
				return false;

			if (editortype.equalsIgnoreCase("htmlarea"))
				return true;

			return false;
		}
		else if (editortype.equalsIgnoreCase("htmlarea"))
			return true;

		return false;
	}

	public boolean isPlainTextEditor()
	{
		if (isFCKeditor() || isHTMLArea()) return false;

		return true;
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
/***		String siteid=getSiteID();
		String sitetype=getSiteType();

		if (sitetype.equals("project")){
			EmailRole emailrole=new EmailRole("/site/"+siteid, "maintain", "Maintain", "All Maintain");
			theroles.add(emailrole);
			EmailRole emailrole2=new EmailRole("/site/"+siteid, "access", "Access", "All Access");
			theroles.add(emailrole2);
		}
		else if (sitetype.equals("course")){
			EmailRole emailrole=new EmailRole("/site/"+siteid, "Instructor", "Instructor", "Instructors");
			theroles.add(emailrole);
			EmailRole emailrole2=new EmailRole("/site/"+siteid, "Student", "Student", "Students");
			theroles.add(emailrole2);
			EmailRole emailrole3=new EmailRole("/site/"+siteid, "Teaching Assistant", "TA", "TAs");
			theroles.add(emailrole3);
		}
***/
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
		/********
		String emailarchive = this.getConfigParam("emailarchive");
		if (emailarchive == null)
			return false;

		if (emailarchive.equals(""))
			return false;
		*********/
		return true;
	}

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

			AuthzGroup therealm = null;
			try {
				//therealm = m_realmService.getRealm(realmid);
				therealm = m_realmService.getAuthzGroup(realmid);
			} catch (Exception e) {}

			//Set users = therealm.getUsersWithRole(emailrole.getRoleid());
			Set users = therealm.getUsersHasRole(emailrole.getRoleid());
			List /* EmailUser */ mailusers = new ArrayList();
			for (Iterator j = users.iterator(); j.hasNext();)
			{
				String userid = (String) j.next();
				try {
					User theuser = m_userDirectoryService.getUser(userid);
					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
					mailusers.add(emailuser);
				} catch (Exception e) {}
			}
			Collections.sort(mailusers);
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
			//logger.debug("Exception: MailtoolBackend.getCurrentUser, " + e.getMessage());
//			 by SK 6/30/2006
			log.debug("Exception: MailtoolBackend.getCurrentUser, " + e.getMessage());

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
			//logger.debug("Mailtool: The channel: " + channelRef + " is null.");
//			 by SK 6/30/2006
			log.debug("Mailtool: The channel: " + channelRef + " is null.");

			return false;
		}
		List mailHeaders = new Vector();
//		mailHeaders.add(MailArchiveService.HEADER_OUTER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
//		mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
////		mailHeaders.add(MailArchiveService.HEADER_OUTER_CONTENT_TYPE + ": text/html; charset=ISO-8859-1");
////		mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": text/html; charset=ISO-8859-1");

		if (isFCKeditor() || isHTMLArea())
		{
			mailHeaders.add(MailArchiveService.HEADER_OUTER_CONTENT_TYPE + ": text/html; charset=ISO-8859-1");
			mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": text/html; charset=ISO-8859-1");
		}
		else
		{
			mailHeaders.add(MailArchiveService.HEADER_OUTER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
			mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": text/plain; charset=ISO-8859-1");
		}

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


	public void processFileUpload(ValueChangeEvent event) throws AbortProcessingException
	{

		Attachment att = new Attachment();
		int maxnumattachment=getMaxNumAttachment();
		if (num_files < maxnumattachment){
	    try
	    {
	        FileItem item = (FileItem) event.getNewValue();
	        String fieldName = item.getFieldName();
	        String fileName = item.getName();
	        long fileSize = item.getSize();
	        //System.out.println("processFileUpload(): item: " + item + " fieldname: " + fieldName + " filename: " + fileName + " length: " + fileSize);

            filename = item.getName();
			if (filename != null) {
				filename = FilenameUtils.getName(filename);
				att.setFilename(filename);
			}
			String ud = getUploadDirectory();
			File dir = new File(ud);

			if (isNotAlreadyUploaded(filename, attachedFiles)==false && dir.isDirectory())
			{
				//System.out.println("NAME: "+filename);
				System.out.println("SIZE: "+item.getSize());

				//File fNew= new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\temp\\", filename);
				//File fNew= new File(ServerConfigurationService.getString("mailtool.upload.directory"), this.getCurrentUser().getUserid()+"-"+filename);
				File fNew= new File(getUploadDirectory(), this.getCurrentUser().getUserid()+"-"+filename);

				// in IE, fi.getName() returns full path, while in FF, returns only name.
				//File fNew= new File("/upload/", fi.getName());
				//files[num_files]=filename;
				//sizes[num_files]=(String) Long.toString(fileSize);

				att.setSize((String) Long.toString(fileSize));
				att.setId(num_id);

				System.out.println(fNew.getAbsolutePath());
				num_files++;
				num_id++;
				item.write(fNew);

				attachedFiles.add(att);
			}

	    }
	    catch (Exception ex)
	    {
	        // handle exception
	    }
		} // end if
	}

	public List getAllAttachments() {

	return attachedFiles;
	}

	    public static String getFacesParamValue(FacesContext fc, String name) {
	        return (String) fc.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	    }

	    public void processRemoveFile()
	    {
	    	String id = getFacesParamValue(facesContext, "id");
//	    	int index=Integer.parseInt(id);
//	    	System.out.println("index="+index);
//	    	attachedFiles.remove(index);

	    	Attachment a=null;
	    	Attachment aForRemoval=null;
	    	Iterator iter = attachedFiles.iterator();
			while(iter.hasNext()) {
				a = (Attachment) iter.next();
				if(id.equals(a.getFilename())) {
					aForRemoval=a;
				}
			}
			attachedFiles.remove(aForRemoval);
	    	num_files--;
	    }

	    public boolean isNotAlreadyUploaded(String s, List attachedFiles)
	    {
	    	Attachment a=null;
	    	Iterator iter = attachedFiles.iterator();
			while(iter.hasNext()) {
				a = (Attachment) iter.next();
				if(s.equals(a.getFilename())) {
					return true;
				}
			}
	    	return false;
	    }
	    public void toggle_attachClicked()
	    {
	    	attachClicked  = attachClicked ? false: true;
	    }
}

