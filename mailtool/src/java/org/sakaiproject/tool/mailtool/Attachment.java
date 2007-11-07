/**********************************************************************************
* $URL: https://source.sakaiproject.org/svn/mailtool/trunk/mailtool/src/java/org/sakaiproject/tool/mailtool/Attachment.java $
* $Id: Attachment.java 27662 2007-03-22 19:44:57 +0000 (Thu, 22 Mar 2007) kimsooil@bu.edu $
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

/**
 * Attachment (info) class in Mailtool
 * 
 * @author kimsooil
 *
 */
public class Attachment {

	private int id;
	private String filename;
	private String size;
	private boolean isSelected;
	
	public Attachment() {
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String f) {
		this.filename = f;
	}

	public String getSize()
	{
		return size;
	}
	public void setSize(String s)
	{
		this.size=s;
	}
	public void setSelected(boolean selected) {
		
		this.isSelected = selected;
	}
	
	public boolean isSelected() {
		
		return isSelected;
	}
 }
