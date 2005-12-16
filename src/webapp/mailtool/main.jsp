<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<%@ taglib uri="http://sakaiproject.org/jsf/mailtool" prefix="mailtool" %>

<f:view>
<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs"/>
<%--
	<f:verbatim><!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
</f:verbatim>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mailtool</title>
	<%= request.getAttribute("sakai.html.head.css.base") %>
	<%= request.getAttribute("sakai.html.head.css.skin") %>
	<%= request.getAttribute("sakai.html.head.js") %>
	 <script type="text/javascript" language="JavaScript" src="/mailtool/mailtool.js"></script>
<h:outputText escape="false" value="#{Mailtool.initJavascript}"/> 
</head>
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">
--%>

<sakai:view_container title="Send Email">
<h:form>
	<sakai:view_content>
		<sakai:messages />

<%--
	<h:commandButton value="Test JavaScript" onclick="testMail();" />	
--%>
<%--	<mailtool:selector value="#{Mailtool.recipientSelector.listbox}"/> --%>
	
	<h:outputText rendered="#{not Mailtool.allowedToSend}" escape="false" value="#{msgs.no_mail_permission}<br/>" />
	
	<h:outputText rendered="#{Mailtool.selectByRole and Mailtool.allowedToSend}" escape="false" value="#{msgs.send_mail_to}<br/>" />

	<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
	  		<f:selectItems value="#{Mailtool.viewChoiceDropdown}"/>
	</h:selectOneListbox>

	<%-- List these Methodically with boolean rendered properties --%>
	<%-- <f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}"> --%>

	<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole and Mailtool.allowedToSend}">
		<jsp:include page="selectByRole.jsp"/>
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
	</f:subview>
	
	<h:selectBooleanCheckbox rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{Mailtool.archiveMessage}" />
	<h:outputText rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{msgs.append_to_email_archive}" /><br/><br/>

	<h:outputText value="#{msgs.message_subject}" rendered="#{Mailtool.allowedToSend}" /><br/>
	<h:inputText value="#{Mailtool.messageSubject}" rendered="#{Mailtool.allowedToSend}"/><br/><br/>
	
	<h:outputText value="#{msgs.message_body}" rendered="#{Mailtool.allowedToSend}"/><br/>
	<h:inputTextarea rows="10" cols="40" value="#{Mailtool.messageBody}" rendered="#{Mailtool.allowedToSend}"/><br/>
	
<sakai:button_bar>

	<sakai:button_bar_item
		action="#{Mailtool.processSendEmail}"
		value="#{msgs.send_mail_button}"
		rendered="#{Mailtool.allowedToSend}"
		immediate="false" />
		
	<sakai:button_bar_item
		action="#{Mailtool.processCancelEmail}"
		value="#{msgs.cancel_mail_button}"
		rendered="#{Mailtool.allowedToSend}"
		immediate="false" />
		

</sakai:button_bar>	
	
	</sakai:view_content>
</h:form>

</sakai:view_container>

</f:view>