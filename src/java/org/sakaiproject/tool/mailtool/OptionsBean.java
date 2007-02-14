/**********************************************************************************
* $URL:$
* $Id:$
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
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.ToolSession;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;

public class OptionsBean {
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


	

	protected String getConfigParam(String parameter)
	{
		String p=ToolManager.getCurrentPlacement().getPlacementConfig().getProperty(parameter);
		if (p==null) return "";
		return p;
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
				//setConfigParam("role"+i+"id", c.getRoleId()); // should not be changed
				//setConfigParam("role"+i+"realmid", c.getRealmid()); // should not be changed. So not shown in options
				if (c.getSingularNew().trim().equals("")!=true && c.getSingularNew()!=null) setConfigParam("role"+i+"singular", c.getSingularNew());
				if (c.getPluralNew().trim().equals("")!=true && c.getPluralNew()!=null) setConfigParam("role"+i+"plural", c.getPluralNew());
				i++;
			}
		}
/*****			
		if (getSubjectPrefix().equals("")!=true && getSubjectPrefix()!=null){
			//setMessageSubject(getSubjectPrefix());
			setConfigParam("subjectprefix", getSubjectPrefix());

		}
*****/			
		//setViewChoice(getViewChoice());
		setConfigParam("recipview", getViewChoice());

/***			
		if (isSendMeCopyInOptions()){
			setConfigParam("sendmecopy", isSendMeCopy() ? "yes": "no");
		}
		else{
			setConfigParam("sendmecopy", "");
		}
		if (isArchiveMessageInOptions()){
			setConfigParam("emailarchive", isArchiveMessage() ? "yes": "no");
		}
		else {
			setConfigParam("emailarchive", "");
		}
***/			
		setConfigParam("sendmecopy", isSendMeCopy() ? "yes": "no");
		setConfigParam("emailarchive", isArchiveMessage() ? "yes": "no");
/*			
		if (isReplyToSender()){
			setConfigParam("replyto", "yes");
		}
		else if (isReplyToOther()){
			setConfigParam("replyto", getReplyToOtherEmail());
		}
		else if (isDoNotReply()){
			setConfigParam("replyto", "no");
		}
*/
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


}
