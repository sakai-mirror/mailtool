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

<STYLE TYPE="text/css" MEDIA=screen>
<!--
.mail-header { text-align:right; vertical-align: top; font-weight: bold }
.mail-inputs { text-align:left }
-->
</STYLE>
</head>

<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<h:form id="mainForm" enctype="multipart/form-data">

<h:panelGroup rendered="#{Mailtool.allowedToConfigure}">

<sakai:tool_bar>
        <sakai:tool_bar_item value="Options" action="#{Mailtool.processGoToOptions}" immediate="true" />
</sakai:tool_bar>
</h:panelGroup>

<sakai:messages />

<%--
<h:panelGroup rendered="#{not Mailtool.allowedToSend}">
	<f:verbatim><br/></f:verbatim>
	<h:outputText escape="false" value="#{msgs.no_mail_permission}<br/>" />
</h:panelGroup>

<h:panelGrid columns="2" rendered="#{Mailtool.allowedToSend}" cellspacing="0" cellpadding="3" columnClasses="mail-header, mail-inputs">
--%>
<h:panelGrid columns="2" cellspacing="0" cellpadding="3" columnClasses="mail-header, mail-inputs">
<f:facet name="header">
<f:verbatim>
<div style="text-align:left; font-weight: normal; font-variant: small-caps ">
<h2>Compose</h2>
</div>
</f:verbatim>
</f:facet>
		<h:panelGroup>
			<h:outputText rendered="#{Mailtool.replyToSelected != 'otheremail'}" value="From: "/>
			<h:outputText rendered="#{Mailtool.replyToSelected == 'otheremail'}" value="Reply-to: "/>
		</h:panelGroup>
		<h:panelGroup>
			<h:outputText rendered="#{Mailtool.replyToSelected == 'yes' }" value="#{Mailtool.currentUser.displayname} <#{Mailtool.currentUser.email}>"/>
			<h:outputText rendered="#{Mailtool.replyToSelected == 'otheremail' }" value="#{Mailtool.replyToOtherEmail}"/>
			<h:outputText rendered="#{Mailtool.replyToSelected == 'no' }" value="No Reply"/>
		</h:panelGroup>
<%--
		<h:outputText escape="false" value="View: "/>
		<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
			<f:selectItems value="#{Mailtool.viewChoiceDropdown}" />
		</h:selectOneListbox>
--%>
		<h:outputText escape="false" value="#{msgs.send_mail_to}: "/>
		<h:panelGroup>
<%--
			<h:panelGrid columns="2" >
					<h:selectBooleanCheckbox value="#{Mailtool.allUsersSelected }" />
					<h:outputText value="All Users" style="font-weight: bold" />
			</h:panelGrid>
