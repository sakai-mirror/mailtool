<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<f:view>
	<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />
<sakai:view_container title="Configuration">

<h:form>
<sakai:view_content>
		<sakai:messages />
		
<h:panelGroup rendered="#{not Mailtool.allowedToSend}" >
<f:verbatim><br/></f:verbatim>
<h:outputText escape="false" value="Recipient View<br/>" />
</h:panelGroup>
<h:panelGroup rendered="#{Mailtool.allowedToSend}">
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
<%--
<div style="margin-left: 10px; margin-right: 10px">

<h:dataTable value="#{Mailtool.configurations}" var="cfg" cellspacing="2" border="1" >
<h:column><f:facet name="header"><h:outputText value="ID"/></f:facet><h:outputText value="#{cfg.id} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Realmid"/></f:facet><h:outputText value="#{cfg.realmid} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Singular"/></f:facet><h:outputText value="#{cfg.singular} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Plural"/></f:facet><h:outputText value="#{cfg.plural} "/></h:column>
</h:dataTable>
</div>
--%>
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