package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.event.ActionEvent;

public class SelectByTree {
	public class TableEntry
	{
		private EmailGroup m_emailgroup = null; /* Keep this around for good measure */
		private boolean m_collapsed = true;
		private EmailRole m_emailrole = null;
		private SelectByUserTable m_usertable = null;
		
		/* JSF Widgets */
		private boolean m_allSelected = false;
		
		public TableEntry(EmailGroup emailgroup)
		{
			m_emailgroup = emailgroup;
			m_emailrole = emailgroup.getEmailrole();
			m_usertable = new SelectByUserTable(emailgroup.getEmailusers());
		}
		
		public DataModel getUserTable()
		{
			return m_usertable.getUserRows();
		}
		
		public String getPluralRolename()
		{
			return m_emailrole.getRoleplural();
		}
		
		public boolean isCollapsed()
		{
			return m_collapsed;
		}
		
		public void setCollapsed(boolean collapsed)
		{
			m_collapsed = collapsed;
		}
		
		public void actionExpand(ActionEvent event)
		{
			m_collapsed = false;
		}
		
		public void actionCollapse(ActionEvent event)
		{
			m_collapsed = true;
		}
		
		public void setAllSelected(boolean value) { m_allSelected = value; }
		public boolean isAllSelected() { return m_allSelected; }
	}

	protected List /* TableEntry */ m_tablerows = new ArrayList();
	
	public SelectByTree(List /* EmailGroup */ groups)
	{
		m_tablerows.clear();
		for (Iterator i = groups.iterator(); i.hasNext();)
		{
			EmailGroup egroup = (EmailGroup) i.next();
			TableEntry te = new TableEntry(egroup);
			m_tablerows.add(te);
		}
	}
	
	public DataModel getRows()
	{
		DataModel returnmodel = new ListDataModel();
		returnmodel.setWrappedData(m_tablerows);
		return returnmodel;
	}
	
	public List /* EmailUser */ getSelectedUsers()
	{
		List /* EmailUser */ selectedusers = new ArrayList();
		
		for (Iterator i = m_tablerows.iterator(); i.hasNext();)
		{
			TableEntry te = (TableEntry) i.next();
			if ((te.isCollapsed() == true) && (te.isAllSelected() == true))
			{
				selectedusers.addAll(te.m_emailgroup.getEmailusers());
			}
			else
			{
				selectedusers.addAll(te.m_usertable.getSelectedUsers());
			}
		}
		
		return selectedusers;
	}
}
