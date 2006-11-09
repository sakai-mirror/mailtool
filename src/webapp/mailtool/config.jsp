<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<STYLE TYPE="text/css" MEDIA=screen>
<!--
.mail-header { text-align:right; vertical-align: top }
.mail-inputs { text-align:left}
-->
</STYLE>

<f:view>
	<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />
<sakai:view_container title="Configuration">

<h:form>
<sakai:view_content>
<sakai:messages />

<f:verbatim><br/></f:verbatim>

<h:panelGrid columns="2" rendered="#{Mailtool.allowedToSend}"  border="1" cellspacing="0" cellpadding="3" columnClasses="mail-header, mail-inputs">
<f:facet name="header">
<f:verbatim>
<div style="text-align:left">
<h2>Options</h2>
<font color=red>(Under construction)</font><br/>
You are currently choosing options for Email.<br/>
Settings chosen on this page will become the default settings for this site.
</div>
</f:verbatim>
</f:facet>

<h:outputText value="Defaults" />
<h:panelGroup>
		<h:selectBooleanCheckbox rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{Mailtool.archiveMessage}" />
		<h:outputText rendered="#{Mailtool.emailArchived and Mailtool.allowedToSend}" value="#{msgs.append_to_email_archive}" />
		<f:verbatim><br/></f:verbatim>
		<h:selectBooleanCheckbox rendered="#{Mailtool.allowedToSend}" value="#{Mailtool.sendMeCopy}" />
		<h:outputText rendered="#{Mailtool.allowedToSend}" value="Send me a copy" />
</h:panelGroup>

<h:outputText value="Recipient view" />
<h:panelGroup>
<h:outputText value="View: " />
<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Mailtool.viewChoice}">
		<f:selectItems value="#{Mailtool.viewChoiceDropdown}" />
		</h:selectOneListbox>


		<f:subview id="selectByRole" rendered="#{Mailtool.selectByRole}"><jsp:include page="selectByRole.jsp" /></f:subview>
		<f:subview id="selectByUser" rendered="#{Mailtool.selectByUser}"><jsp:include page="selectByUser.jsp" /></f:subview>
		<f:subview id="selectByTree" rendered="#{Mailtool.selectByTree}"><jsp:include page="selectByTree.jsp" /></f:subview>
		<f:subview id="selectSideBySide" rendered="#{Mailtool.selectSideBySide}"><jsp:include page="selectSideBySide.jsp" /></f:subview>
		<f:subview id="selectByFoothill" rendered="#{Mailtool.selectByFoothill}"><jsp:include page="selectByFoothill.jsp" /></f:subview>
</h:panelGroup>

<h:outputText value="From " />
<h:panelGroup>
<%--
<h:selectOneRadio value="">
<f:selectItems> value=""/>
</h:selectOneRadio>
--%>
<f:verbatim>
<input type="radio" name="fromline">hidden </input>
</f:verbatim><f:verbatim>
<input type="radio" name="fromline">display</input>
</f:verbatim>
<h:outputText value=" (#{Mailtool.currentUser.email}) "/>
</h:panelGroup>

<h:outputText value="Reply-to " />
<h:panelGroup>
<%--
<h:selectOneRadio value="">
<f:selectItems> value=""/>
</h:selectOneRadio>
--%>
<f:verbatim>
<input type="radio" name="replyto">no-reply </input>
</f:verbatim>
<f:verbatim>
<input type="radio" name="replyto">current user</input>
</f:verbatim>
<h:outputText value=" (#{Mailtool.currentUser.email}) "/>
<f:verbatim>
<input type="radio" name="replyto">other </input>
</f:verbatim>
<h:inputText value="" size="25"/>
</h:panelGroup>

<h:outputText value="Subject Prefix"/>
<h:panelGrid columns="2" cellpadding="3" cellspacing="0">
<h:inputText value="" size="25"/>
<f:verbatim><br/>Default: site/course title</f:verbatim>
</h:panelGrid>

<h:outputText value="Message format " />
<h:panelGroup>
<%--
<h:selectOneRadio value="">
<f:selectItems> value=""/>
</h:selectOneRadio>
--%>
<f:verbatim>
<input type="radio" name="msgformat">text/plain</input>
<input type="radio" name="msgformat">text/html</input>
</f:verbatim>
</h:panelGroup>

<h:panelGroup>
<h:outputText value="Rename roles " />
<f:verbatim><br/>(up to 15)</f:verbatim>
</h:panelGroup>
<h:panelGroup>
<h:panelGrid columns="4" cellspacing="0" cellpadding="3">
	<h:outputText value="Role ID" />
	<h:outputText value="Singular" />
	<h:outputText value="Plural" />
	<h:outputText value="" />
	<h:inputText size="20" value="#{Mailtool.roleID}" />
	<h:inputText size="20" value="#{Mailtool.singular}" />
	<h:inputText size="20" value="#{Mailtool.plural}" />
	<h:commandButton value="Update" action="#{Mailtool.processRenameRole}" />
</h:panelGrid>

<h:outputText value="Curent Roles " />
<h:dataTable value="#{Mailtool.renamedRoles}" var="role"  cellspacing="0" cellpadding="3">
		<h:column><h:outputText value="#{role.roleId} "/></h:column>
		<h:column><h:outputText value="#{role.singular}"/></h:column>
		<h:column><h:outputText value="#{role.plural}"/></h:column>
		<h:column><h:commandLink action="#{Mailtool.processRemoveRole}"><h:outputText value="remove" />
		<f:param name="rid" value="#{role.roleId}" />
		</h:commandLink>
		</h:column>
</h:dataTable>
</h:panelGroup>

</h:panelGrid>

<sakai:button_bar>
	<sakai:button_bar_item
		action="save"
		value="Save"
		rendered="true"
		immediate="false" />
	<sakai:button_bar_item
		action="cancel"
		value="Cancel"
		rendered="true"
		immediate="false" />
</sakai:button_bar>

</sakai:view_content>
</h:form>
</sakai:view_container>

</f:view>