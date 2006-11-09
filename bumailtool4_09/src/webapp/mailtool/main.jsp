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

<%--
 <script type="text/javascript" language="JavaScript" src="/mailtool/mailtool.js"></script>
			<script type="text/javascript" src="/mailtool/stateswitcher.js"> </script>
<h:outputText escape="false" value="#{Mailtool.initJavascript}"/> 
<script type="text/javascript" src="/mailtool/FCKeditor/fckeditor.js">


<sakai:script path="fckeditor.js" contextBase="/mailtool/FCKeditor/" />
<script type="text/javascript">
window.onload = function()
{
var oFCKeditor = new FCKeditor( 'mainForm:FCKeditor1' ) ;
oFCKeditor.BasePath	= '/mailtool/FCKeditor/' ;
oFCKeditor.Config["CustomConfigurationsPath"] = oFCKeditor.BasePath+ "config.js"  ;
oFCKeditor.ReplaceTextarea() ;
}
</script>
--%>
</head>
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<div style="margin-left: 10px; margin-right: 10px">
			<h:form id="mainForm">

				<sakai:view_content><%--
	<h:commandButton value="Test JavaScript" onclick="testMail();" />	
--%><%--	<mailtool:selector value="#{Mailtool.recipientSelector.listbox}"/> --%><%-- List these Methodically with boolean rendered properties --%><%-- <f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}"> --%>
					<sakai:messages />

					<h:outputText rendered="#{not Mailtool.allowedToSend}" escape="false" value="#{msgs.no_mail_permission}<br/>" />
<h:outputText rendered="#{Mailtool.allowedToSend}" escape="false" value="#{msgs.send_mail_to}<br/>" />
<%-- -					<h:outputText rendered="#{Mailtool.selectByRole and Mailtool.allowedToSend}" escape="false" value="#{msgs.send_mail_to}<br/>" />
---%>
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
						style="WIDTH: 540px"
						rows="10"
						value="#{Mailtool.messageBody}" rendered="#{Mailtool.allowedToSend}" />
</h:panelGroup>
<h:panelGroup rendered="#{Mailtool.FCKeditor}">
					<sakai:script path="fckeditor.js" contextBase="/mailtool/FCKeditor/" />

					<h:inputTextarea id="FCKeditor1"
						style="WIDTH: 540px"
						rows="30"
						value="#{Mailtool.messageBody}"
						rendered="#{Mailtool.allowedToSend}" />

					<sakai:script path="call_fckeditor.js" contextBase="/mailtool/" />
</h:panelGroup>					 

<h:panelGroup rendered="#{Mailtool.HTMLArea}">
					<sakai:inputRichText
					 cols="100"
					 rows="30"				
					 buttonList="fontname,space,fontsize,space,formatblock,space,bold,italic,underline,strikethrough,subscript,superscript,linebreak,copy,cut,paste,space,justifyleft,justifycenter,justifyright,justifyfull,orderedlist,unorderedlist,outdent,indent,forecolor,hilitecolor,inserthorizontalrule,createlink,insertimage,inserttable,htmlmode,about"
					 value="#{Mailtool.messageBody}" rendered="#{Mailtool.allowedToSend}" />
<%--- sakai_createlink, ---%>
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
