/**********************************************************************************
* $URL:https://source.sakaiproject.org/contrib/mailtool/branches/2.4.refactor/src/java/org/sakaiproject/tool/mailtool/Configuration.java $
* $Id:Configuration.java 3486 2007-02-14 19:52:13Z kimsooil@bu.edu $
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

public class Configuration {

	private int id;
	private String rid;
	private String realmid;
	private String singular;
	private String singularNew;
	private String plural;
	private String pluralNew;
	
	public Configuration() {
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getRoleId()
	{
		return rid;
	}
	public void setRoleId(String rid)
	{
		this.rid=rid;
	}
	public String getRealmid() {
		return realmid;
	}

	public void setRealmid(String r) {
		this.realmid = r;
	}
	public String getSingular()
	{
		return singular;
	}
	public void setSingular(String s)
	{
		this.singular=s;
	}
	public String getPlural()
	{
		return plural;
	}
	public void setPlural(String p)
	{
		this.plural=p;
	}
	public String getSingularNew()
	{
		return singularNew;
	}
	public void setSingularNew(String s)
	{
		this.singularNew=s;
	}
	public String getPluralNew()
	{
		return pluralNew;
	}
	public void setPluralNew(String p)
	{
		this.pluralNew=p;
	} 
}
