/**********************************************************************************
* $URL$
* $Id$
***********************************************************************************
*
* Copyright (c) 2006, 2007 The Sakai Foundation.
* 
* Licensed under the Educational Community License, Version 1.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at
* 
*      http://www.opensource.org/licenses/ecl1.php
* 
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License.
*
**********************************************************************************/
package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.cover.AuthzGroupService;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.site.api.Group;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.tool.api.ToolSession;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OptionsBean {
	protected static final int NUMBER_ROLES = 15;
	
	private String m_changedViewChoice = "";
	private String m_currentViewChoice = "";
	private boolean m_buildNewView = false;
	protected String m_mode="";
	protected String recipviewDefault="tree";
	protected boolean m_selectByRole = false;
	protected boolean m_selectByUser = false;
	protected boolean m_selectByTree = false;
	protected boolean m_selectSideBySide = false;
	protected boolean m_selectByFoothill = false;
	protected boolean m_sendmecopy = false;
	protected boolean EmailArchiveInSite=false;
	protected boolean m_archiveMessage = false;
	protected String m_replyto="";
	protected String m_textformat = "";
	private boolean showRenamingRolesClicked=false;
	private List renamedRoles = new ArrayList();
	protected String m_replytootheremail="";
	protected boolean m_sendmecopyInOptions = false;
	private boolean groupviewClicked=false;
	private boolean sectionviewClicked=false;
	private boolean GroupAwareRoleExist=false;
	private boolean groupAwareRoleviewClicked=false;
	protected boolean m_archiveMessageInOptions = false;
	private boolean allGroupSelected=false;
	private boolean allSectionSelected=false;
	private boolean allGroupAwareRoleSelected=false;
	
	private AuthzGroupService m_realmService;
	private AuthzGroup arole;
	private UserDirectoryService m_userDirectoryService;
	private SiteService siteService;
	protected Site currentSite = null;
	
	protected RecipientSelector m_recipientSelector = null;
	protected RecipientSelector m_recipientSelector1 = null;
	protected RecipientSelector m_recipientSelector2 = null;
	protected RecipientSelector m_recipientSelector3 = null;
	protected int num_groupawarerole=0;
	private boolean already_configured=false;
	protected String m_sitetype="";
	protected String m_siteid="";
	protected String m_realmid="";
	protected int num_groups=0;
	protected int num_sections=0;
	protected String groupAwareRoleFound="";
	protected String groupAwareRoleDefault="";
	protected String m_subject = "";
	protected String m_subjectprefix="";
	private int num_role_id=0;
	private int num_roles_renamed=0;

	private final Log log = LogFactory.getLog(this.getClass());

	
	public OptionsBean()
	{
		num_groups=0;
		num_sections=0;
		num_groupawarerole=0;
		setCurrentMode("compose");
		m_sitetype=getSiteType();
		m_siteid=getSiteID();
		m_realmid=getSiteRealmID();
		m_changedViewChoice = getRecipview();
		groupAwareRoleDefault=getGroupAwareRoleDefault();
		groupAwareRoleFound=getGroupAwareRole();
		
		setSelectorType();
		getRecipientSelectors();
		
		initializeCurrentRoles(); /* this initialization solves SAK-6810 */
		
		setMessageSubject(getSubjectPrefix().equals("")?getSubjectPrefixFromConfig():getSubjectPrefix());
		setSubjectPrefix(getSubjectPrefixFromConfig());
		setEmailArchiveInSite(isEmailArchiveAddedToSite());

		String reply=getConfigParam("replyto").trim().toLowerCase();
		if (reply.equals("") || reply.equals("yes")){
			setReplyToSelected("yes");			
		} else if (reply.equals("no")){
			setReplyToSelected("no");
		} else { // reply to other email
			setReplyToSelected("otheremail");			
			setReplyToOtherEmail(getConfigParam("replyto").trim().toLowerCase());
		}

		setSendMeCopyInOptions(getConfigParam("sendmecopy").trim().toLowerCase().equals("")!=true);
		setSendMeCopy(getConfigParam("sendmecopy").trim().toLowerCase().equals("yes"));
		
		setArchiveMessageInOptions(getConfigParam("emailarchive").trim().toLowerCase().equals("")!=true);
		setArchiveMessage(getConfigParam("emailarchive").trim().toLowerCase().equals("yes"));
		
		String textformat=getConfigParam("messageformat").trim().toLowerCase();
		if (textformat.equals("") || textformat.equals("htmltext"))
		{
			setTextFormat("htmltext");
		}
		else{
			setTextFormat("plaintext");
		}
		
		log.debug("Constructor");
	}
	
	public void setAuthzGroupService(AuthzGroupService service) { this.m_realmService = service; }
	public void setUserDirectoryService(UserDirectoryService service) { this.m_userDirectoryService = service; }

	protected String getSiteID()
	{
		return (ToolManager.getCurrentPlacement().getContext());
	}
	private String getSiteRealmID()
	{
		return ("/site/" + ToolManager.getCurrentPlacement().getContext());
	}
	protected String getSiteType()
	{
		//String sid=getSiteID();
		String type="";
		try{
		type=SiteService.getSite(m_siteid).getType();
		}
		catch(Exception e)
		{	
			log.debug("Exception: Mailtool.getSiteType(), " + e.getMessage());
		}
		return type;
	}
	public String getGroupAwareRoleDefault()
	{
		if (getSiteType().equals("course"))
			return "Student";
		if (getSiteType().equals("project"))
			return "access";
		return "";
	}
	public String getGroupAwareRole()
	{
/*		String groupAwareRole = this.getConfigParam("GroupAwareRole");
		if (groupAwareRole == null || groupAwareRole.trim().equals(""))
			return groupAwareRoleDefault;
		else
			return groupAwareRole.trim();
*/
		List /* EmailRole */ theroles = new ArrayList();
		String gar=ServerConfigurationService.getString("mailtool.group.aware.role");
		String[] gartokens=gar.split(",");

		try{
			arole=m_realmService.getAuthzGroup(m_realmid);
		} catch (Exception e){
			log.debug("Exception: Mailtool.getEmailRoles(), " + e.getMessage());
		}
		for (Iterator i = arole.getRoles().iterator(); i.hasNext(); ) {
				Role r = (Role) i.next();
				String rolename=r.getId();
				for (int t=0;t<gartokens.length;t++){
					if (gartokens[t].trim().equals(rolename.trim())) return rolename;
				}
		}
/*		if (gar!="" && gar!=null)
		{
			return gar.trim();
		}
*/
		return groupAwareRoleDefault;		
	}
	public boolean isEmailArchiveAddedToSite()
	{
		boolean hasEmailArchive =false;
		String toolid="sakai.mailbox";

		//String sid=getSiteID();
		try{
			Site site=SiteService.getSite(m_siteid);
/*			for (Iterator iPages = site.getPages().iterator();iPages.hasNext();)
			{
				SitePage page = (SitePage) iPages.next();
				for (Iterator iTools = page.getTools().iterator(); iTools.hasNext();)
				{
					ToolConfiguration tool = (ToolConfiguration) iTools.next();
					if (toolid.equals(tool.getTool().getId()))
					{
						hasEmailArchive=true;
						break;
					}
*/
					Collection toolsInSite = site.getTools(toolid);
					if (!toolsInSite.isEmpty())
					{
						hasEmailArchive=true;
					}
		}
		catch(Exception e)
		{	
			log.debug("Exception: Mailtool.isEmailArchiveAddedToSite(), " + e.getMessage());
		}
		return hasEmailArchive;

	}

	public void initializeCurrentRoles()
	{
		//String siteid=getSiteID();
		//String realmid="/site/"+siteid;
		String realmid=getSiteRealmID();
		try{
			arole=m_realmService.getAuthzGroup(realmid);
		} catch (Exception e){
			log.debug("Exception: Mailtool.initializeCurrentRoles(), " + e.getMessage());
		}
		for (Iterator i = arole.getRoles().iterator(); i.hasNext(); ) {
				Role r = (Role) i.next();
				String rolename=r.getId();
//				EmailRole emailrole=new EmailRole("/site/"+siteid, rolename, rolename, rolename);
	//			theroles.add(emailrole);

				// initialize "rename roles" in options
				Configuration c=new Configuration();
				c.setId(num_role_id);
				c.setRoleId(rolename);
				//c.setRealmid("/site/"+siteid);
				c.setRealmid(getSiteRealmID());
				c.setSingular(rolename);
				c.setPlural(rolename+"s");
//				c.setSingularNew("");
//				c.setPluralNew("");
				
				c.setSingularNew(getConfigParam("role"+(num_role_id+1)+"singular"));
				c.setPluralNew(getConfigParam("role"+(num_role_id+1)+"plural"));

				renamedRoles.add(c);
				num_role_id++;
				num_roles_renamed++;
				
				if (isGroupAwareRoleInSettings(rolename)){ setGroupAwareRoleExist(true); }

		}
/*			this is for detection group. it should be done in getEmailGroups()
 * 
			try{
			currentSite = siteService.getSite(siteid);
			}
			catch(Exception e) {}
			Collection groups = currentSite.getGroups();
			for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
			      Group currentGroup = (Group) groupIterator.next();
			      String groupname=currentGroup.getTitle();
			      EmailRole emailrole2=new EmailRole("/site/"+siteid, groupname, groupname, groupname);
			      theroles.add(emailrole2);
			}
			*/
	}
	protected String getSiteTitle()
	{
		//String sid=getSiteID();
		String title="";
		try{
			title=SiteService.getSite(m_siteid).getTitle();
		}
		catch (Exception e)
		{
			log.debug("Exception: Mailtool.getSiteTitle(), " + e.getMessage());
		}
		return title;
		
	}
	public String getSubjectPrefix()
	{
		return m_subjectprefix;
	}
	public void setSubjectPrefix(String prefix)
	{
		m_subjectprefix = prefix;
	}
	public String getSubjectPrefixFromConfig()
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
	public String getMessageSubject()
	{
		return m_subject;
	}
	
	public void setMessageSubject(String subject)
	{
		m_subject = subject;
	}
	protected String getConfigParam(String parameter)
	{
		String p=ToolManager.getCurrentPlacement().getPlacementConfig().getProperty(parameter);
		if (p==null) return "";
		return p;
	}
	public RecipientSelector getRecipientSelector()
	{
		getRecipientSelectors();
		
		return  m_recipientSelector;
	}

	public RecipientSelector getRecipientSelector_GroupAwareRole()
	{
		getRecipientSelectors();
		
		return  m_recipientSelector1;
	}
	public RecipientSelector getRecipientSelector_Group()
	{
		getRecipientSelectors();
		
		return  m_recipientSelector2;
	}

	public RecipientSelector getRecipientSelector_Section()
	{
		getRecipientSelectors();
		
		return  m_recipientSelector3;
	}
	
	
	public void getRecipientSelectors()
	{
	 if ((m_recipientSelector == null) || (m_buildNewView == true))
	 {
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
			m_recipientSelector1 = new TreeSelector();
			m_recipientSelector2 = new TreeSelector(); // groups
			m_recipientSelector3 = new TreeSelector(); // sections
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
		
		if (m_selectByTree == true){
			List emailGroups1 = getEmailGroupsByType("role");
			List emailGroups1_1 = getEmailGroupsByType("role_groupaware");
			List emailGroups2 = getEmailGroupsByType("group");
			List emailGroups3 = getEmailGroupsByType("section");
			m_recipientSelector.populate(emailGroups1);
			m_recipientSelector1.populate(emailGroups1_1);
			m_recipientSelector2.populate(emailGroups2);
			m_recipientSelector3.populate(emailGroups3);
		}
		else {
			List emailGroups = getEmailGroups();
			m_recipientSelector.populate(emailGroups);
		}
		m_buildNewView = false;
	 }
		
		//return m_recipientSelector;
	}
	public boolean isGroupAwareRoleInSettings(String role)
	{

		String gar=ServerConfigurationService.getString("mailtool.group.aware.role");
		String[] gartokens=gar.split(",");

		for (int i=0;i<gartokens.length;i++){
			if (gartokens[i].trim().equals(role.trim())) return true;
		}
		return false;
	}

	public List /* EmailRole */ getEmailRoles()
	{
		
		List /* EmailRole */ theroles = new ArrayList();
		List allgroups = new ArrayList();
		List allsections = new ArrayList();
		
		//String siteid=getSiteID();
		//String realmid="/site/"+siteid;
		//String realmid=getSiteRealmID();
		//String sitetype=getSiteType();
/*		
		if (sitetype.equals("project")){
			EmailRole emailrole=new EmailRole("/site/"+siteid, "maintain", "Maintain", "Maintain roles");
			theroles.add(emailrole);
			EmailRole emailrole2=new EmailRole("/site/"+siteid, "access", "Access", "Access roles");
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
*/
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
				//EmailRole emailrole = new EmailRole(rolerealm,rolename,rolesingular,roleplural);
				EmailRole emailrole=null;
				
				//if (rolesingular.equals("Student"))
				if (isGroupAwareRoleInSettings(rolename)){
					emailrole = new EmailRole(rolerealm,rolename,rolesingular,roleplural, "role_groupaware");
					num_groupawarerole++;
				}
				else 
					emailrole = new EmailRole(rolerealm,rolename,rolesingular,roleplural, "role");

				theroles.add(emailrole);
				already_configured=true;
			}
		} // for
		if (already_configured==false){
			try{
				arole=m_realmService.getAuthzGroup(m_realmid);
			} catch (Exception e){
				log.debug("Exception: Mailtool.getEmailRoles(), " + e.getMessage());
			}
			for (Iterator i = arole.getRoles().iterator(); i.hasNext(); ) {
					Role r = (Role) i.next();
					String rolename=r.getId();
					String singular="";
					String plural="";
					
					if (rolename.equals("maintain") || rolename.equals("access")){
						singular = rolename;
						plural = rolename+" users";
					}
					else {
						singular = rolename;
						plural = rolename+"s";
					}
					EmailRole emailrole=null;
					//EmailRole emailrole=new EmailRole("/site/"+siteid, rolename, singular, plural);
					//if (singular.equals("Student") || singular.equals("access"))
					if (isGroupAwareRoleInSettings(rolename)){
						emailrole=new EmailRole("/site/"+m_siteid, rolename, singular, plural, "role_groupaware");
						num_groupawarerole++;
					}
					else
						emailrole=new EmailRole("/site/"+m_siteid, rolename, singular, plural, "role");
					theroles.add(emailrole);
			}				
		}
		
		////////// adding groups as roles
		
		try{
			currentSite = siteService.getSite(m_siteid);
			}
			catch(Exception e) {}
			Collection groups = currentSite.getGroups();
			for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
			      Group currentGroup = (Group) groupIterator.next();
			      String groupname=currentGroup.getTitle();
			      String groupid=currentGroup.getProviderGroupId(); //???????????????????????????????
			      //EmailRole emailrole2=new EmailRole("/site/"+siteid, groupname, groupname, groupname);
			      EmailRole emailrole2=null;
			      if(currentGroup.getProperties().getProperty("sections_category") != null) {
			    	  emailrole2=new EmailRole(groupid, groupname, groupname, groupname, "section");
			    	  allsections.add(emailrole2);
			    	  num_sections++;
			      }
			      else{
			    	  emailrole2=new EmailRole(groupid, groupname, groupname, groupname, "group");
			    	  allgroups.add(emailrole2);
			    	  num_groups++;
			      }
		      
			      //theroles.add(emailrole2);
			}
			theroles.addAll(allgroups); // for sorted list in side-by-side view & scrolling list view
			theroles.addAll(allsections); // for sorted list ...
			////////////////
		return theroles;
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
			
			if (emailrole.roletype.equals("role"))
			{
				String realmid = emailrole.getRealmid();
				
				AuthzGroup therealm = null;
				try {
					//therealm = m_realmService.getRealm(realmid);
					therealm = m_realmService.getAuthzGroup(realmid);
				} catch (Exception e) {
					log.debug("Exception: Mailtool.getEmailGroups() #1, " + e.getMessage());
				}
				
				//Set users = therealm.getUsersWithRole(emailrole.getRoleid());
				Set users = therealm.getUsersHasRole(emailrole.getRoleid());
				List /* EmailUser */ mailusers = new ArrayList();
				for (Iterator j = users.iterator(); j.hasNext();)
				{
					String userid = (String) j.next();
					try {
						User theuser = m_userDirectoryService.getUser(userid);
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
/***						
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName().equals("") ? theuser.getEmail() : theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
***/
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						// also SAK-7539
						String firstname_for_display = "";
						String lastname_for_display = "";
						if (theuser.getFirstName().trim().equals("")){
							if (theuser.getEmail().trim().equals("") && theuser.getLastName().trim().equals(""))
								firstname_for_display = theuser.getDisplayId(); // fix for SAK-7539
							else
								firstname_for_display = theuser.getEmail();  // fix for SAK-7356
						}
						else {
							firstname_for_display = theuser.getFirstName();
						}
						
						lastname_for_display = theuser.getLastName();

						EmailUser emailuser = new EmailUser(theuser.getId(), firstname_for_display, lastname_for_display, theuser.getEmail());
						
						mailusers.add(emailuser);
					} catch (Exception e) {
						log.debug("Exception: Mailtool.getEmailGroups() #2, " + e.getMessage());
					}
				}
				Collections.sort(mailusers);
				EmailGroup thegroup = new EmailGroup(emailrole, mailusers);
				thegroups.add(thegroup);
			}
			
			else if (emailrole.roletype.equals("group"))
			{
				String sid = getSiteID();
				Site currentSite=null;
				try{
					currentSite = siteService.getSite(sid);
				}
				catch(Exception e)
				{
					log.debug("Exception: Mailtool.getEmailGroups() #3, " + e.getMessage());
				}
				Collection groups = currentSite.getGroups();
				/////Group agroup=currentSite.getGroup(emailrole.getRealmid()); //////?????????????????????
				Group agroup=null;
				for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
				      agroup = (Group) groupIterator.next();
				      String groupname=agroup.getTitle();
				      if (emailrole.getRoleid().equals(groupname)) break;
				}
				Set users2=agroup.getUsers(); ////////////////////////// something like that ---- Need to be tested !!!!!!
				List mailusers2 = new ArrayList();
				for (Iterator k= users2.iterator();k.hasNext();){
				   	  String userid2 = (String) k.next();
				   	  try {
				  		  User theuser2=m_userDirectoryService.getUser(userid2);
//				   		  EmailUser emailuser2 = new EmailUser(theuser2.getId(), theuser2.getSortName(), theuser2.getEmail());

							// trying to fix SAK-7356 (Guests are not included in recipient lists)
							// also SAK-7539
							String firstname_for_display = "";
							String lastname_for_display = "";
							if (theuser2.getFirstName().trim().equals("")){
								if (theuser2.getEmail().trim().equals("") && theuser2.getLastName().trim().equals(""))
									firstname_for_display = theuser2.getDisplayId(); // fix for SAK-7539
								else
									firstname_for_display = theuser2.getEmail();  // fix for SAK-7356
							}
							else {
								firstname_for_display = theuser2.getFirstName();
							}
							
							lastname_for_display = theuser2.getLastName();

							EmailUser emailuser2 = new EmailUser(theuser2.getId(), firstname_for_display, lastname_for_display, theuser2.getEmail());
				  		  
				  		  mailusers2.add(emailuser2);
				   	  } catch (Exception e) {}
				}
				Collections.sort(mailusers2);
				EmailGroup thegroup2 = new EmailGroup(emailrole, mailusers2);
				      thegroups.add(thegroup2);
			} // else
			else if (emailrole.roletype.equals("section"))
			{
				String sid = getSiteID();
				Site currentSite=null;
				try{
					currentSite = siteService.getSite(sid);
				}
				catch(Exception e)
				{
					log.debug("Exception: Mailtool.getEmailGroups() #3, " + e.getMessage());
				}
				Collection groups = currentSite.getGroups();
				/////Group agroup=currentSite.getGroup(emailrole.getRealmid()); //////?????????????????????
				Group agroup=null;
				for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
				      agroup = (Group) groupIterator.next();
				      String groupname=agroup.getTitle();
				      if (emailrole.getRoleid().equals(groupname)) break;
				}
				Set users2=agroup.getUsers(); ////////////////////////// something like that ---- Need to be tested !!!!!!
				List mailusers2 = new ArrayList();
				for (Iterator k= users2.iterator();k.hasNext();){
				   	  String userid2 = (String) k.next();
				   	  try {
				  		  User theuser2=m_userDirectoryService.getUser(userid2);
//				   		  EmailUser emailuser2 = new EmailUser(theuser2.getId(), theuser2.getSortName(), theuser2.getEmail());

							// trying to fix SAK-7356 (Guests are not included in recipient lists)
							// also SAK-7539
							String firstname_for_display = "";
							String lastname_for_display = "";
							if (theuser2.getFirstName().trim().equals("")){
								if (theuser2.getEmail().trim().equals("") && theuser2.getLastName().trim().equals(""))
									firstname_for_display = theuser2.getDisplayId(); // fix for SAK-7539
								else
									firstname_for_display = theuser2.getEmail();  // fix for SAK-7356
							}
							else {
								firstname_for_display = theuser2.getFirstName();
							}
							
							lastname_for_display = theuser2.getLastName();

							EmailUser emailuser2 = new EmailUser(theuser2.getId(), firstname_for_display, lastname_for_display, theuser2.getEmail());
				  		  
				  		  mailusers2.add(emailuser2);
				   	  } catch (Exception e) {}
				}
				Collections.sort(mailusers2);
				EmailGroup thegroup2 = new EmailGroup(emailrole, mailusers2);
				      thegroups.add(thegroup2);
			} // else

		}
		
		return thegroups;
	}
	
	public List /* EmailGroup */ getEmailGroupsByType(String roletypefilter)
	{
		List /* EmailGroup */ thegroups = new ArrayList();
		
		List emailroles = this.getEmailRoles();

		for (Iterator i = emailroles.iterator(); i.hasNext();)
		{
			EmailRole emailrole = (EmailRole) i.next();
			
			if (emailrole.roletype.equals("role") && roletypefilter.equals("role"))
			{
				String realmid = emailrole.getRealmid();
				
				AuthzGroup therealm = null;
				try {
					//therealm = m_realmService.getRealm(realmid);
					therealm = m_realmService.getAuthzGroup(realmid);
				} catch (Exception e) {
					log.debug("Exception: Mailtool.getEmailGroups() #1, " + e.getMessage());
				}
				
				//Set users = therealm.getUsersWithRole(emailrole.getRoleid());
				Set users = therealm.getUsersHasRole(emailrole.getRoleid());
				List /* EmailUser */ mailusers = new ArrayList();
				for (Iterator j = users.iterator(); j.hasNext();)
				{
					String userid = (String) j.next();
					try {
						User theuser = m_userDirectoryService.getUser(userid);
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
/***						
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName().equals("") ? theuser.getEmail() : theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
***/
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						// also SAK-7539
						String firstname_for_display = "";
						String lastname_for_display = "";
						if (theuser.getFirstName().trim().equals("")){
							if (theuser.getEmail().trim().equals("") && theuser.getLastName().trim().equals(""))
								firstname_for_display = theuser.getDisplayId(); // fix for SAK-7539
							else
								firstname_for_display = theuser.getEmail();  // fix for SAK-7356
						}
						else {
							firstname_for_display = theuser.getFirstName();
						}
						
						lastname_for_display = theuser.getLastName();

						EmailUser emailuser = new EmailUser(theuser.getId(), firstname_for_display, lastname_for_display, theuser.getEmail());
						//EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
						
						mailusers.add(emailuser);
					} catch (Exception e) {
						log.debug("Exception: Mailtool.getEmailGroups() #2, " + e.getMessage());
					}
				}
				Collections.sort(mailusers);
				EmailGroup thegroup = new EmailGroup(emailrole, mailusers);
				thegroups.add(thegroup);
			}
			
			else if (emailrole.roletype.equals("group") && roletypefilter.equals("group"))
			{
				String sid = getSiteID();
				Site currentSite=null;
				try{
					currentSite = siteService.getSite(sid);
				}
				catch(Exception e)
				{
					log.debug("Exception: Mailtool.getEmailGroups() #3, " + e.getMessage());
				}
				Collection groups = currentSite.getGroups();
				/////Group agroup=currentSite.getGroup(emailrole.getRealmid()); //////?????????????????????
				Group agroup=null;
				for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
				      agroup = (Group) groupIterator.next();
				      String groupname=agroup.getTitle();
				      if (emailrole.getRoleid().equals(groupname)) break;
				}
				
				//Set users2=agroup.getUsers(); ////////////////////////// something like that ---- Need to be tested !!!!!!
				// filtering non-group-aware Role users
				//
				Set users2=agroup.getUsersHasRole(groupAwareRoleFound);
				List mailusers2 = new ArrayList();
				for (Iterator k= users2.iterator();k.hasNext();){
				   	  String userid2 = (String) k.next();
				   	  try {
				  		  User theuser2=m_userDirectoryService.getUser(userid2);
//				   		  EmailUser emailuser2 = new EmailUser(theuser2.getId(), theuser2.getSortName(), theuser2.getEmail());

							// trying to fix SAK-7356 (Guests are not included in recipient lists)
							// also SAK-7539
							String firstname_for_display = "";
							String lastname_for_display = "";
							if (theuser2.getFirstName().trim().equals("")){
								if (theuser2.getEmail().trim().equals("") && theuser2.getLastName().trim().equals(""))
									firstname_for_display = theuser2.getDisplayId(); // fix for SAK-7539
								else
									firstname_for_display = theuser2.getEmail();  // fix for SAK-7356
							}
							else {
								firstname_for_display = theuser2.getFirstName();
							}
							
							lastname_for_display = theuser2.getLastName();

							EmailUser emailuser2 = new EmailUser(theuser2.getId(), firstname_for_display, lastname_for_display, theuser2.getEmail());
				  		  
				  		  mailusers2.add(emailuser2);
				   	  } catch (Exception e) {}
				}
				Collections.sort(mailusers2);
				EmailGroup thegroup2 = new EmailGroup(emailrole, mailusers2);
				      thegroups.add(thegroup2);
			} // else
			else if (emailrole.roletype.equals("section") && roletypefilter.equals("section"))
			{
				String sid = getSiteID();
				Site currentSite=null;
				try{
					currentSite = siteService.getSite(sid);
				}
				catch(Exception e)
				{
					log.debug("Exception: Mailtool.getEmailGroups() #3, " + e.getMessage());
				}
				Collection groups = currentSite.getGroups();
				/////Group agroup=currentSite.getGroup(emailrole.getRealmid()); //////?????????????????????
				Group agroup=null;
				for (Iterator groupIterator = groups.iterator(); groupIterator.hasNext();){
				      agroup = (Group) groupIterator.next();
				      String groupname=agroup.getTitle();
				      if (emailrole.getRoleid().equals(groupname)) break;
				}
				//Set users2=agroup.getUsers(); ////////////////////////// something like that ---- Need to be tested !!!!!!
				// filtering out non-group-aware Role users
				Set users2=agroup.getUsersHasRole(groupAwareRoleFound);
				
				List mailusers2 = new ArrayList();
				for (Iterator k= users2.iterator();k.hasNext();){
				   	  String userid2 = (String) k.next();
				   	  try {
				  		  User theuser2=m_userDirectoryService.getUser(userid2);
//				   		  EmailUser emailuser2 = new EmailUser(theuser2.getId(), theuser2.getSortName(), theuser2.getEmail());

							// trying to fix SAK-7356 (Guests are not included in recipient lists)
							// also SAK-7539
							String firstname_for_display = "";
							String lastname_for_display = "";
							if (theuser2.getFirstName().trim().equals("")){
								if (theuser2.getEmail().trim().equals("") && theuser2.getLastName().trim().equals(""))
									firstname_for_display = theuser2.getDisplayId(); // fix for SAK-7539
								else
									firstname_for_display = theuser2.getEmail();  // fix for SAK-7356
							}
							else {
								firstname_for_display = theuser2.getFirstName();
							}
							
							lastname_for_display = theuser2.getLastName();

							EmailUser emailuser2 = new EmailUser(theuser2.getId(), firstname_for_display, lastname_for_display, theuser2.getEmail());
				  		  
				  		  mailusers2.add(emailuser2);
				   	  } catch (Exception e) {}
				}
				Collections.sort(mailusers2);
				EmailGroup thegroup2 = new EmailGroup(emailrole, mailusers2);
				      thegroups.add(thegroup2);
			} // else
			else if (emailrole.roletype.equals("role_groupaware") && roletypefilter.equals("role_groupaware"))
			{
				String realmid = emailrole.getRealmid();
				
				AuthzGroup therealm = null;
				try {
					//therealm = m_realmService.getRealm(realmid);
					therealm = m_realmService.getAuthzGroup(realmid);
				} catch (Exception e) {
					log.debug("Exception: Mailtool.getEmailGroups() #1, " + e.getMessage());
				}
				
				//Set users = therealm.getUsersWithRole(emailrole.getRoleid());
				Set users = therealm.getUsersHasRole(emailrole.getRoleid());
				List /* EmailUser */ mailusers = new ArrayList();
				for (Iterator j = users.iterator(); j.hasNext();)
				{
					String userid = (String) j.next();
					try {
						User theuser = m_userDirectoryService.getUser(userid);
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
	//					EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
/***						
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getFirstName().equals("") ? theuser.getEmail() : theuser.getFirstName(), theuser.getLastName(), theuser.getEmail());
***/
						// trying to fix SAK-7356 (Guests are not included in recipient lists)
						// also SAK-7539
						String firstname_for_display = "";
						String lastname_for_display = "";
						if (theuser.getFirstName().trim().equals("")){
							if (theuser.getEmail().trim().equals("") && theuser.getLastName().trim().equals(""))
								firstname_for_display = theuser.getDisplayId(); // fix for SAK-7539
							else
								firstname_for_display = theuser.getEmail();  // fix for SAK-7356
						}
						else {
							firstname_for_display = theuser.getFirstName();
						}
						
						lastname_for_display = theuser.getLastName();

						EmailUser emailuser = new EmailUser(theuser.getId(), firstname_for_display, lastname_for_display, theuser.getEmail());
						//EmailUser emailuser = new EmailUser(theuser.getId(), theuser.getSortName(), theuser.getEmail());
						
						mailusers.add(emailuser);
					} catch (Exception e) {
						log.debug("Exception: Mailtool.getEmailGroups() #4, " + e.getMessage());
					}
				}
				Collections.sort(mailusers);
				EmailGroup thegroup = new EmailGroup(emailrole, mailusers);
				thegroups.add(thegroup);
			} // else


		}
		
		return thegroups;
	}		

	public String processGoToComposeByCancel()
	{
		m_changedViewChoice=m_currentViewChoice;
		m_buildNewView = true;
		setCurrentMode("compose");
		return "compose";
	}	
	public String getViewChoice()
	{
		if (m_changedViewChoice.equals(""))
			return this.getRecipview();
		else
			return m_changedViewChoice;
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
	public String getRecipview()
	{
		//String recipview = m_toolConfig.getPlacementConfig().getProperty("recipview");
		String recipview = this.getConfigParam("recipview");
		if (recipview == null || recipview.trim().equals(""))
			return recipviewDefault;
		else 
			return recipview;
	}
	public List /* SelectItemGroup */ getViewChoiceDropdown()
	{
		List selectItems = new ArrayList();
		
		SelectItem item = new SelectItem();
		item.setLabel("Users"); // User
		item.setValue("user");
		selectItems.add(item);
/*		
		item = new SelectItem();
		item.setLabel("Roles"); // Role
		item.setValue("role");
		selectItems.add(item);
*/		
		item = new SelectItem();
		item.setLabel("Users by Role"); // Tree
		item.setValue("tree");
		selectItems.add(item);
		
		item = new SelectItem();
		item.setLabel("Side-by-Side"); // Side By Side
		item.setValue("sidebyside");
		selectItems.add(item);
		
		item = new SelectItem();
		item.setLabel("Scrolling List"); // Foothill
		item.setValue("foothill");
		selectItems.add(item);
		
		return selectItems;
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
		}
		else if (type.equals("user"))
		{
			m_selectByUser = true;
		}
		else if (type.equals("tree"))
		{
			m_selectByTree = true;
		}
		else if (type.equals("sidebyside"))
		{
			m_selectSideBySide = true;
		}
		else if (type.equals("foothill"))
		{
			m_selectByFoothill = true;
		}
		else /* default to role */
		{
			m_selectByRole = true;
		}
	}

	public String getCurrentMode()
	{
		return m_mode;
	}
	public void setCurrentMode(String m)
	{
		this.m_mode=m;
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
	public boolean isSendMeCopy()
	{
		return m_sendmecopy;
	}
	public void setSendMeCopy(boolean value)
	{
		m_sendmecopy = value;
	}
	public void setSendMeCopyInOptions(boolean value)
	{
		m_sendmecopyInOptions = value;
	}
	public boolean isEmailArchiveInSite() {
		return EmailArchiveInSite;
	}
	public void setEmailArchiveInSite(boolean emailArchiveInSite) {
		EmailArchiveInSite = emailArchiveInSite;
	}
	public boolean isArchiveMessage()
	{
		return m_archiveMessage;
	}
	public void setArchiveMessage(boolean value)
	{
		m_archiveMessage = value;
	}
	public void setArchiveMessageInOptions(boolean value)
	{
		m_archiveMessageInOptions = value;
	}
	public String getReplyToSelected() {
	    return m_replyto;
	}
	public void setReplyToSelected(String r) {
	    this.m_replyto = r;
	}
	public String getTextFormat()
	{
		return m_textformat;
	}
	public void setTextFormat(String format)
	{
		m_textformat = format;
	}
	public boolean isShowRenamingRoles()
	{
		String rename=ServerConfigurationService.getString("mailtool.show.renaming.roles");
		if (rename!="" && rename!=null)
		{
			return (rename.trim().toLowerCase().equals("yes") || rename.trim().toLowerCase().equals("true") ? true : false); 
		}
		return false;
	}
	public boolean isShowRenamingRolesClicked() {
		return showRenamingRolesClicked;
	}
	public void setShowRenamingRolesClicked(boolean showRenamingRolesClicked) {
		this.showRenamingRolesClicked = showRenamingRolesClicked;
	}
	public void toggle_showRemainingRoleClicked()
    {
		showRenamingRolesClicked = showRenamingRolesClicked ?  false : true;
    }
	public List getRenamedRoles() {

		return renamedRoles;	
	}
	protected void setConfigParam(String parameter, String newvalue)
	{
		ToolManager.getCurrentPlacement().getPlacementConfig().setProperty(parameter, newvalue);
	//	ToolManager.getCurrentPlacement().save(); // will be saved in processUpdateOptions 
	}
	public String processUpdateOptions()
	{
		if (isShowRenamingRoles()){
			int i=1;
			Configuration c=null;
			Iterator iter = renamedRoles.iterator();
			
			while (iter.hasNext()){
				c=(Configuration) iter.next();
				if (c.getSingularNew().trim().equals("")!=true && c.getSingularNew()!=null) setConfigParam("role"+i+"singular", c.getSingularNew());
				if (c.getPluralNew().trim().equals("")!=true && c.getPluralNew()!=null) setConfigParam("role"+i+"plural", c.getPluralNew());
				i++;
			}
		}
		
		//setViewChoice(getViewChoice());
		setConfigParam("recipview", getViewChoice());
		setConfigParam("sendmecopy", isSendMeCopy() ? "yes": "no");
		setConfigParam("emailarchive", isArchiveMessage() ? "yes": "no");
		String reply = getReplyToSelected().trim().toLowerCase();
		if (reply.equals("yes")){
			setConfigParam("replyto", "yes");
		} else if (reply.equals("no")){
			setConfigParam("replyto", "no");
		} else if (reply.equals("otheremail")){
			setConfigParam("replyto", getReplyToOtherEmail().trim());
		}
		if (getTextFormat().trim().toLowerCase().equals("htmltext")){
			setConfigParam("messageformat", "htmltext");
		}
		else{
			setConfigParam("messageformat", "plaintext");
		}
		
		ToolManager.getCurrentPlacement().save(); 
		
		// reset Mailtool (with updated options)
		ToolSession ts = SessionManager.getCurrentSession().getToolSession(ToolManager.getCurrentPlacement().getId());
		ts.clearAttributes();
		setCurrentMode("compose");
		return "compose"; // go to Compose
	}
	public String getReplyToOtherEmail()
	{
		return m_replytootheremail;
	}
	public void setReplyToOtherEmail(String email)
	{
		m_replytootheremail = email;
	}
	public boolean isGroupAwareRoleExist() {
		return GroupAwareRoleExist;
	}
	public void setGroupAwareRoleExist(boolean groupAwareRoleExist) {
		GroupAwareRoleExist = groupAwareRoleExist;
	}
	public boolean isGroupAwareRoleviewClicked() {
		return groupAwareRoleviewClicked;
	}
	public void setGroupAwareRoleviewClicked(boolean groupAwareRoleviewClicked) {
		this.groupAwareRoleviewClicked = groupAwareRoleviewClicked;
	}
	public boolean isGroupviewClicked() {
		return groupviewClicked;
	}

	public void setGroupviewClicked(boolean groupviewClicked) {
		this.groupviewClicked = groupviewClicked;
	}
	public boolean isSectionviewClicked() {
		return sectionviewClicked;
	}

	public void setSectionviewClicked(boolean sectionviewClicked) {
		this.sectionviewClicked = sectionviewClicked;
	}
	public int getNum_sections() {
		return num_sections;
	}

	public void setNum_sections(int num_sections) {
		this.num_sections = num_sections;
	}

	public int getNum_groups() {
		return num_groups;
	}

	public void setNum_groups(int num_groups) {
		this.num_groups = num_groups;
	}
	public boolean isAllGroupAwareRoleSelected() {
		return allGroupAwareRoleSelected;
	}
	public void setAllGroupAwareRoleSelected(boolean allGroupAwareRoleSelected) {
		this.allGroupAwareRoleSelected = allGroupAwareRoleSelected;
	}
	public boolean isAllGroupSelected() {
		return allGroupSelected;
	}

	public void setAllGroupSelected(boolean allGroupSelected) {
		this.allGroupSelected = allGroupSelected;
	}
	public boolean isAllSectionSelected() {
		return allSectionSelected;
	}

	public void setAllSectionSelected(boolean allSectionSelected) {
		this.allSectionSelected = allSectionSelected;
	}
}
