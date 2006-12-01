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

<STYLE TYPE="text/css" MEDIA="screen">
<!--
.mail-header { vertical-align: top; font-weight: bold }
.mail-inputs { text-align:left}
-->
</STYLE>
</head>

<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">

<h:form>
<sakai:messages />
		<sakai:button_bar>
			<sakai:button_bar_item
				action="main_onepage"
				value="#{msgs.ok_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>	
	
<h:outputText escape="false" value="#{Mailtool.results}" />
<h:panelGroup rendered="#{Mailtool.sendMeCopy}">
<f:verbatim><br/></f:verbatim>
<h:outputText escape="false" value="(Copy sent to the sender)" />
<f:verbatim><br/></f:verbatim>
</h:panelGroup>

</h:form>

</body>
</html>
</f:view>