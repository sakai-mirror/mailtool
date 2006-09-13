/*
 *
 */
package org.sakaiproject.tool.mailtool;

import java.io.*;

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.fileupload.FileItem;

public class Upload {//
    public String processFileUpload(ValueChangeEvent event)
    throws AbortProcessingException
{
UIComponent component = event.getComponent();
Object newValue = event.getNewValue();
Object oldValue = event.getOldValue();
PhaseId phaseId = event.getPhaseId();
Object source = event.getSource();
System.out.println("processFileUpload() event: " + event + " component: "
        + component + " newValue: " + newValue + " oldValue: " + oldValue
        + " phaseId: " + phaseId + " source: " + source);

if (newValue instanceof String) return "String";
if (newValue == null) return "null";

// must be a FileItem
try
{
    FileItem item = (FileItem) event.getNewValue();
    String fieldName = item.getFieldName();
    String fileName = item.getName();
    long fileSize = item.getSize();
    System.out.println("processFileUpload(): item: " + item + " fieldname: " + fieldName + " filename: " + fileName + " length: " + fileSize);
    
    // Read the file as a stream (may be more memory-efficient)
    InputStream fileAsStream = item.getInputStream();

    // Read the contents as a byte array
    //byte[] fileContents = item.get();
    
    // now process the file.  Do application-specific processing
    // such as parsing the file, storing it in the database,
    // or whatever needs to happen with the uploaded file.
}
catch (Exception ex)
{
    // handle exception
}
return "results";
}


}