--%>	
			<f:subview id="selectByUser" rendered="#{Mailtool.selectByUser}"><jsp:include page="selectByUser.jsp" /></f:subview>
			<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}"><jsp:include page="selectByRole.jsp" /></f:subview>
			<f:subview id="selectByTree" rendered="#{Mailtool.selectByTree}"><jsp:include page="selectByTree.jsp" /></f:subview>
			<f:subview id="selectSideBySide" rendered="#{Mailtool.selectSideBySide}"><jsp:include page="selectSideBySide.jsp" /></f:subview>
			<f:subview id="selectByFoothill" rendered="#{Mailtool.selectByFoothill}"><jsp:include page="selectByFoothill.jsp" /></f:subview>
		</h:panelGroup>

		<h:outputText value="" />
		<h:panelGroup>
		<h:outputText value="Other Recipient(s): " style="font-weight:bold" />
			<h:inputText value="#{Mailtool.otherEmails}" size="40" validator="#{Mailtool.validateEmail}" id="email"/>
			<f:verbatim><br/></f:verbatim>
			<h:outputText value="Separate additional email addresses with commas or semicolons."/>
		</h:panelGroup>

		<h:outputText value="#{msgs.message_subject}: "/>
		<h:panelGrid columns="2">
		<%--
				<h:outputText value="#{Mailtool.subjectPrefix}"/>
		--%>
				
		<h:inputText value="#{Mailtool.messageSubject}"	size="60"/>
		</h:panelGrid>

	<h:panelGroup rendered="#{not Mailtool.attachClicked and Mailtool.maxNumAttachment!=0}">
			<h:graphicImage alt="blank" url="/images/blank.gif" width="27" height="15"/>
			<h:graphicImage alt="attachment_img" url="/images/paperclip.gif"  width="15" height="15"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{not Mailtool.attachClicked and Mailtool.maxNumAttachment!=0}">
			<h:panelGroup rendered="#{Mailtool.num_files == 0}"><h:commandLink action="#{Mailtool.toggle_attachClicked}"><h:outputText value="Attach a file" /></h:commandLink></h:panelGroup>
			<h:panelGroup rendered="#{Mailtool.num_files != 0}"><h:commandLink action="#{Mailtool.toggle_attachClicked}"><h:outputText value="Attach another file" /></h:commandLink></h:panelGroup>
			<f:verbatim><br/></f:verbatim>
	</h:panelGroup>

	<h:panelGroup  rendered="#{Mailtool.attachClicked}">
			<h:graphicImage alt="attachment_img" url="/images/paperclip.gif"  width="15" height="15"/>
	</h:panelGroup>
	<h:panelGrid rendered="#{Mailtool.attachClicked}" columns="5" border="0" cellspacing="0" cellpadding="0">
			<sakai:inputFileUpload size="50" valueChangeListener="#{Mailtool.processFileUpload}" />
			<h:commandButton value="Attach" type="submit"  action="#{Mailtool.toggle_attachClicked}" />
			<h:outputText value=" (max: " />
			<h:outputText value="#{msgs.max_upload_size}" />
			<h:outputText value=" MB)" />
	</h:panelGrid>

	<h:graphicImage alt="blank" url="/images/blank.gif" width="0" height="0"/>
	<h:dataTable value="#{Mailtool.allAttachments}" var="attachment"  cellspacing="0" cellpadding="0">
			<h:column><h:outputText style="font-weight:bold;" value="#{attachment.filename} "/></h:column>
			<h:column><h:outputText value="#{attachment.size/1000}"><f:convertNumber pattern="(### KB)"/></h:outputText></h:column>
			<h:column><h:commandLink action="#{Mailtool.processRemoveFile}"><h:outputText value="remove" />
			<f:param name="id" value="#{attachment.filename}" />
			</h:commandLink>
			</h:column>
	</h:dataTable>

	<f:facet name="footer">
	<h:panelGroup>
	<h:outputText value="#{msgs.message_body}"/>
		<h:panelGroup>
					<h:panelGroup rendered="#{Mailtool.textFormat=='htmltext'}">
						<sakai:rich_text_area  rows="10" columns="70" value="#{Mailtool.messageBody}"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{Mailtool.textFormat=='plaintext'}">
						<h:inputTextarea rows="10" cols="70" value="#{Mailtool.messageBody}"/>
						<f:verbatim><br/></f:verbatim>
					</h:panelGroup>
		</h:panelGroup>
		<h:panelGroup>
			<f:verbatim><br/></f:verbatim>
			<h:selectBooleanCheckbox value="#{Mailtool.sendMeCopy}" />
			<h:outputText value="Send me a copy" />
			<f:verbatim><br/></f:verbatim>
		</h:panelGroup>
		<h:panelGroup>
			<h:selectBooleanCheckbox value="#{Mailtool.archiveMessage}" />
			<h:outputText value="Add to Email Archive, visible to all site participants" />
		</h:panelGroup>

		<h:panelGroup>
			<sakai:button_bar>
					<sakai:button_bar_item action="#{Mailtool.processSendEmail}" value="#{msgs.send_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
					<sakai:button_bar_item action="#{Mailtool.processCancelEmail}" value="#{msgs.cancel_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
			</sakai:button_bar>
		</h:panelGroup>
	</h:panelGroup>
	</f:facet>

</h:panelGrid>

</h:form>

</body>
</html>
</f:view>
