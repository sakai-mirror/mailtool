/**********************************************************************************
* $URL: https://source.sakaiproject.org/svn/mailtool/trunk/mailtool/src/java/org/sakaiproject/tool/mailtool/UserSelector.java $
* $Id: UserSelector.java 33292 2007-07-30 17:36:29 +0000 (Mon, 30 Jul 2007) kimsooil@bu.edu $
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

import javax.faces.model.DataModel;

/**
 * UserSelector, an implementation of RecipientSelector(commented by kimsooil@bu.edu)
 * 
 * @author sgithens
 *
 */
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
			//users.addAll(group.getEmailusers()); 

			// do not add "section" and "group" emailusers
			EmailRole role = (EmailRole) group.getEmailrole();
			if (role.roletype.equals("role")) users.addAll(group.getEmailusers()); 
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
