<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai"%>
 
<f:view>
<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />

<f:verbatim>
<!DOCTYPE html
 PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
</f:verbatim>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Mailtool</title>
<%= request.getAttribute("sakai.html.head.css.base") %>
<%= request.getAttribute("sakai.html.head.css.skin") %>
<%= request.getAttribute("sakai.html.head.js") %>
<%--
<SCRIPT LANGUAGE="JavaScript">
   function popUp(URL) {
     window.open(URL, '',    'toolbar=0,scrollbars=1,location=0,statusbar=1,
     menubar=0,resizable=1,width=1000,height=800,left = 0,top = 0');
   }
</SCRIPT>
--%>
</head>
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<div style="margin-left: 10px; margin-right: 10px">
<h:form id="mainForm" enctype="multipart/form-data">

<sakai:view_content>
<sakai:messages />

<h:outputText rendered="#{not Mailtool.allowedToSend}" escape="false" value="#{msgs.no_mail_permission}<br/>" />
<h:outputText rendered="#{Mailtool.allowedToSend}" escape="false" value="#{msgs.send_mail_to}<br/>" />
<br>
					<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
						<f:selectItems value="#{Mailtool.viewChoiceDropdown}" />
					</h:selectOneListbox>

					<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole and Mailtool.allowedToSend}">
						<jsp:include page="selectByRole.jsp" />
					</f:subview>

					<f:subview id="selectByUser" rendered="#{Mailtool.selectByUser and Mailtool.allowedToSend}">
						<jsp:include page="selectByUser.jsp" />
					</f:subview>

					<f:subview id="selectByTree" rendered="#{Mailtool.selectByTree and Mailtool.allowedToSend}">
						<jsp:include page="selectByTree.jsp" />
					</f:subview>

					<f:subview id="selectSideBySide" rendered="#{Mailtool.selectSideBySide and Mailtool.allowedToSend}">
						<jsp:include page="selectSideBySide.jsp" />
					</f:subview>

					<f:subview id="selectByFoothill" rendered="#{Mailtool.selectByFoothill and Mailtool.allowedToSend}">
						<jsp:include page="selectByFoothill.jsp" />
					</f:subview><P>

					<h:outputText value="#{msgs.message_subject}" rendered="#{Mailtool.allowedToSend}" />
					<h:inputText value="#{Mailtool.messageSubject}"
						style="WIDTH: 300px"
						rendered="#{Mailtool.allowedToSend}" />
					<BR/>
						
<%--
<h:outputLink  target="_blank" onmouseup="javascript:popUp('/mailtool/upload.jsp');"><f:verbatim>Upload</f:verbatim></h:outputLink>
--%>
<%--
<br/>
<sakai:script path="multiple-file-attachment.js" contextBase="/mailtool/multifiles/" />
<span id="content"></span>
<p id="more" class="add" onclick="add();">Attach a file</p>
---%>
<br/>
<h:panelGroup>
<h:graphicImage id="paperclip" alt="attachment_img" url="/htmlarea3/images/paperclip.gif"></h:graphicImage>
<h:commandLink id ="processUpload" action="#{Mailtool.processUpload}"><f:verbatim>Attach a file</f:verbatim></h:commandLink>
</h:panelGroup>	
<br/>
<h:panelGroup><h:outputText value="#{uploadBean.files[0]} " /></h:panelGroup><br/>
<h:panelGroup><h:outputText value="#{uploadBean.files[1]} " /></h:panelGroup><br/>
<h:panelGroup><h:outputText value="#{uploadBean.files[2]} " /></h:panelGroup><br/>

<%--
<h:commandLink id="dummy" action="dummy" onmousedown="javascript: window.open('/mailtool/multifiles/multiple-file-attachment.html','mywin',
'left=20,top=20,width=500,height=500,toolbar=0,resizable=0'); return false;"><f:verbatim>popup</f:verbatim></h:commandLink>
--%>

					
					<BR/>
					<h:outputText value="#{msgs.message_body}" rendered="#{Mailtool.allowedToSend}" />
<h:panelGroup rendered="#{Mailtool.plainEditor}">
					<h:inputTextarea
						style="WIDTH: 520px"
						rows="10"
						value="#{Mailtool.messageBody}" rendered="#{Mailtool.allowedToSend}" />
</h:panelGroup>
<h:panelGroup rendered="#{Mailtool.FCKeditor}">
					<sakai:script path="fckeditor.js" contextBase="/mailtool/FCKeditor/" />

					<h:inputTextarea id="FCKeditor1"
						style="WIDTH: 520px"
						rows="30"
						value="#{Mailtool.messageBody}"
						rendered="#{Mailtool.allowedToSend}" />

					<sakai:script path="call_fckeditor.js" contextBase="/mailtool/" />
</h:panelGroup>					 

<h:panelGroup rendered="#{Mailtool.HTMLArea}">
<%---					<sakai:inputRichText
					 cols="100"
					 rows="30"				
					 buttonList="fontname,space,fontsize,space,formatblock,space,bold,italic,underline,strikethrough,subscript,superscript,linebreak,copy,cut,paste,space,justifyleft,justifycenter,justifyright,justifyfull,orderedlist,unorderedlist,outdent,indent,forecolor,hilitecolor,inserthorizontalrule,createlink,insertimage,inserttable,htmlmode,about"
					 value="#{Mailtool.messageBody}" rendered="#{Mailtool.allowedToSend}" />
---%>				 
					<sakai:script path="call_htmlarea-header.js" contextBase="/mailtool/" />
					<h:inputTextarea id="HTMLArea1"
						style="WIDTH: 520px"					
						rows="30"
						value="#{Mailtool.messageBody}"
						rendered="#{Mailtool.allowedToSend}" />	
					<sakai:script path="htmlarea.js" contextBase="/mailtool/htmlarea3/htmlarea/" />
					<sakai:script path="dialog.js" contextBase="/mailtool/htmlarea3/htmlarea/" />
					<sakai:script path="popupwin.js" contextBase="/mailtool/htmlarea3/htmlarea/" />
					<sakai:script path="en.js" contextBase="/mailtool/htmlarea3/htmlarea/lang/" />
					<sakai:script path="sakai-htmlarea.js" contextBase="/mailtool/htmlarea3/" />
					<sakai:script path="call_htmlarea.js" contextBase="/mailtool/" />

</h:panelGroup>

						<BR>
					<h:selectBooleanCheckbox rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{Mailtool.archiveMessage}" />
					<h:outputText rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{msgs.append_to_email_archive}" />
					<BR>
					<BR>

				<h:commandLink action="#{Mailtool.sendemailwithattachment}" value="Send mail with attachment(s)">
						<f:param name="file1" value="#{uploadBean.files[0]}" />
						<f:param name="file2" value="#{uploadBean.files[1]}"/>
						<f:param name="file3" value="#{uploadBean.files[2]}"/>	
				</h:commandLink>
<br/>
					<sakai:button_bar>
<%--						<sakai:button_bar_item action="#{Mailtool.processSendEmail}" value="#{msgs.send_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
--%>
				
						<sakai:button_bar_item action="#{Mailtool.processSendEmail}" value="#{msgs.send_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
						<sakai:button_bar_item action="#{Mailtool.processCancelEmail}" value="#{msgs.cancel_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
					</sakai:button_bar>

		<h:commandLink action="main_onepage">
			<f:verbatim>One page version</f:verbatim>
		</h:commandLink>  
		
				</sakai:view_content>
    
</h:form>
</div>	

</body>
</html>
</f:view>