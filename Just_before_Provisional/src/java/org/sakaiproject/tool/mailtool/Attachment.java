package org.sakaiproject.tool.mailtool;

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
