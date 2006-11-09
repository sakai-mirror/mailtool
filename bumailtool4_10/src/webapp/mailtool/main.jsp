<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai"%>

<%@ taglib uri="http://sakaiproject.org/jsf/mailtool" prefix="mailtool"%>

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
			<h:form id="mainForm">

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
					<BR>
					<BR>
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

					<sakai:button_bar>
						<sakai:button_bar_item action="#{Mailtool.processSendEmail}" value="#{msgs.send_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
						<sakai:button_bar_item action="#{Mailtool.processCancelEmail}" value="#{msgs.cancel_mail_button}" rendered="#{Mailtool.allowedToSend}" immediate="false" />
					</sakai:button_bar>

				</sakai:view_content>
				<%--
							<sakai:button_bar>
								<sakai:button_bar_item
									action="#{MailTool.processAddAttachRedirect}" 
									value="#{msgs.add_attach}"/>
							</sakai:button_bar>
				--%>
			</h:form>
		</div>	
</f:view>
</body>
</html>
