/**********************************************************************************
* $URL$
* $Id$
***********************************************************************************
*
* Copyright (c) 2006 The Sakai Foundation.
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

import javax.faces.component.html.HtmlDataTable;
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
		//logger.debug("Mailtool Size of emailgroups: " + emailgroups.size());
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
