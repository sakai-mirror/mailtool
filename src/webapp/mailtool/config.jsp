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
<div style="margin-left: 10px; margin-right: 10px">

<h:dataTable value="#{Mailtool.configurations}" var="cfg" cellspacing="2" border="1" >
<h:column><f:facet name="header"><h:outputText value="ID"/></f:facet><h:outputText value="#{cfg.id} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Realmid"/></f:facet><h:outputText value="#{cfg.realmid} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Singular"/></f:facet><h:outputText value="#{cfg.singular} "/></h:column>
<h:column><f:facet name="header"><h:outputText value="Plural"/></f:facet><h:outputText value="#{cfg.plural} "/></h:column>
</h:dataTable>
</div>
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