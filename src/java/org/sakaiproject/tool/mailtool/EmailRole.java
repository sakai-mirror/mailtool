package org.sakaiproject.tool.mailtool;

public class EmailRole {
	String m_realmid = "";
	String m_roleid = "";
	String m_rolesingular = "";
	String m_roleplural = "";
	
	public EmailRole(String realmid, String roleid, String rolesingular, String roleplural)
	{
		m_realmid = realmid;
		m_roleid = roleid;
		m_rolesingular = rolesingular;
		m_roleplural = roleplural;
	}
	
	public String getRealmid() {
		return m_realmid;
	}
	
	public void setRealmid(String m_realmid) {
		this.m_realmid = m_realmid; 
	}
	
	public String getRoleid() {
		return m_roleid;
	}
	public void setRoleid(String m_roleid) {
		this.m_roleid = m_roleid;
	}
	public String getRoleplural() {
		return m_roleplural;
	}
	public void setRoleplural(String m_roleplural) {
		this.m_roleplural = m_roleplural;
	}
	public String getRolesingular() {
		return m_rolesingular;
	}
	public void setRolesingular(String m_rolesingular) {
		this.m_rolesingular = m_rolesingular;
	}
}
