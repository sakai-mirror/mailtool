package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.DataModel;

public class UserSelector implements RecipientSelector {
	SelectByUserTable m_usertable = null;
	List /* EmailGroup */ m_initialgroups = null;
	
	public UserSelector()
	{
		
	}
	
	public void populate(List emailgroups) {
		m_initialgroups = emailgroups;
		
		List /* EmailUser */ users = new ArrayList();
		
		for (Iterator i = emailgroups.iterator(); i.hasNext();)
		{
			EmailGroup group = (EmailGroup) i.next();
			users.addAll(group.getEmailusers());
		}
		
		m_usertable = new SelectByUserTable(users);
	}

	public List getSelectedUsers() {
		return m_usertable.getSelectedUsers();
	}

	public List getSelectedUsersByGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Methods for JSP Fragment **/
	public DataModel getDataModel()
	{
		return m_usertable.getUserRows();
	}
	
}
