/*
 *
 */
package org.sakaiproject.tool.mailtool;

import org.apache.commons.fileupload.*;
import org.apache.commons.io.*;
import javax.faces.event.*;
import javax.faces.event.AbortProcessingException;

import java.util.*;
import java.io.*;

public class UploadBean {
	private String files[];


	private String filename;
	private int num_files;
	public boolean is_uploadFilesAvailable;
	
	public UploadBean()
	{

		files=new String[]{"", "", ""};
		filename="";
		num_files=0;
		is_uploadFilesAvailable=false;
	}
	public boolean isUploadFilesAvailable()
	{
		if (this.num_files > 0) return true;
		
		return false;
	}
	public String getfilename()
	{
		return filename;
	}
	public void setfilename(String filename)
	{
		this.filename=filename;
	}
	public void setfiles(String files[]) {
	  this.files = files;
	}
	public String[] getfiles() {
	  return this.files;
	}

	public int getnum_files()
	{
		return this.num_files;
	}
	public void setnum_files(int num_files)
	{
		this.num_files=num_files;
	}

	
	public void processFileUpload(ValueChangeEvent event) throws AbortProcessingException
	{
	    try
	    {
	        FileItem item = (FileItem) event.getNewValue();
	        String fieldName = item.getFieldName();
	        String fileName = item.getName();
	        long fileSize = item.getSize();
	        //System.out.println("processFileUpload(): item: " + item + " fieldname: " + fieldName + " filename: " + fileName + " length: " + fileSize);
	   
            filename = item.getName();
			if (filename != null) {
				filename = FilenameUtils.getName(filename);
			}
            System.out.println("\nNAME: "+filename+"<br/>");
            System.out.println("SIZE: "+item.getSize()+"<br/>");

            File fNew= new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\temp\\", filename);
            // in IE, fi.getName() returns full path, while in FF, returns only name.
            //File fNew= new File("/upload/", fi.getName());
            files[num_files]=filename;

            System.out.println(fNew.getAbsolutePath()+"<br/>");
            num_files++;
            item.write(fNew);
    		
	    }
	    catch (Exception ex)
	    {
	        // handle exception
	    }
	}	
	/***************
	public String processMultipleFileUpload(){
	        //out.println("Content Type ="+request.getContentType()+"<br/>");

	        DiskFileUpload fu = new DiskFileUpload();
	        // If file size exceeds, a FileUploadException will be thrown
	        fu.setSizeMax(10000000);

	        //HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

	        List fileItems = fu.parseRequest(request);
	        Iterator itr = fileItems.iterator();

	        while(itr.hasNext()) {
	          FileItem fi = (FileItem)itr.next();

	          //Check if not form field so as to only handle the file inputs
	          //else condition handles the submit button input
	          if(!fi.isFormField()) {
	            //System.out.println(fi.getOutputStream().toString());

				// the following is for correcting IE browser's full path returning...
	            filename = fi.getName();
				if (filename != null) {
					filename = FilenameUtils.getName(filename);
				}
	            System.out.println("\nNAME: "+filename+"<br/>");
	            System.out.println("SIZE: "+fi.getSize()+"<br/>");

	            File fNew= new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\temp\\", filename);
	            // in IE, fi.getName() returns full path, while in FF, returns only name.
	            //File fNew= new File("/upload/", fi.getName());
	            files[num_files]=filename;

	            System.out.println(fNew.getAbsolutePath()+"<br/>");
	            num_files++;
	            fi.write(fNew);
	          }
	          else {
	            //System.out.println("Field ="+fi.getFieldName());
	          }
	        }
	}
	***********/
}
