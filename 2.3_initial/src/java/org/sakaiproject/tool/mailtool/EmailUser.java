/**********************************************************************************
* $URL: https://source.sakaiproject.org/contrib/mailtool/branches/2.3-004/src/java/org/sakaiproject/tool/mailtool/EmailUser.java $
* $Id: EmailUser.java 2336 2006-10-13 19:43:07Z kimsooil@bu.edu $
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


public class EmailUser implements Comparable {
	String m_userid = "";
	String m_displayname = "";
	String m_email = "";
	
	public EmailUser(String userid, String displayname, String email)
	{
		m_userid = userid;
		m_displayname = displayname;
		m_email = email;
	}
	
	public boolean equals(EmailUser user)
	{
		if (user.getUserid().equals(m_userid) &&
			user.getEmail().equals(m_email))
		{
			return true;
		}
		else
			return false;
		
	}
	
	
	public String getDisplayname() {
		return m_displayname;
	}
	public void setDisplayname(String m_displayname) {
		this.m_displayname = m_displayname;
	}
	public String getEmail() {
		return m_email;
	}
	public void setEmail(String m_email) {
		this.m_email = m_email;
	}
	public String getUserid() {
		return m_userid;
	}
	public void setUserid(String m_userid) {
		this.m_userid = m_userid;
	}
	
	public String getNiceEmail()
	{
		String nicestring = "";
		
		if (m_displayname == "" || m_displayname == null)
			return m_email;
		else
			nicestring = m_displayname + " <" + m_email + ">";
		
		return nicestring;
	}

	public int compareTo(Object anotherEmailUser) throws ClassCastException
	{
		if (!(anotherEmailUser instanceof EmailUser))
			throw new ClassCastException("An EmailUser object expected.");
			    
		String anotherEmailUserName = ((EmailUser)
					anotherEmailUser).getDisplayname();  
		return (getDisplayname().compareTo (anotherEmailUserName));	       
	}
}