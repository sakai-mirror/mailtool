<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<STYLE TYPE="text/css" MEDIA=screen>
<!--
.mail-header { vertical-align: top; font-weight: bold }
.mail-inputs { text-align:left}
-->
</STYLE>

<f:view>
	<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />
<sakai:view_container title="Configuration">

<h:form>
<sakai:view_content>

<sakai:tool_bar>
        <sakai:tool_bar_item value="Compose" action="compose" immediate="true" />
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

<h:outputText value="Show other options" />
<h:panelGroup>
        <h:selectBooleanCheckbox  required="false" immediate="true" onclick="submit(); return false;" value="#{Mailtool.sendMeCopyInOptions}" />
        <h:outputText value="Send me a copy" />
                <h:panelGroup rendered="#{Mailtool.sendMeCopyInOptions}" >
                        <f:verbatim><br/></f:verbatim>
                        <h:selectBooleanCheckbox value="#{Mailtool.sendMeCopy}"/>
                        <h:outputText value="If checked, default is (Send me a copy)" />
                </h:panelGroup>
                <f:verbatim><br/></f:verbatim>
        <h:selectBooleanCheckbox value="#{Mailtool.archiveMessageInOptions}"  required="false" immediate="true" onclick="submit(); return false;" />
        <h:outputText value="#{msgs.append_to_email_archive} (Visible only if Email Archive is added)"/>
                <h:panelGroup rendered="#{Mailtool.archiveMessageInOptions}" >
                        <f:verbatim><br/></f:verbatim>
                        <h:selectBooleanCheckbox value="#{Mailtool.archiveMessage}"/>
                        <h:outputText value="If checked, default is (Append to Archive)" />
                </h:panelGroup>
</h:panelGroup>

<h:outputText value="Choose selection view" />
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

<h:outputText value="Reply-to " />
<h:panelGroup>
	<h:selectBooleanCheckbox value="#{Mailtool.replyToSender}" onclick="submit()" required="false" immediate="true" valueChangeListener="#{Mailtool.processReplyToSender}"/>
	<h:outputText value="Sender" />
	
	<f:verbatim><br/></f:verbatim>
	<h:selectBooleanCheckbox value="#{Mailtool.replyToOther}" onclick="submit()" required="false"  immediate="true"  valueChangeListener="#{Mailtool.processReplyToOther}"/>
	<h:outputText value="Other " />

	<h:panelGroup rendered="#{Mailtool.replyToOther}">
		<f:verbatim><br/></f:verbatim>	
		<h:outputText value="Reply-to: " />
		<h:inputText value="#{Mailtool.replyToOtherEmail }" size="20" />
	</h:panelGroup>

	<f:verbatim><br/></f:verbatim>	
	<h:selectBooleanCheckbox value="#{Mailtool.doNotReply}" onclick="submit()" required="false"  immediate="true"  valueChangeListener="#{Mailtool.processDoNotReply}"/>
	<h:outputText value="Do not reply" />
</h:panelGroup>

<%--
<h:outputText value="Subject Prefix"/>
<h:panelGrid columns="2" cellpadding="3" cellspacing="0">
	<h:inputText value="" size="25"/>
	<f:verbatim><br/>Default: site/course title</f:verbatim>
	<h:outputText value="#{Mailtool.subjectPrefix}"/>
	<h:commandLink action="#{Mailtool.processUpdateSubject}"><h:outputText value="update" /></h:commandLink>
</h:panelGrid>
--%>

<h:outputText value="Message format " />
<h:panelGroup>
	<h:selectBooleanCheckbox value="#{Mailtool.richTextFormat}" onclick="submit()" />
	<h:outputText value="Enhanced Formatting"/>
	<h:panelGroup rendered="#{Mailtool.richTextFormat}">
		<f:verbatim><br/></f:verbatim>
		<h:outputText value="Message will be formatted in text/html" />
	</h:panelGroup>
	<h:panelGroup rendered="#{not Mailtool.richTextFormat}">
		<f:verbatim><br/></f:verbatim>
		<h:outputText value="Message will be formatted in text/plain" />
	</h:panelGroup>
</h:panelGroup>

<h:outputText value="Subject Prefix " />
<h:panelGroup>
<h:inputText size="50" value="#{Mailtool.subjectPrefix}"/>
<f:verbatim><br/></f:verbatim>
<h:outputText value="(Current: #{Mailtool.subjectPrefixFromConfig})" />
</h:panelGroup>

<h:panelGroup>
	<f:verbatim><br/></f:verbatim>
	<h:outputText value="Rename roles (Max: 15)" />
</h:panelGroup>
<h:panelGroup>
</h:panelGroup>
<f:facet name="footer">
<h:panelGroup>
	<f:verbatim>
	<div style="font-weight: normal; font-variant: small-caps">
	<font color="blue">Choose names that will appear in the "Roles" listing on the "To" menu. See example above.</font>
	</div>
	</f:verbatim>
	<h:dataTable value="#{Mailtool.renamedRoles}" var="role"  cellspacing="0" cellpadding="3">
			<h:column>
<%--
				<f:facet name="header">
				<h:outputLabel value="Role ID"/>
				</f:facet>
--%>				
				<h:outputText value="#{role.roleId}: " style="font-weight:bold; font-style: italic"/>
				<h:outputText value="show this role as "/>
			</h:column>
			<h:column>
<%--
				<f:facet name="header">
				<h:outputLabel value="Singular"/>
				</f:facet>		
--%>				
				<h:inputText size="20" value="#{role.singularNew}" />
				<f:verbatim><br/></f:verbatim>
				<h:outputText value="e.g. #{role.singular }" />
			</h:column>
			<h:column>
<%--
				<f:facet name="header">
				<h:outputLabel value="Plural"/>
				</f:facet>
--%>
				<h:inputText size="20" value="#{role.pluralNew}" />
				<f:verbatim><br/></f:verbatim>
				<h:outputText value="e.g. #{role.plural }" />
			</h:column>
	</h:dataTable>
</h:panelGroup>
</f:facet>

<%--
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
--%>

</h:panelGrid>

<sakai:button_bar>
	<sakai:button_bar_item
		action="#{Mailtool.processSeeChanges}"
		value="See changes"
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