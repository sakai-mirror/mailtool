/**********************************************************************************
* $URL: https://source.sakaiproject.org/svn/mailtool/trunk/mailtool/src/java/org/sakaiproject/tool/mailtool/RecipientSelector.java $
* $Id: RecipientSelector.java 27662 2007-03-22 19:44:57 +0000 (Thu, 22 Mar 2007) kimsooil@bu.edu $
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

import java.util.List;

import javax.faces.model.DataModel;

/**
 * Interface for RecipientSelectors (commented by kimsooil@bu.edu)
 * 
 * @author sgithens
 *
 */
public interface RecipientSelector {

	//Method to populate groups and roles
	public void populate(List /* EmailGroup */ emailgroups);
	
	//Method to get everyone to email
	public List /* EmailUser */ getSelectedUsers();
	
	public List /* EmailGroup */ getSelectedUsersByGroup();
	
	public DataModel getDataModel();
	
}
