
package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;


public class SideBySideSelector implements RecipientSelector {
	List /* emailgroups */ m_initialgroups = null;
	
	SideBySideModel m_model = null;
	
	

	public void populate(List emailgroups) {
		//logger.debug("SWG SideBySide Popsize: " + emailgroups.size());
		m_model = new SideBySideModel(emailgroups);
	}

	public List /* EmailUser */ getSelectedUsers() {
		return m_model.getSelectedUsers();
	}

	public List getSelectedUsersByGroup() {
		// TODO Auto-generated method stub
		return new ArrayList();
	}

	// Methods for JSP Fragment
	public List /* SelectItem */ getSourceListbox() 
	{    
		return m_model.getSourceSelectItems();
	}
	
	public List /* SelectItem */ getSinkListbox()
	{   
		return m_model.getSinkSelectItems();
	}
	
	public List getSourceSelected()
	{
		return m_model.getSinkSelectItems();
	}
	
	public void setSourceSelected(List /* String */ selecteditems)
	{
		m_model.setSourceSelectedObjects(selecteditems);
	}

	public List getSinkSelected()
	{
		return m_model.getSinkSelectItems();
	}
	
	public void setSinkSelected(List /* String */ selecteditems)
	{
		m_model.setSinkSelectedObjects(selecteditems);
	}
	
	public String processAddButton()
	{
		m_model.actionSource();
		return "main";
	}
	
	public String processRemoveButton()
	{
		m_model.actionSink();
		return "main";
	}
}
