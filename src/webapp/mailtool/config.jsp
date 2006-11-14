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
<title>Options</title>
<%= request.getAttribute("sakai.html.head.css.base") %>
<%= request.getAttribute("sakai.html.head.css.skin") %>
<%= request.getAttribute("sakai.html.head.js") %>

<STYLE TYPE="text/css" MEDIA="screen">
<!--
.mail-header { vertical-align: top; font-weight: bold }
.mail-inputs { text-align:left}
.gray-out {color: #777 }
-->
</STYLE>
</head>

<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<h:form id="optionsForm">

<sakai:tool_bar>
        <sakai:tool_bar_item value="Compose" action="#{Mailtool.processGoToCompose}" immediate="true" />
</sakai:tool_bar>

<sakai:messages />

<h:panelGrid rendered="#{Mailtool.allowedToConfigure}" columns="2" border="0" cellspacing="0" cellpadding="3" columnClasses="mail-header, mail-inputs">
<f:facet name="header">
<f:verbatim>
<div style="text-align:left; font-weight: normal; font-variant: small-caps ">
<h2>Options</h2>
<font color="blue">You are currently choosing options for Email.<br/>
Settings chosen on this page will become the default settings for this site.</font>
</div>
</f:verbatim>
</f:facet>

<h:outputText value="Choose selection view" />
<h:outputText value="" />

<h:panelGroup>
	<h:graphicImage alt="blank" url="/images/blank.gif" width="65" height="15"/>
	<h:outputText value="Current view:" />
</h:panelGroup>
<h:panelGroup>
		<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
				<f:selectItems value="#{Mailtool.viewChoiceDropdown}" />
		</h:selectOneListbox>
		<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}"><jsp:include page="selectByRole.jsp" /></f:subview>
		<f:subview id="selectByUser" rendered="#{Mailtool.selectByUser}"><jsp:include page="selectByUser.jsp" /></f:subview>
		<f:subview id="selectByTree" rendered="#{Mailtool.selectByTree}"><jsp:include page="selectByTree.jsp" /></f:subview>
		<f:subview id="selectSideBySide" rendered="#{Mailtool.selectSideBySide}"><jsp:include page="selectSideBySide.jsp" /></f:subview>
		<f:subview id="selectByFoothill" rendered="#{Mailtool.selectByFoothill}"><jsp:include page="selectByFoothill.jsp" /></f:subview>
</h:panelGroup>

<h:outputText value="Copies:" />
<h:panelGroup>
<%--
        <h:selectBooleanCheckbox  required="false" immediate="true" onclick="submit(); return false;" value="#{Mailtool.sendMeCopyInOptions}" />
        <h:outputText value="Show 'Send me a copy'" />

                <h:panelGroup rendered="#{Mailtool.sendMeCopyInOptions}" >
--%>
                <h:panelGroup>
                        <h:selectBooleanCheckbox value="#{Mailtool.sendMeCopy}"/>
                        <h:outputText value="Send me a copy" />
                </h:panelGroup>
<%--                
        <h:selectBooleanCheckbox value="#{Mailtool.archiveMessageInOptions}"  required="false" immediate="true" onclick="submit(); return false;" />
        <h:outputText value="Show 'Add to Email Archive' (Visible only if Email Archive is added)"/>
                <h:panelGroup rendered="#{Mailtool.archiveMessageInOptions}" >
--%>

                <h:panelGroup>
                        <f:verbatim><br/></f:verbatim>
                        <h:selectBooleanCheckbox value="#{Mailtool.archiveMessage}"/>
                        <h:outputText value="Add to Email Archive, visible to all site participants" />
                </h:panelGroup>
</h:panelGroup>
<h:panelGroup>
	<h:outputText value="Reply-to:" />
	<f:verbatim><br/></f:verbatim>
</h:panelGroup>
<h:panelGroup>
<h:selectOneRadio layout="pageDirection" value="#{Mailtool.replyToSelected }" onclick="submit()">
<f:selectItem itemLabel="Sender" itemValue="yes"/>
<f:selectItem itemLabel="Do not allow reply" itemValue="no"/> 
</h:selectOneRadio>
<%--
<f:selectItem itemLabel="Reply to other" itemValue="otheremail"/>

</h:selectOneRadio>
<h:panelGroup rendered="#{Mailtool.replyToSelected == 'otheremail'}">
		<h:outputText value="Reply-to email: " />
		<h:inputText value="#{Mailtool.replyToOtherEmail }" size="20"  validator="#{Mailtool.validateEmail}" id="replyemail"/>
</h:panelGroup>
--%>
</h:panelGroup>

<h:panelGroup>
	<h:outputText value="Message format:" />
	<f:verbatim><br/></f:verbatim>
</h:panelGroup>
<h:panelGroup>
	<h:selectOneRadio layout="pageDirection" value="#{Mailtool.textFormat}" onclick="submit()">
		<f:selectItem itemLabel="HTML formatting" itemValue="htmltext"/>
		<f:selectItem itemLabel="Plain text" itemValue="plaintext"/>
	</h:selectOneRadio>
	
</h:panelGroup>

<h:panelGroup rendered="#{Mailtool.showRenamingRoles }">
<h:panelGroup>
	<f:verbatim><br/></f:verbatim>
	<h:outputText value="Rename roles (Max: 15)" />
</h:panelGroup>

</h:panelGroup>
<f:facet name="footer">
<h:panelGroup rendered="#{Mailtool.showRenamingRoles }">
	<f:verbatim>
	<div style="font-weight: normal">
	<font color="blue">Choose names that will appear in the "Roles" listing on the "To" menu. See example above.</font>
	</div>
	</f:verbatim>
	<h:dataTable value="#{Mailtool.renamedRoles}" var="role"  cellspacing="0" cellpadding="3">
			<h:column>
				<h:outputText value="#{role.roleId}: " style="font-weight:bold; font-style: italic"/>
				<h:outputText value="show this role as "/>
			</h:column>
			<h:column>
				<h:inputText size="20" value="#{role.singularNew}" />
				<f:verbatim><br/></f:verbatim>
				<h:outputText value="e.g. #{role.singular }" />
			</h:column>
			<h:column>
				<h:inputText size="20" value="#{role.pluralNew}" />
				<f:verbatim><br/></f:verbatim>
				<h:outputText value="e.g. #{role.plural }" />
			</h:column>
	</h:dataTable>
</h:panelGroup>
</f:facet>


</h:panelGrid>

<sakai:button_bar>
	<sakai:button_bar_item
		action="#{Mailtool.processUpdateOptions}"
		value="Update Defaults"
		rendered="true"
		immediate="false" />
	<sakai:button_bar_item
		action="#{Mailtool.processGoToCompose}"
		value="Cancel"
		rendered="true"
		immediate="false" />
</sakai:button_bar>

</h:form>

</body>
</html>
</f:view>