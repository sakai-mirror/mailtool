<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<f:view>
<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs"/>
<sakai:view_container title="#{msgs.mailtool_title}">

	<sakai:title_bar value="#{msgs.mailtool_title}"/>

</sakai:view_container>
</f:view>
