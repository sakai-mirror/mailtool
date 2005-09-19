package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;


/**
<h:dataTable rendered="#{Mailtool.selectByRole}" value="#{Mailtool.rolesTable}" var="role">
<h:column>
	<h:selectBooleanCheckbox value="#{role.selected}"/>
</h:column>
<h:column>
	<h:outputText value="#{role.name}" />
</h:column>
</h:dataTable>
**/

public class RoleSelector implements RecipientSelector {
	protected List /* EmailGroup */ m_initialGroups = null;
	protected HtmlDataTable m_widget = null;
	protected SelectByRolesTable m_tabledata = null;
	
	public void populate(List /* EmailGroup */ emailgroups)
	{
		m_initialGroups = emailgroups;
		
		List roles = new ArrayList();
		System.out.println("SWG Size of emailgroups: " + emailgroups.size());
		for (Iterator i = m_initialGroups.iterator(); i.hasNext();)
		{
			roles.add(((EmailGroup) i.next()).getEmailrole());
		}
		
		m_tabledata = new SelectByRolesTable(roles);
	}
	
	public List /* EmailUser */ getSelectedUsers()
	{
		List /* EmailRole */ roles = m_tabledata.getSelectedRoles();
		List /* EmailUser */ usersToReturn = new ArrayList();
		for (Iterator i = roles.iterator(); i.hasNext();)
		{
			EmailRole role = (EmailRole) i.next();
			for (Iterator j = m_initialGroups.iterator(); j.hasNext();)
			{
				EmailGroup group = (EmailGroup) j.next();
				if (role.getRoleid().equals(group.getEmailrole().getRoleid()))
				{
					usersToReturn.addAll(group.getEmailusers());
				}
			}
		}
		return usersToReturn;
	}
	
	public List /* EmailGroup */ getSelectedUsersByGroup()
	{
		return null;
	}

	/** Methods for JSP Fragment **/
	public DataModel getDataModel()
	{
		return this.m_tabledata.getRoles();
	}

	
}
