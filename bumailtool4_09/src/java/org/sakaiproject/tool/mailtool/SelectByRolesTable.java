package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.sakaiproject.jsf.ToolBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.sakaiproject.tool.mailtool.EmailRole;

public class SelectByRolesTable //implements ToolBean 
{
	public class TableEntry
	{
		private boolean m_selected = false;
		private EmailRole m_emailrole = null;
		
		public TableEntry(EmailRole erole, boolean selected)
		{
			m_emailrole = erole; m_selected = selected;
		}
		
		
		public String getName() {
			return m_emailrole.getRoleplural();
		}
		
		public boolean isSelected() {
			return m_selected;
		}
		
		public void setSelected(boolean m_selected) {
			this.m_selected = m_selected;
		}
		
		public String getRoleId() {
			return m_emailrole.m_roleid;
		}
		
		public EmailRole getEmailRole()
		{
			return m_emailrole;
		}
	
	}
	
	List /** TableEntry **/ m_tablerows = new ArrayList();
	
	public SelectByRolesTable(List /* EmailRole */ roles)
	{
		m_tablerows.clear();
		for (Iterator i = roles.iterator(); i.hasNext();)
		{
			EmailRole erole = (EmailRole) i.next();
			TableEntry te = new TableEntry(erole, false);
			m_tablerows.add(te);
		}
	}
	
	public DataModel getRoles()
	{
		DataModel returnmodel = new ListDataModel();
		returnmodel.setWrappedData(m_tablerows);
		return returnmodel;
	}
	
	public List /* EmailRoles */ getSelectedRoles()
	{
		List /* String */ selectedroles = new ArrayList();
		for(Iterator i = m_tablerows.iterator(); i.hasNext();)
		{
			TableEntry te = (TableEntry) i.next();
			if (te.isSelected())
			{
				selectedroles.add(te.getEmailRole());
			}
		}
		
		return selectedroles;
	}
}
