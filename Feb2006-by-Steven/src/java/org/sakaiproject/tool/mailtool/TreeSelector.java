
package org.sakaiproject.tool.mailtool;

import java.util.List;

import javax.faces.model.DataModel;


public class TreeSelector implements RecipientSelector {
	SelectByTree m_tree = null;
	List /* EmailGroup */ m_initialgroups = null;

	public void populate(List emailgroups) {
		m_initialgroups = emailgroups;
		
		m_tree = new SelectByTree(emailgroups);
	}

	public List getSelectedUsers() {
		return m_tree.getSelectedUsers();
	}

	public List getSelectedUsersByGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Methods for JSP Fragment **/
	public DataModel getDataModel()
	{
		return m_tree.getRows();
	}

}
