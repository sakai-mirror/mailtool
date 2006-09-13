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
</head>
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<div style="margin-left: 10px; margin-right: 10px">
<h:form id="mainForm" enctype="multipart/form-data">

<sakai:view_content>
<sakai:messages />
<h:panelGroup rendered="#{not Mailtool.allowedToSend}" >
<f:verbatim><br/></f:verbatim>
<h:outputText escape="false" value="#{msgs.no_mail_permission}<br/>" />
</h:panelGroup>
<h:panelGroup rendered="#{Mailtool.allowedToSend}" >
<f:verbatim><br/></f:verbatim>
<h:outputText escape="false" value="#{msgs.send_mail_to} " />
					<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
						<f:selectItems value="#{Mailtool.viewChoiceDropdown}" />
					</h:selectOneListbox>

					<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}">
						<jsp:include page="selectByRole.jsp" />
					</f:subview>

					<f:subview id="selectByUser" rendered="#{Mailtool.selectByUser}">
						<jsp:include page="selectByUser.jsp" />
					</f:subview>

					<f:subview id="selectByTree" rendered="#{Mailtool.selectByTree}">
						<jsp:include page="selectByTree.jsp" />
					</f:subview>

					<f:subview id="selectSideBySide" rendered="#{Mailtool.selectSideBySide}">
						<jsp:include page="selectSideBySide.jsp" />
					</f:subview>

					<f:subview id="selectByFoothill" rendered="#{Mailtool.selectByFoothill}">
						<jsp:include page="selectByFoothill.jsp" />
					</f:subview>
</h:panelGroup>
<BR/>
<h:panelGroup  rendered="#{Mailtool.allowedToSend}" >
<h:outputText value="#{msgs.message_subject} "/>
<h:inputText value="#{Mailtool.messageSubject}"	size="50"/>
</h:panelGroup>
<BR/>

<h:panelGrid  rendered="#{Mailtool.attachClicked}" columns="7" border="0" cellspacing="5" cellpadding="0">
<h:graphicImage alt="blank" url="/images/blank.gif" width="18" height="15"></h:graphicImage>
<h:graphicImage alt="attachment_img" url="/images/paperclip.gif"  width="15" height="15"></h:graphicImage>
<sakai:inputFileUpload size="50" valueChangeListener="#{Mailtool.processFileUpload}" />
<h:commandButton value="Attach" type="submit"  action="#{Mailtool.toggle_attachClicked}" />
<h:outputText value=" (max: " /><h:outputText value="#{msgs.max_upload_size}" /><h:outputText value=" MB)" />
</h:panelGrid>

<h:dataTable value="#{Mailtool.allAttachments}" var="attachment">
<h:column><h:graphicImage alt="blank" url="/htmlarea3/images/blank.gif" width="26" height="15"></h:graphicImage></h:column>
<h:column><h:outputText style="font-weight:bold;" value="#{attachment.filename} "/></h:column>
<h:column><h:outputText value="(#{attachment.size/1000} KB)" /></h:column>
<h:column><h:commandLink action="#{Mailtool.processRemoveFile}"><h:outputText value="remove" />
<f:param name="id" value="#{attachment.filename}" />
</h:commandLink>
</h:column>
</h:dataTable>

<h:panelGroup><h:outputText value="#{msgs.message_body}" rendered="#{Mailtool.allowedToSend}" /></h:panelGroup>
<h:panelGroup rendered="#{Mailtool.plainTextEditor}">
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

						<BR/>
					<h:selectBooleanCheckbox rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{Mailtool.archiveMessage}" />
					<h:outputText rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{msgs.append_to_email_archive}" />
<br/>

					<sakai:button_bar>
						<sakai:button_bar_item action="#{Mailtool.processSendEmail}" value="#{msgs.send_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
						<sakai:button_bar_item action="#{Mailtool.processCancelEmail}" value="#{msgs.cancel_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
					</sakai:button_bar>
<%---
		<br/>
		<h:commandLink action="main_twopage">
			<f:verbatim>Two page version</f:verbatim>
		</h:commandLink>
---%>
				</sakai:view_content>

</h:form>
</div>

</body>
</html>
</f:view>